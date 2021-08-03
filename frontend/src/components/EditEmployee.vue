<template>
  <div align="center" class="container">
    <div class="card">
      <div class="card-header">
      </div>
      <div class="card-body">
        <form id="app" method="put" novalidate="true" @submit="checkForm" @submit.prevent="editEmployee">
          <div v-if="errors.length">
            <p></p>
            <b>Пожалуйста исправьте указанные ошибки:</b>
            <ul>
              <li v-for="error in errors" :key="error">
                {{ error }}
              </li>
            </ul>
          </div>

          <table>
            <tr>
              <td><label>Имя*:</label></td>
              <td>
                <a-input v-model="employee.firstName" class="form-control" type="text"/>
              </td>
            </tr>
            <tr>
              <td><label>Фамилия*:</label></td>
              <td>
                <a-input v-model="employee.lastName" class="form-control" type="text"/>
              </td>
            </tr>
            <tr>
              <td><label>Дата приема на работу*:</label></td>
              <td>
                <a-input v-model="employee.employmentDate" class="form-control" type="date"/>
              </td>
            </tr>
            <tr>
              <td><label>Предпологаемый конец адаптации*:</label></td>
              <td>
                <a-input v-model="employee.endOfAdaptation" class="form-control" type="date"/>
              </td>
            </tr>
            <tr>
              <td><label>Отдел*:</label></td>
              <td>
                <a-select v-model="employee.department">
                  <a-select-option
                      v-for="department in departments"
                      :key="department.id"
                      :value="department">
                    {{ department.name }}
                  </a-select-option>
                </a-select>

              </td>
            </tr>
            <tr>
              <td><label>Должность*:</label></td>
              <td>
                <a-input v-model="employee.position" class="form-control" type="text"/>
              </td>
            </tr>
            <tr>
              <td><label>Статус*:</label></td>
              <td>
                <a-select v-model="employee.status">
                  <a-select-option value="Проходит адаптацию">Проходит адаптацию</a-select-option>
                  <a-select-option value="Адаптация завершена">Адаптация завершена</a-select-option>
                  <a-select-option value="Уволен">Уволен</a-select-option>
                </a-select>
              </td>
            </tr>
            <tr>
            </tr>
          </table>
          <br>
          <a-button
              html-type="submit"
              type="primary"
          >Отправить
          </a-button>
          <router-link :to="{ name: 'Employee',params: { id: this.employee.id }}" tag="a-button">Отменить
          </router-link>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import moment from 'moment';

export default {
  data() {
    return {
      employee: [],
      errors: [],
      departments: [],
    }
  },
  created: function () {
    this.getDepartments();
    this.getEmployee();
  },
  methods: {
    getDepartments() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/departments', {headers: header})
          .then(response => {
            this.departments = response.data
          })
    },
    getEmployee() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/employees/find/' + this.$route.params.id, {headers: header})
          .then(response => {
            this.employee = response.data;
            this.employee.department = response.data.department.name;
          })
    },
    formatDate: function (date) {
      return moment(date, 'YYYY-MM-DD').format('DD MMM YYYY');
    },
    checkForm: function (e) {
      this.errors = [];
      if (!this.employee.firstName) {
        this.errors.push('Укажите имя.');
      }
      if (!this.employee.lastName) {
        this.errors.push('Укажите фамилию.');
      }
      if (!this.employee.position) {
        this.errors.push('Укажите должность.');
      }
      if (!this.employee.employmentDate) {
        this.errors.push('Укажите дату приема.');
      }
      if (!this.employee.endOfAdaptation) {
        this.errors.push('Укажите дату окончания адаптации.');
      }
      if (!this.employee.department) {
        this.errors.push('Укажите отдел.');
      }
      if (!this.errors.length) {
        return true;
      }
      e.preventDefault();
    },
    editEmployee() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      let uri = '/api/employees/update/' + this.employee.id;
      if (!this.errors.length)
        axios.put(uri, this.employee, {headers: header}).then((response) => {
          this.$router.push({name: 'Employee', params: {id: response.data.id}});
        });
    }
  },
}
</script>
