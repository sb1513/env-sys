<script setup>
import {onMounted, ref, computed} from "vue";
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const router = useRouter()
const aqiQuery = ref([])
const province = ref("")
const city = ref("")
const grade = ref("")
const feedbackDate = ref("")
const pageNum = ref(1)
const pageTotal = ref(1)
const pageSize = ref(0)

const changePage = (num) => {
  pageNum.value = num
  loadAqi()
}

const search = () => {
  pageNum.value = 1
  loadAqi()
}

const loadAqi = () => {
  let params = new URLSearchParams()
  params.append("pageNum", pageNum.value)
  params.append("pageSize", 15)
  params.append("state", 1)

  if (province.value) {
    params.append("province", province.value)
  }

  if (city.value) {
    params.append("city", city.value)
  }

  if (grade.value) {
    params.append("grade", grade.value)
  }

  if (feedbackDate.value) {
    params.append("feedbackDate", feedbackDate.value)
  }

  axios({
    method: "get",
    url: `/admin/supervisor/list?${params.toString()}`
  }).then(res => {
    let pg = res.data.data
    aqiQuery.value = pg.records
    pageTotal.value = pg.pages
    pageSize.value = pg.total
  }).catch(err => {
    console.log(err)
  })
}

const provinces = computed(() => {
  return [...new Set(
      aqiQuery.value
          .map(item => item.provinceName)
          .filter(Boolean)
  )]
})

const cities = computed(() => {
  let list = aqiQuery.value

  if (province.value) {
    list = list.filter(item => item.provinceName === province.value)
  }

  return [...new Set(
      list
          .map(item => item.cityName)
          .filter(Boolean)
  )]
})

const clearFilter = () => {
  province.value = ""
  city.value = ""
  grade.value = ""
  feedbackDate.value = ""
  pageNum.value = 1
  loadAqi()
}

const handleDetail = (item) => {
  router.push(`/admin/confirmaqidetail/${item.afId}`)
}

const getGradeText = (grade) => {
  const map = {
    1: "轻度污染 (一)",
    2: "轻度污染 (二)",
    3: "中度污染 (三)",
    4: "中度污染 (四)",
    5: "重度污染 (五)",
    6: "重度污染 (六)"
  }

  return map[grade] || "未知"
}

onMounted(() => {
  loadAqi()
})
</script>

