<template>
  <div align="center">
    <router-link v-if="!this.$store.getters.isAuthenticated" tag="a-button" to="/login">
      Войдите или зарегистрируйтесь
    </router-link>
    <div v-if="this.$store.getters.isAuthenticated" class="container">
      <table class="table table-striped table-bordered" style="width:50%">
        <thead>
        <tr align="center">
          <th scope="col">Название</th>
          <th scope="col">Руководитель</th>
          <th v-if="isAdmin()"></th>
          <th v-if="isAdmin()"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="department in departments" :key="department.id" align="center">
          <td>{{ department.name }}</td>
          <td>{{ department.supervisor }}</td>
          <td v-if="isAdmin()">
            <router-link :to="{ name: 'EditDepartment' , params: {id : department.id}}" tag="a-button">
              <a-icon type="highlight"/>
              Редактировать
            </router-link>
          </td>
          <td v-if="isAdmin()">
            <a-button v-on:click="deleteDepartment(department.id)">
              <a-icon type="delete"/>
              Удалить
            </a-button>
          </td>
        </tr>
        </tbody>
      </table>
      <br>
      <router-link v-if="isAdmin()" :to="{ name: 'CreateDepartment'}" tag="a-button">
        <a-icon type="cluster"/>
        Новый отдел
      </router-link>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Departments',
  data() {
    return {
      departments: [],
    }
  },
  methods: {
    loadDepartments() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/departments', {headers: header})
          .then(response => {
            this.$data.departments = response.data
          })
          .catch(error => {
            console.log('ERROR: ' + error.response.data)
          })
    },
    deleteDepartment: function (id) {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.delete('/api/departments/' + id, {headers: header})
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
    this.loadDepartments()
  }
}
</script>

<style scoped>
th {
  background: lightgray;
}

td {
  background: #f3f3f3;
}
</style>
