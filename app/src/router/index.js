import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "@/views/LoginView";
import AssignmentsView from "@/views/AssignmentsView";

Vue.use(VueRouter)

  const routes = [
    {
      path: '/login',
      name: 'Login',
      component: LoginView
    },
  {
    path: '/assignments',
    name: 'Assignemnts',
    component: AssignmentsView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
