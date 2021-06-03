<template>
  <v-content :style="{background: $vuetify.theme.themes.light.primary}">
    <v-container fill-height>
      <v-row justify="center">
        <h1>Hello in my application!</h1>
      </v-row>
      <v-row justify="center">
        <v-col cols="4" style="text-align: center">
          Using this application you can manage household duties. Create an account and give it a try.
        </v-col>
      </v-row>
      <v-row justify="center">
        <router-view />
      </v-row>
    </v-container>
    <v-footer absolute color="secondary">
      Version 0.2
    </v-footer>
  </v-content>
</template>

<script>
export default {
  name: "LandingView",
};
</script>