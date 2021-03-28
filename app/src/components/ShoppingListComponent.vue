<template>
  <v-main>
    <v-container>
      <v-row justify="center">
        <v-col cols="9">
          <v-card class="px-3 pt-3">
            <v-card-title>
              List
              <v-spacer></v-spacer>
              <v-text-field
                v-model="search"
                append-icon="mdi-magnify"
                label="Search"
                single-line
                hide-details
              ></v-text-field>
              <v-btn class="ml-6 mt-3"> Add </v-btn>
            </v-card-title>
            <v-data-table
              v-model="selected"
              :headers="headers"
              :items="shoppingItems"
              :search="search"
              item-key="name"
            >
              <template v-slot:item.critical="{ item }">
                <v-icon v-if="item.critical">mdi-alert-box</v-icon>
                <v-icon v-else>mdi-alert-box-outline</v-icon>
              </template>
              <template v-slot:item.bought="{ item }">
                <v-checkbox v-model="item.bought" @click="updateState(item)">
                </v-checkbox>
              </template>
              <template v-slot:item.remove="{}">
                <v-btn small color="red">Remove</v-btn>
              </template>
            </v-data-table>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";
import config from "../config";
export default {
  created() {
    this.getShoppingItems();
  },
  data: function () {
    return {
      headers: [
        { text: "Name", value: "name" },
        { text: "Type", value: "type" },
        { text: "Description", value: "description" },
        { text: "Quantity", value: "quantity" },
        { text: "Critical", value: "critical" },
        { text: "Bought", value: "bought" },
        { text: "Remove", value: "remove" },
      ],
      shoppingItems: [],
      selected: [],
      search: "",
    };
  },
  methods: {
    async getShoppingItems() {
      axios
        .get(
          config.API_URL +
            "/group/" +
            this.$store.state.user.groupId +
            "/shopping-list",
          {
            headers: {
              Authorization: "Bearer " + this.$store.state.user.token,
            },
          }
        )
        .then((res) => (this.shoppingItems = res.data));
    },
    async updateState(item) {
      axios.put(
        config.API_URL +
          "/group/" +
          this.$store.state.user.groupId +
          "/shopping-list",
        item,
        {
          headers: {
            Authorization: "Bearer " + this.$store.state.user.token,
          },
        }
      );
    },
    async addNewItem(item) {
      axios.post(
        config.API_URL +
          "/group/" +
          this.$store.state.user.groupId +
          "/shopping-list",
        item,
        {
          headers: {
            Authorization: "Bearer " + this.$store.state.user.token,
          },
        }
      );
    },
  },
};
</script>