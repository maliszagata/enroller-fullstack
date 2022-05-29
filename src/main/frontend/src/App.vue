<template>
  <div id="app">
    <h1>
      <img src="./assets/logo.svg" alt="Enroller" class="logo">
      System do zapisów na zajęcia
    </h1>
    <div v-if="authenticatedUsername">
      <h2>Witaj {{ authenticatedUsername }}!
        <a @click="logout()" class="float-right  button-outline button">Wyloguj</a>
      </h2>
      <meetings-page :username="authenticatedUsername"></meetings-page>
    </div>
    <div v-else>
        <button @click="registering = false" :class="registering ? 'button button-outline' : ''">Loguję się</button>
        <button @click="registering = true" :class="!registering ? 'button button-outline' : ''">Rejestruję się</button>

        <div v-if="successMessage">{{ successMessage }}</div>
        <div v-if="errorMessage">{{ errorMessage }}</div>

      <login-form @login="login($event)"" v-if="registering === false"></login-form>
      <login-form @login="register($event)"" v-else button-label="Zarejestruj się"></login-form>
    </div>
  </div>
</template>

<script>
    import "milligram";
    import LoginForm from "./LoginForm";
    import MeetingsPage from "./meetings/MeetingsPage";

    export default {
        components: {LoginForm, MeetingsPage},
        data() {
            return {
                authenticatedUsername: "",
                registering: false,
                errorMessage: '',
                successMessage: '',
            };
        },
        methods: {
            login(user) {
                this.authenticatedUsername = user.login;
            },
            logout() {
                this.authenticatedUsername = '';
            },
            register(user) {
            this.successMessage = '';
            this.errorMessage = '';

             this.$http.post('participants', user)
                 .then(response => {
                    this.successMessage = "Super, masz już konto"
                 })
                 .catch(response => {
                    this.errorMessage = "Nie udało się "
                 });
            }
        }
    };
</script>

<style>
  #app {
    max-width: 1000px;
    margin: 0 auto;
  }

  .logo {
    vertical-align: middle;
  }
</style>

