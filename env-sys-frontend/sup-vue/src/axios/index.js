import axios from 'axios';

const service = axios.create({
    timeout: 50000,
    baseURL: "http://localhost:8080",
})

export default service;