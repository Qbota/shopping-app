import Vue from 'vue'
import VueRouter from 'vue-router'
import MainView from "@/views/MainView";
import DragComponent from "@/components/DragComponent";
import LandingView from "@/views/LandingView";
import LoginComponent from "@/components/LoginComponent";
import RegisterComponent from "@/components/RegisterComponent";
import CreateGroupComponent from "@/components/CreateGroupComponent";
import store from '@/store/index'
import MyTasksComponent from "@/components/MyTasksComponent";
import MyGroupComponent from "@/components/MyGroupComponent";
import CreateComponent from "@/components/CreateComponent";

Vue.use(VueRouter)

  const routes = [
    {
      path: '/',
      name: 'Landing',
      component: LandingView,
      redirect: '/login',
      children: [
        {path: 'login',name: 'Login', component: LoginComponent},
        {path: 'register', component: RegisterComponent},
        {path: 'group', name: 'CreateGroup', component: CreateGroupComponent}
      ]
    },
  {
    path: '/main',
    name: 'Main',
    component: MainView,
    beforeEnter: (to, from, next) => {
      if(store.state.user == null) next({name: 'Login'})
      else if(store.state.user.groupId == null) next({name: 'CreateGroup'})
      else next()
    },
    children: [
      {path: 'assignments', name: 'assignments', component: DragComponent},
      {path: 'group', component: MyGroupComponent},
      {path: 'create', component: CreateComponent},
      {path: 'tasks', component: MyTasksComponent}
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
