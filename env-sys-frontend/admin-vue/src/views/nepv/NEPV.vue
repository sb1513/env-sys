<script setup>
import { ref, onMounted, onUnmounted, nextTick } from 'vue';
import * as echarts from 'echarts';
import { useRouter } from 'vue-router';
import axios from '../../axios/index.js';

const gauge1Ref = ref(null);
const gauge2Ref = ref(null);
const pieRef = ref(null);
const lineRef = ref(null);
const mapRef = ref(null);
const bar1Ref = ref(null);
const bar2Ref = ref(null);
const bar3Ref = ref(null);
const aqiData = ref([])
const monthlyAqiData = ref([])

const router = useRouter();
let charts = [];

const dashboardData = ref({
  capitalCoverage: 0,
  bigCityCoverage: 0,
  totalCount: 0,
  goodCount: 0,
  pollutionCount: 0,
  provincePollution: []
});
const provinceStatistics = ref([])

const goBack = () => {
  router.back();
};

const getDashboardData = async () => {
  await axios({
    method: 'get',
    url: 'admin/others',
  }).then(res=>{
    dashboardData.value = res.data.data
    //console.log(res.data.data)
  })
};

const getAqiStatistics = async () => {
  await axios({
    method: 'get',
    url: '/admin/aqistatistics'
  }).then(res=>{
    aqiData.value = res.data.data
    //console.log(res.data.data)
  })
}

const getMonthlyAqiStatistics = async () => {
  await axios({
    method: 'get',
    url: '/admin/monthlyaqi'
  }).then(res => {
    monthlyAqiData.value = res.data.data || []
    console.log('月度AQI数据:', monthlyAqiData.value)
  })
}

const getProvinceStatistics = async () => {
  await axios({
    method: 'get',
    url: '/admin/provincestatistics',
  }).then(res=>{
    provinceStatistics.value = res.data.data || []
    console.log(res.data.data)
  })
}

