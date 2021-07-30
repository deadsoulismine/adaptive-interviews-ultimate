<template>
  <div align="center" class="main">
    <router-link v-if="!this.$store.getters.isAuthenticated" tag="a-button" to="/login">Войдите или зарегистрируйтесь
    </router-link>
    <div v-if="this.$store.getters.isAuthenticated" class="col-md-12">
      <div>
        <a-checkbox
            :checked="checkAll"
            :indeterminate="indeterminate"
            @change="onCheckAllChange"
        >
          Все сотрудники
          </a-checkbox>
        <a-checkbox-group v-model="checkedList" :options="plainOptions" @change="onChange"/>
        <a-input v-model="search" class="form-control" placeholder="Найти сотрудника" style="max-width: 300px"
                 type="text"></a-input>
      </div>
      <br>
      <div class="table-responsive">
        <table class="table table-striped table-bordered" style="width:50%">
          <thead align="center" width="200px">
          <tr>
            <th align="left" scope="col">Имя</th>
            <th align="left" scope="col" @click="sort('lastName')">&#8597;Фамилия <i
                class="fas fa-sort-alpha-down float-right"></i></th>
            <th align="left" scope="col">Отдел <i
                class="fas fa-sort-alpha-down float-right"></i></th>
            <th align="left" scope="col">Должность <i
                class="fas fa-sort-alpha-down float-right"></i></th>
            <th align="left" scope="col">Принят на работу <i
                class="fas fa-sort-alpha-down float-right"></i></th>
            <th align="left" scope="col">Конец адаптации <i
                class="fas fa-sort-alpha-down float-right"></i></th>
            <th align="left" scope="col">Статус<i
                class="fas fa-sort-alpha-down float-right"></i>
            </th>
            <th></th>
            <th v-if="isAdmin()"></th>
          </tr>
          </thead>
          <tbody align="center">
          <tr v-for="employee in (sortedActivity, filteredList)"
              :key="employee.status"
              :class="getClass(`${employee.status}`)">
            <td>{{ employee.firstName }}</td>
            <td>{{ employee.lastName }}</td>
            <td>{{ employee.department.name }}</td>
            <td>{{ employee.position }}</td>
            <td>{{ formatDate(employee.employmentDate) }}</td>
            <td>{{ formatDate(employee.endOfAdaptation) }}</td>
            <td>{{ employee.status }}</td>
            <td>
              <router-link :to="{ name: 'Employee', params: { id: employee.id }}" tag="a-button">
                <a-icon type="search"/>
                Подробнее
              </router-link>
            </td>
            <td v-if="isAdmin()">
              <a-button class="btn btn-danger pull-right" data-toggle="modal" type="button"
                        @click="deleteData(result, employee.id)">
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
      <router-link v-if="isAdmin()" :to="{ name: 'CreateEmployee'}" tag="a-button">
        <a-icon type="user-add"/>
        Новый сотрудник
      </router-link>
    </div>
  </div>
</template>


<script>

import moment from 'moment';
import axios from 'axios';

const plainOptions = ['Проходит адаптацию', 'Адаптация завершена', 'Уволен'];
const defaultCheckedList = ['Проходит адаптацию'];

export default {
  data: () => ({
    checkedList: defaultCheckedList,
    indeterminate: true,
    checkAll: false,
    plainOptions,
    statusList: ['Проходит адаптацию', 'Адаптация завершена', 'Уволен'],
    statuses: [],
    employees: [],
    currentSort: 'lastName',
    currentSortDir: 'asc',
    search: '',
    searchSelection: '',
    pageSize: 30,
    currentPage: 1,
    isCheckAll: false
  }),
  methods: {
    onChange(checkedList) {
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
    sort: function (s) {
      if (s === this.currentSort) {
        this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc';
      }
      this.currentSort = s;
    },
    nextPage: function () {
      if ((this.currentPage * this.pageSize) < this.employees.length) this.currentPage++;
    },
    prevPage: function () {
      if (this.currentPage > 1) this.currentPage--;
    },
    formatDate: function (date) {
      return moment(date, 'YYYY-MM-DD').format('DD MMM YYYY');
    },
    background() {
      if (this.employee.status === 'Проходит адаптацию') return 'lightgreen';
      if (this.employee.status === 'Уволен') return 'lightsalmon';
      else return 'Gold';
    },
    getClass(status) {
      if (status === 'Уволен') {
        this.class = "first";
        return this.class
      }
      if (status === 'Проходит адаптацию') {
        this.class = "second";
        return this.class
      } else {
        this.class = "third";
        return this.class
      }
    },
    deleteData(result, id) {
      axios.delete('api/employees/delete/' + id)
          .then(response => {
            console.log(response)
            this.$router.go(0);
          })
    },
    isAdmin() {
      return this.$store.getters.isAdmin
    }
  },
  computed: {
    sortedActivity: function () {
      return this.employees.slice().sort((a, b) => {
        let modifier = 1;
        if (this.currentSortDir === 'desc') modifier = -1;
        if (a[this.currentSort] < b[this.currentSort]) return modifier * -1;
        if (a[this.currentSort] > b[this.currentSort]) return modifier;
        return 0;
      }).filter((row, index) => {
        let start = (this.currentPage - 1) * this.pageSize;
        let end = this.currentPage * this.pageSize;
        if (index >= start && index < end) return true;
      });
    },
    filteredList() {
      return this.employees.filter((data) => {
        let firstName = data.firstName.toLowerCase().match(this.search.toLowerCase());
        let lastName = data.lastName.toLowerCase().match(this.search.toLowerCase());
        let status = data.status.toLowerCase().match(this.search.toLowerCase());
        let position = data.position.toLowerCase().match(this.search.toLowerCase());
        return firstName || lastName || status || position;
      }).filter((row, index) => {
        let start = (this.currentPage - 1) * this.pageSize;
        let end = this.currentPage * this.pageSize;
        if (index >= start && index < end) return true;
      }).filter(function (employee) {
        return this.checkedList.includes(employee.status);
      }, this);
    },
  },
  created() {
    const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
    axios.get('/api/employees', {headers: header})
        .then(response => {
          this.employees = response.data
        })
  },
}
</script>

<style>

</style>
