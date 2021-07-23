<template>
  <div class="container">
    <div class="card">
      <div class="card-header">
        <a-avatar icon="user" size="large"/>
        <h4>Здравствуйте, {{ user.name }}</h4>
      </div>
      <div class="card-body">
        <form
            id="app"
            method="put"
            novalidate="true"
            @submit="checkForm"
            v-on:submit.prevent="editUser"
        >
          <p v-if="errors.length"></p>
          <b>Пожалуйста исправьте указанные ошибки:</b>
          <ul>
            <li v-for="error in errors" :key="error">{{ error }}</li>
          </ul>

          <table>
            <tr>
              <td><label>Имя*:</label></td>
              <td><input v-model="user.name" class="form-control" type="text"/></td>
            </tr>
            <tr>
              <td><label>Должность*:</label></td>
              <td><input v-model="user.position" class="form-control" type="text"/></td>
            </tr>
            <tr>
              <td><label>Email*:</label></td>
              <td><input v-model="user.email" class="form-control" type="text"/></td>
            </tr>
            <tr>
              <td><label>Логин*:</label></td>
              <td><input v-model="user.username" class="form-control" type="text"/></td>
            </tr>
            <tr>
              <td><label>Пароль*:</label></td>
              <td><input v-model="user.password" class="form-control" type="text"/></td>
            </tr>
            <tr>
              <td>
                <a-button html-type="submit" type="primary">
                  Отправить
                </a-button>
              </td>
              <td>
                <router-link :to="{ name: 'Users'}" tag="a-button">
                  Отменить
                </router-link>
              </td>
            </tr>
          </table>
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
      user: {
        password: '',
        role: '',
      },
      errors: [],
      users: [],
    }
  },
  created: function () {
    this.getUsers();
    this.getThisUser();
  },
  methods: {
    getUsers() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/users', {headers: header})
          .then(response => {
            this.users = response.data
          })
    },
    getThisUser() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/users/find/' + this.$route.params.id, {headers: header})
          .then(response => {
            this.user = response.data
          }).catch(err => {
        this.$router.push('/users')
        console.log(err)
      })
    },
    formatDate: function (date) {
      return moment(date, 'YYYY-MM-DD').format('DD MMM YYYY');
    },
    checkForm: function (e) {
      this.errors = [];
      if (!this.user.name) {
        this.errors.push('Укажите имя.');
      }
      if (!this.user.email) {
        this.errors.push('Укажите email адрес.');
      }
      if (!this.user.position) {
        this.errors.push('Укажите должность.');
      }
      if (!this.user.username) {
        this.errors.push('Введите логин.');
      }
      if (!this.user.password) {
        this.errors.push('Введите пароль');
      }
      if (!this.errors.length) {
        return true;
      }
      e.preventDefault();
    },
    editUser() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      let uri = '/users/edit/' + this.user.id;
      if (!this.errors.length)
        axios.put(uri, this.user, {headers: header}).then((response) => {
          this.$data.users = response.data();
          this.$router.push({name: 'Users'});
        });
    }
  },
}
</script>
