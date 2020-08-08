<template>
  <v-card raised outlined>
    <v-form v-model="valid" ref="form" class="px-12 pt-10 pb-5">
      <v-row>
        <v-text-field v-model="user.login" :rules="loginRules" label="Login" counter="15" required outlined rounded prepend-icon="mdi-account"/>
      </v-row>
      <v-row>
        <v-text-field v-model="user.password" :rules="passwordRules" label="Password" counter="15" required outlined rounded prepend-icon="mdi-account-key"/>
      </v-row>
      <v-row>
        <v-text-field v-model="passwordConfirm" :rules="passwordConfirmRules" label="Retype password" counter="15" required outlined rounded prepend-icon="mdi-account-key"/>
      </v-row>
    </v-form>
    <v-card-actions>
      <v-spacer/><v-btn class="mb-2 mr-2 px-5" rounded :disabled="!valid" @click="registerInApi()">Register</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import axios from 'axios'
export default {
name: "RegisterComponent",
  data: function () {
  return{
    valid: false,
    user: {
      login: '',
      password: ''
    },
    passwordConfirm: '',
    loginRules: [
        v => !!v || 'Login is required',
        v => (v && v.length <= 15) || 'Login must be less than 15 characters'
    ],
    passwordRules: [
        v => !!v || 'Password is required',
        v => (v && v.length > 8) || 'Password must be longer than 8 characters'
    ],
    passwordConfirmRules:[
        v => !!v || 'Please confirm password',
        v => (v && v === this.user.password) || 'Passwords are not matching'
    ]
  }
  },
  methods: {
    async registerInApi(){
      axios.post('http://localhost:8080/user/register', this.user)
        .then((res) => this.$store.commit('setToken', res.data.token))
        .catch((err) => console.log(err))
    }
  }
}
</script>