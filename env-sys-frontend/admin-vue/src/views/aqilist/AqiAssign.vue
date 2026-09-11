<script setup>
import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import axios from "../../axios/index.js";

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const submitting = ref(false)
const errorMessage = ref("")
const feedback = ref(null)

const isOther = ref(false)
const province = ref("")
const city = ref("")
const gridmember = ref("")

const provinces = ref([])
const cities = ref([])
const gridmembers = ref([])

const getDetail = () => {
  axios({
    method: "get",
    url: `/admin/supervisor/aqidetail/${route.params.id}`
  }).then(res => {
    feedback.value = res.data.data;
    //console.log(feedback.value.cityName)
    city.value = feedback.value.cityId
    getGridMember()
  }).catch(err => {
    console.log(err);
  }).finally(() => {
    loading.value = false;
  });
}

const getProvinces = () => {
  axios({
    method: "get",
    url: "/grid/province/list"
  }).then(res => {
    provinces.value = res.data.data
  })
}

const getCities = () => {
    city.value = ""
    gridmember.value = ""
    cities.value = []
    gridmembers.value = []
    if (!province.value) {
        return
    }
    axios({
      method: "get",
      url: `/grid/city/list/${province.value}`
    }).then(res => {
      cities.value = res.data.data
    })
}

const getGridMember = () => {
    gridmember.value = ""
    gridmembers.value = []
    if (!city.value) {
        return
    }
    axios({
      method: "get",
      url: `/admin/gridmember/${city.value}`
    }).then(res => {
      gridmembers.value = res.data.data
    })
}

const changeOther = () => {
    gridmember.value = ""
    if (!isOther.value && feedback.value?.cityId) {
        city.value = feedback.value.cityId
        getGridMember()
    } else {
        gridmembers.value = []
    }
}

const assign = () => {
    if (!feedback.value?.afId) {
        return
    }
    if (!gridmember.value) {
        alert("请选择指派监督员")
        return
    }
    submitting.value = true
    const data = {
        afId: feedback.value.afId,
        gmId: gridmember.value,
        state: 1
    }
    axios({
      method: "post",
      url: "/admin/supervisor/aqiassign",
      data: data
    }).then(res => {
      alert("指派成功")
      router.back()
    })
    submitting.value = false
}

const getGradeText = (grade) => {
    const map = {
        1: "优",
        2: "良",
        3: "轻度污染",
        4: "中度污染",
        5: "重度污染",
        6: "严重污染"
    }
    return map[grade] || "未知"
}

const getStateText = (state) => {
    const map = {
        0: "未处理",
        1: "已指派",
        2: "处理中",
        3: "已完成"
    }
    return map[state] || "未知"
}

const back = () => {
    router.back()
}

onMounted(() => {
    getProvinces()
    getDetail()
})
</script>

