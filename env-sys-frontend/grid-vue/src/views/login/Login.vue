<script setup>
import {useRouter} from "vue-router";
import {ref} from "vue";
import axios from "../../axios/index.js";

const router = useRouter()
const errMessage = ref("")
const userName = ref("")
const Pwd = ref("")

function send(){
  //用axios向后端发送请求
  errMessage.value=''
  if(!userName.value.trim()){
    errMessage.value="用户名不能为空"
    alert(errMessage.value)
  }else if(!Pwd.value.trim()){
    errMessage.value="密码不能为空"
    alert(errMessage.value)
  }else{
    axios({
      method: 'post',
      url: '/grid/login',
      data: {
        userName: userName.value,
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
    <div class="login-card">
      <h2 class="title">东软环保公众监督平台-网格员端</h2>

      <div class="input-group">
        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="#b0bec5" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
        <input type="text" v-model="userName" placeholder="请输入登录编码"/>
      </div>

      <div class="input-group">
        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="#b0bec5" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect><path d="M7 11V7a5 5 0 0 1 10 0v4"></path></svg>
        <input type="password" v-model="Pwd" placeholder="请输入密码"/>
      </div>

      <button class="login-btn" @click="send">登录</button>

      <div class="register-wrap">
        <RouterLink class="register-link" :to="{name: 'GridRegisterPage-index'}">注册</RouterLink>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container { min-height: 100vh; background-color: #ffffff; display: flex; justify-content: center; align-items: center; font-family: sans-serif; }
.login-card { width: 320px; display: flex; flex-direction: column; align-items: center; padding: 20px; box-sizing: border-box; }
.title { color: #5da8d6; font-size: 20px; font-weight: bold; margin: 0 0 40px 0; letter-spacing: 1px; text-align: center; }
.input-group { width: 100%; display: flex; align-items: center; background-color: #f0f2f5; border-radius: 25px; padding: 12px 20px; margin-bottom: 20px; box-sizing: border-box; }
.icon { width: 20px; height: 20px; margin-right: 10px; flex-shrink: 0; }
.input-group input { border: none; outline: none; background: transparent; width: 100%; font-size: 15px; color: #333; }
.input-group input::placeholder { color: #9eaaab; }
.login-btn { width: 100%; background: linear-gradient(90deg, #6bd5f0, #6a9ef5); color: #ffffff; border: none; border-radius: 25px; padding: 14px 0; font-size: 18px; letter-spacing: 2px; cursor: pointer; margin-top: 10px; transition: opacity 0.3s; box-shadow: 0 4px 10px rgba(106, 158, 245, 0.3); }
.login-btn:hover { opacity: 0.9; }
.register-wrap { margin-top: 15px; }
.register-link { color: #78909c; font-size: 14px; text-decoration: none; }
.register-link:hover { color: #5da8d6; text-decoration: underline; }
</style>