<script setup>
import {useRouter} from "vue-router";
import useSupAqiFeedbackStore from "../../stores/supAqiFeedback.js";
import {onMounted, ref} from "vue";
import axios from "../../axios/index.js";

const router = useRouter()
const supaqifeedbackStore = useSupAqiFeedbackStore()
const errMessage = ref("")
const provinces = ref([])
const cities = ref([])

const getProvinces = () => {
    try {
        axios({
          method: 'get',
          url: '/grid/province/list'
        }).then(res=>{
          provinces.value = res.data.data
        })
      if(supaqifeedbackStore.aqi.provinceId){
        axios({
          method: 'get',
          url: `/grid/city/list/${supaqifeedbackStore.aqi.provinceId}`
        }).then(res=>{
          cities.value = res.data.data
        })
      }
    } catch (error) {
        console.error('获取省份失败', error)
    }
}

const changeProvince = () => {
    supaqifeedbackStore.aqi.cityId
    cities.value = []
    if (!supaqifeedbackStore.aqi.provinceId) {
        return
    }
    try {
        axios({
          method: 'get',
          url: `/grid/city/list/${supaqifeedbackStore.aqi.provinceId}`
        }).then(res=>{
          cities.value = res.data.data
        })
    } catch (error) {
        console.error('获取城市失败', error)
    }
}

function next(){
  errMessage.value=''
  if(!supaqifeedbackStore.aqi.provinceId){
    errMessage.value="请选择所在省"
    alert(errMessage.value)
  }else if(!supaqifeedbackStore.aqi.cityId){
    errMessage.value="请选择所在市"
    alert(errMessage.value)
  }else if(!supaqifeedbackStore.aqi.address.trim()){
    errMessage.value="请输入详细地址"
    alert(errMessage.value)
  }else{
    router.push('/supervisor/gridchoose')
  }
}

const clearStore = () => {
  return true
}

onMounted(()=>{
  clearStore()
  getProvinces()
})
</script>

<template>
  <h3>选择网格</h3>
  <div>
    <div>
      <label>所在省:</label>
      <select v-model="supaqifeedbackStore.aqi.provinceId" @change="changeProvince">
        <option value="">请选择省份</option>
        <option
            v-for="province in provinces"
            :key="province.provinceId"
            :value="province.provinceId"
        >{{ province.provinceName }}</option>
      </select>
      <label>所在市:</label>
      <select v-model="supaqifeedbackStore.aqi.cityId">
        <option value="">请选择城市</option>
        <option
            v-for="city in cities"
            :key="city.cityId"
            :value="city.cityId"
        >{{ city.cityName }}</option>
      </select>
    </div>
    <label>地址</label>
    <input type="text" placeholder="详细地址" v-model="supaqifeedbackStore.aqi.address">
    <button @click="next">下一步</button>
  </div>
</template>

<style scoped>

</style>