const initCharts = async () => {
  const gauge1 = echarts.init(gauge1Ref.value);
  const provinceAbbr = provinceStatistics.value.map(item => item.provinceAbbr)
  const so2Data = provinceStatistics.value.map(item => item.so2Count)
  const coData = provinceStatistics.value.map(item => item.coCount)
  const spmData = provinceStatistics.value.map(item => item.spmCount)
  gauge1.setOption({
    series: [{
      type: 'gauge',
      startAngle: 210,
      endAngle: -30,
      min: 0,
      max: 100,
      radius: '85%',
      progress: {
        show: true,
        width: 10,
        itemStyle: { color: '#00ff00' }
      },
      axisLine: {
        lineStyle: {
          width: 10,
          color: [[1, '#333']]
        }
      },
      axisTick: { show: false },
      splitLine: { show: false },
      axisLabel: { show: false },
      pointer: {
        itemStyle: { color: '#00ff00' }
      },
      detail: {
        valueAnimation: true,
        fontSize: 22,
        color: '#00ff00',
        offsetCenter: [0, '55%'],
        formatter: '{value}%'
      },
      data: [{ value: dashboardData.value.capitalCoverage }]
    }]
  });
  charts.push(gauge1);

  const gauge2 = echarts.init(gauge2Ref.value);
  gauge2.setOption({
    series: [{
      type: 'gauge',
      startAngle: 210,
      endAngle: -30,
      min: 0,
      max: 100,
      radius: '85%',
      progress: {
        show: true,
        width: 10,
        itemStyle: { color: '#ff4d4d' }
      },
      axisLine: {
        lineStyle: {
          width: 10,
          color: [[1, '#333']]
        }
      },
      axisTick: { show: false },
      splitLine: { show: false },
      axisLabel: { show: false },
      pointer: {
        itemStyle: { color: '#ff4d4d' }
      },
      detail: {
        valueAnimation: true,
        fontSize: 22,
        color: '#ff4d4d',
        offsetCenter: [0, '55%'],
        formatter: '{value}%'
      },
      data: [{ value: dashboardData.value.bigCityCoverage }]
    }]
  });
  charts.push(gauge2);

  const pie = echarts.init(pieRef.value);
  const pieData = aqiData.value.map(item => ({
    value: item.aqiCount,
    name: item.aqiExplain
  }))

  pie.setOption({
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      bottom: 0,
      textStyle: {
        color: '#fff'
      }
    },
    series: [{
      type: 'pie',
      radius: ['35%', '65%'],
      center: ['50%', '43%'],
      data: pieData,
      label: {
        color: '#fff',
        formatter: '{b}\n{c}'
      }
    }]
  })
  charts.push(pie);

  const line = echarts.init(lineRef.value);

  const now = new Date();
  const currentYear = now.getFullYear();
  const currentMonth = now.getMonth() + 1;

  const last12Months = [];

  for (let i = 11; i >= 0; i--) {
    const date = new Date(currentYear, currentMonth - 1 - i, 1);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');

    last12Months.push({
      year: String(year),
      month,
      label: month
    });
  }

  const lineData = last12Months.map(item => {
    const found = monthlyAqiData.value.find(
      data => String(data.year) === item.year &&
              String(data.month).padStart(2, '0') === item.month
    );

    return found ? Number(found.aqiCount) : 0;
  });

  const lineLabels = last12Months.map(item => item.label);

  line.setOption({
    grid: {
      top: 20,
      right: 20,
      bottom: 20,
      left: 30
    },
    tooltip: {
      trigger: 'axis',
      formatter: params => {
        const index = params[0].dataIndex;
        const item = last12Months[index];
        return `${item.year}年${Number(item.month)}月<br/>AQI超标：${params[0].value}次`;
      }
    },
    xAxis: {
      type: 'category',
      data: lineLabels,
      axisLabel: {
        color: '#fff'
      }
    },
    yAxis: {
      type: 'value',
      min: 0,
      axisLabel: {
        color: '#fff'
      },
      splitLine: {
        lineStyle: {
          color: '#333'
        }
      }
    },
    series: [{
      name: 'AQI超标',
      data: lineData,
      type: 'line',
      smooth: true,
      lineStyle: {
        color: '#00bfff'
      },
      itemStyle: {
        color: '#00bfff'
      },
      areaStyle: {
        color: 'rgba(0, 191, 255, 0.08)'
      }
    }]
  });

  charts.push(line);

  const response = await fetch('/chinaMap.geojson');
  if (!response.ok) {
    throw new Error('chinaMap.geojson 加载失败');
  }

  const chinaMapJson = await response.json();
  echarts.registerMap('china', chinaMapJson);
  const map = echarts.init(mapRef.value);
  const mapData = provinceStatistics.value.map(item => ({
    name: item.provinceName,
    value: item.aqiCount
  }));

  //console.log('地图数据:', mapData);

  map.setOption({
    tooltip: {
      trigger: 'item',
      formatter: params => {
        //console.log(params)
        if (params.value === undefined || params.value === null) {
          return `${params.name}<br/>暂无数据`;
        }
        return `${params.name}<br/>AQI超标累计：${params.value}`;
      }
    },
    visualMap: {
      min: 0,
      max: Math.max(...mapData.map(item => item.value), 1),
      left: 'left',
      bottom: 10,
      calculable: false,
      textStyle: {
        color: '#fff'
      },
      pieces: [
        { min: 0, max: 0, label: '无超标', color: '#00e400' },
        { min: 1, max: 2, label: '1-2次', color: '#ffff00' },
        { min: 3, max: 5, label: '3-5次', color: '#ff7e00' },
        { min: 6, max: 10, label: '6-10次', color: '#ff0000' },
        { min: 11, max: 20, label: '11-20次', color: '#99004c' },
        { min: 21, label: '21次以上', color: '#7e0023' }
      ]
    },
    series: [{
      type: 'map',
      map: 'china',
      roam: true,
      zoom: 1.15,
      itemStyle: {
        areaColor: '#1a3a5c',
        borderColor: '#00bfff',
        borderWidth: 1
      },
      emphasis: {
        itemStyle: {
          areaColor: '#00bfff'
        }
      },
      label: {
        show: false
      },
      data: mapData
    }]
  });
  charts.push(map);

  const bar1 = echarts.init(bar1Ref.value);

  bar1.setOption({
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: provinceAbbr,
      axisLabel: {
        color: '#fff',
        interval: 0,
        rotate: 0
      }
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        color: '#fff'
      }
    },
    series: [{
      type: 'bar',
      data: spmData
    }]
  })
  charts.push(bar1);

  const bar2 = echarts.init(bar2Ref.value);
  bar2.setOption({
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: provinceAbbr,
      axisLabel: {
        color: '#fff',
        interval: 0,
        rotate: 0
      }
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        color: '#fff'
      }
    },
    series: [{
      type: 'bar',
      data: so2Data
    }]
  })
  charts.push(bar2);

  const bar3 = echarts.init(bar3Ref.value);
  bar3.setOption({
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: provinceAbbr,
      axisLabel: {
        color: '#fff',
        interval: 0,
        rotate: 0
      }
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        color: '#fff'
      }
    },
    series: [{
      type: 'bar',
      data: coData
    }]
  })
  charts.push(bar3);
};

