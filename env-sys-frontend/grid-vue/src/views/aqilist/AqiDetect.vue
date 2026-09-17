<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import {useRoute, useRouter} from "vue-router";
import axios from "../../axios/index.js";

const route = useRoute()
const router = useRouter()
const afId = route.params.afId
const feedbackInfo = ref({})
const aqiStandards = ref([])

// 2. 表单数据
const formData = ref({
  so2: null,
  co: null,
  spm: null
});

const getAqiStandards = () => {
  axios({
    method: 'get',
    url: '/grid/aqistandard'
  }).then(res=>{
    aqiStandards.value = res.data.data
    //console.log(res.data.data)
  })
}

const getAqiDetail = () => {
  axios({
    method: 'get',
    url: `/grid/aqidetail/${afId}`
  }).then(res=>{
    feedbackInfo.value = res.data.data
    //console.log(res.data.msg)
  })
}

// 辅助函数：根据数值和类型查找对应的等级
const findGrade = (value, type) => {
  if (value === null || value === undefined || value === '') {
  return {
    aqiId: 0, chineseExplain: '-', aqiExplain: '', color: '#cccccc'
  };
}

  for (let standard of aqiStandards.value) {
    let min, max;
    if (type === 'so2') { min = standard.so2Min; max = standard.so2Max; }
    if (type === 'co') { min = standard.coMin; max = standard.coMax; }
    if (type === 'spm') { min = standard.spmMin; max = standard.spmMax; }

    if (value >= min && value <= max) {
      return standard;
    }
  }
  // 如果超出最大值，默认按最高等级算
  return aqiStandards.value[aqiStandards.value.length - 1];
};

// 计算各项指标的单独等级
const getSo2Grade = () => findGrade(formData.value.so2, 'so2');
const getCoGrade = () => findGrade(formData.value.co, 'co');
const getSpmGrade = () => findGrade(formData.value.spm, 'spm');

const finalAqi = computed(() => {
  if (formData.value.so2 === null && formData.value.co === null && formData.value.spm === null) {
    return { aqiId: 0, chineseExplain: '--', aqiExplain: '等待输入', color: '#cccccc' };
  }

  const grades = [
    getSo2Grade(),
    getCoGrade(),
    getSpmGrade()
  ];

  const validGrades = grades.filter(g => g.aqiId !== 0);

  if (validGrades.length === 0) {
    return { aqiId: 0, chineseExplain: '--', aqiExplain: '等待输入', color: '#cccccc' };
  }

  return validGrades.reduce((prev, current) =>
    prev.aqiId > current.aqiId ? prev : current
  );
});

// 获取中文等级文字
const getGradeText = (gradeId) => {
  const standard = aqiStandards.value.find(s => Number(s.aqiId) === Number(gradeId));
  return standard ? standard.chineseExplain : '';
};

// 获取等级描述
const getGradeExplain = (gradeId) => {
  const standard = aqiStandards.value.find(s => Number(s.aqiId) === Number(gradeId));
  return standard ? standard.aqiExplain : '';
};

// 提交数据
const submitData = () => {
  if (formData.value.so2 == null || formData.value.so2 === '' || formData.value.co == null || formData.value.co === '' || formData.value.spm == null || formData.value.spm === '') {
    alert('请填写完整检测数据')
    return
  }
  const submitPayload = {
    fdId: feedbackInfo.value.telId,
    provinceId: feedbackInfo.value.provinceId,
    cityId: feedbackInfo.value.cityId,
    address: feedbackInfo.value.address,
    so2Value: formData.value.so2,
    so2Level: getSo2Grade().aqiId,
    coValue: formData.value.co,
    coLevel: getCoGrade().aqiId,
    spmValue: formData.value.spm,
    spmLevel: getSpmGrade().aqiId,
    gmId: feedbackInfo.value.gmId,
    aqiId: finalAqi.value.aqiId, // 提交最终计算出的等级
    information: feedbackInfo.value.information,
    // ... 其他需要提交的字段
  };
  axios({
    method:'post',
    url: `/grid/postaqi/${feedbackInfo.value.afId}`,
    data: submitPayload
  }).then(res=>{
      alert('提交成功！最终等级：' + finalAqi.value.aqiExplain);
      //console.log(res.data.msg)
      router.push('/grid/aqilist')
  })
};

// 模拟从后端获取 AQI 标准数据
onMounted(() => {
  // axios.get('/api/aqi/standards').then(res => { aqiStandards.value = res.data })
  getAqiStandards()
  getAqiDetail()
});
</script>

