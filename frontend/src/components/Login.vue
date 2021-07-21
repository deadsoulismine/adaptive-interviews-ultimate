<template>

  <a-form
      id="components-form-demo-normal-login"
      :form="form"
      class="login-form"
      @submit="handleSubmit"
  >
    <b-alert
        :show="dismissCountDown"
        dismissible
        variant="danger"
        @dismissed="dismissCountDown=0"
        @dismiss-count-down="countDownChanged"
    > {{ alertMessage }}
    </b-alert>
    <a-form-item>
      <a-input
          v-decorator="[
          'username',
          { rules: [{ required: true, message: 'Пожалуйста введите ваш логин!' }] }
        ]"
          placeholder="Логин"
      >
        <template v-slot:prefix
                  style="color: rgba(0,0,0,.25)"
                  type="user"
        />
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-input
          v-decorator="[
          'password',
          { rules: [{ required: true, message: 'Пожалуйста введите ваш пароль!' }] }
        ]"
          placeholder="Пароль"
          type="password"
      >
        <template v-slot:prefix
                  style="color: rgba(0,0,0,.25)"
                  type="lock"
        />
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-button
          class="login-form-button"
          html-type="submit"
          type="primary"
      >
        Войти
      </a-button>
      Или
      <router-link :to="{ name: 'CreateUser'}">зарегистрируйтесь!</router-link>
    </a-form-item>
  </a-form>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      dismissSecs: 5,
      dismissCountDown: 0,
      alertMessage: 'Request error',
    }
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);

  },
  name: 'Login',
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          axios.post(`/api/authenticate`, {'username': values.username, 'password': values.password})
              .then(response => {
                this.$store.dispatch('login', {
                  'jwtToken': response.data.jwtToken,
                  'roles': response.data.authorities,
                  'name': response.data.username
                });
                this.$router.push('employees')

              }).catch(error => {
            localStorage.removeItem('user-jwtToken');
            this.$data.alertMessage = "Вы указали неверный логин или пароль"
            this.showAlert();
            console.log('ERROR: ' + error.response.data)
          })
        }
      })
    },
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown
    },
    showAlert() {
      this.dismissCountDown = this.dismissSecs
    },
  },
}
</script>

<style>
.login-form {
  margin-left: 38%;
  margin-top: 50px;
  max-width: 300px;
}
</style>
