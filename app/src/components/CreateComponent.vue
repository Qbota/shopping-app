<template>
  <v-content>
    <v-row justify="center">
      <v-col cols="8">
        <v-card raised outlined v-on:keyup.enter="registerInApi()">
          <v-stepper v-model="stepper">
            <v-stepper-header>
              <v-stepper-step :complete="stepper > 1" step="1">General information</v-stepper-step>
              <v-stepper-step :complete="stepper > 2" step="2">Time box</v-stepper-step>
              <v-stepper-step :complete="stepper > 3" step="3">Confirm</v-stepper-step>
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
                    <v-text-field v-model="assignment.assignee" :rules="assigneeRules" label="Assignee" counter="15" required outlined rounded prepend-icon="mdi-account"/>
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
                    <v-date-picker range v-model="range"></v-date-picker>
                  </v-row>
                  <v-row justify="end">
                    <v-btn @click="stepper = 3">Next</v-btn>
                  </v-row>
                </div>
              </v-stepper-content>
              <v-stepper-content step="3">
                <v-row justify="center">
                <v-card>
                  <v-card-title>Summary</v-card-title>
                  <v-card-text>{{assignment.name}}</v-card-text>
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
export default {
name: "CreateComponent",
  data: function (){
    return{
      assignment: {},
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
      range: []


    }
  },
  methods: {
    async registerInApi(){
      console.log(this.assignment)
    }
  }
}
</script>