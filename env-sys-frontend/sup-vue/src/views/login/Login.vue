<script setup>
import {useRouter} from "vue-router";
import {ref} from "vue";
import axios from "../../axios/index.js";

const router = useRouter()
const errMessage = ref("")
const phone = ref("")
const Pwd = ref("")

function send(){
  //用axios向后端发送请求
  errMessage.value=''
  if(!phone.value.trim()){
    errMessage.value="手机号不能为空"
    alert(errMessage.value)
  }else if(!Pwd.value.trim()){
    errMessage.value="密码不能为空"
    alert(errMessage.value)
  }else{
    axios({
      method: 'post',
      url: '/supervisor/login',
      data: {
        phone: phone.value,
        password: Pwd.value
      }
    }).then(res=>{
      alert(res.data.msg)
      errMessage.value=res.data.msg

      let user = res.data.data.user
      let token = res.data.data.token
      sessionStorage.setItem("cur_user",JSON.stringify(user))
      sessionStorage.setItem("token",token)
      router.push({name: "HomePage-index"})
    })
  }
  //console.log(errMessage)
}
</script>

<template>
  <div class="login-container">
    <div class="header">
      <h1 class="title">东软环保公众监督平台</h1>
      <div class="subtitle-wrap">
        <span class="line"></span>
        <span class="subtitle">公众监督员端</span>
        <span class="line"></span>
      </div>
    </div>

    <div class="login-card">
      <div class="input-group">
        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
        <input type="text" v-model="phone" placeholder="输入手机号"/>
      </div>

      <div class="input-group">
        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect><path d="M7 11V7a5 5 0 0 1 10 0v4"></path></svg>
        <input type="password" v-model="Pwd" placeholder="输入登录密码"/>
      </div>

      <div class="register-wrap">
        <RouterLink class="register-link" :to="{name: 'SupRegisterPage-index'}">注册</RouterLink>
      </div>

      <button class="login-btn" @click="send">登录</button>
    </div>
  </div>
</template>

<style scoped>
.login-container { min-height: 100vh; background-color: #e0f2f1; display: flex; flex-direction: column; align-items: center; padding-top: 8vh; font-family: sans-serif; }
.header { text-align: center; margin-bottom: 30px; }
.title { color: #4caf50; font-size: 26px; font-weight: bold; margin: 0 0 15px 0; letter-spacing: 1px; }
.subtitle-wrap { display: flex; align-items: center; justify-content: center; gap: 10px; }
.line { width: 60px; height: 1px; background-color: #81c784; }
.subtitle { color: #4caf50; font-size: 16px; }
.login-card { background-color: #ffffff; border-radius: 12px; padding: 35px 25px; width: 320px; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); }
.input-group { display: flex; align-items: center; border: 1px solid #e0e0e0; border-radius: 8px; padding: 12px 15px; margin-bottom: 20px; background-color: #fcfcfc; }
.icon { width: 20px; height: 20px; color: #b0bec5; margin-right: 10px; flex-shrink: 0; }
.input-group input { border: none; outline: none; background: transparent; width: 100%; font-size: 15px; color: #333; }
.input-group input::placeholder { color: #b0bec5; }
.register-wrap { display: flex; justify-content: flex-start; margin-bottom: 15px; }
.register-link { color: #78909c; font-size: 14px; text-decoration: none; cursor: pointer; }
.login-btn { width: 100%; background-color: #75b7c8; color: #ffffff; border: none; border-radius: 8px; padding: 14px 0; font-size: 18px; letter-spacing: 2px; cursor: pointer; transition: background-color 0.3s; }
.login-btn:hover { background-color: #62a4b5; }
</style>