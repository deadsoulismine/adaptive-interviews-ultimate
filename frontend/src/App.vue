<template>
  <div id="app">
    <div align="center">
      <a-menu mode="horizontal">
        <a-menu-item key="employees">
          <router-link to="/employees">
            <a-icon theme="filled" type="android"/>
            Сотрудники
          </router-link>
        </a-menu-item>
        <a-menu-item key="interviews">
          <router-link to="/interviews">
            <a-icon theme="outlined" type="wechat"/>
            Беседы
          </router-link>
        </a-menu-item>
        <a-menu-item key="users">
          <router-link to="/users">
            <a-icon type="team"/>
            Пользователи
          </router-link>
        </a-menu-item>
        <a-menu-item key="departments">
          <router-link to="/departments">
            <a-icon type="cluster"/>
            Отделы
          </router-link>
        </a-menu-item>
        <a-menu-item v-if="!this.$store.getters.isAuthenticated" key="login">
          <router-link to="/login">
            <a-icon type="login"/>
            Войти
          </router-link>
        </a-menu-item>

        <a-menu-item v-if="(this.$store.getters.isAuthenticated)" key="logout">
          <a v-if="this.$store.getters.isAuthenticated" class="nav-link text-light" href="#" v-on:click="logout">
            <a-icon type="logout"/>
            Выйти
          </a>
        </a-menu-item>

        <a-menu-item v-if="(this.$store.getters.isAuthenticated && this.$store.getters.getUsername)" key="user"
                     disabled>
          <a-icon type="user"/>
          Вы вошли как {{ this.$store.getters.getUsername }}
        </a-menu-item>
      </a-menu>
    </div>
    <h3></h3>
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
  methods: {
    logout() {
      this.$store.dispatch('logout');
      this.$router.push('/login')
    }
  }
}
</script>

<style>

html {
  background: lightgray !important;
  min-height: 100%;
  padding-left: 50px;
  padding-right: 50px;
}

body {
  overflow-x: hidden;
}

p, h1, h2, h3, h4, h5 {
  overflow-wrap: normal;
  white-space: normal;
}

th {
  cursor: pointer;
  width: 600px !important;
  white-space: nowrap;
  padding: 15px;
  background: lightgray;
  border: black;
}

tr {
  white-space: nowrap;
}

td {
  padding: 5px;
}

.table-responsive {
}

.first {
  background-color: lightsalmon
}

.second {
  background-color: lightgreen
}

.third {
  background-color: #dfbd00;
}

</style>