<template>
    <div class="detail-container">
        <div class="title">
            <span>公众监督员反馈详情</span>
            <button class="back-btn" @click="back">返回</button>
        </div>

        <div v-if="loading" class="loading">加载中...</div>

        <div v-else-if="!feedback" class="empty">
            {{ errorMessage || "暂无反馈信息" }}
        </div>

        <div v-else class="detail-table">
            <div class="detail-row">
                <div class="label">公众监督员反馈编号</div>
                <div class="value">{{ feedback.afId }}</div>
            </div>

            <div class="detail-row">
                <div class="label">反馈者信息</div>
                <div class="value">
                    <span class="tag">{{ feedback.realName }}</span>
                </div>
            </div>

            <div class="detail-row">
                <div class="label">反馈者联系电话</div>
                <div class="value">{{ feedback.telId }}</div>
            </div>

            <div class="detail-row">
                <div class="label">反馈信息所在地</div>
                <div class="value">
                    <span class="tag">{{ feedback.provinceName }}</span>
                    <span class="tag">{{ feedback.cityName }}</span>
                    <span class="tag">{{ feedback.address }}</span>
                </div>
            </div>

            <div class="detail-row">
                <div class="label">反馈信息描述</div>
                <div class="value">{{ feedback.information }}</div>
            </div>

            <div class="detail-row">
                <div class="label">预估等级</div>
                <div class="value">
                    <span class="grade" :class="'grade' + feedback.estimatedGrade">
                        {{ getGradeText(feedback.estimatedGrade) }}
                    </span>
                </div>
            </div>

            <div class="detail-row">
                <div class="label">反馈日期时间</div>
                <div class="value">
                    <span class="tag">{{ feedback.afDate }}</span>
                    <span class="tag">{{ feedback.afTime }}</span>
                </div>
            </div>

            <div class="detail-row">
                <div class="label">处理状态</div>
                <div class="value">
                    <span class="tag">{{ getStateText(feedback.state) }}</span>
                </div>
            </div>

            <div v-if="feedback.assignDate || feedback.assignTime" class="detail-row">
                <div class="label">指派时间</div>
                <div class="value tags">
                    <span class="tag">{{ feedback.assignDate }}</span>
                    <span class="tag">{{ feedback.assignTime }}</span>
                </div>
            </div>

            <div v-if="feedback.remarks" class="detail-row">
                <div class="label">备注</div>
                <div class="value">{{ feedback.remarks }}</div>
            </div>
        </div>

        <div v-if="feedback" class="assign-area">
            <div class="assign-row">
                <span class="assign-label">是否异地抽检</span>
                <input
                    type="checkbox"
                    v-model="isOther"
                    @change="changeOther"
                    class="switch"
                />
            </div>

            <div v-if="isOther" class="assign-row">
                <span class="assign-label">省区</span>
                <select v-model="province" @change="getCities">
                    <option value="">请选择</option>
                    <option
                        v-for="item in provinces"
                        :key="item.provinceId"
                        :value="item.provinceId"
                    >
                        {{ item.provinceName }}
                    </option>
                </select>

                <span class="assign-label">市区</span>
                <select v-model="city" @change="getGridMember()">
                    <option value="">请选择</option>
                    <option
                        v-for="item in cities"
                        :key="item.cityId"
                        :value="item.cityId"
                    >
                        {{ item.cityName }}
                    </option>
                </select>

                <span class="assign-label">异地抽检点</span>
                <select v-model="gridmember">
                    <option value="">请选择</option>
                    <option
                        v-for="item in gridmembers"
                        :key="item.gmId"
                        :value="item.gmId"
                    >
                        {{ item.gmName }}
                    </option>
                </select>

                <button
                    class="assign-btn"
                    @click="assign"
                    :disabled="submitting"
                >
                    {{ submitting ? "指派中..." : "异地指派" }}
                </button>
            </div>

            <div v-else class="assign-row">
                <span class="assign-label">指派监督员</span>

                <select v-model="gridmember">
                    <option value="">请选择</option>
                    <option
                        v-for="item in gridmembers"
                        :key="item.gmId"
                        :value="item.gmId"
                    >
                        {{ item.gmName }}
                    </option>
                </select>

                <button
                    class="assign-btn"
                    @click="assign"
                    :disabled="submitting"
                >
                    {{ submitting ? "指派中..." : "指派" }}
                </button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.detail-container {
    width: 100%;
    min-height: 100vh;
    background: #fff;
    font-size: 13px;
    color: #555;
}

.title {
    height: 35px;
    line-height: 35px;
    font-size: 14px;
    font-weight: bold;
    padding-left: 10px;
    border-bottom: 1px solid #eee;
    position: relative;
}

.back-btn {
    position: absolute;
    right: 10px;
    top: 3px;
    border: none;
    background: #409eff;
    color: white;
    padding: 5px 14px;
    border-radius: 3px;
    cursor: pointer;
}

.detail-row {
    display: flex;
    min-height: 34px;
    border-bottom: 1px solid #eee;
}

.label {
    width: 290px;
    background: #f5f7fa;
    padding: 8px 10px;
    box-sizing: border-box;
    font-weight: 500;
}

.value {
    flex: 1;
    padding: 8px 10px;
    box-sizing: border-box;
}

.tags {
    display: flex;
    align-items: center;
    gap: 6px;
}

.tag {
    background: #ecf5ff;
    color: #409eff;
    border-radius: 3px;
    padding: 3px 8px;
}

.grade {
    border-radius: 3px;
    padding: 3px 10px;
}

.grade1 {
    background: #67c23a;
    color: white;
}

.grade2 {
    background: #a6d96a;
    color: white;
}

.grade3 {
    background: #f7ba2a;
    color: white;
}

.grade4 {
    background: #e6a23c;
    color: white;
}

.grade5 {
    background: #f56c6c;
    color: white;
}

.grade6 {
    background: #c45656;
    color: white;
}

.assign-area {
    margin-top: 15px;
    padding: 0 10px;
}

.assign-row {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 12px;
}

.assign-label {
    color: #555;
    white-space: nowrap;
}

select {
    height: 30px;
    min-width: 130px;
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    padding: 0 8px;
    color: #606266;
    background: white;
}

.assign-btn {
    height: 30px;
    padding: 0 15px;
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    background: white;
    color: #606266;
    cursor: pointer;
}

.assign-btn:hover {
    color: #409eff;
    border-color: #409eff;
}

.assign-btn:disabled {
    opacity: .6;
    cursor: not-allowed;
}

.switch {
    width: 36px;
    height: 20px;
    cursor: pointer;
    accent-color: #409eff;
}

.loading {
    padding: 30px;
    text-align: center;
}

.empty {
    padding: 30px;
    text-align: center;
    color: #999;
}
</style>

