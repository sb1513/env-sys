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
  <div class="page-container">
    <div class="form-card">
      <div class="form-row">
        <label class="form-label">所在省</label>
        <select class="form-select" v-model="supaqifeedbackStore.aqi.provinceId" @change="changeProvince">
          <option value="">请选择省份</option>
          <option v-for="province in provinces" :key="province.provinceId" :value="province.provinceId">{{ province.provinceName }}</option>
        </select>
      </div>

      <div class="form-row">
        <label class="form-label">所在市</label>
        <select class="form-select" v-model="supaqifeedbackStore.aqi.cityId">
          <option value="">请选择市区</option>
          <option v-for="city in cities" :key="city.cityId" :value="city.cityId">{{ city.cityName }}</option>
        </select>
      </div>

      <div class="form-row">
        <label class="form-label">地址</label>
        <input class="form-input" type="text" placeholder="具体地址" v-model="supaqifeedbackStore.aqi.address">
      </div>

      <button class="next-btn" @click="next">下一步</button>
    </div>
  </div>
</template>

<style scoped>
.page-container { min-height: 100vh; background-color: #e6f2f0; display: flex; justify-content: center; align-items: center; font-family: sans-serif; }
.form-card { background-color: #ffffff; border-radius: 12px; padding: 35px 25px; width: 320px; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); display: flex; flex-direction: column; gap: 25px; }
.form-row { display: flex; align-items: center; }
.form-label { width: 60px; font-size: 15px; color: #5d6b6a; font-weight: bold; flex-shrink: 0; }
.form-select { flex: 1; border: none; outline: none; background: transparent; font-size: 15px; color: #333; cursor: pointer; appearance: none; -webkit-appearance: none; background-image: url("data:image/svg+xml;charset=US-ASCII,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%22292.4%22%20height%3D%22292.4%22%3E%3Cpath%20fill%3D%22%23333%22%20d%3D%22M287%2069.4a17.6%2017.6%200%200%200-13-5.4H18.4c-5%200-9.3%201.8-12.9%205.4A17.6%2017.6%200%200%200%200%2082.2c0%205%201.8%209.3%205.4%2012.9l128%20127.9c3.6%203.6%207.8%205.4%2012.8%205.4s9.2-1.8%2012.8-5.4L287%2095c3.5-3.5%205.4-7.8%205.4-12.8%200-5-1.9-9.2-5.5-12.8z%22%2F%3E%3C%2Fsvg%3E"); background-repeat: no-repeat; background-position: right 0 center; background-size: 10px; padding-right: 20px; }
.form-input { flex: 1; border: none; border-bottom: 1px solid #b0bec5; background: transparent; padding: 6px 0; font-size: 15px; color: #333; outline: none; transition: border-color 0.3s; }
.form-input:focus { border-bottom-color: #75b7c8; }
.form-input::placeholder { color: #9eaaab; }
.next-btn { width: 100%; background-color: #75b7c8; color: #ffffff; border: none; border-radius: 6px; padding: 14px 0; font-size: 18px; letter-spacing: 2px; cursor: pointer; margin-top: 10px; transition: background-color 0.3s; box-shadow: 0 2px 6px rgba(117, 183, 200, 0.4); }
.next-btn:hover { background-color: #62a4b5; }
</style>