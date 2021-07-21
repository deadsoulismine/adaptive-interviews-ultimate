<template>
  <div>
    <router-link v-if="!this.$store.getters.isAuthenticated" tag="a-button" to="/login">Войдите или зарегистрируйтесь
    </router-link>
    <div v-if="this.$store.getters.isAuthenticated" class="col-md-12">
      <div style="max-width: 334px">
        <a-input v-model="search" class="form-control" placeholder="Найти беседу" type="text"></a-input>
      </div>
      <a-range-picker :ranges="{ Сегодня: [moment(), moment()], 'Текущий месяц': [moment(), moment().endOf('month')] }"
                      @change="onChange"
      />
      <div class="table-responsive">
        <table class="table table-striped table-bordered" style="width:100%">
          <thead>
          <tr>
            <th align="left" scope="col" @click="sort('asc')">&#8597;Дата <i
                class="fas fa-sort-alpha-down float-right"></i></th>
            <th align="left" scope="col">Название</th>
            <th align="left" scope="col">Сотрудник <i
                class="fas fa-sort-alpha-down float-right"></i></th>
            <th align="left" scope="col">Пользователи</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(interview) in (sortedActivity, filteredList)" :key="interview.id"
              :style="{ background: interview.description === '' ? 'lightsalmon' : 'lightgreen' }">
            <td>{{ formatDate(interview.date) }}</td>
            <td>{{ interview.name }}</td>
            <td>{{ interview.employee.firstName }} {{ interview.employee.lastName }}</td>
            <td>
              <a-tag v-for="user in interview.users" :key="user.name" color="blue">{{ user.name }}</a-tag>
            </td>
            <td>
              <div v-if="interview.description === ''">
                <router-link v-if="isAuthenticated()" :to="{ name: 'Interview', params: { id: interview.id }}"
                             tag="a-button">
                  <a-icon type="edit"/>
                  Оставить отзыв
                </router-link>
              </div>
              <div v-if="interview.description !== ''">
                <router-link v-if="isAuthenticated()" :to="{ name: 'Interview', params: { id: interview.id }}"
                             tag="a-button">
                  <a-icon type="highlight"/>
                  Редактировать
                </router-link>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <a-button class="float-left btn btn-outline-info btn-sm" type="primary" @click="prevPage"><i
          class="fas fa-arrow-left"></i>
        <a-icon type="step-backward"/>
        Предыдущие
      </a-button>
      <a-button class="float-right btn btn-outline-info btn-sm" type="primary" @click="nextPage">
        Следующие
        <a-icon type="step-forward"/>
        <i
            class="fas fa-arrow-right"></i>
      </a-button>
      <h4></h4>
      <div>
        <router-link v-if="this.$store.getters.isAdmin" :to="{ name: 'CreateInterview'}" tag="a-button">
          <a-icon type="aliwangwang"/>
          Новая беседа
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';

moment.locale('ru');

export default {
  data: () => ({
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
    moment,
    onChange(dates, dateStrings) {
      this.startDate = dateStrings[0];
      this.endDate = dateStrings[1];
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
      return this.interviews.filter((data) => {
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
  },

  created() {
    const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
    axios.get('/api/interviews', {headers: header})
        .then(response => {
          this.interviews = response.data
        })
  },
}

</script>

<style>

th {
  cursor: pointer;
  width: 500px !important;
  white-space: nowrap;
}

tr {
  white-space: nowrap;
}
</style>
