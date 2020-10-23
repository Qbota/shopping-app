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
                    <v-textarea v-model="assignment.description" outlined prepend-icon="mdi-account" label="Description" counter rounded></v-textarea>
                  </v-row>
                  <v-row>
                    <v-text-field v-model="assignment.type" :rules="typeRules" label="Type" counter="15" required outlined rounded prepend-icon="mdi-account"/>
                  </v-row>
                  <v-row>
                    <v-spacer/>
                    <v-btn @click="stepper = 2" :disabled="!valid">Next</v-btn>
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
                    <v-btn @click="stepper = 3" :disabled="assignment.assignee === ''">Next</v-btn>
                  </v-row>
                </div>
              </v-stepper-content>
              <v-stepper-content step="3">
                <div class="px-12 pt-10 pb-5">
                  <v-row justify="center">
                    <v-menu
                        v-model="menuBeginDate"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        transition="scale-transition"
                        offset-y
                        min-width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="range[0]"
                            label="Begin date"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                          v-model="range[0]"
                          @input="menuBeginDate = false"
                      ></v-date-picker>
                    </v-menu>
                    <v-menu
                        ref="menuBeginTime"
                        v-model="menuBeginTime"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        :return-value.sync="range[1]"
                        transition="scale-transition"
                        offset-y
                        max-width="290px"
                        min-width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="range[1]"
                            label="Begin time"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="menuBeginTime"
                          v-model="range[1]"
                          full-width
                          format="24hr"
                          @click:minute="$refs.menuBeginTime.save(range[1])"
                      ></v-time-picker>
                    </v-menu>
                  </v-row>
                  <v-row justify="center">
                    <v-menu
                        v-model="menuEndDate"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        transition="scale-transition"
                        offset-y
                        min-width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="range[2]"
                            label="End date"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                          v-model="range[2]"
                          @input="menuEndDate = false"
                      ></v-date-picker>
                    </v-menu>
                    <v-menu
                        ref="menuEndTime"
                        v-model="menuEndTime"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        :return-value.sync="range[3]"
                        transition="scale-transition"
                        offset-y
                        max-width="290px"
                        min-width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="range[3]"
                            label="End time"
                            prepend-icon="mdi-calendar"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                          v-if="menuEndTime"
                          v-model="range[3]"
                          full-width
                          format="24hr"
                          @click:minute="$refs.menuEndTime.save(range[3])"
                      ></v-time-picker>
                    </v-menu>
                  </v-row>
                  <v-row justify="end">
                    <v-btn @click="saveDates();stepper = 4" :disabled="ifTimesAreNotChosen()">Next</v-btn>
                  </v-row>
                </div>
              </v-stepper-content>
              <v-stepper-content step="4">
                <v-row justify="center">
                  <div class="px-12 pt-10 pb-5">
                    <v-card>
                      <v-card raised outlined>
                        <v-card-title>{{ assignment.name }}</v-card-title>
                        <v-card-text>
                          <p>{{assignment.description}}</p>
                          <p>
                            Start: {{range[0]}} {{range[1]}} <br>
                            End: {{range[2]}} {{range[3]}} <br>
                          </p>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer/>
                          <v-btn @click="registerInApi()">Register</v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-card>

                  </div>
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
        assignee: '',
        addedBy: this.$store.state.user.login,
        state: 'To Do'
      },
      valid: false,
      nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 30) || 'Name must be less than 15 characters'
      ],
      descriptionRules: [
        v => !!v || 'Description is required'
      ],
      typeRules: [
        v => !!v || 'Type is required',
        v => (v && v.length <= 30) || 'Type must be less than 15 characters'
      ],
      dialog: false,
      stepper: 1,
      range: [],
      myGroup: null,
      myGroupMembers: [],
      menuBeginDate: false,
      menuBeginTime: false,
      menuEndDate: false,
      menuEndTime: false,
    }
  },
  created() {
    this.getMyGroupMembersFromApi()
    this.getMyGroupFromApi()
  },
  methods: {
    async registerInApi(){
      let url = 'http://localhost:8080/user/'
      if(this.assigneeIsGroup()){
        url = 'http://localhost:8080/group/'
      }
      axios.post(url + this.assignment.assignee + '/assignment', this.assignment, {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
        .then(() => this.$router.push('/main'))
    },
    assigneeIsGroup(){
      return this.assignment.assignee === this.myGroup.id;
    },
    async getMyGroupFromApi(){
      axios.get('http://localhost:8080/group/' + this.$store.state.user.groupId, {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
          .then(res => this.myGroup = res.data)
      console.log(this.range[1])
    },
    async getMyGroupMembersFromApi(){
      axios.get('http://localhost:8080/group/' + this.$store.state.user.groupId + '/user', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
          .then(res => this.myGroupMembers = res.data)
    },
    saveDates(){
      this.assignment.begin = this.range[0] + 'T' + this.range[1]
      this.assignment.end = this.range[2] + 'T' + this.range[3]
    },
    ifTimesAreNotChosen(){
      return this.range[0] === undefined || this.range[1] === undefined || this.range[2] === undefined || this.range[3] === undefined
    }
  }
}
</script>