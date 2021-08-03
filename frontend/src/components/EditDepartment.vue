<template>
  <div align="center" class="container">
    <div class="card">
      <div class="card-header">
      </div>
      <div class="card-body">
        <form
            id="app"
            method="post"
            novalidate="true"
            @submit="checkForm"
            v-on:submit.prevent="updateDepartment"
        >
          <p v-if="errors.length > 0">
            <b>Пожалуйста исправьте указанные ошибки:</b>
          </p>
          <ul>
            <li v-for="error in errors" :key="error">
              {{ error }}
            </li>
          </ul>
          <table>
            <tr>
              <td><label>Название*:</label></td>
              <td>
                <a-input v-model="department.name" class="form-control" type="text"/>
              </td>
            </tr>
            <tr>
              <td><label>Руководитель*:</label></td>
              <td>
                <a-input v-model="department.supervisor" class="form-control" type="text"/>
              </td>
            </tr>
            <tr>
              <td>
                <a-button
                    html-type="submit"
                    type="primary"

                >Отправить
                </a-button>
              </td>
              <td>
                <router-link :to="{ name: 'Departments'}" tag="a-button">Отменить</router-link>
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
  name: 'Department',
  data() {
    return {
      errors: [],
      department: [],
    }
  },
  methods: {
    checkForm: function (e) {
      this.errors = [];
      if (!this.department.name) {
        this.errors.push('Укажите название отдела.');
      }
      if (!this.department.supervisor) {
        this.errors.push('Укажите начальника отдела.');
      }
      if (!this.errors.length) {
        return true;
      }
      e.preventDefault();
    },
    getDepartment: function () {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/departments/find/' + this.$route.params.id, {headers: header})
          .then(response => {
            this.department = response.data
          })
    },
    formatDate: function (date) {
      return moment(date, 'YYYY-MM-DD').format('DD MMM YYYY');
    },
    updateDepartment() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      let uri = '/api/departments/update/' + this.$route.params.id;
      if (!this.errors.length)
        axios.put(uri, this.department, {headers: header}).then((response) => {
          this.$router.push({name: 'Departments'});
          console.log(response)
        });
    }
  },
  created() {
    this.getDepartment()
  }
}
</script>

<style scoped>

</style>
