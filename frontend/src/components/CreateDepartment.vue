<template>
  <div align="center" class="main">
    <a-form
        :form="form"
        class="department-form"
        @submit="handleSubmit"
    >
      <h1 align="center">Создание нового отдела</h1>
      <a-form-item
          v-bind="formItemLayout"
          label="Название"
      >
        <a-input
            v-decorator="[
            'name', {
              rules: [{
                required: true, message: 'Пожалуйста введите название отдела!',
              }],
              }
            ]"
            placeholder="Введите название отдела"
            @blur="handleConfirmBlur"
        />
      </a-form-item>
      <a-form-item
          v-bind="formItemLayout"
          label="Руководитель"
      >
        <a-input
            v-decorator="[
          'supervisor',
          {
            rules: [{
              required: true, message: 'Пожалуйста укажите руководителя отдела!',
            }],
          }
        ]"
            placeholder="Введите руководителя отдела"
            @blur="handleConfirmBlur"
        />
      </a-form-item>
      <a-form-item v-bind="tailFormItemLayout">
        <a-button
            html-type="submit"
            type="primary"
        >
          Отправить
        </a-button>
        <router-link :to="{ name: 'Departments'}" tag="a-button">Отменить</router-link>
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
      id: '',
      department: [],
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
    }
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          let uri = '/api/departments/add';
          this.department.name = values.name;
          this.department.supervisor = values.supervisor;
          const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
          console.log(this.department)
          axios.post(uri, this.department, {headers: header}).then(response => {
            console.log(response)
            this.$router.push({name: 'Departments'});
          }).catch(error => {
            console.log('ERROR: ' + error.response.data)
          })
        }
      })
    },
    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },
  },
}
</script>

<style>
.department-form {
  margin-left: 5%;
  margin-top: 50px;
  max-width: 500px;
}
</style>
