import { createRouter,createWebHistory} from 'vue-router';
import Login from "../views/login/Login.vue";
import Home from "../views/home/Home.vue";
import AqiList from "../views/aqilist/AqiList.vue";
import AqiDetail from "../views/aqilist/AqiDetail.vue";
import AqiAssign from "../views/aqilist/AqiAssign.vue";
import ConfirmAqiList from "../views/aqilist/ConfirmAqiList.vue";
import ConfirmAqiDetail from "../views/aqilist/ConfirmAqiDetail.vue";
import ProvinceStatistics from "../views/provincestatistics/ProvinceStatistics.vue";
import AqiStatistics from "../views/aqistatistics/AqiStatistics.vue";
import MonthlyAqi from "../views/monthlyaqi/MonthlyAqi.vue";

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
    {
        path: '/admin/aqilist',
        name: 'AqiListPage-index',
        component: AqiList,
        meta: {isLogin: true}
    },
    {
        path: '/admin/aqidetail/:id',
        name: 'AqiDetailPage-index',
        component: AqiDetail,
        meta: {isLogin: true}
    },
    {
        path: '/admin/aqiassign/:id',
        name: 'AqiAssignPage-index',
        component: AqiAssign,
        meta: {isLogin: true}
    },
    {
        path: '/admin/confirmaqi',
        name: 'ConfirmAqiPage-index',
        component: ConfirmAqiList,
        meta: {isLogin: true}
    },
    {
        path: '/admin/confirmaqidetail/:id',
        name: 'ConfirmAqiDetailPage-index',
        component: ConfirmAqiDetail,
        meta: {isLogin: true}
    },
    {
        path: '/admin/provincestatistics',
        name: 'ProvinceStatisticsPage-index',
        component: ProvinceStatistics,
        meta: {isLogin: true}
    },
    {
        path: '/admin/aqistatistics',
        name: 'AqiStatisticsPage-index',
        component: AqiStatistics,
        meta: {isLogin: true}
    },
    {
        path: '/admin/monthlyaqi',
        name: 'MonthlyAqiPage-index',
        component: MonthlyAqi,
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