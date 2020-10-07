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
                        <v-list-item :key="item.id" v-if="item.state !== 'Done'">
                          <v-list-item-content>
                            <v-list-item-title>{{item.name}}</v-list-item-title>
                            <v-list-item-subtitle>{{item.description}} <br>
                              Deadline: {{formatDate(item.end)}} <br>
                              State: {{item.state}}
                            </v-list-item-subtitle>
                          </v-list-item-content>
                          <v-list-item-action>
                            <v-btn icon @click="launchDialog(item)">
                              <v-icon>mdi-pencil</v-icon>
                            </v-btn>
                          </v-list-item-action>
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
                              <v-list-item :key="item.id" v-if="item.state !== 'Done'">
                                <v-list-item-content>
                                  <v-list-item-title>{{item.name}}</v-list-item-title>
                                  <v-list-item-subtitle>{{item.description}} <br>
                                    Deadline: {{formatDate(item.end)}} <br>
                                    State: {{item.state}}
                                  </v-list-item-subtitle>
                                </v-list-item-content>
                                  <v-list-item-action>
                                    <v-btn icon @click="launchDialog(item)">
                                      <v-icon>mdi-pencil</v-icon>
                                    </v-btn>
                                  </v-list-item-action>
                              </v-list-item>
                            </template>
                          </draggable>
                        </v-list>
                    </v-col>
                  </template>
                </v-row>
            </v-container>
          <v-dialog v-model="dialog" scrollable max-width="300pt">
            <v-card>
              <v-card-title>Choose state</v-card-title>
              <v-card-text>
                <v-radio-group v-model="radioValue">
                  <v-radio label="To Do" value="To Do"></v-radio>
                  <v-radio label="In Progress" value="In Progress"></v-radio>
                  <v-radio label="Done" value="Done"></v-radio>
                </v-radio-group>
              </v-card-text>
              <v-card-actions>
                <v-spacer/>
                <v-btn @click="updateTaskState()">Confirm</v-btn>
                <v-btn @click="closeDialog()">Cancel</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
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
              .then(res => this.members = res.data)
              .catch((err) => console.log(err))
        },
        async getGroupItemsFromDb(){
          axios.get('http://localhost:8080/group/' + this.$store.state.user.groupId + '/assignment', {headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
              .then((res) => this.groupItems = res.data)
              .catch((err) => console.log(err))
        },
        formatDate(value){
          let split = value.split('T')
          let date = split[0]
          let time = split[1].substring(0,5)
          return date + ' ' +time
        },
        launchDialog(task){
          this.chosen = task
          this.dialog = true
        },
        closeDialog(){
          this.dialog = false
          this.chosen = null
        },
        async updateTaskState(){
          let task = this.chosen
          task.state = this.radioValue
          console.log(task)
          await axios.put('http://localhost:8080/assignment', task,{headers: {'Authorization': 'Bearer ' + this.$store.state.user.token}})
          this.closeDialog()
        }
      },
        data: function (){
          return {
            members: [],
            groupItems: [],
            dialog: false,
            chosen: null,
            radioValue: null
          }
        },
    }
</script>