<template>
  <div align="center" class="container">
    <a-card :bordered=true :title="employee.firstName + '  ' + employee.lastName">
      <a-timeline style="width: 275px">
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
      <br><br>
      <p>Отдел: {{ employee.department.name }}</p>
      <p>Начальник отдела: {{ employee.department.supervisor }}</p>
      <p>Должность: {{ employee.position }}</p>
      <p>Статус: {{ employee.status }}</p>
      <router-link :to="{ name: 'Employees'}" tag="a-button">
        <a-icon type="rollback"/>
        Назад
      </router-link>
      <router-link v-if="this.$store.getters.isAdmin"
                   :to="{ name: 'EditEmployee', params: { id: employee.id }}" tag="a-button">
        <a-icon type="highlight"/>
        Редактировать
      </router-link>
      <a-button v-if="this.file !== ''" v-on:click="submitForm()">
        <a-icon type="upload"/>
        Прикрепить
      </a-button>
      <input id="file"
             ref="file"
             placeholder="Выберите файл"
             type="file"
             @change="onChangeFileUpload()"/>
    </a-card>
    <p></p>
    <!--    <a-table :dataSource="interviews" v-if="interviews.length > 0" style="word-break: break-all" class="int-table">-->
    <!--      <template v-slot:title style="color: #1890ff">-->
    <!--        <span>-->
    <!--          Беседы-->
    <!--        </span>-->
    <!--      </template>-->
    <!--      <a-table-column-->
    <!--          dataIndex="date"-->
    <!--          key="date"-->
    <!--          width="150px"-->
    <!--      >-->
    <!--        <span v-slot:title:>Дата</span>-->
    <!--      </a-table-column>-->
    <!--      <a-table-column-->
    <!--          title="Беседа"-->
    <!--          dataIndex="name"-->
    <!--          key="name"-->
    <!--          width="150px"-->
    <!--      />-->
    <!--      <a-table-column-->
    <!--          title="Пользователи"-->
    <!--          dataIndex="users"-->
    <!--          key="users"-->
    <!--          width="400px"-->
    <!--      >-->
    <!--        <template v-slot:users>-->
    <!--        <span>-->
    <!--          <a-tag v-for="user in users" color="blue" :key="user.name">{{user.name}}</a-tag>-->
    <!--        </span>-->
    <!--        </template>-->
    <!--      </a-table-column>-->
    <!--      <a-table-column-->
    <!--          title="Отзыв"-->
    <!--          dataIndex="description"-->
    <!--          key="description"-->
    <!--          width="800px"-->
    <!--          style="word-wrap: break-word"-->
    <!--      />-->
    <!--      <a-table-column-->
    <!--          title="Действие"-->
    <!--          key="action"-->
    <!--          width="100px"-->
    <!--      >-->
    <!--        <template v-slot:interview>-->
    <!--        <span>-->
    <!--              <router-link :to="{ name: 'Interview', params: { id: interview.id }}" tag="a-button"-->
    <!--                           v-if="isAuthenticated()">-->
    <!--                <a-icon type="edit"/>-->

    <!--              </router-link>-->
    <!--        </span>-->
    <!--        </template>-->
    <!--      </a-table-column>-->
    <!--    </a-table>-->

    <div v-if="this.files.length > 0">
      <h1>Загруженные файлы</h1>
      <table border="1" class="table table-striped table-bordered" style="width:70%">
        <thead width="400px">
        <tr>
          <th align="left" scope="col">Файл</th>
          <i class="fas fa-sort-alpha-down float-right"></i>
          <th align="right" scope="col">Скачать</th>
          <i class="fas fa-sort-alpha-down float-right"></i>
          <th v-if="isAdmin()" align="right" scope="col">Удалить</th>
          <i class="fas fa-sort-alpha-down float-right"></i>
        </tr>
        </thead>
        <tbody>
        <tr v-for="file in files" :key="file.id">
          <td>{{ file.fileName }}</td>
          <i class="fas fa-sort-alpha-down float-right"></i>
          <td>
            <a-button v-if="isAuthenticated()" @click="getFile(file.id)">
              <a-icon type="file-text"/>
              Скачать
            </a-button>
          </td>
          <i class="fas fa-sort-alpha-down float-right"></i>
          <td v-if="isAdmin()">
            <a-button @click="deleteFile(file.id)">
              <a-icon type="delete"/>
              Удалить
            </a-button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import moment from 'moment';

export default {
  name: 'Employee',
  data: () => ({
    files: [],
    file: '',
    interviews: [],
    employee: [],
    message: '',
    columns: [{
      title: 'action',
      key: 'action',
      render: () => (
          <a-button>
            <a-icon type="file-text"/>
            Скачать
          </a-button>
      )
    }]
  }),
  methods: {
    getFile: function (id) {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios({
        url: '/api/employees/download/' + this.$route.params.id + '/' + id,
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
      axios.delete('/api/employees/delete/' + this.$route.params.id + '/' + id, {headers: header})
          .then((response) => {
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
      console.log(this.file)
      formData.append('file', this.file);
      let url = '/api/employees/upload/' + this.employee.id;
      axios.post(url, formData, {
            headers: {
              'Content-Type': 'multipart/form-data', 'Authorization': 'Bearer '
                  + this.$store.getters.getToken,
            }
          }
      ).then(response => {
        console.log(response)
        this.$store.state.reset;
        this.$router.go(0);
      }).catch(function () {
        this.message = 'Не удалось прикрепить файл!';
        console.log(this.message)
      });
    },
    onChangeFileUpload() {
      this.file = this.$refs.file.files[0];
    },
  },
  beforeCreate() {
    const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
    axios.get('/api/employees/interviews/' + this.$route.params.id, {headers: header})
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
