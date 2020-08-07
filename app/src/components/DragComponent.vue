<template>
    <v-app id="inspire">
        <v-content>
            <v-container fluid>
                <v-row justify="center">
                  <v-list two-line elevation="1">
                    <v-subheader>
                      Group tasks
                    </v-subheader>
                    <draggable v-model="mainItem" :options="{group:'people'}" style="min-height: 10px" @end="saveDashboard()">
                      <template v-for="item in mainItem">
                        <v-list-item :key="item.id">
                          <v-list-item-avatar>
                            <img :src="item.avatar">
                          </v-list-item-avatar>
                          <v-list-item-content>
                            <v-list-item-title v-html="item.title"></v-list-item-title>
                            <v-list-item-subtitle v-html="item.subtitle"></v-list-item-subtitle>
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
                            List of tasks to do by user of id: {{member.id}}
                          </v-subheader>
                          <draggable v-model="member.items" :options="{group:'people'}" style="min-height: 10px" @end="saveDashboard()">
                            <template v-for="item in member.items">
                              <v-list-item :key="item.id">
                                <v-list-item-avatar>
                                  <img :src="item.avatar">
                                </v-list-item-avatar>
                                <v-list-item-content>
                                  <v-list-item-title v-html="item.title"></v-list-item-title>
                                  <v-list-item-subtitle v-html="item.subtitle"></v-list-item-subtitle>
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
    export default {
        components: {
          draggable
        },
      methods: {
         saveDashboard(){
          console.log('saving dashboard in DB')
        }
      },
        data: () => ( {
              members:[{
                id: 1,
                items: []
              },
                {
                  id: 2,
                  items: []
                },
                {
                  id: 3,
                  items: []
                },
                {
                  id: 4,
                  items: []
                },
                {
                  id: 5,
                  items: []
                },
                {
                  id: 6,
                  items: []
                },
                {
                  id: 7,
                  items: []
                }],
              mainItem: [
                {
                  id: 1,
                  avatar: "https://s3.amazonaws.com/vuetify-docs/images/lists/1.jpg",
                  title: "Brunch this life?",
                  subtitle: "Subtitle 1"
                },
                {
                  id: 2,
                  avatar: "https://s3.amazonaws.com/vuetify-docs/images/lists/2.jpg",
                  title: "Winter Lunch",
                  subtitle: "Subtitle 2"
                },
                {
                  id: 3,
                  avatar: "https://s3.amazonaws.com/vuetify-docs/images/lists/3.jpg",
                  title: "Oui oui",
                  subtitle: "Subtitle 3"
                },
                {
                  id: 4,
                  avatar: "https://s3.amazonaws.com/vuetify-docs/images/lists/4.jpg",
                  title: "Brunch this weekend?",
                  subtitle: "Subtitle 4"
                },
                {
                  id: 5,
                  avatar: "https://s3.amazonaws.com/vuetify-docs/images/lists/5.jpg",
                  title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>',
                  subtitle: "Subtitle 5"
                }
              ]
            }
        ),
    }
</script>