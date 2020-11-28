import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import {
  Button,
  Field,
  Form,
  Icon,
  Image as VanImage,
  Tabbar,
  TabbarItem
} from "vant";

Vue.use(Button)
  .use(Field)
  .use(Form)
  .use(Icon)
  .use(Tabbar)
  .use(TabbarItem)
  .use(VanImage);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
