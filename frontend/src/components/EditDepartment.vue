<template>
  <div align="center" class="container">
    <div class="card">
      <div class="card-header">
      </div>
      <div class="card-body">
        <h1>Изменение данных отдела</h1>
        <a-form
            :form="form"
            class="interview-form"
            @submit="handleSubmit"
        >
          <a-form-item
              v-bind="formItemLayout"
              label="Название"
          >
            <a-input v-model="department.name"
                     v-decorator="[
                  'name',
                  {
                    initialValue: department.name,
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
            <a-input v-model="department.supervisor"
                     v-decorator="[
                  'supervisor',
                  {
                    initialValue: department.supervisor,
                    rules: [{
                      required: true, message: 'Пожалуйста укажите руководителя отдела!',
                    }],
                  }
                ]"
                     placeholder="Введите руководителя отдела"
                     @blur="handleConfirmBlur"
            />
          </a-form-item>
          <a-button
              html-type="submit"
              type="primary"
          >Отправить
          </a-button>
          <router-link :to="{ name: 'Departments'}" tag="a-button">
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
  name: 'Department',
  data() {
    return {
      department: [],
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
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
          this.department.name = values.name;
          this.department.supervisor = values.supervisor;
          let uri = '/api/departments/' + this.$route.params.id;
          axios.put(uri, this.department, {headers: header}).then((response) => {
            this.$router.push({name: 'Departments'});
            console.log(response)
          });
        }
      })
    },
    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },
    getDepartment: function () {
      const header = {'Authorization': 'Bearer ' + this.$store.getters.getToken};
      axios.get('/api/departments/' + this.$route.params.id, {headers: header})
          .then(response => {
            this.department = response.data
          })
    },
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },
  created() {
    this.getDepartment()
  }
}
</script>

<style scoped>

</style>