<template>
  <div class="aqi-detection-page">
    <!-- 顶部反馈详情卡片 -->
    <div class="detail-card">
      <div class="detail-row">
        <span class="label">反馈用户</span>
        <span class="value">{{ feedbackInfo.realName }} {{ feedbackInfo.telId }}</span>
      </div>
      <div class="detail-row">
        <span class="label">网格</span>
        <span class="value">{{ feedbackInfo.provinceName }} {{ feedbackInfo.cityName }}</span>
      </div>
      <div class="detail-row">
        <span class="label">详细地址</span>
        <span class="value">{{ feedbackInfo.address }}</span>
      </div>
      <div class="detail-row">
        <span class="label">预估等级</span>
        <span class="value">{{ getGradeText(feedbackInfo.estimatedGrade) }}级 {{ getGradeExplain(feedbackInfo.estimatedGrade) }}</span>
      </div>
      <div class="detail-row">
        <span class="label">信息描述</span>
        <span class="value">{{ feedbackInfo.information }}</span>
      </div>
    </div>

    <!-- 数据录入区域 -->
    <div class="input-section">
      <!-- SO2 输入 -->
      <div class="input-row">
        <span class="input-label">SO₂浓度</span>
        <div class="input-wrapper">
          <input type="number" v-model.number="formData.so2" placeholder="请输入SO₂浓度"/>
          <span class="unit">ug/m3</span>
        </div>
        <div class="grade-badge" :style="{ backgroundColor: getSo2Grade().color }">{{ getSo2Grade().chineseExplain }}</div>
      </div>

      <!-- CO 输入 -->
      <div class="input-row">
        <span class="input-label">CO浓度</span>
        <div class="input-wrapper">
          <input type="number" v-model.number="formData.co" placeholder="请输入CO浓度"/>
          <span class="unit">ug/m3</span>
        </div>
        <div class="grade-badge" :style="{ backgroundColor: getCoGrade().color }">{{ getCoGrade().chineseExplain }}</div>
      </div>

      <!-- PM2.5 输入 -->
      <div class="input-row">
        <span class="input-label">PM2.5</span>
        <div class="input-wrapper">
          <input type="number" v-model.number="formData.spm" placeholder="请输入PM2.5浓度"/>
          <span class="unit">ug/m3</span>
        </div>
        <div class="grade-badge" :style="{ backgroundColor: getSpmGrade().color }">{{ getSpmGrade().chineseExplain }}</div>
      </div>
    </div>

    <!-- 实测 AQI 结果横幅 -->
    <div class="result-banner" :style="{ backgroundColor: finalAqi.color }">实测AQI等级：{{ finalAqi.chineseExplain }}级 {{ finalAqi.aqiExplain }}</div>

    <!-- 提交按钮 -->
    <div class="submit-section">
      <button class="submit-btn" @click="submitData">提交实测数据</button>
    </div>
    <RouterLink class="back-link" :to="{name:'AqiListPage-index'}">返回</RouterLink>
  </div>
</template>

<style scoped>
.aqi-detection-page { max-width: 500px; margin: 0 auto; padding: 20px; font-family: sans-serif; background-color: #f5f5f5; min-height: 100vh; box-sizing: border-box; }
.detail-card { border: 1px solid #e0e0e0; border-radius: 4px; margin-bottom: 20px; background-color: #fff; overflow: hidden; }
.detail-row { display: flex; border-bottom: 1px solid #e0e0e0; }
.detail-row:last-child { border-bottom: none; }
.detail-row .label { width: 80px; padding: 12px 10px; background-color: #f0f0f0; color: #666; font-size: 14px; border-right: 1px solid #e0e0e0; display: flex; align-items: center; flex-shrink: 0; }
.detail-row .value { flex: 1; padding: 12px 10px; color: #333; font-size: 14px; display: flex; align-items: center; word-break: break-all; }
.input-section { margin-bottom: 20px; }
.input-row { display: flex; align-items: center; margin-bottom: 15px; }
.input-label { width: 70px; font-size: 14px; color: #333; font-weight: bold; flex-shrink: 0; }
.input-wrapper { flex: 1; display: flex; align-items: center; position: relative; margin-right: 10px; }
.input-wrapper input { width: 100%; padding: 10px 45px 10px 15px; border: 1px solid #ccc; border-radius: 20px; font-size: 14px; outline: none; background-color: #f9f9f9; transition: border-color 0.2s; box-sizing: border-box; }
.input-wrapper input:focus { border-color: #409eff; background-color: #fff; }
.input-wrapper .unit { position: absolute; right: 15px; font-size: 12px; color: #999; }
.grade-badge { width: 32px; height: 32px; border-radius: 4px; display: flex; justify-content: center; align-items: center; color: #fff; font-weight: bold; font-size: 16px; flex-shrink: 0; }
.result-banner { text-align: center; padding: 15px; color: #fff; font-size: 16px; font-weight: bold; border-radius: 4px; margin-bottom: 30px; transition: background-color 0.3s; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
.submit-section { text-align: center; }
.submit-btn { background-color: #409eff; color: #fff; border: none; padding: 12px 60px; font-size: 16px; border-radius: 4px; cursor: pointer; transition: background-color 0.2s; box-shadow: 0 2px 5px rgba(64, 158, 255, 0.3); }
.submit-btn:hover { background-color: #66b1ff; }
.back-link { display: block; text-align: center; margin-top: 20px; color: #78909c; font-size: 14px; text-decoration: none; }
.back-link:hover { color: #409eff; text-decoration: underline; }
</style>