<template>
  <v-content :style="{background: $vuetify.theme.themes.light.primary}">
    <NavBarComponent/>
    <router-view/>
  </v-content>
</template>
<script>
  import NavBarComponent from "@/components/NavBarComponent";
  export default {
    components: {NavBarComponent}
  }
</script>