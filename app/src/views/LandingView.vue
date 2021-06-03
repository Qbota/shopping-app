<template>
  <v-content :style="{background: $vuetify.theme.themes.light.primary}">
    <v-container fill-height>
      <v-row justify="center">
        <router-view />
      </v-row>
    </v-container>
    <v-footer absolute color="secondary">
      Version 1.1
    </v-footer>
  </v-content>
</template>

<script>
export default {
  name: "LandingView",
};
</script>