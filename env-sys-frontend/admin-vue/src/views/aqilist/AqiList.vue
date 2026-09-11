<script setup>
import { onMounted, ref, computed } from "vue";
import axios from "../../axios/index.js";
import {useRouter} from "vue-router";

const router = useRouter()
const aqiQuery = ref([]);
const province = ref("");
const city = ref("");
const grade = ref("");
const feedbackDate = ref("");
const state = ref("");
const pageNum = ref(1)//当前页数
const pageTotal = ref(1)// 总页数
const pageSize = ref(0)//结果数量

function changePage(num){
  pageNum.value=num
  loadAqi()
}

const search = () => {
  pageNum.value = 1;
  loadAqi();
};

const loadAqi = () => {
  let params = new URLSearchParams();
  params.append("pageNum", pageNum.value);
  params.append("pageSize", 15);
  if (province.value) {
    params.append("province", province.value);
  }if (city.value) {
    params.append("city", city.value);
  }if (grade.value) {
    params.append("grade", grade.value);
  }if (feedbackDate.value) {
    params.append("feedbackDate", feedbackDate.value);
  }if (state.value !== "") {
    params.append("state", state.value);
  }
  axios({
    method: "get",
    url: `/admin/supervisor/list?${params.toString()}`
  }).then(res => {
    let pg = res.data.data
    aqiQuery.value=pg.records
    pageTotal.value=pg.pages
    pageSize.value=pg.total
  }).catch(err => {
    console.log(err);
  });
};

const provinces = computed(() => {
  return [...new Set(aqiQuery.value.map(item => item.provinceName).filter(Boolean))];
});

const cities = computed(() => {
  let list = aqiQuery.value;
  if (province.value) {
    list = list.filter(item => item.provinceName === province.value);
  }
  return [...new Set(list.map(item => item.cityName).filter(Boolean))];
});

const clearFilter = () => {
  province.value = "";
  city.value = "";
  grade.value = "";
  feedbackDate.value = "";
  state.value = "";
  pageNum.value = 1;
  loadAqi();
};

const handleDetail = (item) => {
  router.push(`/admin/aqidetail/${item.afId}`);
};

const handleAssign = (item) => {
  router.push(`/admin/aqiassign/${item.afId}`);
};

const getGradeText = (grade) => {
  const map = {
    1: "轻度污染 (一)",
    2: "轻度污染 (二)",
    3: "中度污染 (三)",
    4: "中度污染 (四)",
    5: "重度污染 (五)",
    6: "重度污染 (六)"
  };
  return map[grade] || "未知";
};

onMounted(() => {
  loadAqi();
});
</script>

<template>
  <div class="history-container">
    <div class="filter-area">
      <div class="filter-item">
        <span>省区域</span>
        <select v-model="province">
          <option value="">-- 全部 --</option>
          <option v-for="item in provinces" :key="item" :value="item">{{ item }}</option>
        </select>
      </div>

      <div class="filter-item">
        <span>市区域</span>
        <select v-model="city">
          <option value="">-- 全部 --</option>
          <option v-for="item in cities" :key="item" :value="item">{{ item }}</option>
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
      <button class="search-btn" @click="search()">查询</button>

      <div class="state-filter">
        <label><input type="radio" value="" v-model="state"> 全部</label>
        <label><input type="radio" value="0" v-model="state"> 未指派</label>
        <label><input type="radio" value="1" v-model="state"> 已指派</label>
        <label><input type="radio" value="2" v-model="state"> 已确认</label>
      </div>
    </div>

    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>编号</th>
            <th>反馈者姓名</th>
            <th>所在省</th>
            <th>所在市</th>
            <th>预估污染等级</th>
            <th>反馈日期</th>
            <th>反馈时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in aqiQuery" :key="item.afId">
            <td>{{ item.afId }}</td>
            <td>{{ item.realName }}</td>
            <td>{{ item.provinceName }}</td>
            <td>{{ item.cityName }}</td>
            <td>
              <span class="grade" :class="'grade' + item.estimatedGrade">
                {{ getGradeText(item.estimatedGrade) }}
              </span>
            </td>
            <td>{{ item.afDate }}</td>
            <td>{{ item.afTime }}</td>
            <td>
              <button class="detail-btn" @click="handleDetail(item)">详细</button>
              <button class="detail-btn" @click="handleAssign(item)">指派</button>
            </td>
          </tr>
          <tr v-if="aqiQuery.length === 0">
            <td colspan="8" class="empty">暂无反馈信息</td>
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
        :class="{ active: pageNum === p }"
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
  padding: 20px 25px;
  background: #fff;
  min-height: 100vh;
}

.filter-area {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
  padding: 15px;
  background: #fafafa;
  border: 1px solid #eee;
  margin-bottom: 15px;
}

.filter-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
}

select,input[type="date"] {
  height: 32px;
  padding: 0 8px;
  border: 1px solid #dcdfe6;
  border-radius: 3px;
  background: white;
  outline: none;
}

.clear-btn,.search-btn {
  height: 32px;
  padding: 0 16px;
  border: none;
  border-radius: 4px;
  color: white;
  cursor: pointer;
}

.clear-btn {
  background: #ff6b6b;
}

.search-btn {
  background: #409eff;
}

.state-filter {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-left: 10px;
  font-size: 13px;
}

.state-filter label {
  cursor: pointer;
}

.table-container {
  width: 100%;
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  table-layout: fixed;
  font-size: 13px;
}

thead {
  background: #fafafa;
}

th {
  height: 42px;
  color: #606266;
  font-weight: 500;
  border-bottom: 1px solid #ebeef5;
}

td {
  height: 42px;
  text-align: center;
  color: #606266;
  border-bottom: 1px solid #ebeef5;
}

tbody tr:hover {
  background: #f5f7fa;
}

.grade {
  font-weight: bold;
}

.grade1 {
  color: #67c23a;
}

.grade2 {
  color: #e6a23c;
}

.grade3,.grade4 {
  color: #f56c6c;
}

.grade5,.grade6 {
  color: #c45656;
}

.detail-btn {
  border: none;
  background: transparent;
  color: #409eff;
  cursor: pointer;
  font-size: 17px;
}

.empty {
  height: 100px;
  color: #999;
}
.pagination { display: flex; align-items: center; justify-content: center; gap: 6px; margin-top: 20px; padding-bottom: 20px; } .page-btn { min-width: 34px; height: 32px; padding: 0 10px; border: 1px solid #dcdfe6; border-radius: 4px; background: #fff; color: #606266; cursor: pointer; } .page-btn:hover:not(:disabled) { color: #409eff; border-color: #409eff; } .page-btn.active { background: #409eff; color: #fff; border-color: #409eff; } .page-btn:disabled { color: #c0c4cc; background: #f5f7fa; cursor: not-allowed; } .page-info { margin-left: 10px; color: #606266; font-size: 13px; }
</style>