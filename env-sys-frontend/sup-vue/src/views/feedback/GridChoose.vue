<script setup>
import useSupAqiFeedbackStore from "../../stores/supAqiFeedback.js";
import {ref} from "vue";
import axios from "../../axios/index.js";
import router from "../../router/index.js";

const supaqifeedbackStore = useSupAqiFeedbackStore()
const errMessage = ref("")

function send(){
  errMessage.value=''
  if(!supaqifeedbackStore.aqi.estimatedGrade){
    errMessage.value="请选择空气质量等级"
    alert(errMessage.value)
  }else if(!supaqifeedbackStore.aqi.information){
    errMessage.value="请填写反馈信息"
    alert(errMessage.value)
  }else{
    supaqifeedbackStore.aqi.telId=JSON.parse(sessionStorage.getItem("cur_user")).id
    supaqifeedbackStore.aqi.state=0
    axios({
      method: 'post',
      url: '/supervisor/aqifeedback',
      data: supaqifeedbackStore.aqi
    }).then(res=>{
            console.log(res.data.msg)
      alert(res.data.msg)
      //errMessage.value=res.data.msg
      supaqifeedbackStore.$reset()
      router.push({name: "HomePage-index"})
    })
  }
}
</script>

<template>
  <div class="page-container">
    <div class="aqi-table">
      <div class="table-row">
        <div class="table-cell grade-cell"><span>一</span><span>优</span></div>
        <div class="table-cell desc-cell">空气质量令人满意，基本无空气污染</div>
      </div>
      <div class="table-row">
        <div class="table-cell grade-cell"><span>二</span><span>良</span></div>
        <div class="table-cell desc-cell">空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响</div>
      </div>
      <div class="table-row">
        <div class="table-cell grade-cell"><span>三</span><span>轻度污染</span></div>
        <div class="table-cell desc-cell">易感人群症状有轻度加剧，健康人群出现刺激症状</div>
      </div>
      <div class="table-row">
        <div class="table-cell grade-cell"><span>四</span><span>中度污染</span></div>
        <div class="table-cell desc-cell">进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响</div>
      </div>
      <div class="table-row">
        <div class="table-cell grade-cell"><span>五</span><span>重度污染</span></div>
        <div class="table-cell desc-cell">心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状</div>
      </div>
      <div class="table-row">
        <div class="table-cell grade-cell"><span>六</span><span>严重污染</span></div>
        <div class="table-cell desc-cell">健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病</div>
      </div>
    </div>

    <div class="form-card">
      <div class="form-row">
        <label class="form-label">请预估当前空气质量指数等级:</label>
        <div class="radio-group">
          <label class="radio-btn color-1"><input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="1"><span>一</span></label>
          <label class="radio-btn color-2"><input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="2"><span>二</span></label>
          <label class="radio-btn color-3"><input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="3"><span>三</span></label>
          <label class="radio-btn color-4"><input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="4"><span>四</span></label>
          <label class="radio-btn color-5"><input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="5"><span>五</span></label>
          <label class="radio-btn color-6"><input type="radio" v-model="supaqifeedbackStore.aqi.estimatedGrade" value="6"><span>六</span></label>
        </div>
      </div>

      <div class="form-row">
        <label class="form-label">请填写反馈信息:</label>
        <textarea class="form-textarea" v-model="supaqifeedbackStore.aqi.information" placeholder="请填写反馈信息"></textarea>
      </div>

      <button class="submit-btn" @click="send">提交</button>
    </div>
  </div>
</template>

<style scoped>
.page-container { min-height: 100vh; background-color: #e6f2f0; display: flex; flex-direction: column; align-items: center; padding: 20px; font-family: sans-serif; gap: 20px; }
.aqi-table { background-color: #ffffff; width: 100%; max-width: 500px; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }
.table-row { display: flex; border-bottom: 1px solid #e0e0e0; }
.table-row:last-child { border-bottom: none; }
.table-cell { padding: 10px 15px; font-size: 13px; color: #5d6b6a; display: flex; align-items: center; }
.grade-cell { width: 70px; flex-shrink: 0; border-right: 1px solid #e0e0e0; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: 2px; }
.grade-cell span:first-child { font-size: 16px; font-weight: bold; color: #333; }
.grade-cell span:last-child { font-size: 12px; }
.desc-cell { flex: 1; line-height: 1.5; }
.form-card { background-color: #ffffff; border-radius: 12px; padding: 25px; width: 100%; max-width: 500px; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); display: flex; flex-direction: column; gap: 20px; }
.form-row { display: flex; flex-direction: column; gap: 10px; }
.form-label { font-size: 15px; color: #5d6b6a; font-weight: bold; }
.radio-group { display: flex; gap: 15px; flex-wrap: wrap; }
.radio-btn { display: flex; align-items: center; justify-content: center; width: 36px; height: 36px; border-radius: 50%; cursor: pointer; position: relative; border: 2px solid transparent; transition: all 0.2s; }
.radio-btn input { position: absolute; opacity: 0; cursor: pointer; }
.radio-btn span { color: #ffffff; font-size: 16px; font-weight: bold; }
.color-1 { background-color: #4caf50; border-color: #4caf50; }
.color-2 { background-color: #ffeb3b; border-color: #ffeb3b; }
.color-2 span { color: #333; }
.color-3 { background-color: #ff9800; border-color: #ff9800; }
.color-4 { background-color: #f44336; border-color: #f44336; }
.color-5 { background-color: #9c27b0; border-color: #9c27b0; }
.color-6 { background-color: #212121; border-color: #212121; }
.radio-btn input:checked + span { transform: scale(1.1); }
.radio-btn:has(input:checked) { box-shadow: 0 0 0 3px #e6f2f0, 0 0 0 5px #75b7c8; }
.form-textarea { width: 100%; height: 120px; border: 1px solid #e0e0e0; border-radius: 8px; padding: 12px; font-size: 15px; color: #333; outline: none; resize: none; transition: border-color 0.3s; box-sizing: border-box; }
.form-textarea:focus { border-color: #75b7c8; }
.form-textarea::placeholder { color: #9eaaab; }
.submit-btn { width: 100%; background-color: #75b7c8; color: #ffffff; border: none; border-radius: 6px; padding: 14px 0; font-size: 18px; letter-spacing: 2px; cursor: pointer; margin-top: 10px; transition: background-color 0.3s; box-shadow: 0 2px 6px rgba(117, 183, 200, 0.4); }
.submit-btn:hover { background-color: #62a4b5; }
</style>