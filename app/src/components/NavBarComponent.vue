<template>
    <v-app-bar
            app
            color="primary"
            dark
    >
      <v-btn class="mx-1" @click="redirectTo('/main/create')">Create</v-btn>
      <v-spacer/>
        <v-btn class="mx-1" @click="redirectTo('/main/assignments')">Manage household duties</v-btn>
        <v-btn class="mx-1" @click="redirectTo('/main/group')">My Household</v-btn>
        <v-btn class="mx-1" @click="redirectTo('/main/tasks')">My Duties</v-btn>
        <!-- <v-btn class="mx-1" @click="redirectTo('/main/tasks')">Ranking</v-btn> -->
        <v-btn class="mx-1" @click="logout()">Logout</v-btn>
    </v-app-bar>
</template>

<script>
    export default {
      name: "NavBarComponent",
      methods: {
        redirectTo(path){
          this.$router.push(path)
        },
        logout(){
          this.$store.commit('setUser',null)
          this.$router.push('/login')
        }
      }
    }
</script>