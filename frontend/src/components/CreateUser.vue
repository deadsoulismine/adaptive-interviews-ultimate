<template>
  <a-form
      :form="form"
      class="register-form"
      @submit="handleSubmit"
  >
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
          @blur="handleConfirmBlur"
      />
    </a-form-item>
    <a-form-item
        v-bind="formItemLayout"
        label="E-mail"
    >
      <a-input
          v-decorator="[
          'email',
          {
            rules: [{
              type: 'email', message: 'Вы ввели некоректный E-mail!',
            }, {
              required: true, message: 'Пожалуйста введите свой E-mail!',
            }]
          }
        ]"
      />
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
          @blur="handleConfirmBlur"
      />
    </a-form-item>
    <a-form-item
        v-bind="formItemLayout"
    >
      <!--      <span>-->
      <template v-slot:babel>
        Логин
      </template>
      <a-tooltip title="Используется только для входа">
        <a-icon type="question-circle-o"/>
      </a-tooltip>
      <!--      </span>-->
      <a-input
          v-decorator="[
          'username',
          {
            rules: [{ required: true, message: 'Пожалуйста введите ваш логин', whitespace: true }]
          }
        ]"
      />
    </a-form-item>
    <a-form-item
        v-bind="formItemLayout"
        label="Пароль"
    >
      <a-input
          v-decorator="[
          'password',
          {
            rules: [{
              required: true, message: 'Пожалуйста введите ваш пароль!',
            }, {
              validator: validateToNextPassword,
            }],
          }
        ]"
          type="password"
      />
    </a-form-item>
    <a-form-item
        v-bind="formItemLayout"
        label="Подтвердите пароль"
    >
      <a-input
          v-decorator="[
          'confirm',
          {
            rules: [{
              required: true, message: 'Пожалуйста повторите ваш пароль!',
            }, {
              validator: compareToFirstPassword,
            }],
          }
        ]"
          type="password"
          @blur="handleConfirmBlur"
      />
    </a-form-item>

    <a-form-item v-bind="tailFormItemLayout">
      <a-button
          html-type="submit"
          type="primary"
      >
        Зарегистрироваться
      </a-button>
    </a-form-item>
  </a-form>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      user: {
        role: 'USER',
      },
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
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          let uri = '/users/add';
          this.user.name = values.firstname + ' ' + values.lastname;
          this.user.email = values.email;
          this.user.password = values.password;
          this.user.position = values.position;
          this.user.username = values.username;
          axios.post(uri, this.user).then((response) => {
            this.$data.user = response.data();
            this.$router.push({name: 'Login'});
          });
        }
      })
    },

    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },
    compareToFirstPassword(rule, value, callback) {
      const form = this.form;
      if (value && value !== form.getFieldValue('password')) {
        callback('Введенные пароли не совпадают!');
      } else {
        callback();
      }
    },
    validateToNextPassword(rule, value, callback) {
      const form = this.form;
      if (value && this.confirmDirty) {
        form.validateFields(['confirm'], {force: true});
      }
      callback();
    },
  },
};
</script>
<style>
.register-form {
  margin-left: 28%;
  margin-top: 50px;
  max-width: 500px;
}
</style>
