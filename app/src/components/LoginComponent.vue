<template>
    <v-card raised outlined v-on:keyup.enter="loginAction()">
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
      <v-card-actions>
        <v-row justify="center">
          <v-btn @click="$router.push('register')">Register</v-btn>
        </v-row>
      </v-card-actions>
      <v-snackbar v-model="snackBar" top>
        Error during login action
        <v-btn text dark @click="snackBar = false">
          Close
        </v-btn>
      </v-snackbar>
    </v-card>
</template>

<script>
import axios from 'axios'
    export default {
        name: "LoginComponent",
        data: function (){
          return {
            snackBar: false,
            user: {
              login: '',
              password: ''
            }
        }
      },
        methods: {
            async loginAction() {
              axios.post('http://localhost:8080/user/login', this.user)
                  .then((res) => {
                    this.$store.commit('setUser', res.data)
                    console.log(res.data)
                    this.$router.push('main')
                  })
                  .catch(() => this.showErrorMessage())

            },
          showErrorMessage(){
              this.snackBar = true
          }
        }
    }
</script>