<template>
  <div align="center" class="container">
    <div class="card">
      <div class="card-header">
        <a-avatar icon="user" size="large"/>
        <br><br>
        <h4>Здравствуйте, {{ user.name }}</h4>
      </div>
      <div class="card-body">
        <form
            id="app"
            method="put"
            novalidate="true"
            @submit="checkForm"
            @submit.prevent="editUser"
        >
          <p v-if="errors.length > 0">
            <b>Пожалуйста исправьте указанные ошибки:</b>
          </p>
          <ul>
            <li v-for="error in errors" :key="error">{{ error }}</li>
          </ul>
          <table>
            <tr>
              <td><label>Имя*:</label></td>
              <td>
                <a-input v-model="user.name" class="form-control" type="text"/>
              </td>
            </tr>
            <tr>
              <td><label>Должность*:</label></td>
              <td>
                <a-input v-model="user.position" class="form-control" type="text"/>
              </td>
            </tr>
            <tr>
              <td><label>Email*:</label></td>
              <td>
                <a-input v-model="user.email" class="form-control" type="text"/>
              </td>
            </tr>
            <tr>
              <td><label>Логин*:</label></td>
              <td>
                <a-input v-model="user.username"
                         class="form-control"
                         placeholder="Введите новый логин"
                         type="text"/>
              </td>
            </tr>
            <tr>
              <td><label>Пароль*:</label></td>
              <td>
                <a-input v-model="user.password"
                         class="form-control"
                         placeholder="Введите новый пароль"
                         type="text"/>
              </td>
            </tr>
            <tr v-if="this.$store.getters.isAdmin">
              <td><label>Уровень доступа*:</label></td>
              <td>
                <a-select v-model="user.role"
                          placeholder="Выберите один из вариантов"
                          style="width: 250px; height: 40px">
                  <a-select-option value="ADMIN">Администратор</a-select-option>
                  <a-select-option value="USER">Пользователь</a-select-option>
                </a-select>
              </td>
            </tr>
          </table>
          <br>
          <a-button html-type="submit" type="primary">
            Отправить
          </a-button>
          <router-link :to="{ name: 'Users'}" tag="a-button">
            Отменить
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
      selected: this.$store.getters.getRole,
      user: [],
      errors: [],
      users: [],
    }

  },
  created: function () {
    this.getUsers();
    this.getThisUser();
  },
  mounted() {

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
        console.log(err)
        this.$router.push('/users')
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
      if (!this.user.role) {
        this.errors.push('Выберите уровень доступа');
      }
      if (!this.errors.length) {
        return true;
      }
      e.preventDefault();
    },
    editUser() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      let url = '/api/users/update/' + this.user.id;
      if (!this.errors.length) {
        axios.put(url, this.user, {headers: header})
            .then((response) => {
              console.log(response)
              if (this.user.id === this.$store.getters.getId) {
                axios.post(`/api/authenticate`, {'username': this.user.username, 'password': this.user.password})
                    .then(response => {
                      console.log(response)
                      this.$store.dispatch('login', {
                        'jwtToken': response.data.jwtToken,
                        'roles': response.data.authorities,
                        'name': response.data.username,
                        'id': response.data.id
                      });
                      this.$router.push({name: 'Users'});
                    })
              }
              this.$router.push({name: 'Users'});
            });
      }
    },
  }
}

</script>
