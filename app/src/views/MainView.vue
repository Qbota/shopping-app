<template>
  <v-content>
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