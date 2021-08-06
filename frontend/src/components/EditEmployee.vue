<template>
  <div align="center" class="container">
    <div class="card">
      <div class="card-header">
      </div>
      <div class="card-body">
        <a-form
            :form="form"
            class="employee-form"
            @submit="handleSubmit"
        >
          <h1 align="center">Изменение данных сотрудника</h1>
          <a-form-item
              v-bind="formItemLayout"
              label="Имя"
          >
            <a-input v-model="employee.firstName"
                     v-decorator="[ 'firstName', {
                           initialValue: employee.firstName,
                            rules: [{
                              required: true,
                              message: 'Пожалуйста введите имя!',
                            }],
                         }]"
                     placeholder="Введите новое имя"
                     @blur="handleConfirmBlur"
            />
          </a-form-item>
          <a-form-item
              v-bind="formItemLayout"
              label="Фамилия"
          >
            <a-input v-model="employee.lastName"
                     v-decorator="[
                          'lastName', {
                            initialValue: employee.lastName,
                            rules: [{
                              required: true,
                              message: 'Пожалуйста введите фамилию!',
                            }],
                          }
                        ]"
                     placeholder="Введите новую фамилию"
                     @blur="handleConfirmBlur"
            />
          </a-form-item>
          <a-form-item
              v-bind="formItemLayout"
              align="left"
              label="Дата приема на работу"

          >
            <a-date-picker v-model="employee.employmentDate"
                           v-decorator="['employmentDate', {
                              initialValue: moment(employee.employmentDate, dateFormat),
                              rules: [{
                                type: 'date',
                                required: true,
                                message: 'Пожалуйста введите дату приёма!',
                              }],
                            }
                          ]"
                           placeholder="Введите новую дату приёма"
                           style="width: 367px"
            />
          </a-form-item>
          <a-form-item
              v-bind="formItemLayout"
              align="left"
              label="Окончание адаптации"
          >
            <a-date-picker v-model="employee.endOfAdaptation"
                           v-decorator="['endOfAdaptation', {
                                initialValue: moment(employee.endOfAdaptation, dateFormat),
                                rules: [{
                                  required: true,
                                  message: 'Пожалуйста введите конец адаптации!',
                                }],
                               }
                               ]"
                           placeholder="Введите конец адаптации"
                           style="width: 367px"
            >
            </a-date-picker>
          </a-form-item>
          <a-form-item
              v-bind="formItemLayout"
              label="Отдел"
          >
            <a-select v-model="employee.department"
                      v-decorator="[
                          'department', {
                            initialValue: employee.department.name,
                            rules: [{
                              required: true,
                              message: 'Пожалуйста выберите отдел!' }],
                          }]"
                      placeholder="Выберите отдел"
            >
              <a-select-option
                  v-for="department in departments"
                  :key="department.name"
              >
                {{ department.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item
              v-bind="formItemLayout"
              label="Должность"
          >
            <a-input v-model="employee.position"
                     v-decorator="[
                          'position',
                          {
                            initialValue: employee.position,
                            rules: [{
                              required: true,
                              message: 'Пожалуйста введите должность!',
                            }],
                          }
                        ]"
                     placeholder="Введите должность"
                     @blur="handleConfirmBlur"
            />
          </a-form-item>
          <a-form-item
              v-bind="formItemLayout"
              label="Статус"
          >
            <a-select v-model="employee.status"
                      v-decorator="['status', {
                          initialValue: employee.status,
                            rules: [{
                              required: true,
                              message: 'Пожалуйста укажите статус сотрудника!' }],
                          }]"
                      placeholder="Выберите статус"
            >
              <a-select-option value="Проходит адаптацию">Проходит адаптацию</a-select-option>
              <a-select-option value="Адаптация завершена">Адаптация завершена</a-select-option>
              <a-select-option value="Уволен">Уволен</a-select-option>
            </a-select>
          </a-form-item>
          <br>
          <a-form-item v-bind="tailFormItemLayout">
            <a-button
                html-type="submit"
                type="primary"
            >
              Отправить
            </a-button>
            <router-link :to="{ name: 'Employee',params: { id: this.employee.id }}" tag="a-button">
              Отменить
            </router-link>
          </a-form-item>
        </a-form>
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
      dateFormat: 'YYYY-MM-DD',
      employee: [],
      departments: [],
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
    }
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },
  created: function () {
    this.getDepartments();
    this.getEmployee();
  },
  methods: {
    moment,
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.employee.firstName = values.firstName;
          this.employee.lastName = values.lastName;
          this.employee.employmentDate = moment(values.employmentDate).format('YYYY-MM-DD');
          this.employee.endOfAdaptation = moment(values.endOfAdaptation).format('YYYY-MM-DD');
          this.employee.department = values.department;
          this.employee.position = values.position;
          this.employee.status = values.status;
          const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
          let uri = '/api/employees/update/' + this.employee.id;
          axios.put(uri, this.employee, {headers: header}).then((response) => {
            this.$router.push({name: 'Employee', params: {id: response.data.id}});
          });
        }
      })
    },
    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },
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
          })
    },
  },
}
</script>
<style>
.employee-form {
  margin-left: 5%;
  margin-top: 40px;
  max-width: 550px;
}
</style>
