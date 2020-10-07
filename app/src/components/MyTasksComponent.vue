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
              <v-spacer/><v-btn text @click="launchDialog(task)">Change state</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </template>
    </v-row>
    <v-dialog v-model="dialog" scrollable max-width="300pt">
      <v-card>
        <v-card-title>Choose state</v-card-title>
        <v-card-text>
          <v-radio-group v-model="radioValue">
            <v-radio label="To Do" value="To Do"></v-radio>
            <v-radio label="In Progress" value="In Progress"></v-radio>
            <v-radio label="Done" value="Done"></v-radio>
          </v-radio-group>
        </v-card-text>
        <v-card-actions>
          <v-spacer/>
          <v-btn @click="updateTaskState()">Confirm</v-btn>
          <v-btn @click="closeDialog()">Cancel</v-btn>
        </v-card-actions>
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
      dialog: false,
      chosen: null,
      radioValue: null
    }
  },
  methods: {
    async getTasksFromApi(){
      axios.get('http://localhost:8080/user/' + this.$store.state.user.id + '/assignment', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
      .then(res => this.tasks = res.data)
    },
    async updateTaskState(){
      let task = this.chosen
      task.state = this.radioValue
      console.log(task)
      await axios.put('http://localhost:8080/assignment', task,{headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
      this.closeDialog()
    },
    formatDate(value){
      let split = value.split('T')
      let date = split[0]
      let time = split[1].substring(0,5)
      return date + ' ' +time
    },
    launchDialog(task){
      this.chosen = task
      this.dialog = true
    },
    closeDialog(){
      this.dialog = false
      this.chosen = null
    }
  }
}
</script>
