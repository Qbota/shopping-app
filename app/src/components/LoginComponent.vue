<template>
  <v-card
    raised
    outlined
    v-on:keyup.enter="loginAction()"
    color="primary"
    width="300pt"
  >
    <v-form class="px-12 pt-10 pb-5">
      <v-row justify="center" class="welcome">
        <h3>Welcome to House Duties</h3>
        <p>
          This application can help you to manage household duties. Feel free to
          create new account or use credentials below:
        </p>
        test<br />
        test123!
      </v-row>
      <v-row>
        <v-text-field
          v-model="user.login"
          placeholder="Login"
          color="accent"
          prepend-icon="fas fa-user"
        />
      </v-row>
      <v-row>
        <v-text-field
          v-model="user.password"
          placeholder="Password"
          type="password"
          color="accent"
          prepend-icon="fas fa-user-shield"
        />
      </v-row>
      <v-row>
        <v-spacer />
        <v-btn class="accent--text" @click="loginAction()" color="primary"
          >Login</v-btn
        >
      </v-row>
    </v-form>
    <v-card-actions>
      <v-row justify="center">
        <v-btn
          class="mb-4 accent--text"
          @click="$router.push('register')"
          color="primary"
          >Register</v-btn
        >
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
          this.$router.push({ name: "assignments" });
        })
        .catch(() => this.showErrorMessage());
    },
    showErrorMessage() {
      this.snackBar = true;
    },
  },
};
</script>
<style>
.welcome {
  text-align: justify;
  text-justify: inter-word;
}
</style>