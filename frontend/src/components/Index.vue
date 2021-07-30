<template>
  <div align="center" class="hello">
    <h2>Добро пожаловать в "Адаптивные беседы"</h2>
    <h2 v-if="!this.$store.getters.isAuthenticated">Для продолжения работы:</h2>
    <router-link v-if="!this.$store.getters.isAuthenticated" tag="a-button" to="/login">
      Войдите или зарегистрируйтесь
    </router-link>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {}
  },
  mounted() {
    if (this.$store.getters.isAuthenticated) {
      this.$store.state.reset;
      this.$router.push("Employees");
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
