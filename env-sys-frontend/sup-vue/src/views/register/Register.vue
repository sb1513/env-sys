<script setup>
import {ref} from "vue";
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const today = new Date().toISOString().split('T')[0]

const user = ref({
  telId: "",
  realName: "",
  birthday: today,
  sex: 1,//1是男，0是女
  Pwd: "",
  rePwd: "",
})
const errMessage = ref("")
const router = useRouter()

function send(){
  //用axios向后端发送请求
  errMessage.value=''
  if(!user.value.telId.trim()){
    errMessage.value="手机号不能为空"
    alert(errMessage.value)
  }else if(!user.value.realName.trim()){
    errMessage.value="名字不能为空"
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
      url: '/supervisor/register',
      data: user.value
    }).then(res=>{
      alert(res.data.msg)
      //errMessage.value=res.data.msg
      router.push({name: "HomePage-index"})
    })
  }
  //console.log(errMessage)
}

</script>

<template>
  <div class="register-container">
    <div class="header-banner">
      <h1 class="banner-text">保护环境人人有责</h1>
    </div>

    <div class="form-card">
      <div class="form-row">
        <label class="form-label">手机号码</label>
        <input class="form-input" type="text" v-model="user.telId" placeholder="请输入手机号码"/>
      </div>

      <div class="form-row">
        <label class="form-label">真实姓名</label>
        <input class="form-input" type="text" v-model="user.realName" placeholder="真实姓名便于我们联系您"/>
      </div>

      <div class="form-row">
        <label class="form-label">出生日期</label>
        <input class="form-input" type="date" v-model="user.birthday"/>
      </div>

      <div class="form-row">
        <label class="form-label">性别</label>
        <div class="radio-group">
          <label class="radio-label"><input type="radio" v-model="user.sex" :value="1"> 男</label>
          <label class="radio-label"><input type="radio" v-model="user.sex" :value="0"> 女</label>
        </div>
      </div>

      <div class="form-row">
        <label class="form-label">密码</label>
        <input class="form-input" type="password" v-model="user.Pwd" placeholder="请输入密码"/>
      </div>

      <div class="form-row">
        <label class="form-label">确认密码</label>
        <input class="form-input" type="password" v-model="user.rePwd" placeholder="请再次输入密码"/>
      </div>

      <button class="submit-btn" @click="send">注册</button>
      <div class="login-link-wrap">
        <RouterLink class="login-link" :to="{name: 'SupLoginPage-index'}">已有账号？去登录</RouterLink>
      </div>
    </div>
  </div>
</template>

<style scoped>
.register-container { min-height: 100vh; background-color: #e6f2f0; font-family: sans-serif; display: flex; flex-direction: column; align-items: center; }
.header-banner { width: 100%; height: 80px; display: flex; justify-content: center; align-items: center; margin-bottom: 20px; margin-top: 30px; }
.banner-text { color: #4caf50; font-size: 28px; font-weight: bold; margin: 0; letter-spacing: 2px; }
.form-card { background-color: #ffffff; border-radius: 12px; padding: 35px 30px; width: 350px; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); display: flex; flex-direction: column; gap: 20px; }
.form-row { display: flex; align-items: center; justify-content: space-between; }
.form-label { width: 70px; font-size: 15px; color: #5d6b6a; font-weight: bold; flex-shrink: 0; }
.form-input { flex: 1; border: none; border-bottom: 1px solid #b0bec5; background: transparent; padding: 6px 0; font-size: 14px; color: #333; outline: none; transition: border-color 0.3s; }
.form-input:focus { border-bottom-color: #75b7c8; }
.form-input::placeholder { color: #9eaaab; }
.radio-group { flex: 1; display: flex; gap: 20px; }
.radio-label { font-size: 14px; color: #5d6b6a; display: flex; align-items: center; gap: 5px; cursor: pointer; }
.radio-label input[type="radio"] { accent-color: #75b7c8; cursor: pointer; }
.submit-btn { width: 100%; background-color: #75b7c8; color: #ffffff; border: none; border-radius: 6px; padding: 14px 0; font-size: 18px; letter-spacing: 2px; cursor: pointer; margin-top: 10px; transition: background-color 0.3s; box-shadow: 0 2px 6px rgba(117, 183, 200, 0.4); }
.submit-btn:hover { background-color: #62a4b5; }
.login-link-wrap { text-align: center; margin-top: 5px; }
.login-link { color: #78909c; font-size: 14px; text-decoration: none; }
.login-link:hover { color: #4caf50; text-decoration: underline; }
</style>