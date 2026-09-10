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
  <div class="daisyui">
    <h1>登录</h1>
    <div>
      手机号:<input type="text" v-model="phone" placeholder="请输入手机号"/><br/>
    </div>
    <div>
      密码:<input type="password" v-model="Pwd" placeholder="请输入密码"/><br/>
    </div>
    <div>
      <button @click="send">登录</button>
      <span>
        <RouterLink :to="{name: 'SupRegisterPage-index'}">注册</RouterLink>
      </span>
    </div>
  </div>
</template>

<style scoped>

</style>