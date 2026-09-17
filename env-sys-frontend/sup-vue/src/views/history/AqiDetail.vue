<script setup>
import {useRoute, useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import axios from "../../axios/index.js";

const router = useRouter()
const route = useRoute()
const feedback = ref(null)
const id = route.params.id

function loadDetail(){
  axios({
      method: 'get',
      url: `/supervisor/aqidetail/${id}`,
    }).then(res=>{
      console.log(res.data.data)
      feedback.value=res.data.data
    }).catch(err=>{
      console.log(err)
  })
}

onMounted(()=>{
  loadDetail()
})
</script>

<template>
  <div class="page-container">
    <div v-if="feedback" class="detail-card">
      <h2 class="card-title">反馈详情</h2>

      <div class="detail-row">
        <span class="detail-label">反馈等级</span>
        <span class="detail-value grade-badge" :class="'grade-' + feedback.estimatedGrade">
          <span v-if="feedback.estimatedGrade == 1">一</span>
          <span v-else-if="feedback.estimatedGrade == 2">二</span>
          <span v-else-if="feedback.estimatedGrade == 3">三</span>
          <span v-else-if="feedback.estimatedGrade == 4">四</span>
          <span v-else-if="feedback.estimatedGrade == 5">五</span>
          <span v-else-if="feedback.estimatedGrade == 6">六</span>
          <span v-else>{{ feedback.estimatedGrade }}</span>
        </span>
      </div>

      <div class="detail-row">
        <span class="detail-label">反馈日期</span>
        <span class="detail-value">{{ feedback.afDate }}</span>
      </div>

      <div class="detail-row">
        <span class="detail-label">反馈时间</span>
        <span class="detail-value">{{ feedback.afTime }}</span>
      </div>

      <div class="detail-row">
        <span class="detail-label">省份</span>
        <span class="detail-value">{{ feedback.provinceName }}</span>
      </div>

      <div class="detail-row">
        <span class="detail-label">城市</span>
        <span class="detail-value">{{ feedback.cityName }}</span>
      </div>

      <div class="detail-row">
        <span class="detail-label">地址</span>
        <span class="detail-value">{{ feedback.address }}</span>
      </div>

      <div class="detail-row column-row">
        <span class="detail-label">反馈信息</span>
        <span class="detail-value info-text">{{ feedback.information }}</span>
      </div>

      <RouterLink class="back-btn" to="/supervisor/aqihistory">返回</RouterLink>
    </div>

    <div v-else class="loading-tip">正在加载....</div>
  </div>
</template>

<style scoped>
.page-container { min-height: 100vh; background-color: #e6f2f0; display: flex; justify-content: center; align-items: center; font-family: sans-serif; padding: 20px; box-sizing: border-box; }
.detail-card { background-color: #ffffff; border-radius: 12px; padding: 30px 25px; width: 100%; max-width: 400px; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); display: flex; flex-direction: column; gap: 15px; }
.card-title { text-align: center; color: #4caf50; font-size: 22px; margin: 0 0 10px 0; letter-spacing: 1px; }
.detail-row { display: flex; justify-content: space-between; align-items: center; font-size: 15px; }
.column-row { flex-direction: column; align-items: flex-start; gap: 8px; }
.detail-label { color: #5d6b6a; font-weight: bold; flex-shrink: 0; }
.detail-value { color: #333; text-align: right; word-break: break-all; }
.info-text { text-align: left; line-height: 1.5; background-color: #f5f9f9; padding: 10px; border-radius: 6px; width: 100%; box-sizing: border-box; }
.grade-badge { width: 28px; height: 28px; border-radius: 6px; display: flex; justify-content: center; align-items: center; color: #ffffff; font-size: 14px; font-weight: bold; }
.grade-1 { background-color: #ffeb3b; color: #333; }
.grade-2 { background-color: #ff9800; }
.grade-3 { background-color: #ff9800; }
.grade-4 { background-color: #f44336; }
.grade-5 { background-color: #9c27b0; }
.grade-6 { background-color: #212121; }
.back-btn { display: block; text-align: center; background-color: #75b7c8; color: #ffffff; border-radius: 6px; padding: 12px 0; font-size: 16px; letter-spacing: 2px; text-decoration: none; margin-top: 10px; transition: background-color 0.3s; box-shadow: 0 2px 6px rgba(117, 183, 200, 0.4); }
.back-btn:hover { background-color: #62a4b5; }
.loading-tip { color: #9eaaab; font-size: 16px; }
</style>