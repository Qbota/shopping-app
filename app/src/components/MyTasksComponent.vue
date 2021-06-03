<template>
  <v-content>
    <v-row class="my-2" justify="center">
      <div class="filter mx-2">
        <v-select
          v-model="typeFilter"
          color="accent"
          item-color="accent"
          class="accent--text"
          label="type"
          :items="tasks.map((x) => x.type).concat('Any')"
        />
      </div>
      <div class="filter mx-2">
        <v-select
          v-model="stateFilter"
          color="accent"
          item-color="accent"
              class="accent--text"
          label="state"
          :items="tasks.map((x) => x.state).concat('Any')"
        />
      </div>
      <div class="filter mx-2">
        <v-menu
          v-model="menuBeginDate"
          :close-on-content-click="false"
          :nudge-right="40"
          transition="scale-transition"
          color="accent"
              class="accent--text"
          offset-y
          min-width="290px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="beginDateFilter"
              label="Begin date"
              prepend-icon="mdi-calendar"
              readonly
              color="accent"
              item-color="accent"
              class="accent--text"
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
              prepend-icon="mdi-calendar"
              readonly
              color="accent"
              item-color="accent"
              class="accent--text"
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
        <v-btn color="primary" class="accent--text" @click="resetDatesFilters()">Any dates</v-btn>
      </div>
    </v-row>
    <v-row justify="center">
      <template v-for="task in getFilteredTasks()">
        <v-card
          class="mx-3 mb-3 accent--text"
          width="200pt"
          color="primary"
          raised
          outlined
          v-bind:key="task.id"
        >
          <v-card-title>
            {{ task.name }}
            <v-spacer />
            {{ task.points }}
          </v-card-title>
          <v-card-subtitle>Added by {{ task.addedBy }}</v-card-subtitle>
          <v-card-text>
            <p>{{ task.description }}</p>
            <p>
              Added: {{ formatDate(task.begin) }} <br />
              Deadline: {{ formatDate(task.end) }} <br />
              State: {{ task.state }}
            </p>
          </v-card-text>
          <v-card-actions>
            <v-spacer /><v-btn text class="accent--text" @click="launchDialog(task)"
              >Change state</v-btn
            >
          </v-card-actions>
        </v-card>
      </template>
    </v-row>
    <v-dialog v-model="dialog" scrollable max-width="300pt">
      <v-card color="primary" class="accent--text">
        <v-card-title>Choose state</v-card-title>
        <v-card-text>
          <v-radio-group v-model="radioValue">
            <v-radio color="accent" label="To Do" value="To Do"></v-radio>
            <v-radio color="accent" label="In Progress" value="In Progress"></v-radio>
            <v-radio color="accent" label="Done" value="Done"></v-radio>
          </v-radio-group>
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn color="primary" class="accent--text" @click="updateTaskState()">Confirm</v-btn>
          <v-btn color="primary" class="accent--text" @click="closeDialog()">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-content>
</template>

<script>
import axios from "axios";
import config from "../config";
export default {
  name: "MyTasksComponent",
  created() {
    this.getTasksFromApi();
  },
  data: function () {
    return {
      tasks: [],
      dialog: false,
      chosen: null,
      radioValue: null,
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
    async getTasksFromApi() {
      axios
        .get(
          config.API_URL + "/user/" + this.$store.state.user.id + "/assignment",
          {
            headers: {
              Authorization: "Bearer " + this.$store.state.user.token,
            },
          }
        )
        .then((res) => (this.tasks = res.data));
    },
    async updateTaskState() {
      let task = this.chosen;
      task.state = this.radioValue;
      console.log(task);
      await axios.put(config.API_URL + "/assignment", task, {
        headers: { Authorization: "Bearer " + this.$store.state.user.token },
      });
      this.closeDialog();
    },
    getFilteredTasks() {
      return this.tasks
        .filter((x) =>
          this.typeFilter === "Any" ? true : x.type === this.typeFilter
        )
        .filter((x) =>
          this.stateFilter === "Any" ? true : x.state === this.stateFilter
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
    formatDate(value) {
      let split = value.split("T");
      let date = split[0];
      let time = split[1].substring(0, 5);
      return date + " " + time;
    },
    launchDialog(task) {
      this.chosen = task;
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
      this.chosen = null;
    },
  },
};
</script>
<style scoped>
.filter {
  width: 100pt;
}
</style>