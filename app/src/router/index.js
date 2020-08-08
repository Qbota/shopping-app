import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginView from "@/views/LoginView";
import AssignmentsView from "@/views/AssignmentsView";
import RegisterView from "@/views/RegisterView";

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
  },
    {
      path: '/register',
      name: 'Register',
      component: RegisterView
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
