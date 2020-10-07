<template>
<v-content class="ml-7 mr-7">
  <v-row justify="center">
    <h1>{{myGroup.name}}</h1>
  </v-row>
    <v-row justify="center">
      <template v-for="member in members">
        <v-col cols="3" v-bind:key="member.id">
          <v-card raised outlined>
            <v-card-title>{{ member.data.login }}</v-card-title>
            <v-list>
              <v-list-item v-for="(item, i) in member.items" :key="i">
                <v-list-item-content>
                  <v-list-item-title>{{item.name}}</v-list-item-title>
                  <v-list-item-subtitle>{{item.description}} <br>
                    Deadline: {{formatDate(item.end)}} <br>
                    State: {{item.state}}
                  </v-list-item-subtitle>
                </v-list-item-content>
                <v-list-item-action>
                  <v-btn icon @click="launchDialog(item)">
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
                </v-list-item-action>
              </v-list-item>
            </v-list>
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
import axios from 'axios';

export default {
name: "MyGroupComponent",
  created() {
    this.getMyGroupData()
    this.getMyGroupMembers()
  },
  data: function (){
   return{
     myGroup: null,
     members: [],
     dialog: false,
     chosen: null,
     radioValue: null
   }
  },
  methods: {
    async getMyGroupData(){
      axios.get('http://localhost:8080/group/' + this.$store.state.user.groupId, {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
        .then(res => this.myGroup = res.data)
    },
    async getMyGroupMembers(){
      axios.get('http://localhost:8080/group/' + this.$store.state.user.groupId + '/user', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
          .then(res => res.data.forEach((data) => {
            this.members.push({
              data: data,
              items: []
            })
            this.getAssignmentsForMembers()
          }))
    },
    getAssignmentsForMembers(){
      this.members.map(member => member.data.id)
          .forEach(id => this.getAssignmentsForMember(id))
    },
    getAssignmentsForMember(id){
      axios.get('http://localhost:8080/user/' + id + '/assignment', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
          .then(res => {
            this.members.find(x => x.data.id ===id).items = res.data
          })
    },
    launchDialog(task){
      this.chosen = task
      this.dialog = true
    },
    closeDialog(){
      this.dialog = false
      this.chosen = null
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
  }
}
</script>
