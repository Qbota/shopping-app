<template>
  <v-main>
    <v-container>
      <v-row justify="center">
        <v-col cols="8">
          <v-card class="px-7 py-5" raised outlined color="secondary">
          <v-simple-table class="secondary">
            <thead>
              <th class="text-left">Login</th>
              <th class="text-left">Points</th>
            </thead>
            <tbody>
              <tr v-for="item in ranking" :key="item.login">
                <td>{{ item.login }}</td>
                <td>{{ item.points }}</td>
              </tr>
            </tbody>
          </v-simple-table>
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
    this.getRankingFromApi();
  },
  data: function () {
    return {
      ranking: [],
    };
  },
  methods: {
    async getRankingFromApi() {
      axios.get(config.API_URL + '/group/' + this.$store.state.user.groupId + '/ranking', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
      .then(res => this.ranking=res.data)
      .catch(err => console.error(err))
    },
  },
};
</script>