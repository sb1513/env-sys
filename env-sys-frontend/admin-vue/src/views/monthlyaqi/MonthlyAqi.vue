<script setup>
import { ref, onMounted } from 'vue';
import axios from "../../axios/index.js";
const tableData = ref([]);
const loading = ref(false);
const loadData = () => {
  loading.value = true;
  axios({
    method: 'get',
    url: '/admin/monthlyaqi',
  }).then(res=>{
    tableData.value = res.data.data || []
    //console.log(res.data.msg)
  }).finally(
      loading.value = false
  )
};
onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="aqi-container">
    <div class="header-bar">AQI空气质量指数超标趋势</div>
    <div class="table-wrapper">
      <table class="aqi-table">
        <thead>
          <tr>
            <th width="20%">序号</th>
            <th width="40%">月份</th>
            <th width="40%">AQI空气质量指数超标数量</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading">
            <td colspan="3" class="text-center">加载中...</td>
          </tr>
          <tr v-else-if="tableData.length === 0">
            <td colspan="3" class="text-center">暂无数据</td>
          </tr>
          <tr v-else v-for="(item, index) in tableData" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ item.year }}-{{ item.month }}</td>
            <td>{{ item.aqiCount }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <RouterLink :to="{name: 'HomePage-index'}">返回</RouterLink>
  </div>
</template>

<style scoped>
.aqi-container { width: 100%; max-width: 800px; margin: 20px auto; font-family: sans-serif; background-color: #fff; }
.header-bar { background-color: #7bc043; color: #fff; padding: 12px 20px; font-size: 16px; border-radius: 4px 4px 0 0; }
.table-wrapper { border: 1px solid #e0e0e0; border-top: none; border-radius: 0 0 4px 4px; }
.aqi-table { width: 100%; border-collapse: collapse; text-align: left; }
.aqi-table th, .aqi-table td { padding: 12px 20px; font-size: 14px; color: #333; border-bottom: 1px solid #f0f0f0; }
.aqi-table th { color: #888; font-weight: normal; }
.aqi-table tbody tr:nth-child(even) { background-color: #fafafa; }
.aqi-table tbody tr:hover { background-color: #f5f5f5; }
.text-center { text-align: center; color: #999; padding: 20px 0; }
</style>