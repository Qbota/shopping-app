<template>
    <v-app-bar
            app
            color="primary"
            dark
    >
      <v-btn class="mx-1" @click="redirectTo('/main/create')">Create</v-btn>
      <v-spacer/>
        <v-btn class="mx-1" @click="redirectTo('/main/assignments')">Group Assignments</v-btn>
        <v-btn class="mx-1" @click="redirectTo('/main/group')">My Group</v-btn>
        <v-btn class="mx-1" @click="redirectTo('/main/tasks')">My tasks</v-btn>
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