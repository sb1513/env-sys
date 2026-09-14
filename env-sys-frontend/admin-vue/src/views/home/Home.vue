<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 左侧菜单展开/收起
const publicDataOpen = ref(true)
const statisticsOpen = ref(true)

// 当前菜单
const activeMenu = ref('home')

// 登出
const logout = () => {
  sessionStorage.removeItem('token')
  sessionStorage.removeItem('cur_user')

  router.push('/admin')
}

// 菜单点击
const selectMenu = (menu) => {
  activeMenu.value = menu
}
</script>

<template>
  <div class="home-page">

    <!-- ================= 顶部 ================= -->
    <header class="top-bar">

      <div class="system-title">
        <span class="title-icon">♧</span>
        <span>东软环保公众监督平台管理系统</span>
      </div>

      <div class="admin-info">
        <span class="admin-text">
          系统管理员：administrator
        </span>

        <button class="logout-btn" @click="logout">
          OUT&nbsp; »
        </button>
      </div>

    </header>


    <!-- ================= 主体 ================= -->
    <div class="main-container">

      <!-- ========== 左侧菜单 ========== -->
      <aside class="sidebar">

        <!-- 公众监督数据管理 -->
        <div class="menu-group">

          <div
            class="group-title"
            @click="publicDataOpen = !publicDataOpen"
          >
            <span class="group-icon">💬</span>

            <span>公众监督数据管理</span>

            <span class="arrow">
              {{ publicDataOpen ? '⌃' : '⌄' }}
            </span>
          </div>


          <div v-show="publicDataOpen" class="submenu">

            <RouterLink :to="{name:'AqiListPage-index'}"
              class="menu-item"
              :class="{ active: activeMenu === 'public' }"
              @click="selectMenu('public')"

            >
              公众监督数据列表
            </RouterLink>

            <RouterLink :to="{name:'ConfirmAqiPage-index'}"
              class="menu-item"
              :class="{ active: activeMenu === 'confirm' }"
              @click="selectMenu('confirm')"
            >
              确认AQI数据列表
            </RouterLink>

          </div>

        </div>


        <!-- 统计数据管理 -->
        <div class="menu-group">

          <div
            class="group-title"
            @click="statisticsOpen = !statisticsOpen"
          >
            <span class="group-icon">▣</span>

            <span>统计数据管理</span>

            <span class="arrow">
              {{ statisticsOpen ? '⌃' : '⌄' }}
            </span>
          </div>


          <div v-show="statisticsOpen" class="submenu">

            <RouterLink :to="{name: 'ProvinceStatisticsPage-index'}"
              class="menu-item"
              :class="{ active: activeMenu === 'province' }"
              @click="selectMenu('province')"
            >
              省分组检查统计
            </RouterLink>

            <RouterLink :to="{name: 'AqiStatisticsPage-index'}"
              class="menu-item"
              :class="{ active: activeMenu === 'distribution' }"
              @click="selectMenu('distribution')"
            >
              AQI指数分布统计
            </RouterLink>

            <RouterLink :to="{name: 'MonthlyAqiPage-index'}"
              class="menu-item"
              :class="{ active: activeMenu === 'trend' }"
              @click="selectMenu('trend')"
            >
              AQI指数趋势统计
            </RouterLink>

            <div
              class="menu-item"
              :class="{ active: activeMenu === 'other' }"
              @click="selectMenu('other')"
            >
              其它数据统计
            </div>

          </div>

        </div>

      </aside>

    </div>


    <!-- ================= 底部 ================= -->
    <footer class="footer">
      © 版权所有 东软睿道 www.neusoft.com
    </footer>

  </div>
</template>


<style scoped>

/* =========================================
   整个页面
========================================= */

* {
  box-sizing: border-box;
}

.home-page {
  width: 100%;
  height: 100vh;
  min-width: 900px;
  overflow: hidden;

  font-family:
    "Microsoft YaHei",
    "微软雅黑",
    Arial,
    sans-serif;

  background: #ffffff;
}


/* =========================================
   顶部
========================================= */

.top-bar {
  height: 53px;

  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 0 13px 0 17px;

  background: #4099ed;

  color: white;
}


/* 左侧标题 */

.system-title {
  display: flex;
  align-items: center;

  font-size: 20px;
  font-weight: 500;
}

.title-icon {
  width: 27px;
  margin-right: 8px;

  font-size: 27px;
  transform: rotate(-20deg);
}


/* 右侧管理员 */

.admin-info {
  display: flex;
  align-items: center;

  height: 100%;
}

.admin-text {
  margin-right: 25px;

  font-size: 14px;
}


/* OUT按钮 */

.logout-btn {
  width: 58px;
  height: 20px;

  border: none;
  border-radius: 2px;

  background: #ff7777;

  color: white;

  font-size: 11px;

  cursor: pointer;

  transition: 0.2s;
}

.logout-btn:hover {
  background: #ff5c5c;
}


/* =========================================
   主体
========================================= */

.main-container {
  display: flex;

  height: calc(100vh - 104px);
}


/* =========================================
   左侧菜单
========================================= */

.sidebar {
  width: 187px;
  flex-shrink: 0;

  background: #73b4ee;

  color: white;

  padding-top: 8px;
}


/* 菜单组 */

.menu-group {
  width: 100%;
}


/* 一级菜单 */

.group-title {
  height: 40px;

  display: flex;
  align-items: center;

  padding: 0 20px;

  font-size: 13px;

  cursor: pointer;

  user-select: none;

  transition: background 0.2s;
}

.group-title:hover {
  background: rgba(255, 255, 255, 0.08);
}


/* 图标 */

.group-icon {
  width: 25px;

  font-size: 14px;
}


/* 箭头 */

.arrow {
  margin-left: auto;

  font-size: 12px;
}


/* 子菜单 */

.submenu {
  padding-bottom: 4px;
}


/* 子菜单项目 */

.menu-item {
  height: 51px;

  display: flex;
  align-items: center;

  padding-left: 34px;

  font-size: 13px;

  cursor: pointer;

  transition:
    background 0.2s,
    color 0.2s;
}

.menu-item:hover {
  background: rgba(255, 255, 255, 0.12);
}


/* 当前菜单 */

.menu-item.active {
  background: rgba(255, 255, 255, 0.16);
}


/* =========================================
   中间内容
========================================= */

.content {
  position: relative;

  flex: 1;

  overflow: hidden;

  background: white;
}

/* =========================================
   底部
========================================= */

.footer {
  height: 51px;

  display: flex;
  align-items: center;
  justify-content: center;

  background: #4099ed;

  color: white;

  font-size: 12px;
}

</style>