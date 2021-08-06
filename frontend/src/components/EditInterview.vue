<template>
  <div align="center" className="container">
    <a-form
        :form="form"
        class="interview-form"
        @submit="handleSubmit"
    >
      <h1>Изменение данных беседы</h1>
      <div className="card">
        <div className="card-header">
          <a-form-item v-bind="formItemLayout" label="Название">
            <a-input v-model="interview.name"
                     v-decorator="['name', {
                       initialValue: interview.name,
                       rules:
                     [{ required: true, message: 'Пожалуйста введите название!' }],
                      }]"
                     placeholder="Введите название"/>
          </a-form-item>
          <a-form-item
              v-bind="formItemLayout"
              align="left"
              label="Дата"
          >
            <a-date-picker v-model="interview.date"
                           v-decorator="['date', {
                    initialValue: moment(interview.date, dateFormat),
                    rules: [{ required: true, message: 'Пожалуйста укажите дату!'
                                        }],
                                }]"
                           format="YYYY-MM-DD"
                           placeholder="Выберите дату">
            </a-date-picker>
          </a-form-item>
          <a-form-item v-bind="formItemLayout" label="С кем проведена">
            <a-select
                v-decorator="[
                  'employee', {
                    initialValue: interview.employee.id,
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
          <a-form-item v-bind="formItemLayout" label="Список участников на данный момент">
            <div v-if="interview.users.length > 0">
              <a-tag v-for="user in interview.users" :key="user" color="blue">
                {{ user.name }}
              </a-tag>
            </div>
            <div v-if="interview.users.length === 0">
              В этой беседе нет участников!
            </div>
          </a-form-item>
        </div>
        <a-form-item
            v-bind="formItemLayout"
            label="Пользователи"
        >
          <a-select
              v-decorator="['users', {
              rules: [{ required: false, message: 'Пожалуйста выберите пользователей!' }],
            }]"

              mode="multiple"
              placeholder="Выберите новых участников беседы"
              style="width: 100%"
              @change="handleChange"
          >
            <a-select-option v-for="user in users" :key="user.id">
              {{ user.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <div className="card-body">
          <a-form-item>
            <label>Отзыв:*</label>
            <div className="form-group">
              <label>
                <a-textarea v-model="interview.description" className="form-control" placeholder="Напишите ваш отзыв"
                            style="width: 450px; height: 300px"
                ></a-textarea>
              </label>
            </div>
          </a-form-item>
          <div className="form-group">
            <a-form-item>
              <a-button html-type="submit" type="primary">
                Сохранить
              </a-button>
              <router-link :to="{ name: 'Interviews'}" tag="a-button">Отменить</router-link>
            </a-form-item>
          </div>
        </div>
      </div>
    </a-form>
  </div>
</template>
<script>
import axios from 'axios'
import moment from 'moment';

export default {
  data() {
    return {
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
      dateFormat: 'YYYY-MM-DD',
      interview: [],
      employees: [],
      users: [],
      selectedUsers: [],
    }
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },
  created: function () {
    this.getUsers()
    this.getInterview()
    this.getEmployees()
  },
  methods: {
    moment,
    getInterview() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/interviews/find/' + this.$route.params.id, {headers: header})
          .then(response => {
            this.interview = response.data
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
    handleChange(selectedUsers) {
      this.selectedUsers = selectedUsers
    },
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
          let url = '/api/interviews/update/' + this.$route.params.id;
          if (this.selectedUsers.length > 0) {
            this.interview.users = this.selectedUsers;
          } else {
            this.interview.users.forEach((index) => {
              this.selectedUsers.push(index.id)
            })
            this.interview.users = this.selectedUsers;
          }
          this.interview.date = moment(values.date).format('YYYY-MM-DD');
          this.interview.employeeId = values.employee;
          axios.put(url, this.interview, {headers: header}).then((response) => {
            console.log(response)
            this.$router.push({name: 'Interviews'});
          });
        }
      })
    },
  }
}
</script>
