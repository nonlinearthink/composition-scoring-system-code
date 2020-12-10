import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "axios";
import VueAxios from "vue-axios";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faLock } from "@fortawesome/free-solid-svg-icons";
// import { faFontAwesome } from "@fortawesome/free-brands-svg-icons";
// import { faUserSecret } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
  Button,
  Checkbox,
  CheckboxGroup,
  Col,
  Divider,
  Field,
  Form,
  Icon,
  Image as VanImage,
  NavBar,
  Row,
  Sticky,
  Tabbar,
  TabbarItem,
  Uploader
} from "vant";

Vue.use(Button)
  .use(Checkbox)
  .use(CheckboxGroup)
  .use(Col)
  .use(Divider)
  .use(Field)
  .use(Form)
  .use(Icon)
  .use(NavBar)
  .use(Row)
  .use(Sticky)
  .use(Tabbar)
  .use(TabbarItem)
  .use(VanImage)
  .use(Uploader);

axios.defaults.baseURL = "http://localhost:8000/api";
axios.interceptors.request.use(
  config => {
    // 在每次请求的时候添加 token
    const token = localStorage.getItem("token");
    token && (config.headers.Authorization = token);
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);
Vue.use(VueAxios, axios);

library.add(faLock);
Vue.component("font-awesome-icon", FontAwesomeIcon);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
