<template>
    <v-content>
      <v-container fluid>
        <v-row justify="center">
          <v-list two-line color="secondary" elevation="1">
            <v-subheader> Not assigned </v-subheader>
            <draggable
              v-model="groupItems"
              :options="{ group: 'people' }"
              style="min-height: 10px"
              @change="addedToGroup($event)"
            >
              <template v-for="item in groupItems">
                <v-card
                  width="150pt"
                  raised
                  outlined
                  color="primary"
                  v-bind:key="item.id"
                  class="mx-3 mb-3 accent--text"
                  v-if="item.state.toLowerCase() !== 'done'"
                >
                  <v-card-title
                    @click="showDetails(item)"
                    style="cursor: pointer"
                  >
                    {{ item.name }}
                    <v-spacer />
                    {{ item.points }}
                  </v-card-title>
                  <v-card-subtitle>{{ item.type }}</v-card-subtitle>
                  <v-card-text>
                    <p>
                      End: {{ formatDate(item.end) }}<br />
                      Current state: {{ item.state }}
                      <v-btn icon @click="launchchangeStateDialog(item)">
                        <v-icon>mdi-pencil</v-icon>
                      </v-btn>
                    </p>
                  </v-card-text>
                </v-card>
              </template>
            </draggable>
          </v-list>
        </v-row>
        <v-row justify="center">
          <template v-for="member in members">
            <v-list
              class="mx-3 my-3"
              v-bind:key="member.id"
              two-line
              color="secondary"
              elevation="1"
              width=""
            >
              <v-subheader>
                Duties assigned to {{ member.data.login }}
              </v-subheader>
              <draggable
                v-model="member.items"
                :options="{ group: 'people' }"
                style="min-height: 10px"
                @change="addedToUser($event, member)"
              >
                <template v-for="item in member.items">
                  <v-card
                    width="150pt"
                    raised
                    outlined
                    color="primary"
                    v-bind:key="item.id"
                    class="mx-3 mb-3 accent--text"
                    v-if="item.state.toLowerCase() !== 'done'"
                  >
                    <v-card-title
                      @click="showDetails(item)"
                      style="cursor: pointer"
                    >
                      {{ item.name }}
                      <v-spacer />
                      {{ item.points }}
                    </v-card-title>
                    <v-card-subtitle>{{ item.type }}</v-card-subtitle>
                    <v-card-text>
                      <p>
                        End: {{ formatDate(item.end) }}<br />
                        Current state: {{ item.state }}
                      </p>
                    </v-card-text>
                  </v-card>
                </template>
              </draggable>
            </v-list>
          </template>
        </v-row>
      </v-container>
      <v-dialog v-model="changeStateDialog" scrollable max-width="300pt">
        <v-card color="secondary" class="accent--text">
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
            <v-btn color="primary" class="accent--text" @click="closechangeStateDialog()">Cancel</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="detailsDialog" width="300pt">
        <v-card>
          <div v-if="detailedItem">
            <v-card-title>
              {{ detailedItem.name }}
              <v-spacer />
              {{ detailedItem.points }}
            </v-card-title>
            <v-card-subtitle>{{ detailedItem.type }}</v-card-subtitle>
            <v-card-text>
              <p>
                {{ detailedItem.description }}
              </p>
              <p>
                Added by: {{ detailedItem.addedBy }}<br />
                Begin: {{ formatDate(detailedItem.begin) }}<br />
                End: {{ formatDate(detailedItem.end) }}<br />
                Current state: {{ detailedItem.state }}
              </p>
            </v-card-text>
          </div>
          <v-card-actions>
            <v-spacer/>
            <v-btn @click="closeDetails()"> Close </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-content>
</template>

<script>
import draggable from "vuedraggable";
import axios from "axios";
import config from "../config";
export default {
  components: {
    draggable,
  },
  created() {
    this.getGroupItemsFromDb();
    this.getGroupMembersFromApi();
  },
  methods: {
    async addedToUser(event, user) {
      if (event.removed == null) {
        await axios.put(
          config.API_URL +
            "/assignment/" +
            event.added.element.id +
            "/user/" +
            user.data.id,
          null,
          {
            headers: {
              Authorization: "Bearer " + this.$store.state.user.token,
            },
          }
        );
      }
    },
    async addedToGroup(event) {
      if (event.removed == null) {
        await axios.put(
          config.API_URL +
            "/assignment/" +
            event.added.element.id +
            "/group/" +
            this.$store.state.user.groupId,
          null,
          {
            headers: {
              Authorization: "Bearer " + this.$store.state.user.token,
            },
          }
        );
      }
    },
    async getGroupMembersFromApi() {
      axios
        .get(
          config.API_URL + "/group/" + this.$store.state.user.groupId + "/user",
          {
            headers: {
              Authorization: "Bearer " + this.$store.state.user.token,
            },
          }
        )
        .then((res) => (this.members = res.data))
        .catch((err) => console.log(err));
    },
    async getGroupItemsFromDb() {
      axios
        .get(
          config.API_URL +
            "/group/" +
            this.$store.state.user.groupId +
            "/assignment",
          {
            headers: {
              Authorization: "Bearer " + this.$store.state.user.token,
            },
          }
        )
        .then((res) => (this.groupItems = res.data))
        .catch((err) => console.log(err));
    },
    showDetails(item) {
      this.detailsDialog = true;
      this.detailedItem = item;
    },
    closeDetails() {
      this.detailsDialog = false;
    },
    formatDate(value) {
      let split = value.split("T");
      let date = split[0];
      let time = split[1].substring(0, 5);
      return date + " " + time;
    },
    launchchangeStateDialog(task) {
      this.chosen = task;
      this.changeStateDialog = true;
    },
    closechangeStateDialog() {
      this.changeStateDialog = false;
      this.chosen = null;
    },
    async updateTaskState() {
      let task = this.chosen;
      task.state = this.radioValue;
      console.log(task);
      await axios.put(config.API_URL + "/assignment", task, {
        headers: { Authorization: "Bearer " + this.$store.state.user.token },
      });
      this.closechangeStateDialog();
    },
  },
  data: function () {
    return {
      members: [],
      groupItems: [],
      changeStateDialog: false,
      detailsDialog: false,
      detailedItem: null,
      chosen: null,
      radioValue: null,
    };
  },
};
</script>