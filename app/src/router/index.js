import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home'
import ContactComponent from '../components/ContactComponent'
import HomeComponent from '../components/HomeComponent'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      {
      path: 'home',
      component: HomeComponent
    },
    {
      path: 'contact',
      component: ContactComponent
    }
  ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
