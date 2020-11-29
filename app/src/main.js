import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import {
  Button,
  Col,
  Divider,
  Field,
  Form,
  Icon,
  Image as VanImage,
  NavBar,
  Row,
  Tabbar,
  TabbarItem,
  Uploader
} from "vant";

Vue.use(Button)
  .use(Col)
  .use(Divider)
  .use(Field)
  .use(Form)
  .use(Icon)
  .use(NavBar)
  .use(Row)
  .use(Tabbar)
  .use(TabbarItem)
  .use(VanImage)
  .use(Uploader);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
