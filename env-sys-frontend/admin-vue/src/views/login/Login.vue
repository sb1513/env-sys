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
      url: '/admin/login',
      data: {
        user_name: userName.value,
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
      <div class="card-header">
        <h2 class="title">登录</h2>
      </div>

      <div class="card-body">
        <div class="form-row">
          <label class="form-label">登录编码</label>
          <input class="form-input" type="text" v-model="userName" placeholder="请输入用户名"/>
        </div>

        <div class="form-row">
          <label class="form-label">登录密码</label>
          <div class="input-wrapper">
            <input class="form-input" type="password" v-model="Pwd" placeholder="请输入密码"/>
            <svg class="eye-icon" viewBox="0 0 24 24" fill="none" stroke="#b0bec5" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle><line x1="1" y1="1" x2="23" y2="23"></line></svg>
          </div>
        </div>

        <div class="btn-row">
          <button class="login-btn" @click="send">登录</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container { min-height: 100vh; background-color: #f0f2f5; display: flex; justify-content: center; align-items: center; font-family: sans-serif; }
.login-card { background-color: #ffffff; border-radius: 4px; width: 420px; box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05); overflow: hidden; }
.card-header { padding: 20px 25px; border-bottom: 1px solid #ebeef5; }
.title { font-size: 20px; color: #303133; margin: 0; font-weight: normal; letter-spacing: 1px; }
.card-body { padding: 30px 25px; display: flex; flex-direction: column; gap: 25px; }
.form-row { display: flex; align-items: center; }
.form-label { width: 80px; font-size: 15px; color: #606266; text-align: right; margin-right: 15px; flex-shrink: 0; }
.form-input { flex: 1; border: 1px solid #dcdfe6; border-radius: 4px; padding: 10px 15px; font-size: 15px; color: #606266; outline: none; transition: border-color 0.2s; box-sizing: border-box; width: 100%; }
.form-input:focus { border-color: #409eff; }
.form-input::placeholder { color: #c0c4cc; }
.input-wrapper { flex: 1; position: relative; display: flex; align-items: center; }
.input-wrapper .form-input { padding-right: 40px; }
.eye-icon { position: absolute; right: 12px; width: 18px; height: 18px; cursor: pointer; flex-shrink: 0; }
.btn-row { display: flex; justify-content: flex-start; padding-left: 95px; }
.login-btn { background-color: #409eff; color: #ffffff; border: none; border-radius: 4px; padding: 12px 40px; font-size: 16px; cursor: pointer; transition: background-color 0.3s; }
.login-btn:hover { background-color: #66b1ff; }
</style>