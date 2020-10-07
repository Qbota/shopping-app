<template>
  <v-content>
    <v-row justify="center">
      <v-col cols="8">
        <v-card raised outlined v-on:keyup.enter="registerInApi()">
          <v-stepper v-model="stepper">
            <v-stepper-header>
              <v-stepper-step :complete="stepper > 1" step="1">General information</v-stepper-step>
              <v-stepper-step :complete="stepper > 2" step="2">Assignee</v-stepper-step>
              <v-stepper-step :complete="stepper > 3" step="3">Time box</v-stepper-step>
              <v-stepper-step :complete="stepper > 4" step="4">Confirm</v-stepper-step>
            </v-stepper-header>
            <v-stepper-items>
              <v-stepper-content step="1">
                <v-form v-model="valid" ref="form" class="px-12 pt-10 pb-5">
                  <v-row>
                    <v-text-field v-model="assignment.name" :rules="nameRules" label="Name" counter="15" required outlined rounded prepend-icon="mdi-account"/>
                  </v-row>
                  <v-row>
                    <v-text-field v-model="assignment.description" :rules="descriptionRules" label="Description" counter="15" required outlined rounded prepend-icon="mdi-account"/>
                  </v-row>
                  <v-row>
                    <v-text-field v-model="assignment.type" :rules="typeRules" label="Type" counter="15" required outlined rounded prepend-icon="mdi-account"/>
                  </v-row>
                  <v-row>
                    <v-spacer/>
                    <v-btn @click="stepper = 2">Next</v-btn>
                  </v-row>
                </v-form>
              </v-stepper-content>
              <v-stepper-content step="2">
                <div class="px-12 pt-10 pb-5">
                  <v-row justify="center">
                    <v-radio-group v-model="assignment.assignee">
                      <v-radio :label="'My Group: '+myGroup.name" :value="myGroup.id"></v-radio>
                      <template v-for="member in myGroupMembers">
                        <v-radio v-bind:key="member.id" :label="member.data.login" :value="member.data.id"></v-radio>
                      </template>
                    </v-radio-group>
                  </v-row>
                  <v-row justify="end">
                    <v-btn @click="stepper = 3">Next</v-btn>
                  </v-row>
                </div>
              </v-stepper-content>
              <v-stepper-content step="3">
                <div class="px-12 pt-10 pb-5">
                  <v-row justify="center">
                    <v-date-picker range v-model="range"></v-date-picker>
                  </v-row>
                  <v-row justify="end">
                    <v-btn @click="saveDates();stepper = 4">Next</v-btn>
                  </v-row>
                </div>
              </v-stepper-content>
              <v-stepper-content step="4">
                <v-row justify="center">
                <v-card>
                  <v-card-title>Summary</v-card-title>
                  <v-card-text>{{assignment}}</v-card-text>
                  <v-card-actions>
                    <v-btn>Register</v-btn>
                  </v-card-actions>
                </v-card>
                </v-row>
              </v-stepper-content>
            </v-stepper-items>
          </v-stepper>
        </v-card>
      </v-col>
    </v-row>
  </v-content>
</template>

<script>
import axios from 'axios'
export default {
name: "CreateComponent",
  data: function (){
    return{
      assignment: {
        name: '',
        description: '',
        type: '',
        begin: '',
        end: '',
        assignee: ''
      },
      valid: false,
      nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 15) || 'Login must be less than 15 characters'
      ],
      descriptionRules: [
        v => !!v || 'Description is required',
        v => (v && v.length <= 15) || 'Login must be less than 15 characters'
      ],
      typeRules: [
        v => !!v || 'Type is required',
        v => (v && v.length <= 15) || 'Login must be less than 15 characters'
      ],
      assigneeRules: [
        v => !!v || 'Assignee is required',
        v => (v && v.length <= 15) || 'Login must be less than 15 characters'
      ],
      dialog: false,
      stepper: 1,
      range: [],
      myGroup: null,
      myGroupMembers: []
    }
  },
  created() {
    this.getMyGroupMembersFromApi()
    this.getMyGroupFromApi()
  },
  methods: {
    async registerInApi(){
      console.log(this.assignment)
    },
    async getMyGroupFromApi(){
      axios.get('http://localhost:8080/group/' + this.$store.state.user.groupId, {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
          .then(res => this.myGroup = res.data)
    },
    async getMyGroupMembersFromApi(){
      axios.get('http://localhost:8080/group/' + this.$store.state.user.groupId + '/user', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
          .then(res => this.myGroupMembers = res.data)
    },
    saveDates(){
      this.assignment.begin = this.range[0]
      this.assignment.end = this.range[1]
    }
  }
}
</script>