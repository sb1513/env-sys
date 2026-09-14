<script setup>
import { onMounted, ref } from "vue";
import axios from "../../axios/index.js";

const statistics = ref([]);
const loading = ref(false);

const getStatistics = () => {
  loading.value = true;
  axios({
    method: 'get',
    url: '/admin/provincestatistics'
  }).then(res=>{
    statistics.value = res.data.data || []
    console.log(res.data.msg)
  }).finally(
      loading.value = false
  )
};

onMounted(() => {
  getStatistics();
});
</script>

<template>
  <div class="statistics-page">
    <div class="title">省份超标检查统计</div>
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>省编号</th>
            <th>省简称</th>
            <th>省名称</th>
            <th>SO2超标累计</th>
            <th>CO超标累计</th>
            <th>PM2.5超标累计</th>
            <th>AQI超标累计</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading">
            <td colspan="7">正在加载...</td>
          </tr>
          <tr v-else-if="statistics.length === 0">
            <td colspan="7">暂无数据</td>
          </tr>
          <tr v-for="item in statistics" :key="item.provinceId">
            <td>{{ item.provinceId }}</td>
            <td>{{ item.provinceAbbr }}</td>
            <td>{{ item.provinceName }}</td>
            <td>{{ item.so2Count }}</td>
            <td>{{ item.coCount }}</td>
            <td>{{ item.spmCount }}</td>
            <td>{{ item.aqiCount }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <RouterLink :to="{name: 'HomePage-index'}">返回</RouterLink>
  </div>
</template>

<style scoped>
.statistics-page {
  width: 100%;
  background: #fff;
}

.title {
  height: 28px;
  line-height: 28px;
  padding-left: 14px;
  background: #5bc43a;
  color: #fff;
  font-size: 13px;
  border-radius: 3px 3px 0 0;
}

.table-container {
  height: 320px;
  overflow-y: auto;
  overflow-x: hidden;
}

.table-container::-webkit-scrollbar {
  width: 8px;
}

.table-container::-webkit-scrollbar-thumb {
  background: #aaa;
  border-radius: 4px;
}

table {
  width: 100%;
  border-collapse: collapse;
  table-layout: fixed;
  font-size: 12px;
  color: #555;
}

thead {
  position: sticky;
  top: 0;
  background: #fff;
  z-index: 1;
}

th,
td {
  height: 30px;
  padding: 0 8px;
  text-align: left;
  border-bottom: 1px solid #eee;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

th {
  color: #777;
  font-weight: 600;
}

td {
  color: #555;
}

th:nth-child(1),
td:nth-child(1) {
  width: 55px;
}

th:nth-child(2),
td:nth-child(2) {
  width: 65px;
}

th:nth-child(3),
td:nth-child(3) {
  width: 90px;
}

th:nth-child(4),
td:nth-child(4),
th:nth-child(5),
td:nth-child(5),
th:nth-child(6),
td:nth-child(6),
th:nth-child(7),
td:nth-child(7) {
  width: 105px;
}

tbody tr:hover {
  background: #f7f7f7;
}
</style>