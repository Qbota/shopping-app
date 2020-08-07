<template>
  <v-app>
    <NavBarComponent/>

    <v-content>
      <router-view/>
    </v-content>
  </v-app>
</template>

<script>

import NavBarComponent from "@/components/NavBarComponent";
export default {
  name: 'App',
  components: {NavBarComponent},
};
</script>
