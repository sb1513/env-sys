<script setup>

</script>

<template>
  <div class="menu-container">
    <div class="header">
      <h1 class="title">东软环保公众监督平台</h1>
      <div class="subtitle-wrap">
        <span class="line"></span>
        <span class="subtitle">公众监督员端</span>
        <span class="line"></span>
      </div>
    </div>

    <div class="menu-card">
      <RouterLink class="menu-btn" :to="{name: 'AqiChoosePage-index'}">
        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 20h9"></path><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"></path></svg>
        去上报
      </RouterLink>

      <RouterLink class="menu-btn" :to="{name: 'AqiHistoryPage-index'}">
        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
        上报记录
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