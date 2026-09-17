<script setup>
import {onMounted, ref} from "vue";
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const provinces = ref([])
const cities = ref([])

const user = ref({
  gmName: "",
  gmCode: "",
  provinceId: '',
  cityId: '',
  tel: "",
  Pwd: "",
  rePwd: "",
})
const errMessage = ref("")
const router = useRouter()

const getProvinces = () => {
    try {
        axios({
          method: 'get',
          url: '/grid/province/list'
        }).then(res=>{
          provinces.value = res.data.data
        })
    } catch (error) {
        console.error('获取省份失败', error)
    }
}

const changeProvince = () => {
    user.value.cityId = ''
    cities.value = []
    if (!user.value.provinceId) {
        return
    }
    try {
        axios({
          method: 'get',
          url: `/grid/city/list/${user.value.provinceId}`
        }).then(res=>{
          cities.value = res.data.data
        })
    } catch (error) {
        console.error('获取城市失败', error)
    }
}

function send(){
  //用axios向后端发送请求
  errMessage.value=''
  if(!user.value.gmName.trim()){
    errMessage.value="名字不能为空"
    alert(errMessage.value)
  }else if(!user.value.gmCode.trim()){
    errMessage.value="用户名不能为空"
    alert(errMessage.value)
  }else if(!user.value.provinceId){
    errMessage.value="请选择网格省份"
    alert(errMessage.value)
  }else if(!user.value.cityId){
    errMessage.value="请选择网格城市"
    alert(errMessage.value)
  }else if(!user.value.tel.trim()){
    errMessage.value="手机号不能为空"
    alert(errMessage.value)
  }else if(!user.value.Pwd.trim()){
    errMessage.value="密码不能为空"
    alert(errMessage.value)
  } else if(user.value.Pwd.trim()!==user.value.rePwd.trim()){
    errMessage.value="密码不相同"
    alert(errMessage.value)
  }else{
    axios({
      method: 'post',
      url: '/grid/register',
      data: user.value
    }).then(res=>{
      alert(res.data.msg)
      //errMessage.value=res.data.msg
      router.push({name: "HomePage-index"})
    })
  }
  //console.log(errMessage)
}

onMounted(() => {
    getProvinces()
})

</script>

<template>
  <div class="page-container">
    <div class="header-banner">
      <h1 class="banner-text">东软环保公众监督平台</h1>
      <div class="subtitle-wrap">
        <span class="line"></span>
        <span class="subtitle">网格员端</span>
        <span class="line"></span>
      </div>
    </div>

    <div class="form-card">
      <div class="form-row">
        <label class="form-label">真实姓名</label>
        <input class="form-input" type="text" v-model="user.gmName" placeholder="请输入真实姓名"/>
      </div>

      <div class="form-row">
        <label class="form-label">用户名</label>
        <input class="form-input" type="text" v-model="user.gmCode" placeholder="请输入用户名"/>
      </div>

      <div class="form-row">
        <label class="form-label">省份</label>
        <select class="form-select" v-model="user.provinceId" @change="changeProvince">
          <option value="">请选择省份</option>
          <option v-for="province in provinces" :key="province.provinceId" :value="province.provinceId">{{ province.provinceName }}</option>
        </select>
      </div>

      <div class="form-row">
        <label class="form-label">城市</label>
        <select class="form-select" v-model="user.cityId">
          <option value="">请选择城市</option>
          <option v-for="city in cities" :key="city.cityId" :value="city.cityId">{{ city.cityName }}</option>
        </select>
      </div>

      <div class="form-row">
        <label class="form-label">手机号</label>
        <input class="form-input" type="text" v-model="user.tel" placeholder="请输入手机号"/>
      </div>

      <div class="form-row">
        <label class="form-label">密码</label>
        <input class="form-input" type="password" v-model="user.Pwd" placeholder="请输入密码"/>
      </div>

      <div class="form-row">
        <label class="form-label">确认密码</label>
        <input class="form-input" type="password" v-model="user.rePwd" placeholder="请确认密码"/>
      </div>

      <button class="submit-btn" @click="send">注册</button>
      <div class="login-link-wrap">
        <RouterLink class="login-link" :to="{name: 'GridLoginPage-index'}">已有账号？去登录</RouterLink>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page-container { min-height: 100vh; background-color: #e6f2f0; display: flex; flex-direction: column; align-items: center; padding-top: 40px; font-family: sans-serif; }
.header-banner { text-align: center; margin-bottom: 25px; }
.banner-text { color: #4caf50; font-size: 24px; font-weight: bold; margin: 0 0 15px 0; letter-spacing: 1px; }
.subtitle-wrap { display: flex; align-items: center; justify-content: center; gap: 10px; }
.line { width: 50px; height: 1px; background-color: #81c784; }
.subtitle { color: #4caf50; font-size: 16px; }
.form-card { background-color: #ffffff; border-radius: 12px; padding: 30px 25px; width: 360px; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); display: flex; flex-direction: column; gap: 20px; box-sizing: border-box; }
.form-row { display: flex; align-items: center; justify-content: space-between; }
.form-label { width: 70px; font-size: 15px; color: #5d6b6a; font-weight: bold; flex-shrink: 0; }
.form-input { flex: 1; border: none; border-bottom: 1px solid #b0bec5; background: transparent; padding: 6px 0; font-size: 14px; color: #333; outline: none; transition: border-color 0.3s; }
.form-input:focus { border-bottom-color: #75b7c8; }
.form-input::placeholder { color: #9eaaab; }
.form-select { flex: 1; border: none; border-bottom: 1px solid #b0bec5; outline: none; background: transparent; font-size: 14px; color: #333; cursor: pointer; appearance: none; -webkit-appearance: none; background-image: url("data:image/svg+xml;charset=US-ASCII,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%22292.4%22%20height%3D%22292.4%22%3E%3Cpath%20fill%3D%22%23333%22%20d%3D%22M287%2069.4a17.6%2017.6%200%200%200-13-5.4H18.4c-5%200-9.3%201.8-12.9%205.4A17.6%2017.6%200%200%200%200%2082.2c0%205%201.8%209.3%205.4%2012.9l128%20127.9c3.6%203.6%207.8%205.4%2012.8%205.4s9.2-1.8%2012.8-5.4L287%2095c3.5-3.5%205.4-7.8%205.4-12.8%200-5-1.9-9.2-5.5-12.8z%22%2F%3E%3C%2Fsvg%3E"); background-repeat: no-repeat; background-position: right 0 center; background-size: 10px; padding-right: 20px; }
.form-select:focus { border-bottom-color: #75b7c8; }
.submit-btn { width: 100%; background-color: #75b7c8; color: #ffffff; border: none; border-radius: 6px; padding: 14px 0; font-size: 18px; letter-spacing: 2px; cursor: pointer; margin-top: 10px; transition: background-color 0.3s; box-shadow: 0 2px 6px rgba(117, 183, 200, 0.4); }
.submit-btn:hover { background-color: #62a4b5; }
.login-link-wrap { text-align: center; margin-top: 5px; }
.login-link { color: #78909c; font-size: 14px; text-decoration: none; }
.login-link:hover { color: #4caf50; text-decoration: underline; }
</style>