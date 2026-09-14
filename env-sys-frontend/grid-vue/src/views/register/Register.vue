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
  <div class="daisyui">
    <h1>注册</h1>
    <div>
      真实姓名:<input type="text" v-model="user.gmName" placeholder="请输入真实姓名"/><br/>
    </div>
    <div>
      用户名:<input type="text" v-model="user.gmCode" placeholder="请输入用户名"/><br/>
    </div>
    <div>
      <label>省份:</label>
      <select v-model="user.provinceId" @change="changeProvince">
        <option value="">请选择省份</option>
        <option
            v-for="province in provinces"
            :key="province.provinceId"
            :value="province.provinceId"
        >{{ province.provinceName }}</option>
      </select>
      <label>城市:</label>
      <select v-model="user.cityId">
        <option value="">请选择城市</option>
        <option
            v-for="city in cities"
            :key="city.cityId"
            :value="city.cityId"
        >{{ city.cityName }}</option>
      </select>
    </div>
    <div>
      手机号:<input type="text" v-model="user.tel" placeholder="请输入手机号"/><br/>
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
        <RouterLink :to="{name: 'GridLoginPage-index'}">登录</RouterLink>
      </span>
    </div>
  </div>
</template>

<style scoped>
.daisyui{
}

</style>