const handleResize = () => {
  charts.forEach(chart => chart.resize());
};

onMounted(async () => {
  await nextTick();
  await getDashboardData();
  await getAqiStatistics()
  await getMonthlyAqiStatistics();
  await getProvinceStatistics()
  await initCharts();
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
  charts.forEach(chart => chart.dispose());
});
</script>

<template>
  <div class="dashboard-container">
    <header class="header">
      <button class="back-btn" @click="goBack">
        <span>‹</span> 返回
      </button>
      <div class="header-title">东软空气质量环保公众监督平台</div>
    </header>

    <div class="main-content">
      <div class="panel left-panel">
        <div class="chart-box">
          <div class="box-title">全国省会城市网格覆盖率(%)</div>
          <div ref="gauge1Ref" class="chart"></div>
        </div>

        <div class="chart-box">
          <div class="box-title">全国大城市网格覆盖率(%)</div>
          <div ref="gauge2Ref" class="chart"></div>
        </div>

        <div class="chart-box pie-box">
          <div class="box-title">空气质量指数级别分布</div>
          <div ref="pieRef" class="chart"></div>
        </div>

        <div class="chart-box">
          <div class="box-title">12个月内空气质量超标趋势</div>
          <div ref="lineRef" class="chart"></div>
        </div>
      </div>

      <div class="panel center-panel">
        <div ref="mapRef" class="map-chart"></div>

        <div class="bottom-stats">
          <div class="stat-item">
            <div class="stat-value">{{ dashboardData.totalCount }}</div>
            <div class="stat-label">空气质量检测总数量</div>
          </div>

          <div class="stat-item">
            <div class="stat-value">{{ dashboardData.goodCount }}</div>
            <div class="stat-label">空气质量良好数量</div>
          </div>

          <div class="stat-item">
            <div class="stat-value">{{ dashboardData.pollutionCount }}</div>
            <div class="stat-label">空气质量污染数量</div>
          </div>
        </div>
      </div>

      <div class="panel right-panel">
        <div class="chart-box">
          <div class="box-title">悬浮颗粒物 (PM2.5) 浓度超标累计</div>
          <div ref="bar1Ref" class="chart"></div>
        </div>

        <div class="chart-box">
          <div class="box-title">二氧化硫 (SO2) 浓度超标累计</div>
          <div ref="bar2Ref" class="chart"></div>
        </div>

        <div class="chart-box">
          <div class="box-title">一氧化碳 (CO) 浓度超标累计</div>
          <div ref="bar3Ref" class="chart"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
