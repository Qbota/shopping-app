<template>
  <v-content>
    <v-container>
      <v-row justify="center" align="center">
        <RegisterComponent/>
      </v-row>
    </v-container>
  </v-content>
</template>

<script>
import RegisterComponent from "@/components/RegisterComponent";
export default {
name: "RegisterView",
  components: {RegisterComponent}
}
</script>
