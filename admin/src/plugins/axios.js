"use strict";

import Vue from "vue";
import axios from "axios";
import store from "../store";
import router from "../router/index";

// Full config:  https://github.com/axios/axios#request-config
axios.defaults.baseURL = "http://localhost:8000/api";
// axios.defaults.baseURL = "http://192.168.123.204:8000/api";
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function(config) {
    // Do something before request is sent
    const token = store.state.token;
    token && (config.headers.Authorization = token);
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
_axios.interceptors.response.use(
  function(response) {
    // Do something with response data
    return response;
  },
  function(error) {
    // Do something with response error
    switch (error.response.data["code"]) {
      // token 错误跳转到登录界面
      case 14:
        localStorage.clear();
        router.replace("/login");
        break;
    }
    return Promise.reject(error);
  }
);

Plugin.install = function(Vue) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      }
    },
    $axios: {
      get() {
        return _axios;
      }
    }
  });
};

Vue.use(Plugin);

export default Plugin;