* { box-sizing: border-box; margin: 0; padding: 0; }
.dashboard-container { width: 100dvw; height: 100dvh; min-width: 100%; min-height: 100%; background-color: #0b1a2f; color: #fff; overflow: hidden; font-family: sans-serif; }
.header { position: relative; height: clamp(45px, 5vh, 70px); line-height: clamp(45px, 5vh, 70px); text-align: center; font-size: clamp(18px, 2vw, 28px); font-weight: bold; color: #00bfff; background: linear-gradient(90deg, transparent, rgba(0, 191, 255, 0.2), transparent); white-space: nowrap; overflow: hidden; }
.header-title { width: 100%; height: 100%; }
.back-btn { position: absolute; left: clamp(10px, 1.5vw, 25px); top: 50%; transform: translateY(-50%); z-index: 10; height: clamp(28px, 3vh, 36px); padding: 0 clamp(10px, 1vw, 16px); border: 1px solid rgba(0, 191, 255, 0.6); border-radius: 4px; background: rgba(0, 191, 255, 0.08); color: #00bfff; font-size: clamp(12px, 0.8vw, 15px); cursor: pointer; transition: all 0.2s; }
.back-btn span { font-size: 22px; vertical-align: -2px; }
.back-btn:hover { background: rgba(0, 191, 255, 0.2); border-color: #00bfff; box-shadow: 0 0 10px rgba(0, 191, 255, 0.35); }
.main-content { display: flex; width: 100%; height: calc(100dvh - clamp(45px, 5vh, 70px)); padding: clamp(5px, 0.7vw, 10px); gap: clamp(5px, 0.7vw, 10px); overflow: hidden; }
.panel { min-width: 0; min-height: 0; background-color: rgba(255, 255, 255, 0.03); border: 1px solid rgba(0, 191, 255, 0.2); border-radius: 4px; overflow: hidden; }
.left-panel { flex: 2.5; display: flex; flex-direction: column; gap: clamp(5px, 0.7vh, 10px); }
.center-panel { flex: 4; display: flex; flex-direction: column; min-width: 0; min-height: 0; }
.right-panel { flex: 3.5; display: flex; flex-direction: column; gap: clamp(5px, 0.7vh, 10px); }
.chart-box { flex: 1; min-width: 0; min-height: 0; display: flex; flex-direction: column; padding: clamp(3px, 0.4vw, 6px); }
.pie-box { flex: 1.5; min-height: 0; }
.box-title { flex-shrink: 0; font-size: clamp(10px, 0.75vw, 14px); line-height: 1.4; color: #00bfff; padding-left: clamp(5px, 0.5vw, 10px); border-left: 3px solid #00bfff; margin-bottom: 3px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.chart { flex: 1; min-width: 0; min-height: 0; width: 100%; height: 100%; }
.map-chart { flex: 1; min-width: 0; min-height: 0; width: 100%; height: 100%; }
.bottom-stats { height: clamp(60px, 9vh, 90px); flex-shrink: 0; display: flex; justify-content: space-around; align-items: center; border-top: 1px solid rgba(0, 191, 255, 0.2); padding: 0 10px; }
.stat-item { min-width: 0; text-align: center; flex: 1; }
.stat-value { font-size: clamp(18px, 2vw, 30px); line-height: 1.2; font-weight: bold; color: #00bfff; }
.stat-label { margin-top: 4px; font-size: clamp(9px, 0.7vw, 13px); color: #aaa; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
@media (max-width:1200px) { .left-panel{flex:2.3} .center-panel{flex:4} .right-panel{flex:3.3} .box-title{font-size:10px} .stat-label{font-size:9px} }
@media (max-height:700px) { .header{font-size:18px} .box-title{font-size:10px;margin-bottom:1px} .bottom-stats{height:55px} .stat-value{font-size:18px} .stat-label{font-size:8px} }
</style>