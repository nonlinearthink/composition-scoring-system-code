import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
// 第三方插件
import "./plugins/axios";
import "./plugins/vant";
import "./plugins/vueTouch";
import "./plugins/fontAwesome";
// 虚拟控制台，用于移动端调试
import Vconsole from "vconsole";
new Vconsole();

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
