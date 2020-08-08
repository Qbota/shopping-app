<template>
    <v-card raised outlined>
        <v-form class="px-12 pt-10 pb-5">
            <v-row>
                <v-text-field v-model="user.login" rounded placeholder="Login" prepend-icon="mdi-lock" outlined/>
            </v-row>
            <v-row>
                <v-text-field v-model="user.password" rounded placeholder="Password" type="password" prepend-icon="mdi-lock" outlined/>
            </v-row>
            <v-row>
                <v-spacer/>
                <v-btn @click="loginAction()" rounded>Login</v-btn>
            </v-row>
        </v-form>
    </v-card>
</template>

<script>
import axios from 'axios'
    export default {
        name: "LoginComponent",
        data: function (){
          return {
            user: {
              login: '',
              password: ''
            }
        }
      },
        methods: {
            async loginAction() {
              axios.post('http://localhost:8080/user/login', this.user)
                  .then((res) => this.$store.commit('setToken', res.data.token))
                  .catch((err) => console.log(err))
            }
        }
    }
</script>