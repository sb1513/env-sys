<script setup>
import { ref, onMounted } from 'vue';
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const feedbackList = ref([]);
const router = useRouter()

function getAqiList(){
  axios({
    method: 'get',
    url: `/grid/aqilist`
  }).then(res=>{
    feedbackList.value=res.data.data || []
    //console.log(res.data.msg)
  })
}

onMounted(() => {
  getAqiList()
});

// 根据等级获取背景颜色
const getGradeColor = (grade) => {
  const colors = {
    1: '#FFD700', // 一级 黄色
    2: '#FFD700', // 二级 黄色 (截图里二也是黄色，可根据实际AQI标准调整)
    3: '#FFA500', // 三级 橙色
    4: '#FF0000', // 四级 红色
    5: '#800080', // 五级 紫色
    6: '#8B0000'  // 六级 深红/褐红
  };
  return colors[grade] || '#999999'; // 默认灰色
};

// 根据等级获取文字（中文数字）
const getGradeText = (grade) => {
  const texts = {
    1: '一',
    2: '二',
    3: '三',
    4: '四',
    5: '五',
    6: '六'
  };
  return texts[grade] || '';
};

const handleGoDetect = (item) => {
  router.push(`/grid/aqidetect/${item.afId}`)
};
</script>

<template>
  <div class="feedback-list-container">
    <!-- 遍历反馈列表 -->
    <div v-for="item in feedbackList" :key="item.afId" class="feedback-card">
      <!-- 左侧等级图标 -->
      <div class="grade-icon" :style="{ backgroundColor: getGradeColor(item.estimatedGrade) }">{{ getGradeText(item.estimatedGrade) }}</div>

      <!-- 中间信息区域 -->
      <div class="info-content">
        <div class="info-header">
          <span class="location">{{ item.provinceName }} {{ item.cityName }}</span>
          <span class="date">{{ item.afDate }}</span>
        </div>
        <div class="info-address">{{ item.address }}</div>
      </div>

      <!-- 右侧按钮 -->
      <div class="action-btn">
        <button @click="handleGoDetect(item)">
          去检测
          <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4.5 16.5c-1.5 1.26-2 5-2 5s3.74-.5 5-2c.71-.84.7-2.13-.09-2.91a2.18 2.18 0 0 0-2.91-.09z"></path><path d="m12 15-3-3a22 22 0 0 1 2-3.95A12.88 12.88 0 0 1 22 2c0 2.72-.78 7.5-6 11a22.35 22.35 0 0 1-4 2z"></path><path d="M9 12H4s.55-3.03 2-4c1.62-1.08 5 0 5 0"></path><path d="M12 15v5s3.03-.55 4-2c1.08-1.62 0-5 0-5"></path></svg>
        </button>
      </div>
    </div>

    <!-- 空状态提示 -->
    <div v-if="feedbackList.length === 0" class="empty-state">暂无反馈任务</div>
    <RouterLink class="back-link" :to="{name: 'HomePage-index'}">返回</RouterLink>
  </div>
</template>

<style scoped>
.feedback-list-container { padding: 15px; background-color: #eef7f8; min-height: 100vh; box-sizing: border-box; font-family: sans-serif; }
.feedback-card { display: flex; align-items: center; padding: 15px 0; border-bottom: 1px dashed #b0bec5; }
.grade-icon { width: 40px; height: 40px; display: flex; justify-content: center; align-items: center; color: #fff; font-size: 18px; font-weight: bold; border-radius: 4px; margin-right: 12px; flex-shrink: 0; }
.info-content { flex: 1; display: flex; flex-direction: column; justify-content: center; overflow: hidden; }
.info-header { display: flex; align-items: center; margin-bottom: 4px; font-size: 15px; color: #333; font-weight: bold; }
.location { margin-right: 10px; }
.date { color: #5d6b6a; font-size: 14px; font-weight: normal; }
.info-address { font-size: 13px; color: #5d6b6a; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.action-btn { margin-left: 10px; flex-shrink: 0; }
.action-btn button { display: flex; align-items: center; justify-content: center; gap: 4px; background-color: #e6f1fc; color: #409eff; border: none; padding: 6px 12px; border-radius: 4px; font-size: 14px; cursor: pointer; transition: all 0.2s; }
.action-btn button:hover { background-color: #409eff; color: #fff; }
.icon { width: 14px; height: 14px; flex-shrink: 0; }
.empty-state { text-align: center; color: #9eaaab; padding: 20px; font-size: 14px; }
.back-link { display: block; text-align: center; margin-top: 20px; color: #78909c; font-size: 14px; text-decoration: none; }
.back-link:hover { color: #4caf50; text-decoration: underline; }
</style>