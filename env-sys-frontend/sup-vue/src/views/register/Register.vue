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
  <div class="daisyui">
    <h1>注册</h1>
    <div>
      手机号:<input type="text" v-model="user.telId" placeholder="请输入手机号"/><br/>
    </div>
    <div>
      真实姓名:<input type="text" v-model="user.realName" placeholder="请输入姓名"/><br/>
    </div>
    <div>
      <span>性别:</span>
      <input type="radio" v-model="user.sex" value="1">男
      <input type="radio" v-model="user.sex" value="0">女
    </div>
    <div>
      出生日期:<input type="date" v-model="user.birthday"/><br/>
    </div>
    <div>
      密码:<input type="password" v-model="user.Pwd" placeholder="请输入密码"/><br/>
    </div>
    <div>
      确认密码:<input type="password" v-model="user.rePwd" placeholder="请确认密码"/><br/>
    </div>
    <div>
      <button @click="send">注册</button>
      <span>
        <RouterLink :to="{name: 'SupLoginPage-index'}">登录</RouterLink>
      </span>
    </div>
  </div>
</template>

<style scoped>
.daisyui{
}

</style>