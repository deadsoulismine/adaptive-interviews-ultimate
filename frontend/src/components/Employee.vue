<template>
  <div class="container">
    <a-row :gutter="16">
      <a-col :span="8">
        <a-card :bordered=false :title="employee.firstName + '  ' + employee.lastName">
          <p>Отдел: {{ employee.department.name }}</p>
          <p>Начальник отдела: {{ employee.department.supervisor }}</p>
          <p>Должность: {{ employee.position }}</p>
          <p>Статус: {{ employee.status }}</p>
          <p>
            <router-link v-if="this.$store.getters.isAdmin" :to="{ name: 'EditEmployee', params: { id: employee.id }}"
                         tag="a-button">
              <a-icon type="highlight"/>
              Редактировать
            </router-link>
          </p>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card :bordered=false title="">
          <a-timeline>
            <a-timeline-item color="green">Принят на работу: {{ formatDate(employee.employmentDate) }}</a-timeline-item>
            <div v-if="interviews.length > 0" class="container">
              <a-timeline-item v-for="interview in interviews" :key="interview.id" color="blue">
                {{ interview.name }} {{ formatDate(interview.date) }}
              </a-timeline-item>
              <a-timeline-item color="green">
                Окончание адаптации: {{ formatDate(employee.endOfAdaptation) }}
              </a-timeline-item>
            </div>
          </a-timeline>
        </a-card>
      </a-col>
    </a-row>

    <a-table v-if="interviews.length > 0" :dataSource="interviews" class="int-table" style="width: 100%">
      <template v-slot:title style="color: #1890ff">
          <span>
            Беседы
          </span>
      </template>
      <a-table-column key="date" dataIndex="date">
        <template v-slot:title>
          <span>
            Дата
          </span>
        </template>
      </a-table-column>
      <a-table-column key="name" dataIndex="name" title="Беседа"/>
      <a-table-column key="users" dataIndex="users" title="Пользователи">
        <template v-slot:users>
        <span>
          <a-tag v-for="user in users" :key="user.name" color="blue">
            {{ user.name }}
          </a-tag>
        </span>
        </template>
      </a-table-column>
      <a-table-column key="description" dataIndex="description" title="Отзыв" width="800px"/>
      <a-table-column key="action" title="Действие">
        <template v-slot:interview>
        <span>
          <router-link v-if="isAuthenticated()" :to="{ name: 'Interview', params: { id: interview.id }}" tag="a-button">
            <a-icon type="edit"/>
          </router-link>
        </span>
        </template>
      </a-table-column>
    </a-table>

    <a-table v-if="files.length > 0" :dataSource="files" style="width: 100%">
      <template v-slot:title style="color: #1890ff">
        <span>
          Файлы
        </span>
      </template>
      <a-table-column key="fileName" dataIndex="fileName">
        <template v-slot:title>
          <span>
          </span>
        </template>
      </a-table-column>
      <a-table-column key="action1" align="right" title="">
        <template v-slot:file>
        <span>
          <a-button v-if="isAuthenticated()" id="1" class="" v-on:click="getFile(file.id)">
            <a-icon type="file-text"/> Скачать
          </a-button>
        </span>
        </template>
      </a-table-column>
      <a-table-column key="action2" align="right" title="">
        <template v-slot:file>
        <span>
          <a-button v-if="isAdmin()" id="" class="" v-on:click="deleteFile(file.id)">
            <a-icon type="file-text"/>Удалить
          </a-button>
        </span>
        </template>
      </a-table-column>
    </a-table>

    <input id="file" ref="file" placeholder="Выберите файл" type="file" v-on:change="onChangeFileUpload()"/>
    <a-button v-on:click="submitForm()">
      <a-icon type="upload"/>
      Прикрепить
    </a-button>
  </div>
</template>

<script>
import axios from 'axios'
import moment from 'moment';

export default {
  name: 'Employee',
  data: () => ({
    interviews: [],
    employee: [],
    files: [],
    message: {},
  }),
  methods: {
    getFile: function (id) {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios({
        url: '/api/employees/' + this.$route.params.id + '/' + id,
        method: 'GET',
        responseType: 'blob',
        headers: header,

      }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        let filename = response.headers["content-disposition"].substr(20, 55);
        link.setAttribute('download', filename);
        document.body.appendChild(link);
        link.click();
      });

    },
    deleteFile: function (id) {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios({
        url: '/api/employees/' + this.$route.params.id + '/' + id,
        method: 'DELETE',
        headers: header,
      }).then((response) => {
        console.log(response)
        this.$router.go(0);
      });

    },
    formatDate: function (date) {
      return moment(date, 'YYYY-MM-DD').format('DD MMM YYYY');
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    },
    isAdmin() {
      return this.$store.getters.isAdmin;
    },
    submitForm() {
      let formData = new FormData();
      formData.append('file', this.file);
      let url = '/api/employees/upload/' + this.employee.id;
      axios.post(url,
          formData,
          {
            headers: {
              'Content-Type': 'multipart/form-data',
              'Authorization': 'Bearer ' + this.$store.getters.getToken,
            }
          }
      ).then(response => {
        this.message = response.data;
        this.$router.go(0);
      })
          .catch(function () {
            this.message = 'Не удалось прикрепить файл!!';
          });
    },
    onChangeFileUpload() {
      this.file = this.$refs.file.files[0];
    },
  },
  beforeCreate() {
    const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
    axios.get('/api/employees/' + this.$route.params.id + '/interviews', {headers: header})
        .then(response => {
          this.interviews = response.data;
          this.interviews.sort((a, b) => {
            if (this.currentSortDir === 'desc') {
              return new Date(b.date) - new Date(a.date);
            } else {
              return new Date(a.date) - new Date(b.date);
            }
          })
        }),
        axios.get('/api/employees/files/' + this.$route.params.id, {headers: header})
            .then(response => {
              this.files = response.data
            })
        ,
        axios.get('/api/employees/find/' + this.$route.params.id, {headers: header})
            .then(response => {
              this.employee = response.data
            })
  },
}
</script>

<style>

</style>
