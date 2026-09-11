import axios from 'axios';

const service = axios.create({
    timeout: 50000,
    baseURL: "http://localhost:8080",
})

service.interceptors.request.use(
  (config) => {
    // 从本地存储动态获取 token
    const token = sessionStorage.getItem('token');
    if (token) {
      // 为每个请求动态添加 Authorization 头
      config.headers['Authorization'] = `${token}`;
    }
    return config; // 必须返回 config，否则请求会被阻塞
  })

export default service;