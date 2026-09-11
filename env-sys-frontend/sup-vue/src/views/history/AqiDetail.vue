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
  <div v-if="feedback">
    <h2>反馈详情</h2>
    <p>反馈等级：{{ feedback.estimatedGrade }}</p>
    <p>反馈日期：{{ feedback.afDate }}</p>
    <p>反馈时间：{{ feedback.afTime }}</p>
    <p>省份：{{ feedback.provinceName }}</p>
    <p>城市：{{ feedback.cityName }}</p>
    <p>地址：{{ feedback.address }}</p>
    <p>反馈信息：{{ feedback.information }}</p>
  </div>
  <div v-else>正在加载....</div>
  <RouterLink to="/supervisor/aqihistory">返回</RouterLink>
</template>

<style scoped>

</style>