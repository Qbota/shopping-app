<template>
  <v-card raised outlined color="primary" width="300pt">
    <v-form v-model="valid" ref="form" class="px-12 pt-10 pb-5">
      <v-row>
        <v-text-field
          v-model="user.login"
          :rules="loginRules"
          label="Login"
          counter="15"
          required
          color="accent"
          prepend-icon="fas fa-user"
        />
      </v-row>
      <v-row>
        <v-text-field
          v-model="user.password"
          :rules="passwordRules"
          label="Password"
          counter="15"
          type="password"
          required
          color="accent"
          prepend-icon="fas fa-user-shield"
        />
      </v-row>
      <v-row>
        <v-text-field
          v-model="passwordConfirm"
          :rules="passwordConfirmRules"
          label="Retype password"
          counter="15"
          type="password"
          required
          color="accent"
          prepend-icon="fas fa-user-shield"
        />
      </v-row>
      <v-row>
        It's a side project, so please don't use any real credentials
      </v-row>
    </v-form>
    <v-card-actions>
      <v-spacer /><v-btn
        class="mb-2 mr-2 px-5 accent--text"
        :disabled="!valid"
        :loading="loading"
        color="primary"
        @click="registerInApi()"
        >Register</v-btn
      >
    </v-card-actions>
    <v-snackbar v-model="snackBar" top>
      Login already taken
      <v-btn text dark @click="snackBar = false"> Close </v-btn>
    </v-snackbar>
  </v-card>
</template>

<script>
import axios from "axios";
import config from "../config";
export default {
  name: "RegisterComponent",
  data: function () {
    return {
      valid: false,
      snackBar: false,
      loading: false,
      user: {
        login: "",
        password: "",
      },
      passwordConfirm: "",
      loginRules: [
        (v) => !!v || "Login is required",
        (v) => (v && v.length <= 15) || "Login must be less than 15 characters",
      ],
      passwordRules: [
        (v) => !!v || "Password is required",
        (v) =>
          (v && v.length >= 8) || "Password must be longer than 8 characters",
      ],
      passwordConfirmRules: [
        (v) => !!v || "Please confirm password",
        (v) => (v && v === this.user.password) || "Passwords are not matching",
      ],
    };
  },
  methods: {
    async registerInApi() {
      this.loading = true
      axios
        .post(config.API_URL + "/user/register", this.user)
        .then((res) => {
          this.loading = false
          this.$store.commit("setUser", res.data);
          this.$router.push({name: 'assignments'});
        })
        .catch(() => {
          this.loading = false
          this.snackBar = true
        });
    },
  },
};
</script>