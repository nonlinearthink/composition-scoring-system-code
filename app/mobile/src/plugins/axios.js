import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
import store from "../store";
import router from "../router/index";

// axios.defaults.baseURL = "http://192.168.123.204:8000/api";
axios.defaults.baseURL = "http://localhost:8000/api";
// axios.defaults.baseURL = "http://192.168.43.185:8000/api";

axios.interceptors.request.use(
  config => {
    // 在每次请求的时候添加 token
    const token = store.state.token;
    token && (config.headers.Authorization = token);
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);
axios.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    switch (error.response.data["code"]) {
      // token 错误跳转到登录界面
      case 13:
      case 14:
        localStorage.clear();
        router.replace("/login");
        break;
    }
    return Promise.reject(error);
  }
);
Vue.use(VueAxios, axios);
