<template>
  <div align="center">
    <a-form
        :form="form"
        class="employee-form"
        @submit="handleSubmit"
    >
      <h1 align="center">Создание нового сотрудника</h1>
      <a-form-item
          v-bind="formItemLayout"
          label="Имя"
      >
        <a-input
            v-decorator="[
          'firstname',
          {
            rules: [{
              required: true, message: 'Пожалуйста введите ваше имя!',
            }],
          }
        ]"
            placeholder="Введите имя"
            @blur="handleConfirmBlur"
        />
      </a-form-item>
      <a-form-item
          v-bind="formItemLayout"
          label="Фамилия"
      >
        <a-input
            v-decorator="[
          'lastname',
          {
            rules: [{
              required: true, message: 'Пожалуйста введите вашу фамилию!',
            }],
          }
        ]"
            placeholder="Введите фамилию"
            @blur="handleConfirmBlur"
        />
      </a-form-item>
      <a-form-item
          v-bind="formItemLayout"
          align="left"
          label="Дата приема на работу"
      >
        <a-date-picker
            v-decorator="['employmentDate', {
            rules: [{ required: true, message: 'Пожалуйста укажите дату приема на работу!' }],
          }]"
            disabledTime
            format="YYYY-MM-DD"
            placeholder="Выберите дату">

        </a-date-picker>
      </a-form-item>
      <a-form-item
          v-bind="formItemLayout"
          align="left"
          label="Окончание адаптации"
      >
        <a-date-picker
            v-decorator="['endOfAdaptation', {
            rules: [{ required: true, message: 'Пожалуйста укажите дату предпологаемого окончания адапьтации!' }],
          }]"
            disabledTime
            format="YYYY-MM-DD"
            placeholder="Выберите дату"></a-date-picker>
      </a-form-item>
      <a-form-item
          v-bind="formItemLayout"
          label="Отдел"
      >
        <a-select
            v-decorator="[
          'department', {
            rules: [{ required: true, message: 'Пожалуйста выберите отдел!' }],
          }]"
            placeholder="Выберите отдел"
        >
          <a-select-option v-for="department in departments" :key="department.name">
            {{ department.name }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item
          v-bind="formItemLayout"
          label="Должность"
      >
        <a-input
            v-decorator="[
          'position',
          {
            rules: [{
              required: true, message: 'Пожалуйста укажите вашу должность!',
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
        <a-select
            v-decorator="[
          'status', {
            rules: [{ required: true, message: 'Пожалуйста укажите статус сотрудника!' }],
          }]"
            placeholder="Выберите статус"
        >
          <a-select-option value="Проходит адаптацию">Проходит адаптацию</a-select-option>
          <a-select-option value="Адаптация завершена">Адаптация завершена</a-select-option>
          <a-select-option value="Уволен">Уволен</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="tailFormItemLayout">
        <a-button
            html-type="submit"
            type="primary"
        >
          Добавить сотрудника
        </a-button>
        <router-link :to="{ name: 'Employees'}" tag="a-button">Отменить</router-link>
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
      employee: {},
      errors: [],
      departments: [],
      id: '',
      confirmDirty: false,
      autoCompleteResult: [],
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
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);

  },
  created: function () {
    this.getDepartments();
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          let uri = '/api/employees/add';
          const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
          this.employee.firstName = values.firstname;
          this.employee.lastName = values.lastname;
          this.employee.employmentDate = moment(values.employmentDate).format('YYYY-MM-DD');
          this.employee.endOfAdaptation = moment(values.endOfAdaptation).format('YYYY-MM-DD');
          this.employee.department = values.department;
          this.employee.position = values.position;
          this.employee.status = values.status;
          axios.post(uri, this.employee, {headers: header}).then((response) => {
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
  },
};
</script>
<style>
.employee-form {
  margin-left: 5%;
  margin-top: 40px;
  max-width: 550px;
}
</style>
