<script setup>

</script>

<template>
  <div class="menu-container">
    <div class="header">
      <h1 class="title">东软环保公众监督平台</h1>
      <div class="subtitle-wrap">
        <span class="line"></span>
        <span class="subtitle">网格员端</span>
        <span class="line"></span>
      </div>
    </div>

    <div class="menu-card">
      <RouterLink class="menu-btn" :to="{name: 'AqiListPage-index'}">
        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg>
        Aqi列表
      </RouterLink>
    </div>
  </div>
</template>

<style scoped>
.menu-container { min-height: 100vh; background-color: #e0f2f1; display: flex; flex-direction: column; align-items: center; padding-top: 8vh; font-family: sans-serif; }
.header { text-align: center; margin-bottom: 30px; }
.title { color: #4caf50; font-size: 26px; font-weight: bold; margin: 0 0 15px 0; letter-spacing: 1px; }
.subtitle-wrap { display: flex; align-items: center; justify-content: center; gap: 10px; }
.line { width: 60px; height: 1px; background-color: #81c784; }
.subtitle { color: #4caf50; font-size: 16px; }
.menu-card { background-color: #ffffff; border-radius: 12px; padding: 35px 25px; width: 320px; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); display: flex; flex-direction: column; gap: 20px; }
.menu-btn { display: flex; align-items: center; justify-content: center; gap: 10px; text-decoration: none; background-color: #75b7c8; color: #ffffff; border-radius: 8px; padding: 14px 0; font-size: 18px; letter-spacing: 2px; transition: background-color 0.3s; cursor: pointer; }
.menu-btn:hover { background-color: #62a4b5; }
.icon { width: 20px; height: 20px; color: #ffffff; flex-shrink: 0; }
</style>