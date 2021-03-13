<template>
<v-content class="ml-7 mr-7">
  <v-row justify="center">
    <h1>
      {{myGroup.name}}
    </h1>
  </v-row>
  <v-row justify="center">
      Invite code: {{myGroup.inviteCode}}
  </v-row>
    <v-row justify="center">
      <template v-for="assignment in assignments">
        <v-col cols="3" v-bind:key="assignment.id">
          <v-card raised outlined>
            <v-card-title>{{assignment.name}}</v-card-title>
            <v-card-subtitle>{{assignment.type}}</v-card-subtitle>
            <v-card-text>
              <p>
                {{assignment.description}}
              </p>
              <p>
                Added by: {{assignment.addedBy}}<br>
                Begin: {{formatDate(assignment.begin)}}<br>
                End: {{formatDate(assignment.end)}}<br>
                Current assignee: {{assignment.assignee}}<br>
                Current state: {{assignment.state}}
              </p>
            </v-card-text>
          </v-card>
        </v-col>
      </template>
    </v-row>
</v-content>
</template>

<script>
import axios from 'axios';
import config from "../config";
export default {
name: "MyGroupComponent",
  created() {
    this.getMyGroupData()
    this.getMyGroupAssignments()
  },
  data: function (){
   return{
     myGroup: null,
     assignments: []
   }
  },
  methods: {
    async getMyGroupData(){
      axios.get(config.API_URL + '/group/' + this.$store.state.user.groupId, {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
        .then(res => this.myGroup = res.data)
    },
    async getMyGroupAssignments(){
      axios.get(config.API_URL + '/group/' + this.$store.state.user.groupId + '/assignment/all', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
      .then(res => this.assignments = res.data)
    },
    formatDate(value){
      let split = value.split('T')
      let date = split[0]
      let time = split[1].substring(0,5)
      return date + ' ' +time
    }
  }
}
</script>
