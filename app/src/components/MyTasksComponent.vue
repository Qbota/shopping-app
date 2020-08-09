<template>
  <v-content>
    <v-row justify="center">
      <template v-for="task in tasks">
        <v-col cols="3" v-bind:key="task.id">
          <v-card raised outlined>
            <v-card-title>{{ task.name }}</v-card-title>
            <v-card-subtitle>Added by {{task.addedBy}}</v-card-subtitle>
            <v-card-text>
              <p>{{task.description}}</p>
              <p>
                Added: {{formatDate(task.begin)}} <br>
                Deadline: {{formatDate(task.end)}} <br>
                State: {{task.state}}
              </p>
            </v-card-text>
            <v-card-actions>
              <v-spacer/><v-btn text>Change state</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </template>
    </v-row>
    <v-dialog v-model="dialog" scrollable max-width="300pt">
      <v-card>
        <v-card-title>Choose state</v-card-title>
        <v-list>
          <v-list-item>Done</v-list-item>
          <v-list-item>In progress</v-list-item>
        </v-list>
      </v-card>
    </v-dialog>
  </v-content>
</template>

<script>
import axios from "axios";

export default {
name: "MyTasksComponent",
  created() {
    this.getTasksFromApi()
  },
  data: function (){
    return {
      tasks: [],
      dialog: true
    }
  },
  methods: {
    async getTasksFromApi(){
      axios.get('http://localhost:8080/user/' + this.$store.state.user.id + '/assignment', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
      .then(res => this.tasks = res.data)
    },
    formatDate(value){
      let splitted = value.split('T')
      let date = splitted[0]
      let time = splitted[1].substring(0,5)
      return date + ' ' +time
    }
  }
}
</script>
