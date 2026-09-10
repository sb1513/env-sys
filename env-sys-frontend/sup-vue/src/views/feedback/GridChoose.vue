<script setup>
import useSupAqiFeedbackStore from "../../stores/supAqiFeedback.js";
import {ref} from "vue";
import axios from "../../axios/index.js";
import router from "../../router/index.js";

const supaqifeedbackStore = useSupAqiFeedbackStore()
const errMessage = ref("")

function send(){
  errMessage.value=''
  if(!supaqifeedbackStore.aqi.estimatedGrade){
    errMessage.value="请选择空气质量等级"
    alert(errMessage.value)
  }else if(!supaqifeedbackStore.aqi.information){
    errMessage.value="请填写反馈信息"
    alert(errMessage.value)
  }else{
    supaqifeedbackStore.aqi.telId=JSON.parse(sessionStorage.getItem("cur_user")).id
    supaqifeedbackStore.aqi.state=0
    axios({
      method: 'post',
      url: '/supervisor/aqifeedback',
      data: supaqifeedbackStore.aqi
    }).then(res=>{
            console.log(res.data.msg)
      alert(res.data.msg)
      //errMessage.value=res.data.msg
      supaqifeedbackStore.$reset()
      router.push({name: "HomePage-index"})
    })
  }
}
</script>

<template>
  <label>选择AQI</label>
  <div>
    <div>
      <label>请预估当前空气质量指数等级:</label><br/>
      <input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="1">一
      <input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="2">二
      <input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="3">三
      <input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="4">四
      <input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="5">五
      <input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="6">六
    </div>
    <div>
      <label>请填写反馈信息:</label><br/>
      <textarea cols="50" rows="10" v-model="supaqifeedbackStore.aqi.information" placeholder="请填写反馈信息"></textarea>
    </div>
    <button @click="send">提交</button>
  </div>
</template>

<style scoped>

</style>