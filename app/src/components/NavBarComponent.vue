<template>
  <div>
    <v-app-bar app color="secondary" dark v-if="!$vuetify.breakpoint.sm && !$vuetify.breakpoint.xs">
      <v-btn class="mx-1 accent--text" color="primary" @click="redirectTo('/main/create')">Create</v-btn>
      <v-spacer />
      <v-btn class="mx-1 accent--text" color="primary" @click="redirectTo('/main/assignments')"
        >Manage household duties</v-btn
      >
      <v-btn class="mx-1 accent--text" color="primary" @click="redirectTo('/main/group')"
        >My Household</v-btn
      >
      <v-btn class="mx-1 accent--text" color="primary" @click="redirectTo('/main/tasks')">My Duties</v-btn>
      <v-btn class="mx-1 accent--text" color="primary" @click="redirectTo('/main/ranking')">Ranking</v-btn>
      <v-btn class="mx-1 accent--text" color="primary" @click="redirectTo('/main/list')"
        >Shopping List</v-btn
      >
      <v-btn class="mx-1 accent--text" color="primary" @click="logout()">Logout</v-btn>
    </v-app-bar>
    <v-app-bar app color="primary" dark v-else>
      <v-app-bar-nav-icon
        @click.stop="navigation = !navigation"
      ></v-app-bar-nav-icon>
      <v-spacer/>
      <v-btn @click="logout()">Logout</v-btn>
    </v-app-bar>
    <v-navigation-drawer absolute temporary v-model="navigation" color="secondary">
      <v-list-item link to="/main/create">
        <v-list-item-content>
          <v-list-item-title>Create</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item link to="/main/assignments">
        <v-list-item-content>
          <v-list-item-title>Manage household duties</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item link to="/main/group">
        <v-list-item-content>
          <v-list-item-title>My Household</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item link to="/main/tasks">
        <v-list-item-content>
          <v-list-item-title>My duties</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item link to="/main/ranking">
        <v-list-item-content>
          <v-list-item-title>Ranking</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item link to="/main/list">
        <v-list-item-content>
          <v-list-item-title>Shopping List</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  name: "NavBarComponent",
  data: function () {
    return {
      navigation: false,
    };
  },
  methods: {
    redirectTo(path) {
      this.$router.push(path);
    },
    logout() {
      this.$store.commit("setUser", null);
      this.$router.push("/login");
    },
  },
};
</script>