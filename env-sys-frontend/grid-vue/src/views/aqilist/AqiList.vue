<script setup>
import { ref, onMounted } from 'vue';
import axios from "../../axios/index.js";

const feedbackList = ref([]);

function getAqiList(){
  axios({
    method: 'get',
    url: `/grid/aqilist`
  }).then(res=>{
    feedbackList.value=res.data.data
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

// 点击“去检测”按钮的处理逻辑
const handleGoDetect = (item) => {

};
</script>
<template>
  <div class="feedback-list-container">
    <!-- 遍历反馈列表 -->
    <div
      v-for="item in feedbackList"
      :key="item.afId"
      class="feedback-card"
    >
      <!-- 左侧等级图标 -->
      <div
        class="grade-icon"
        :style="{ backgroundColor: getGradeColor(item.estimatedGrade) }"
      >
        {{ getGradeText(item.estimatedGrade) }}
      </div>

      <!-- 中间信息区域 -->
      <div class="info-content">
        <div class="info-header">
          <span class="location">{{ item.provinceName }} {{ item.cityName }}</span>
          <span class="date">{{ item.afDate }}</span>
        </div>
        <div class="info-address">
          {{ item.address }}
        </div>
      </div>

      <!-- 右侧按钮 -->
      <div class="action-btn">
        <button @click="handleGoDetect(item)">
          去检测
          <!-- 简单的图标 SVG -->
          <svg class="icon" viewBox="0 0 1024 1024" width="14" height="14">
            <path d="M512 85.333333c-235.648 0-426.666667 191.018667-426.666667 426.666667s191.018667 426.666667 426.666667 426.666667 426.666667-191.018667 426.666667-426.666667-191.018667-426.666667-426.666667-426.666667z m0 768c-188.586667 0-341.333333-152.746667-341.333333-341.333333s152.746667-341.333333 341.333333-341.333333 341.333333 152.746667 341.333333 341.333333-152.746667 341.333333-341.333333 341.333333z" fill="#409eff"/>
            <path d="M512 256c-23.552 0-42.666667 19.114667-42.666667 42.666667v213.333333c0 23.552 19.114667 42.666667 42.666667 42.666667s42.666667-19.114667 42.666667-42.666667v-213.333333c0-23.552-19.114667-42.666667-42.666667-42.666667z" fill="#409eff"/>
          </svg>
        </button>
      </div>
    </div>

    <!-- 空状态提示 -->
    <div v-if="feedbackList.length === 0" class="empty-state">
      暂无反馈任务
    </div>
    <RouterLink :to="{name: 'HomePage-index'}">返回</RouterLink>
  </div>
</template>

<style scoped>
.feedback-list-container {
  padding: 10px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.feedback-card {
  display: flex;
  align-items: center;
  background-color: #ffffff;
  border-radius: 4px;
  margin-bottom: 10px;
  padding: 15px 10px;
  /* 模仿截图中卡片下方的虚线分割效果，如果是独立卡片可去掉 border-bottom */
  border-bottom: 1px dashed #e0e0e0;
  box-shadow: 0 1px 3px rgba(0,0,0,0.05);
}

/* 左侧等级图标样式 */
.grade-icon {
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  border-radius: 4px;
  margin-right: 12px;
  flex-shrink: 0;
}

/* 中间信息区域样式 */
.info-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  overflow: hidden; /* 防止文字溢出 */
}

.info-header {
  display: flex;
  align-items: center;
  margin-bottom: 6px;
  font-size: 16px;
  color: #333;
  font-weight: 500;
}

.location {
  margin-right: 8px;
}

.date {
  color: #666;
  font-size: 14px;
  font-weight: normal;
}

.info-address {
  font-size: 13px;
  color: #666;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 右侧按钮样式 */
.action-btn {
  margin-left: 10px;
  flex-shrink: 0;
}

.action-btn button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  background-color: #e6f1fc; /* 浅蓝色背景 */
  color: #409eff;           /* 蓝色文字 */
  border: 1px solid #b3d8ff;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.action-btn button:hover {
  background-color: #409eff;
  color: #fff;
}

.action-btn button:hover .icon path {
  fill: #fff; /* 悬停时图标变白 */
}

.empty-state {
  text-align: center;
  color: #999;
  padding: 20px;
}
</style>