<template>
  <div class="history-container">
    <div class="filter-area">
      <div class="filter-item">
        <span>省区域</span>
        <select v-model="province">
          <option value="">-- 全部 --</option>
          <option
              v-for="item in provinces"
              :key="item"
              :value="item"
          >
            {{ item }}
          </option>
        </select>
      </div>

      <div class="filter-item">
        <span>市区域</span>
        <select v-model="city">
          <option value="">-- 全部 --</option>
          <option
              v-for="item in cities"
              :key="item"
              :value="item"
          >
            {{ item }}
          </option>
        </select>
      </div>

      <div class="filter-item">
        <span>预估等级</span>
        <select v-model="grade">
          <option value="">-- 全部 --</option>
          <option value="1">一级</option>
          <option value="2">二级</option>
          <option value="3">三级</option>
          <option value="4">四级</option>
          <option value="5">五级</option>
          <option value="6">六级</option>
        </select>
      </div>

      <div class="filter-item">
        <span>反馈日期</span>
        <input type="date" v-model="feedbackDate">
      </div>

      <button class="clear-btn" @click="clearFilter">清空</button>
      <button class="search-btn" @click="search">查询</button>
    </div>

    <div class="table-container">
      <table>
        <thead>
        <tr>
          <th>编号</th>
          <th>反馈者</th>
          <th>所在省</th>
          <th>所在市</th>
          <th>AQI污染等级</th>
          <th>指派日期</th>
          <th>指派时间</th>
          <th>网格员</th>
          <th>反馈者</th>
          <th>操作</th>
        </tr>
        </thead>

        <tbody>
        <tr
            v-for="item in aqiQuery"
            :key="item.afId"
        >
          <td>{{ item.afId }}</td>
          <td>{{ item.realName }}</td>
          <td>{{ item.provinceName }}</td>
          <td>{{ item.cityName }}</td>

          <td>
            <span
                class="grade"
                :class="'grade' + item.estimatedGrade"
            >
              {{ getGradeText(item.estimatedGrade) }}
            </span>
          </td>

          <td>{{ item.assignDate }}</td>
          <td>{{ item.assignTime }}</td>

          <td>
            {{ item.gmName || "未知" }}
          </td>

          <td>{{ item.realName }}</td>

          <td>
            <button
                class="detail-btn"
                @click="handleDetail(item)"
            >
              详细
            </button>
          </td>
        </tr>

        <tr v-if="aqiQuery.length === 0">
          <td colspan="11" class="empty">
            暂无已指派反馈
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="pagination">
      <button
          class="page-btn"
          :disabled="pageNum === 1"
          @click="changePage(pageNum - 1)"
      >
        上一页
      </button>

      <button
          v-for="p in pageTotal"
          :key="p"
          class="page-btn"
          :class="{active: pageNum === p}"
          @click="changePage(p)"
      >
        {{ p }}
      </button>

      <button
          class="page-btn"
          :disabled="pageNum === pageTotal"
          @click="changePage(pageNum + 1)"
      >
        下一页
      </button>

      <span class="page-info">
        {{ pageNum }}/{{ pageTotal }} 页，共 {{ pageSize }} 条
      </span>
    </div>
    <RouterLink :to="{name: 'HomePage-index'}">返回</RouterLink>
  </div>
</template>

<style scoped>
.history-container {
  width: 100%;
  min-height: 100vh;
  background: #fff;
  font-size: 13px;
  color: #555;
}

.filter-area {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 12px 10px;
  border-bottom: 1px solid #eee;
  flex-wrap: wrap;
}

.filter-item {
  display: flex;
  align-items: center;
  gap: 6px;
}

.filter-item select,
.filter-item input {
  height: 28px;
  min-width: 90px;
  border: 1px solid #dcdfe6;
  border-radius: 3px;
  padding: 0 7px;
  color: #606266;
  background: white;
}

.clear-btn,
.search-btn {
  height: 28px;
  border: none;
  border-radius: 3px;
  padding: 0 14px;
  cursor: pointer;
}

.clear-btn {
  background: #f56c6c;
  color: white;
}

.search-btn {
  background: #409eff;
  color: white;
}

.table-container {
  width: 100%;
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  table-layout: fixed;
}

th,
td {
  height: 34px;
  border-bottom: 1px solid #ebeef5;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

th {
  background: #fafafa;
  color: #606266;
  font-weight: 500;
}

.grade {
  display: inline-block;
  padding: 2px 7px;
  border-radius: 3px;
}

.grade1 {
  color: #67c23a;
}

.grade2 {
  color: #85c442;
}

.grade3 {
  color: #e6a23c;
}

.grade4 {
  color: #f56c6c;
}

.grade5 {
  color: #f56c6c;
}

.grade6 {
  color: #c45656;
}

.detail-btn {
  border: none;
  background: transparent;
  color: #409eff;
  cursor: pointer;
}

.detail-btn:hover {
  text-decoration: underline;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
  margin-top: 15px;
}

.page-btn {
  min-width: 28px;
  height: 28px;
  border: none;
  border-radius: 3px;
  background: #f4f4f5;
  color: #606266;
  cursor: pointer;
}

.page-btn.active {
  background: #409eff;
  color: white;
}

.page-btn:disabled {
  color: #c0c4cc;
  cursor: not-allowed;
}

.page-info {
  margin-left: 5px;
  color: #909399;
}

.empty {
  padding: 20px;
  color: #999;
}
</style>