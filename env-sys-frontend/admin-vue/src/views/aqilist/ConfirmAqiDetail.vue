<script setup>
import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import axios from "../../axios/index.js";

const route = useRoute();
const router = useRouter();

const aqi = ref(null);
const loading = ref(true);

const loadDetail = () => {
  axios({
    method: "get",
    url: `/admin/supervisor/aqidetail/${route.params.id}`
  }).then(res => {
    aqi.value = res.data.data;
  }).catch(err => {
    console.log(err);
  }).finally(() => {
    loading.value = false;
  });
};

const goBack = () => {
  router.back();
};

const getGradeText = (grade) => {
  const map = {
    1: "轻度污染 (一)",
    2: "轻度污染 (二)",
    3: "中度污染 (三)",
    4: "中度污染 (四)",
    5: "重度污染 (五)",
    6: "重度污染 (六)"
  };

  return map[grade] || "未知";
};

const getStateText = (state) => {
  const map = {
    0: "未指派",
    1: "已指派",
    2: "已确认",
    3: "已完成"
  };

  return map[state] || "未知";
};

onMounted(() => {
  loadDetail();
});
</script>

<template>
  <div class="detail-container">
    <div class="title">
      <span>网格员反馈详情</span>
      <button class="back-btn" @click="goBack">返回</button>
    </div>

    <div v-if="loading" class="loading">
      加载中...
    </div>

    <div v-else-if="!aqi" class="empty">
      暂无反馈信息
    </div>

    <div v-else class="detail-table">
      <div class="detail-row">
        <div class="label">反馈编号</div>
        <div class="value">{{ aqi.afId }}</div>
      </div>

      <div class="detail-row">
        <div class="label">公众监督员</div>
        <div class="value">
          <span class="tag">{{ aqi.realName }}</span>
        </div>
      </div>

      <div class="detail-row">
        <div class="label">联系电话</div>
        <div class="value">{{ aqi.telId }}</div>
      </div>

      <div class="detail-row">
        <div class="label">反馈信息所在地</div>
        <div class="value">
          <span class="tag">{{ aqi.provinceName }}</span>
          <span class="tag">{{ aqi.cityName }}</span>
          <span class="tag">{{ aqi.address }}</span>
        </div>
      </div>

      <div class="detail-row">
        <div class="label">反馈信息描述</div>
        <div class="value">
          {{ aqi.information }}
        </div>
      </div>

      <div class="detail-row">
        <div class="label">预估等级</div>
        <div class="value">
          <span
            class="grade"
            :class="'grade' + aqi.estimatedGrade"
          >
            {{ getGradeText(aqi.estimatedGrade) }}
          </span>
        </div>
      </div>

      <div class="detail-row">
        <div class="label">反馈日期时间</div>
        <div class="value">
          <span class="tag">{{ aqi.afDate }}</span>
          <span class="tag">{{ aqi.afTime }}</span>
        </div>
      </div>

      <div class="detail-row">
        <div class="label">负责网格员</div>
        <div class="value">
          <span class="tag">{{ aqi.gmName }}</span>
          <span class="tag">{{ aqi.gmTel }}</span>
        </div>
      </div>

      <div class="detail-row">
        <div class="label">处理状态</div>
        <div class="value">
          <span class="tag">{{ getStateText(aqi.state) }}</span>
        </div>
      </div>

      <div v-if="aqi.assignDate || aqi.assignTime" class="detail-row">
        <div class="label">指派时间</div>
        <div class="value">
          <span class="tag">{{ aqi.assignDate }}</span>
          <span class="tag">{{ aqi.assignTime }}</span>
        </div>
      </div>

      <div v-if="aqi.remarks" class="detail-row">
        <div class="label">处理备注</div>
        <div class="value">{{ aqi.remarks }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.detail-container {
  width: 100%;
  min-height: 100vh;
  padding: 20px 25px;
  background: #fff;
  box-sizing: border-box;
}

.title {
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 16px;
  font-weight: bold;
  color: #303133;
  border-bottom: 1px solid #ebeef5;
}

.back-btn {
  border: none;
  background: #409eff;
  color: white;
  padding: 6px 14px;
  border-radius: 4px;
  cursor: pointer;
}

.back-btn:hover {
  background: #66b1ff;
}

.detail-table {
  width: 100%;
  margin-top: 10px;
  border: 1px solid #ebeef5;
}

.detail-row {
  display: flex;
  min-height: 42px;
  border-bottom: 1px solid #ebeef5;
}

.detail-row:last-child {
  border-bottom: none;
}

.label {
  width: 35%;
  min-width: 220px;
  padding: 10px;
  box-sizing: border-box;
  background: #f5f7fa;
  color: #606266;
  font-size: 13px;
  border-right: 1px solid #ebeef5;
}

.value {
  flex: 1;
  padding: 10px;
  color: #606266;
  font-size: 13px;
  word-break: break-all;
}

.tag {
  display: inline-block;
  margin-right: 6px;
  padding: 3px 7px;
  border-radius: 3px;
  background: #ecf5ff;
  color: #409eff;
}

.grade {
  font-weight: bold;
}

.grade1 {
  color: #67c23a;
}

.grade2 {
  color: #e6a23c;
}

.grade3,
.grade4 {
  color: #f56c6c;
}

.grade5,
.grade6 {
  color: #c45656;
}

.loading,
.empty {
  padding: 50px;
  text-align: center;
  color: #999;
}
</style>