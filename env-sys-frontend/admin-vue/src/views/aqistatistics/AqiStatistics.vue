<script setup>
import { ref, onMounted } from "vue";
import axios from "../../axios/index.js";

const aqiData = ref([])
const loading = ref(false)

const getAqiStatistics = async () => {
  loading.value = true
  axios({
    method: 'get',
    url: '/admin/aqistatistics'
  }).then(res=>{
    aqiData.value = res.data.data || []
    //console.log(res.data.data)
  }).finally(
      loading.value = false
  )
};

onMounted(() => {
  getAqiStatistics();
});
</script>

<template>
  <div class="page">
    <div class="title">AQI空气质量指数级别分布</div>
    <table>
      <thead>
        <tr>
          <th>AQI指数等级</th>
          <th>AQI指数等级描述</th>
          <th>AQI指数等级分布统计</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="loading">
          <td colspan="3">正在加载...</td>
        </tr>
        <tr v-for="item in aqiData" :key="item.aqiId">
          <td>{{ item.aqiId }}</td>
          <td>{{ item.aqiExplain }}</td>
          <td>{{ item.aqiCount }}</td>
        </tr>
      </tbody>
    </table>
    <RouterLink :to="{name: 'HomePage-index'}">返回</RouterLink>
  </div>
</template>

<style scoped>
.page {
  width: 100%;
  padding: 0 16px;
  box-sizing: border-box;
}
.title {
  height: 26px;
  line-height: 26px;
  background: #5bc43a;
  color: white;
  font-size: 12px;
  padding-left: 12px;
  border-radius: 3px;
  margin-bottom: 14px;
}
table {
  width: 100%;
  border-collapse: collapse;
  table-layout: fixed;
  font-size: 12px;
  color: #666;
}
th,
td {
  box-sizing: border-box;
  height: 28px;
  padding: 0 8px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  font-weight: 600;
  color: #888;
}
</style>