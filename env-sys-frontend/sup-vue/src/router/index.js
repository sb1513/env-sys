import { createRouter,createWebHistory} from 'vue-router';
import Login from "../views/login/Login.vue";
import Home from "../views/home/Home.vue";
import Register from "../views/register/Register.vue";
import AqiChoose from "../views/feedback/AqiChoose.vue";
import GridChoose from "../views/feedback/GridChoose.vue";

const routes = [
    {
        path: '/supervisor',
        name: 'SupLoginPage-index',
        component: Login
    },
    {
        path: '/supervisor/home',
        name: 'HomePage-index',
        component: Home,
        meta: {isLogin: true},
    },
    {
        path: '/supervisor/register',
        name: 'SupRegisterPage-index',
        component: Register,
    },
    {
        path: '/supervisor/aqichoose',
        name: 'AqiChoosePage-index',
        component: AqiChoose,
        meta: {isLogin: true},
    },
    {
        path: '/supervisor/gridchoose',
        name: 'GridChoosePage-index',
        component: GridChoose,
        meta: {isLogin: true},
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
  return ("/login")
})

export default router;