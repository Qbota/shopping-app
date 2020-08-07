<template>
    <v-card raised outlined>
        <v-form class="px-12 pt-10 pb-5">
            <v-row>
                <v-text-field v-model="login" rounded placeholder="Login" prepend-icon="mdi-lock" outlined/>
            </v-row>
            <v-row>
                <v-text-field v-model="password" rounded placeholder="Password" type="password" prepend-icon="mdi-lock" outlined/>
            </v-row>
            <v-row>
                <v-spacer/>
                <v-btn @click="loginAction()" rounded>Login</v-btn>
            </v-row>
        </v-form>
    </v-card>
</template>

<script>
    export default {
        name: "LoginComponent",
        data: () => ({
            login: '',
            password: ''
        }),
        methods: {
            loginAction() {
                console.log(this.login, this.password)
            }
        }
    }
</script>