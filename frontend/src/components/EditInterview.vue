<template>
  <div align="center" className="container">
    <form v-on:submit.prevent="updateInterview">
      <div className="card">
        <div className="card-header">
          <table>
            <tr>
              <td><label>Название*: </label></td>
              <td>
                <a-input v-model="interview.name" class="form-control" type="text"/>
              </td>
            </tr>
            <tr>
              <td><label>Дата проведения*: </label></td>
              <td>
                <a-input v-model="interview.date" class="form-control" type="date"/>
              </td>
            </tr>
            <tr>
              <td>С кем проведена*:</td>
              <td>
                <a-select v-model="interviewEmployee">
                  <a-select-option v-for="employee in employees"
                                   :key="employee"
                                   :value="employee">
                    {{ employee.firstName + ' ' + employee.lastName }}
                  </a-select-option>
                </a-select>
              </td>
            </tr>
            <tr>
              <div v-if="interviewUsers.length > 0">
                <td>Список участников на данный момент*:</td>
                <a-tag v-for="user in interview.users" :key="user" color="blue">
                  {{ user.name }}
                </a-tag>
              </div>
              <div v-if="interviewUsers.length == 0">
                <td>В этой беседе нет участников!</td>
              </div>
            </tr>
          </table>
        </div>
        <br>
        <a-select placeholder="Выберите новых участников беседы" style="width: 300px;">
          <a-select-option v-for="user in users"
                           :key="user.id"
                           v-model="selectedUsers"
                           :value="user"
                           multiple

                           @change="handleChange">
            {{ user.name }}
          </a-select-option>
        </a-select>

        <br><br>
        <p v-if="selectedUsers.length > 0">
          Вы выбрали:
          <a-tag v-for="user in selectedUsers" :key="user"> {{ user.name }}</a-tag>
        </p>

        <div className="card-body">
          <label>Отзыв:*</label>
          <br><br>
          <div className="form-group">
            <label>
              <a-textarea v-model="interview.description" className="form-control" placeholder="Напишите ваш отзыв"
                          style="width: 500px; height: 300px"
              ></a-textarea>
            </label>
          </div>
          <br>
          <div className="form-group">
            <a-input className="btn btn-primary" style="width: 90px; height: 20px" type="submit" value="Сохранить"/>
            <router-link :to="{ name: 'Interviews'}" tag="a-button">Отменить</router-link>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>
<script>
import axios from 'axios'
import moment from 'moment';

export default {
  data() {
    return {
      interview: [],
      employees: [],
      interviewEmployee: '',
      selectedUsers: [],
      interviewUsers: [],
      users: [],
    }
  },
  created: function () {
    this.getInterview()
    this.getEmployees()
    this.getUsers()
  },
  methods: {
    getInterview() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/interviews/find/' + this.$route.params.id, {headers: header})
          .then(response => {
            this.interview = response.data
            this.interviewEmployee = response.data.employee
            this.interviewUsers = response.data.users
          })
    },
    getEmployees() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/employees/', {headers: header})
          .then(response => {
            this.employees = response.data
          })
    },
    getUsers() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/users', {headers: header})
          .then(response => {
            this.users = response.data
          })
    },
    formatDate: function (date) {
      return moment(date, 'YYYY-MM-DD').format('DD MMM YYYY');
    },
    handleChange(selectedUsers) {
      this.selectedUsers = selectedUsers
    },
    updateInterview() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      let url = '/api/interviews/update/' + this.$route.params.id;
      this.interview.employee = this.interviewEmployee
      if (this.selectedUsers.length > 0) {
        this.interview.users = this.selectedUsers
      }
      axios.put(url, this.interview, {headers: header}).then((response) => {
        console.log(response)
        this.$router.push({name: 'Interviews'});
      });
    }
  }
}
</script>
