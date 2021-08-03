<template>
  <div align="center">
    <router-link v-if="!this.$store.getters.isAuthenticated" tag="a-button" to="/login">Войдите или зарегистрируйтесь
    </router-link>
    <div v-if="this.$store.getters.isAuthenticated" class="col-md-12">
      <a-checkbox
          :checked="checkAll"
          :indeterminate="indeterminate"
          @change="onCheckAllChange"
      >
        Все беседы
      </a-checkbox>
      <a-checkbox-group v-model="checkedList" :options="plainOptions" @change="onChangeCheckbox"/>
      <a-input v-model="search" class="form-control" placeholder="Найти беседу" style="max-width: 300px"
               type="text"></a-input>
      <a-range-picker :ranges="{ Сегодня: [moment(), moment()], 'Текущий месяц': [moment(), moment().endOf('month')] }"
                      style="max-width: 300px" @change="onChange"
      />
      <br>
      <br>
      <div class="table-responsive">
        <table class="table table-striped table-bordered" style="width:50%">
          <thead align="center">
          <tr>
            <th align="left" scope="col" @click="sort('asc')">&#8597;Дата <i
                class="fas fa-sort-alpha-down float-right"></i></th>
            <th align="left" scope="col">Название</th>
            <th align="left" scope="col">Сотрудник <i
                class="fas fa-sort-alpha-down float-right"></i></th>
            <th align="left" scope="col">Пользователи</th>
            <th></th>
            <th></th>
          </tr>
          </thead>
          <tbody align="center">
          <tr v-for="interview in (sortedActivity, filteredList,  filterCheckbox)"
              :key="interview.description"
              :style="{ background: interview.description === '' ? 'lightsalmon' : 'lightgreen' }">
            <td>{{ formatDate(interview.date) }}</td>
            <td>{{ interview.name }}</td>
            <td>{{ interview.employee.firstName }} {{ interview.employee.lastName }}</td>
            <td>
              <a-tag v-for="user in interview.users" :key="user" color="blue">
                {{ user.name }}
              </a-tag>
            </td>
            <td>
              <div v-if="interview.description === ''">
                <router-link v-if="isAuthenticated()"
                             :to="{ name: 'EditInterview', params: { id: interview.id }}" tag="a-button">
                  <a-icon type="edit"/>
                  Оставить отзыв/Редактировать
                </router-link>
              </div>
              <div v-if="interview.description !== ''">
                <router-link v-if="isAuthenticated()"
                             :to="{ name: 'EditInterview', params: { id: interview.id }}" tag="a-button">
                  <a-icon type="highlight"/>
                  Изменить отзыв/Редактировать
                </router-link>
              </div>
            </td>
            <td>
              <a-button v-if="isAdmin()" id="" class="" v-on:click="deleteInterview(interview.id)">
                <a-icon type="delete"/>
                Удалить
              </a-button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <br>
      <a-button class="float-left btn btn-outline-info btn-sm" type="primary" @click="prevPage">
        <i class="fas fa-arrow-left"></i>
        <a-icon type="step-backward"/>
        Предыдущие
      </a-button>
      <a-button class="float-right btn btn-outline-info btn-sm" type="primary" @click="nextPage">
        Следующие
        <a-icon type="step-forward"/>
        <i class="fas fa-arrow-right"></i>
      </a-button>
      <router-link v-if="this.$store.getters.isAdmin" :to="{ name: 'CreateInterview'}" tag="a-button">
        <a-icon type="aliwangwang"/>
        Новая беседа
      </router-link>
    </div>
  </div>
</template>

<script>

import axios from 'axios';
import moment from 'moment';

moment.locale('ru');

const plainOptions = ['Есть отзыв', 'Нет отзыва', 'Будущие', 'Прошедшие'];
const defaultCheckedList = ['Будущие'];

export default {
  data: () => ({
    checkedList: defaultCheckedList,
    indeterminate: true,
    checkAll: false,
    plainOptions,
    dateFormat: 'YYYY/MM/DD',
    interviews: [],
    currentSort: 'date',
    currentSortDir: 'desc',
    search: '',
    searchSelection: '',
    pageSize: 20,
    currentPage: 1,
    startDate: '',
    endDate: '',
  }),
  methods: {
    onChangeCheckbox(checkedList) {
      this.indeterminate = !!checkedList.length && (checkedList.length < plainOptions.length);
      this.checkAll = checkedList.length === plainOptions.length;
    },
    onCheckAllChange(e) {
      Object.assign(this, {
        checkedList: e.target.checked ? plainOptions : [],
        indeterminate: false,
        checkAll: e.target.checked,
      })
    },
    interviewMapping(interviews) {
      interviews.forEach((interview) => {
        if (interview.description === '') {
          interview.checkDescription = 'Нет отзыва'
        } else {
          interview.checkDescription = 'Есть отзыв'
        }
        let today = new Date()
        let dateSet = interview.date.toString().split('-')
        let interviewDate = new Date(dateSet[0], dateSet[1] - 1, dateSet[2])
        if (today <= interviewDate) {
          interview.checkDate = 'Будущие'
        } else {
          interview.checkDate = 'Прошедшие'
        }
      })
    },
    moment,
    onChange(dates, dateStrings) {
      this.startDate = dateStrings[0];
      this.endDate = dateStrings[1];
    },
    isAdmin() {
      return this.$store.getters.isAdmin;
    },
    sort: function (s) {
      if (s === this.currentSortDir) {
        this.currentSortDir = 'desc';
      } else this.currentSortDir = 'asc';
    },
    nextPage: function () {
      if ((this.currentPage * this.pageSize) < this.interviews.length) this.currentPage++;
    },
    prevPage: function () {
      if (this.currentPage > 1) this.currentPage--;
    },
    formatDate: function (date) {
      return moment(date, 'YYYY-MM-DD').format('DD MMM YYYY');
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    },
    deleteInterview: function (id) {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.delete('/api/interviews/delete/' + id, {headers: header})
          .then((response) => {
            console.log(response)
            this.$router.go(0);
          });
    },
  },
  computed: {
    sortedActivity: function () {
      return this.interviews.slice().sort((a, b) => {
        if (this.currentSortDir === 'desc') {
          return new Date(b.date) - new Date(a.date);
        } else {
          return new Date(a.date) - new Date(b.date);
        }
      }).filter((row, index) => {
        let start = (this.currentPage - 1) * this.pageSize;
        let end = this.currentPage * this.pageSize;
        if (index >= start && index < end) return true;
      })
    },
    filteredList() {
      return this.interviews
          .filter((data) => {
            return data.employee.lastName.toLowerCase().match(this.search.toLowerCase());
          }).filter((row, index) => {
            let start = (this.currentPage - 1) * this.pageSize;
            let end = this.currentPage * this.pageSize;
            if (index >= start && index < end) return true;
          }).filter(function (interview) {
            let startDate = this.startDate;
            let endDate = this.endDate;
            if (!startDate && !endDate) {
              return true;
            } else {
              return (interview.date >= startDate && interview.date <= endDate);
            }
          }, this);
    },
    filterCheckbox() {
      return this.interviews.filter(function (interview) {
        return this.checkedList.includes(interview.checkDescription) || this.checkedList.includes(interview.checkDate)
      }, this)
    },
  },

  created() {
    const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
    axios.get('/api/interviews', {headers: header})
        .then(response => {
          this.interviews = response.data
          this.interviewMapping(this.interviews)
        })
  },
}

</script>

<style>

</style>
