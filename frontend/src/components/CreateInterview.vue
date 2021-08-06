<template>
  <div align="center">
    <a-form
        :form="form"
        class="interview-form"
        @submit="handleSubmit"
    >
      <h1 align="center">Создание новой беседы</h1>
      <a-form-item
          v-bind="formItemLayout"
          align="left"
          label="Дата"
      >
        <a-date-picker
            v-decorator="['date', {rules: [{ required: true, message: 'Пожалуйста укажите дату!'
                                        }],
                                }]"
            disabledTime
            format="YYYY-MM-DD"
            placeholder="Выберите дату">
        </a-date-picker>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="Сотрудник">
        <a-select
            v-decorator="[
          'employee', {
            rules: [{ required: true, message: 'Пожалуйста выберите сотрудника!' }],
          }]"
            placeholder="Выберите сотрудника"
        >
          <a-select-option v-for="employee in employees" :key="employee.id">
            <template v-if="employee.status==='Проходит адаптацию'">
              {{ employee.firstName }} {{ employee.lastName }}
            </template>
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="Название">
        <a-input v-model="name"
                 v-decorator="['title', { rules:
                 [{ required: true, message: 'Пожалуйста введите название!' }],
         }]"
                 placeholder="Введите название"/>
      </a-form-item>
      <a-form-item
          v-bind="formItemLayout"
          label="Пользователи"
      >
        <a-select
            :value="selectedUsers"
            v-decorator="['users', {
              rules: [{ required: true, message: 'Пожалуйста выберите пользователей!' }],
            }]"
            mode="multiple"
            placeholder="Выберите пользователей"
            style="width: 100%"
            @change="handleChange"
        >
          <a-select-option v-for="user in users"
                           :key="user.id">
            {{ user.name }}
          </a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item v-bind="tailFormItemLayout">
        <a-button html-type="submit" type="primary">
          Отправить
        </a-button>
        <router-link :to="{ name: 'Interviews'}" tag="a-button">Отменить</router-link>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import axios from 'axios'
import moment from 'moment';

moment.locale('ru');


export default {
  data() {
    return {
      dateFormat: 'YYYY/MM/DD',
      formItemLayout: {
        labelCol: {
          xs: {span: 24},
          sm: {span: 8},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
      },
      tailFormItemLayout: {
        wrapperCol: {
          xs: {
            span: 24,
            offset: 0,
          },
          sm: {
            span: 16,
            offset: 8,
          },
        },
      },
      interview: {
        description: null,
      },
      errors: [],
      users: [],
      selectedUsers: [],
      employees: [],
      id: '',
    }
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },
  created: function () {
    this.getUsers();
    this.getEmployees();
  },
  computed: {
  },

  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          let url = '/api/interviews/add';
          this.interview.name = this.name;
          this.interview.users = this.selectedUsers;
          this.interview.date = moment(values.date).format('YYYY-MM-DD');
          this.interview.employeeId = values.employee;
          const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
          axios.post(url, this.interview, {headers: header}).then(response => {
            this.interviews = response.data;
            this.$router.push({name: 'Interviews'});
          });
        }
      })
    },
    handleChange(selectedUsers) {
      this.selectedUsers = selectedUsers
    },
    getUsers() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/users', {headers: header})
          .then(response => {
            this.users = response.data
          })
    },
    getEmployees() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/employees', {headers: header})
          .then(response => {
            this.employees = response.data
          })
    },
  },
}
</script>

<style>
.interview-form {
  margin-left: 5%;
  margin-top: 50px;
  max-width: 500px;
}
</style>
