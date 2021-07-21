<template>
  <div>
    <router-link v-if="!this.$store.getters.isAuthenticated" tag="a-button" to="/login">
      Войдите или зарегистрируйтесь
    </router-link>
    <div v-if="this.$store.getters.isAuthenticated" class="container">
      <table class="table table-striped table-bordered" style="width:100%">
        <thead width="400px">
        <tr>
          <th align="left" scope="col">Название</th>
          <th align="left" scope="col">Руководитель</th>
          <i
              class="fas fa-sort-alpha-down float-right"></i>
          <!--          <router-link v-if="isAdmin()" :to="{ name: 'Department' , params: {id : department.id}}" tag="a-button">-->
          <!--            <a-icon type="highlight"/>Редактировать-->
          <!--          </router-link>-->
        </tr>
        </thead>
        <tbody>
        <tr v-for="department in departments" :key="department.id">
          <td>{{ department.name }}</td>
          <td>{{ department.supervisor }}</td>
        </tr>
        </tbody>
      </table>

      <router-link v-if="this.$store.getters.isAuthenticated" :to="{ name: 'CreateDepartment'}" tag="a-button">
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
    isAdmin() {
      return this.$store.getters.isAdmin;
    },
  },
  mounted() {
    this.loadDepartments()
  }
}
</script>

<style>
</style>
