<script setup>
import {onMounted, ref} from 'vue';
import axios from "../../axios/index.js";
const statsData = ref([])

const loadData = () => {
  axios({
    method: 'get',
    url: '/admin/others'
  }).then(res=>{
    statsData.value = res.data.data
    console.log(res.data.data)
  })
}

onMounted(()=>{
  loadData()
})
</script>

<template>
  <div class="stats-container">
    <div class="title">其它数据统计</div>
    <table class="stats-table">
      <tbody>
        <tr>
          <td class="label-col">空气质量检测总数量</td>
          <td class="value-col">{{ statsData.totalCount }}</td>
        </tr>
        <tr>
          <td class="label-col">空气质量检测良好数量</td>
          <td class="value-col">{{ statsData.goodCount }}</td>
        </tr>
        <tr>
          <td class="label-col">省会城市网格覆盖范围</td>
          <td class="value-col">{{ statsData.capitalCoverage }}%</td>
        </tr>
        <tr>
          <td class="label-col">大城市网格覆盖范围</td>
          <td class="value-col">{{ statsData.bigCityCoverage }}%</td>
        </tr>
      </tbody>
    </table>
    <RouterLink :to="{name: 'HomePage-index'}">返回</RouterLink>
  </div>
</template>

<style scoped>
.stats-container { width: 100%; max-width: 600px; font-family: sans-serif; margin: 20px; }
.title { font-size: 16px; font-weight: bold; color: #333; margin-bottom: 15px; }
.stats-table { width: 100%; border-collapse: collapse; border: 1px solid #e8e8e8; }
.stats-table td { padding: 12px 15px; font-size: 14px; border-bottom: 1px solid #e8e8e8; }
.stats-table tr:last-child td { border-bottom: none; }
.label-col { width: 70%; color: #666; background-color: #f5f5f5; }
.value-col { width: 30%; color: #888; border-left: 1px solid #e8e8e8; }
</style>