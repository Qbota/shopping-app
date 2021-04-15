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
              <v-btn class="ml-6 mt-3" @click="createDialog = true">
                Add
              </v-btn>
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
              <template v-slot:item.remove="{ item }">
                <v-btn v-if="item.bought" @click="deleteItem(item)" color="red" small>Remove</v-btn>
                <v-btn
                  v-else
                  color="red"
                  small
                  @click="
                    deleteConfirmDialog = true;
                    toBeRemoved = item;
                  "
                  >Remove</v-btn
                >
              </template>
            </v-data-table>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <v-dialog v-model="deleteConfirmDialog" width="400">
      <v-card>
        <v-card-title> Confirmation </v-card-title>
        <div class="mx-5">
          Are you sure you want to delete <b>{{ toBeRemoved.name }}</b> from the
          shopping list? It's still not bought.
        </div>
        <v-card-actions>
          <v-spacer />
          <v-btn color="red" @click="deleteItem(toBeRemoved)">Confirm</v-btn>
          <v-btn @click="deleteConfirmDialog = false">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="createDialog" width="500">
      <v-card>
        <v-card-title>
          Create new item to buy
        </v-card-title>
        <v-container>
        <v-row justify="center">
          <v-form v-model="valid" ref="form" lazy-validation>
            <v-text-field v-model="toBeAdded.name" :rules="textRules" outlined label="Name" rounded/>
            <v-text-field v-model="toBeAdded.type" :rules="textRules" outlined label="Type" rounded/>
            <v-text-field v-model="toBeAdded.description" :rules="textRules" outlined label="Description" rounded/>
            <v-text-field v-model="toBeAdded.quantity" :rules="quantityRules" outlined label="Quantity" rounded/>
            <v-checkbox v-model="toBeAdded.isCritical" :label="'Is Critical?'"/>
          </v-form>
        </v-row>  
        </v-container>
        <v-card-actions>
          <v-spacer />
          <v-btn :disabled="!valid" @click="createItem()">Confirm</v-btn>
          <v-btn @click="createDialog = false">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
      deleteConfirmDialog: false,
      createDialog: false,
      toBeRemoved: {
        name: "",
      },
      valid: false,
      textRules: [
        v => !!v || 'Field is required'
      ],
      quantityRules: [
        v => (v && !isNaN(v)) || 'Quantity must be valid number'
      ],
      toBeAdded: {
        name: '',
        type: '',
        description: '',
        quantity: 0,
        isCritical: false
      }
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
    createItem(){
      this.addNewItemInApi(this.toBeAdded)
      this.createDialog = false
    },
    async addNewItemInApi(item) {
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
      ).then(() => this.getShoppingItems());
    },
    async deleteItem(item) {
      axios.delete(
        config.API_URL +
          "/group/" +
          this.$store.state.user.groupId +
          "/shopping-list",
        {
          headers: {
            Authorization: "Bearer " + this.$store.state.user.token,
          },data: item
        }
      ).then(() => {
        this.deleteConfirmDialog = false
        this.getShoppingItems()
      }).catch(err => console.log(err));
    },
  },
};
</script>