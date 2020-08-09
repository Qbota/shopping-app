<template>
    <v-app id="inspire">
        <v-content>
            <v-container fluid>
                <v-row justify="center">
                  <v-list two-line elevation="1">
                    <v-subheader>
                      Group tasks
                    </v-subheader>
                    <draggable v-model="groupItems" :options="{group:'people'}" style="min-height: 10px" @change="addedToGroup($event)">
                      <template v-for="item in groupItems">
                        <v-list-item :key="item.id">
                          <v-list-item-content>
                            Task: {{item.name}} <br>
                            Added By: {{item.addedBy}}
                          </v-list-item-content>
                        </v-list-item>
                      </template>
                    </draggable>
                  </v-list>
                </v-row>
                <v-row justify="center">
                  <template v-for="member in members">
                  <v-col v-bind:key="member.id" cols="2">
                        <v-list two-line elevation="1">
                          <v-subheader>
                            List of tasks to do by {{member.data.login}}
                          </v-subheader>
                          <draggable v-model="member.items" :options="{group:'people'}" style="min-height: 10px" @change="addedToUser($event, member)">
                            <template v-for="item in member.items">
                              <v-list-item :key="item.id">
                                <v-list-item-content>
                                  Task: {{item.name}} <br>
                                  Added By: {{item.addedBy}}
                                </v-list-item-content>
                              </v-list-item>
                            </template>
                          </draggable>
                        </v-list>
                    </v-col>
                  </template>
                </v-row>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
import draggable from 'vuedraggable'
import axios from "axios";

export default {
        components: {
          draggable
        },
      created() {
        this.getGroupItemsFromDb()
        this.getGroupMembersFromApi()
      },
      methods: {
        async addedToUser(event, user){
          if(event.removed == null){
            await axios.put('http://localhost:8080/assignment/'+ event.added.element.id+'/user/' + user.data.id,null, {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
          }
        },
        async addedToGroup(event){
          if(event.removed == null){
            await axios.put('http://localhost:8080/assignment/'+ event.added.element.id+'/group/' + this.$store.state.user.groupId,null, {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})

          }
        },
        async getGroupMembersFromApi(){
          axios.get('http://localhost:8080/group/' + this.$store.state.user.groupId + '/user', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
              .then((res) => {
                  res.data.forEach((data) => {
                    this.members.push({
                      data: data,
                      items: []
                    })
                  })
                this.getAssignmentsForMembers()
              })
              .catch((err) => console.log(err))
        },
        getAssignmentsForMembers(){
          this.members.map(member => member.data.id)
            .forEach(id => this.getAssignmentsForMember(id))
        },
        getAssignmentsForMember(id){
          axios.get('http://localhost:8080/user/' + id + '/assignment', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
              .then(res => {
                this.members.find(x => x.data.id ===id).items = res.data
              })
        },
        async getGroupItemsFromDb(){
          axios.get('http://localhost:8080/group/' + this.$store.state.user.groupId + '/assignment', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
              .then((res) => this.groupItems = res.data)
              .catch((err) => console.log(err))
        }
      },
        data: function (){
          return {
            members: [],
            groupItems: []
          }
        },
    }
</script>