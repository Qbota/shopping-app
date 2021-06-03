<template>
  <v-content class="ml-7 mr-7">
    <v-row justify="center">
      <h1>
        {{ myGroup.name }}
      </h1>
    </v-row>
    <v-row class="my-2" justify="center">
      Invite code: {{ myGroup.inviteCode }}
    </v-row>
    <v-row class="my-2" justify="center">
      <div class="filter mx-2">
        <v-select
          v-model="typeFilter"
          color="accent"
          item-color="accent"
          class="accent--text"
          label="type"
          :items="assignments.map((x) => x.type).concat('Any')"
        />
      </div>
      <div class="filter mx-2">
        <v-select
          v-model="stateFilter"
          color="accent"
          item-color="accent"
          class="accent--text"
          label="state"
          :items="assignments.map((x) => x.state).concat('Any')"
        />
      </div>
      <div class="filter mx-2">
        <v-select
          v-model="assigneeFilter"
          color="accent"
          item-color="accent"
          class="accent--text"
          label="assignee"
          :items="assignments.map((x) => x.assignee).concat('Any')"
        />
      </div>
      <div class="filter mx-2">
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
              v-model="beginDateFilter"
              label="Begin date"
              color="accent"
              item-color="accent"
              class="accent--text"
              prepend-icon="mdi-calendar"
              readonly
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="beginDateFilter"
            color="secondary"
            @input="menuBeginDate = false"
          ></v-date-picker>
        </v-menu>
      </div>
      <div class="filter mx-2">
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
              v-model="endDateFilter"
              label="End date"
              color="accent"
              item-color="accent"
              class="accent--text"
              prepend-icon="mdi-calendar"
              readonly
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="endDateFilter"
            color="secondary"
            @input="menuEndDate = false"
          ></v-date-picker>
        </v-menu>
      </div>
      <div class="mt-3 mx-2">
        <v-btn
          color="primary"
          class="accent--text"
          @click="resetDatesFilters()"
          >Any dates</v-btn
        >
      </div>
    </v-row>
    <v-row justify="center">
      <template v-for="assignment in getFilteredAssignments()">
        <v-card
          color="primary"
          width="200pt"
          raised
          outlined
          v-bind:key="assignment.id"
          class="mx-3 mb-3 accent--text"
        >
          <v-card-title>
            {{ assignment.name }}
            <v-spacer />
            {{ assignment.points }}
          </v-card-title>
          <v-card-subtitle>{{ assignment.type }}</v-card-subtitle>
          <v-card-text>
            <p>
              {{ assignment.description }}
            </p>
            <p>
              Added by: {{ assignment.addedBy }}<br />
              Begin: {{ formatDate(assignment.begin) }}<br />
              End: {{ formatDate(assignment.end) }}<br />
              Current assignee: {{ assignment.assignee }}<br />
              Current state: {{ assignment.state }}
            </p>
          </v-card-text>
        </v-card>
      </template>
    </v-row>
  </v-content>
</template>

<script>
import axios from "axios";
import config from "../config";
export default {
  name: "MyGroupComponent",
  created() {
    this.getMyGroupData();
    this.getMyGroupAssignments();
  },
  data: function () {
    return {
      myGroup: null,
      assignments: [],
      typeFilter: "Any",
      stateFilter: "Any",
      assigneeFilter: "Any",
      beginDateFilter: null,
      endDateFilter: null,
      menuBeginDate: false,
      menuEndDate: false,
    };
  },
  methods: {
    async getMyGroupData() {
      axios
        .get(config.API_URL + "/group/" + this.$store.state.user.groupId, {
          headers: { Authorization: "Bearer " + this.$store.state.user.token },
        })
        .then((res) => (this.myGroup = res.data));
    },
    async getMyGroupAssignments() {
      axios
        .get(
          config.API_URL +
            "/group/" +
            this.$store.state.user.groupId +
            "/assignment/all",
          {
            headers: {
              Authorization: "Bearer " + this.$store.state.user.token,
            },
          }
        )
        .then((res) => (this.assignments = res.data));
    },
    getFilteredAssignments() {
      return this.assignments
        .filter((x) =>
          this.typeFilter === "Any" ? true : x.type === this.typeFilter
        )
        .filter((x) =>
          this.stateFilter === "Any" ? true : x.state === this.stateFilter
        )
        .filter((x) =>
          this.assigneeFilter === "Any"
            ? true
            : x.assignee === this.assigneeFilter
        )
        .filter((x) =>
          this.beginDateFilter === null
            ? true
            : new Date(x.begin) >= new Date(this.beginDateFilter)
        )
        .filter((x) =>
          this.endDateFilter === null
            ? true
            : new Date(x.end) <= new Date(this.endDateFilter)
        );
    },
    resetDatesFilters() {
      this.beginDateFilter = null;
      this.endDateFilter = null;
    },
    formatDate(value) {
      let split = value.split("T");
      let date = split[0];
      let time = split[1].substring(0, 5);
      return date + " " + time;
    },
  },
};
</script>
<style scoped>
.filter {
  width: 100pt;
}
</style>