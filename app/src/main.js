import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
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

library.add(faLock);
Vue.component("font-awesome-icon", FontAwesomeIcon);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
