<template>
  <v-card raised outlined v-on:keyup.enter="loginAction()">
    <v-form class="px-12 pt-10 pb-5">
      <v-row>
        <v-text-field
          v-model="user.login"
          rounded
          placeholder="Login"
          prepend-icon="mdi-lock"
          outlined
        />
      </v-row>
      <v-row>
        <v-text-field
          v-model="user.password"
          rounded
          placeholder="Password"
          type="password"
          prepend-icon="mdi-lock"
          outlined
        />
      </v-row>
      <v-row>
        <v-spacer />
        <v-btn @click="loginAction()" rounded>Login</v-btn>
      </v-row>
    </v-form>
    <v-card-actions>
      <v-row justify="center">
        <v-btn class="mb-4" @click="$router.push('register')">Register</v-btn>
      </v-row>
    </v-card-actions>
    <v-snackbar v-model="snackBar" top>
      Invalid login or password
      <v-btn text dark @click="snackBar = false"> Close </v-btn>
    </v-snackbar>
  </v-card>
</template>

<script>
import config from "../config";
import axios from "axios";
export default {
  name: "LoginComponent",
  data: function () {
    return {
      snackBar: false,
      user: {
        login: "",
        password: "",
      },
    };
  },
  methods: {
    async loginAction() {
      axios
        .post(config.API_URL + "/user/login", this.user)
        .then((res) => {
          this.$store.commit("setUser", res.data);
          console.log(res.data);
          this.$router.push({name: 'assignments'});
        })
        .catch(() => this.showErrorMessage());
    },
    showErrorMessage() {
      this.snackBar = true;
    },
  },
};
</script>