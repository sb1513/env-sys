import {createRouter, createWebHistory} from "vue-router";
import Login from "../views/login/Login.vue";
import Register from "../views/register/Register.vue";
import Home from "../views/home/Home.vue";
import AqiList from "../views/aqilist/AqiList.vue";

const routes = [
    {
        path: '/grid',
        name: 'GridLoginPage-index',
        component: Login
    },
    {
        path: '/grid/register',
        name: 'GridRegisterPage-index',
        component: Register
    },
    {
        path: '/grid/home',
        name: 'HomePage-index',
        component: Home
    },
    {
        path: '/grid/aqilist',
        name: 'AqiListPage-index',
        component: AqiList,
        meta: {isLogin: true}
    },
]

const router = createRouter({
    routes,
    history: createWebHistory(),
})

router.beforeEach((to,from)=>{
  if(!to.meta.isLogin || sessionStorage.getItem("token")){
      return
  }
  alert("请登录")
  return ("/grid")
})

export default router;