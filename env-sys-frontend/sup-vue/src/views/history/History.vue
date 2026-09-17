<script setup>
import {onMounted, ref} from "vue";
import axios from "../../axios/index.js";
import router from "../../router/index.js";

const aqiQuery = ref([])

const loadAqi = async () => {
  await axios({
      method: 'get',
      url: '/supervisor/aqiquery'
    }).then(res=>{
      console.log(res.data.msg)
      aqiQuery.value=res.data.data
    }).catch(err=>{
      console.log(err)
  })
}

function handleDetail(aqiquery){
  router.push(`/supervisor/aqidetail/${aqiquery.afId}`)
}

onMounted(()=>{
  loadAqi()
})
</script>

<template>
  <div class="page-container">
    <label class="page-title">历史反馈信息列表</label>
    <div v-if="!aqiQuery.length" class="empty-tip">暂无反馈</div>
    <div v-else class="list-wrap">
      <div class="list-item" v-for="aqiquery in aqiQuery" :key="aqiquery.afId" @click="handleDetail(aqiquery)">
        <div class="grade-badge" :class="'grade-' + aqiquery.estimatedGrade">
          <span v-if="aqiquery.estimatedGrade == 1">一</span>
          <span v-else-if="aqiquery.estimatedGrade == 2">二</span>
          <span v-else-if="aqiquery.estimatedGrade == 3">三</span>
          <span v-else-if="aqiquery.estimatedGrade == 4">四</span>
          <span v-else-if="aqiquery.estimatedGrade == 5">五</span>
          <span v-else-if="aqiquery.estimatedGrade == 6">六</span>
          <span v-else>{{ aqiquery.estimatedGrade }}</span>
        </div>
        <div class="info-cell date-cell">{{ aqiquery.afDate }} {{ aqiquery.afTime }}</div>
        <div class="info-cell province-cell">{{ aqiquery.provinceName }}</div>
        <div class="info-cell city-cell">{{ aqiquery.cityName }}</div>
      </div>
    </div>
    <RouterLink class="back-link" :to="{name: 'HomePage-index'}">返回</RouterLink>
  </div>
</template>

<style scoped>
.page-container { min-height: 100vh; background-color: #e6f2f0; display: flex; flex-direction: column; align-items: center; padding: 20px; font-family: sans-serif; gap: 15px; }
.page-title { font-size: 16px; color: #5d6b6a; font-weight: bold; align-self: flex-start; width: 100%; max-width: 500px; margin-bottom: 5px; }
.empty-tip { color: #9eaaab; font-size: 14px; margin-top: 20px; }
.list-wrap { width: 100%; max-width: 500px; display: flex; flex-direction: column; gap: 10px; }
.list-item { display: flex; align-items: center; background-color: #eef7f8; border-radius: 6px; padding: 12px 15px; cursor: pointer; transition: background-color 0.2s; }
.list-item:hover { background-color: #e0f0f2; }
.grade-badge { width: 32px; height: 32px; border-radius: 6px; display: flex; justify-content: center; align-items: center; color: #ffffff; font-size: 16px; font-weight: bold; flex-shrink: 0; margin-right: 15px; }
.grade-1 { background-color: #ffeb3b; color: #333; }
.grade-2 { background-color: #ff9800; }
.grade-3 { background-color: #ff9800; }
.grade-4 { background-color: #f44336; }
.grade-5 { background-color: #9c27b0; }
.grade-6 { background-color: #212121; }
.info-cell { font-size: 14px; color: #5d6b6a; }
.date-cell { flex: 1; min-width: 160px; }
.province-cell { width: 70px; text-align: center; flex-shrink: 0; }
.city-cell { width: 70px; text-align: center; flex-shrink: 0; }
.back-link { margin-top: 15px; color: #78909c; font-size: 14px; text-decoration: none; }
.back-link:hover { color: #4caf50; text-decoration: underline; }
</style>