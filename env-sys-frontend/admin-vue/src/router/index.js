import { createRouter,createWebHistory} from 'vue-router';
import Login from "../views/login/Login.vue";
import Home from "../views/home/Home.vue";

const routes = [
    {
        path: '/admin',
        name: 'AdminLoginPage-index',
        component: Login,
    },
    {
        path: '/admin/home',
        name: 'HomePage-index',
        component: Home,
        meta: {isLogin: true}
    },
]

const router = createRouter({
    routes,
    history: createWebHistory(),
})

router.beforeEach((to,from)=>{
  if(!to.meta.isLogin || sessionStorage.getItem("token")){
      return true
  }
  alert("请登录")
  return ("/admin")
})

export default router;