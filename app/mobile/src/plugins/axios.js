import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
import store from "../store";

// axios.defaults.baseURL = "http://192.168.123.204:8000/api";
axios.defaults.baseURL = "http://localhost:8000/api";

axios.interceptors.request.use(
  config => {
    if (config.method != "OPTIONS") {
      // 在每次请求的时候添加 token
      const token = store.state.token;
      token && (config.headers.Authorization = token);
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);
axios.interceptors.response.use(
  response => {
    return response.data;
  },
  error => Promise.reject(error.response)
);
Vue.use(VueAxios, axios);
