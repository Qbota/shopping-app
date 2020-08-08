<template>
  <v-content>
    <v-card outlined raised>
      <v-card-title>Group</v-card-title>
      <v-card-subtitle>We have detected that you have no group! That's a shame. Before we can let you go assign yourself to group or create your own.</v-card-subtitle>
      <v-row justify="center">
        <v-btn-toggle class="mb-4">
          <v-btn v-model="create">Create new Group</v-btn>
          <v-btn v-model="assign">Assign to existing group</v-btn>
        </v-btn-toggle>
      </v-row>
      <div v-if="create">
        <v-form v-model="valid" ref="form" class="px-12 pt-10 pb-5" >
          <v-row>
            <v-text-field v-model="name" :rules="nameRules" label="Group Name" counter="30" outlined rounded/>
          </v-row>
        </v-form>
        <v-card-actions>
          <v-spacer/><v-btn class="mb-2 mr-2 px-5" rounded :disabled="!valid" @click="creatGroup()">Create</v-btn>
        </v-card-actions>
      </div>
      <div v-if="assign">
        <v-form v-model="valid" ref="form" class="px-12 pt-10 pb-5" >
          <v-row>
            <v-text-field v-model="code" :rules="codeRules" label="Group Code" outlined rounded/>
          </v-row>
        </v-form>
        <v-card-actions>
          <v-spacer/><v-btn class="mb-2 mr-2 px-5" rounded :disabled="!valid" @click="assignToGroup()">Assign</v-btn>
        </v-card-actions>
      </div>
    </v-card>
  </v-content>
</template>

<script>
import axios from 'axios'
export default {
name: "CreateGroupComponent",
  data: function (){
    return{
      valid: true,
      name: '',
      nameRules: [
        v => !!v || 'Please specify group name',
        v => (v && v.length <= 30) || 'Name must be shorter than 30 characters'
      ],
      code: '',
      codeRules: [
        v => !!v || 'Code can\'t be empty '
      ],
      create: false,
      assign: false
    }
  },
  methods: {
    assignToGroup(){
      axios.post('http://localhost:8080/group/' + this.code+'/user/' + this.$store.state.user.id, {}, {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
      .then((res) => {
        this.$store.commit('setUserGroupId', res.data.id)
        this.$router.push('main/assignments')
      })
      .catch((err) => console.log(err))
    },
    creatGroup(){
      axios.post('http://localhost:8080/group', {name: this.name}, {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
      .then((res) => {
        console.log(res)
        this.$store.commit('setUserGroupId', res.data.id)
        this.code = res.data.inviteCode
        this.assignToGroup()
      })
    }
  }
}
</script>
