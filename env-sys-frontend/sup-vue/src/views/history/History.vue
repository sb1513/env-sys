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
  <label>历史反馈信息列表</label>
  <div v-if="!aqiQuery.length">暂无反馈</div>
  <div v-else v-for="aqiquery in aqiQuery" :key="aqiquery.afId">
    <div @click="handleDetail(aqiquery)">
      <span>{{aqiquery.estimatedGrade}}</span>
      <span>{{aqiquery.afDate}}</span>
      <span>{{aqiquery.afTime}}</span>
      <span>{{aqiquery.provinceName}}</span>
      <span>{{aqiquery.cityName}}</span>
    </div>
  </div>
  <RouterLink :to="{name: 'HomePage-index'}">返回</RouterLink>
</template>

<style scoped>
span {
  display: inline-block;
  margin-right: 20px;
}
</style>