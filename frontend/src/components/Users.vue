<template>
  <div>
    <router-link v-if="!this.$store.getters.isAuthenticated" tag="a-button" to="/login">
      Войдите или зарегистрируйтесь
    </router-link>
    <div v-if="this.$store.getters.isAuthenticated" class="container">
      <table class="table table-striped table-bordered" style="width:100%">
        <thead width="400px">
        <tr>
          <th align="left" scope="col">Имя</th>
          <th align="left" scope="col">Должность</th>
          <th align="left" scope="col">E-mail</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.name }}</td>
          <td>{{ user.position }}</td>
          <td>{{ user.email }}</td>
          <td>
            <a-button v-if="isAdmin()" id="" class="" @click="deleteUser(user.id)">
              <a-icon type="delete"/>
              Удалить
            </a-button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <p></p>
    <div class="container">
      <router-link v-if="this.$store.getters.isAdmin" :to="{ name: 'CreateUser'}" tag="a-button">
        <a-icon type="user-add"/>
        Новый пользователь
      </router-link>
      <router-link v-if="this.$store.getters.isAuthenticated"
                   :to="{ name: 'EditUser', params: { id: this.$store.getters.getId }}"
                   tag="a-button">
        <a-icon type="edit"/>
        Редактировать профиль
      </router-link>
    </div>
  </div>
</template>


<script>
import axios from 'axios'

export default {
  name: 'Users',
  data() {
    return {
      username: {},
      users: [],
    }
  },
  methods: {
    loadUsers() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/users', {headers: header})
          .then(response => {
            this.$data.users = response.data
          })
          .catch(error => {
            console.log('ERROR: ' + error.response.data)
          })
    },
    deleteUser: function (id) {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.delete('/api/users/delete/' + id, {headers: header})
          .then((response) => {
            console.log(response)
            this.$router.go(0);
          });
    },
    isAdmin() {
      return this.$store.getters.isAdmin;
    },
  },


  mounted() {
    this.loadUsers();
    this.username = this.$store.getters.getUsername;
  },
  beforeCreate() {

  }
}
</script>

<style>

.table-responsive {
}

.first {
  background-color: lightsalmon
}

.second {
  background-color: lightgreen
}

.third {
  background-color: #dfbd00;
}

th {
  cursor: pointer;
  width: 500px !important;
  white-space: nowrap;
}

tr {
  white-space: nowrap;
}
</style>
