<template>
  <v-content>
    <v-row justify="center">
      <v-col cols="8">
        <v-card raised outlined v-on:keyup.enter="registerInApi()">
          <v-stepper v-model="stepper">
            <v-stepper-header>
              <v-stepper-step :complete="stepper > 1" step="1"
                >General information</v-stepper-step
              >
              <v-stepper-step :complete="stepper > 2" step="2"
                >Assignee</v-stepper-step
              >
              <v-stepper-step :complete="stepper > 3" step="3"
                >Time box</v-stepper-step
              >
              <v-stepper-step :complete="stepper > 4" step="4"
                >Confirm</v-stepper-step
              >
            </v-stepper-header>
            <v-stepper-items>
              <v-stepper-content step="1">
                <div style="max-width: 500pt; margin: auto">
                      <v-form
                        v-model="valid"
                        ref="form"
                        class="px-12 pt-10 pb-5"
                      >
                        <v-row>
                          <v-text-field
                            v-model="assignment.name"
                            :rules="nameRules"
                            label="Name"
                            counter="15"
                            required
                          />
                        </v-row>
                        <v-row>
                          <v-textarea
                            v-model="assignment.description"
                            :rules="descriptionRules"
                            label="Description"
                            counter
                          ></v-textarea>
                        </v-row>
                        <v-row>
                          <v-select
                            v-model="assignment.type"
                            :rules="typeRules"
                            :items="types"
                            label="Type"
                            required
                          />
                        </v-row>
                        <v-row>
                          <v-text-field
                            v-model="assignment.points"
                            :rules="pointsRules"
                            label="Points"
                            required
                            type="number"
                          />
                        </v-row>
                        <v-row>
                          <v-spacer />
                          <v-btn @click="stepper = 2" :disabled="!valid"
                            >Next</v-btn
                          >
                        </v-row>
                      </v-form>
                </div>
                
              </v-stepper-content>
              <v-stepper-content step="2">
                <div class="px-12 pt-10 pb-5">
                  <v-row justify="center">
                    <h1>Choose assignee</h1>
                  </v-row>
                  <v-row justify="center">
                    <v-radio-group v-model="assignment.assignee">
                      <v-radio label="Unassigned" :value="myGroup.id"></v-radio>
                      <template v-for="member in myGroupMembers">
                        <v-radio
                          v-bind:key="member.id"
                          :label="member.data.login"
                          :value="member.data.id"
                        ></v-radio>
                      </template>
                    </v-radio-group>
                  </v-row>
                  <v-row justify="end">
                    <v-btn
                      @click="stepper = 3"
                      :disabled="assignment.assignee === ''"
                      >Next</v-btn
                    >
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
                          v-model="beginDate"
                          label="Begin date"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="beginDate"
                        @input="menuBeginDate = false"
                      ></v-date-picker>
                    </v-menu>
                    <v-menu
                      ref="menuBeginTime"
                      v-model="menuBeginTime"
                      :close-on-content-click="false"
                      :nudge-right="40"
                      :return-value.sync="beginTime"
                      transition="scale-transition"
                      offset-y
                      max-width="290px"
                      min-width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="beginTime"
                          label="Begin time"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                        v-if="menuBeginTime"
                        v-model="beginTime"
                        full-width
                        format="24hr"
                        @click:minute="$refs.menuBeginTime.save(beginTime)"
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
                          v-model="endDate"
                          label="End date"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        v-model="endDate"
                        @input="menuEndDate = false"
                      ></v-date-picker>
                    </v-menu>
                    <v-menu
                      ref="menuEndTime"
                      v-model="menuEndTime"
                      :close-on-content-click="false"
                      :nudge-right="40"
                      :return-value.sync="endTime"
                      transition="scale-transition"
                      offset-y
                      max-width="290px"
                      min-width="290px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="endTime"
                          label="End time"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-time-picker
                        v-if="menuEndTime"
                        v-model="endTime"
                        full-width
                        format="24hr"
                        @click:minute="$refs.menuEndTime.save(endTime)"
                      ></v-time-picker>
                    </v-menu>
                  </v-row>
                  <v-row justify="end">
                    <v-btn
                      @click="
                        saveDates();
                        stepper = 4;
                      "
                      :disabled="ifTimesAreNotChosen()"
                      >Next</v-btn
                    >
                  </v-row>
                </div>
              </v-stepper-content>
              <v-stepper-content step="4">
                <v-row justify="center">
                  <div class="px-12 pt-10 pb-5">
                    <v-card width="250px">
                      <v-card raised outlined>
                        <v-card-title>
                          {{ assignment.name }}
                          <v-spacer />
                          {{ assignment.points }}
                        </v-card-title>
                        <v-card-text>
                          <p>{{ assignment.description }}</p>
                          <p>
                            Start: {{ beginDate }} {{ beginTime }} <br />
                            End: {{ endDate }} {{ endTime }} <br />
                          </p>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer />
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
import axios from "axios";
import config from "../config";
export default {
  name: "CreateComponent",
  data: function () {
    return {
      assignment: {
        name: "",
        description: "",
        type: "",
        begin: "",
        end: "",
        assignee: "",
        points: null,
        addedBy: this.$store.state.user.login,
        state: "To Do",
      },
      types: ["Shopping", "Cleaning", "School", "General duties"],
      valid: false,
      nameRules: [
        (v) => !!v || "Name is required",
        (v) => (v && v.length <= 30) || "Name must be less than 30 characters",
      ],
      descriptionRules: [(v) => !!v || "Description is required"],
      typeRules: [
        (v) => !!v || "Type is required",
        (v) => (v && v.length <= 30) || "Type must be less than 30 characters",
      ],
      pointsRules: [
        (v) => !!v || "Points are required",
        (v) =>
          (!isNaN(v) && v > 0 && v <= 20) ||
          "Please provide numeric value from 0 to 20",
      ],
      dialog: false,
      stepper: 1,
      beginDate: null,
      beginTime: "00:00",
      endDate: null,
      endTime: "00:00",
      myGroup: null,
      myGroupMembers: [],
      menuBeginDate: false,
      menuBeginTime: false,
      menuEndDate: false,
      menuEndTime: false,
    };
  },
  created() {
    this.getMyGroupMembersFromApi();
    this.getMyGroupFromApi();
  },
  methods: {
    async registerInApi() {
      let url = config.API_URL + "/user/";
      if (this.assigneeIsGroup()) {
        url = config.API_URL + "/group/";
      }
      axios
        .post(url + this.assignment.assignee + "/assignment", this.assignment, {
          headers: { Authorization: "Bearer " + this.$store.state.user.token },
        })
        .then(() => this.$router.push({ name: "assignments" }));
    },
    assigneeIsGroup() {
      return this.assignment.assignee === this.myGroup.id;
    },
    async getMyGroupFromApi() {
      axios
        .get(config.API_URL + "/group/" + this.$store.state.user.groupId, {
          headers: { Authorization: "Bearer " + this.$store.state.user.token },
        })
        .then((res) => (this.myGroup = res.data));
    },
    async getMyGroupMembersFromApi() {
      axios
        .get(
          config.API_URL + "/group/" + this.$store.state.user.groupId + "/user",
          {
            headers: {
              Authorization: "Bearer " + this.$store.state.user.token,
            },
          }
        )
        .then((res) => (this.myGroupMembers = res.data));
    },
    saveDates() {
      this.assignment.begin = this.beginDate + "T" + this.beginTime;
      this.assignment.end = this.endDate + "T" + this.endTime;
    },
    ifTimesAreNotChosen() {
      return (
        this.beginDate === undefined ||
        this.beginTime === undefined ||
        this.endDate === undefined ||
        this.endTime === undefined
      );
    },
  },
};
</script>