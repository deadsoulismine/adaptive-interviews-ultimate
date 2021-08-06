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
  name: 'Index',
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

<style scoped>
h1, h2 {
  font-weight: normal;
}
a {
  color: #42b983;
}
</style>
