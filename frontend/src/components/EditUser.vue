<template>
  <div align="center" class="container">
    <div class="card">
      <div class="card-body">
        <a-avatar icon="user" size="large"/>
        <br><br>
        <h2>Изменение данных пользователя</h2>
        <br>
        <b-alert
            :show="dismissCountDown"
            dismissible
            variant="danger"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged"
        > {{ alertMessage }} <br><br>
        </b-alert>
        <a-form
            :form="form"
            align="center"
            class="register-form"
            @submit="handleSubmit"
        >
          <a-form-item
              v-bind="formItemLayout" label="Имя">
            <a-input v-model="user.name"
                     v-decorator="[
                    'name', {
                        initialValue: user.name,
                        rules: [{
                          required: true, message: 'Пожалуйста введите ваше имя!',
                        }],
                        }
                      ]"
                     placeholder="Введите имя"
                     @blur="handleConfirmBlur"
            />
          </a-form-item>
          <a-form-item v-bind="formItemLayout" label="E-mail">
            <a-input v-model="user.email"
                     v-decorator="[
                  'email',
                  {
                    initialValue: user.email,
                    rules: [{
                      type: 'email', message: 'Вы ввели некоректный E-mail!',
                    }, {
                      required: true, message: 'Пожалуйста введите свой E-mail!',
                    }]
                  }
                ]"
                     placeholder="Введите e-mail"
            />
          </a-form-item>
          <a-form-item
              v-bind="formItemLayout"
              label="Должность"
          >
            <a-input v-model="user.position"
                     v-decorator="[
                      'position',
                      {
                        initialValue: user.position,
                        rules: [{
                          required: true, message: 'Пожалуйста укажите вашу должность!',
                        }],
                      }
                    ]"
                     placeholder="Введите должность"
                     @blur="handleConfirmBlur"
            />
          </a-form-item>
          <a-form-item v-bind="formItemLayout" label="Логин">
            <a-input v-model="user.username"
                     v-decorator="['username', {
                        initialValue: user.username,
                        rules: [{ required: true, message: 'Пожалуйста введите ваш логин', whitespace: true }]
                        }
                     ]"
                     placeholder="Введите логин"
            />
          </a-form-item>
          <a-form-item
              v-bind="formItemLayout"
              label="Пароль"
          >
            <a-input v-model="user.password"
                     v-decorator="[ 'password', {
                       initialValue: user.password,
                       rules: [{
                          required: true, message: 'Пожалуйста введите ваш пароль!',
                       }]
                      }
                    ]"
                     placeholder="Введите пароль"
                     type="password"
            />
          </a-form-item>
          <a-form-item
              v-if="!(this.$store.getters.getId === this.user.id) && this.$store.getters.isAdmin"
              v-bind="formItemLayout"
              label="Уровень доступа"
          >
            <a-select v-model="user.role"
                      v-decorator="['role', {
                      rules: [{ required: true, message: 'Пожалуйста укажите уровень доступа!' }],
                      }]"
                      placeholder="Выберите один из вариантов">
              <a-select-option value="ADMIN">Администратор</a-select-option>
              <a-select-option value="USER">Пользователь</a-select-option>
            </a-select>
          </a-form-item>
          <a-button html-type="submit" type="primary">
            Отправить
          </a-button>
          <router-link :to="{ name: 'Users'}" tag="a-button">
            Отменить
          </router-link>
        </a-form>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  data() {
    return {
      dismissSecs: 5,
      dismissCountDown: 0,
      alertMessage: 'Request error',
      selected: this.$store.getters.getRole,
      user: [],
      currentLogin: '',
      currentEmail: '',
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
    this.getThisUser();
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
          let url = '/api/users/' + this.user.id;
          this.user.name = values.name;
          this.user.email = values.email;
          this.user.username = values.username;
          this.user.password = values.password;
          this.user.position = values.position;
          this.user.sameLogin = this.currentLogin === values.username;
          this.user.sameEmail = this.currentEmail === values.email;
          if (!this.$store.getters.isAdmin) {
            this.user.role = 'USER';
          } else {
            this.user.role = 'ADMIN';
          }
          axios.put(url, this.user, {headers: header})
              .then(() => {
                if (this.user.id === this.$store.getters.getId) {
                  axios.post(`/api/authenticate`, {'username': this.user.username, 'password': this.user.password})
                      .then(response => {
                        console.log(response)
                        this.$store.dispatch('login', {
                          'jwtToken': response.data.jwtToken,
                          'roles': response.data.authorities,
                          'name': response.data.username,
                          'id': response.data.id
                        });
                        this.$router.push({name: 'Users'});
                      }).catch(error => {
                    this.validate(error)
                  })
                }
                this.$router.push({name: 'Users'});
              }).catch(error => {
            this.validate(error)
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
    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },
    getThisUser() {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/users/' + this.$route.params.id, {headers: header})
          .then(response => {
            this.user = response.data
            this.currentLogin = this.user.login;
            this.currentEmail = this.user.email;
          }).catch(() => {
        this.$router.push('/users')
      })
    },
    validate(error) {
      if (error.response.data.problem === "login") {
        this.$data.alertMessage = "Пользователь с таким логином уже есть!"
        this.showAlert();
      } else if (error.response.data.problem === "e-mail") {
        this.$data.alertMessage = "Пользователь с таким e-mail уже есть!"
        this.showAlert();
      }
    },
  }
}

</script>
