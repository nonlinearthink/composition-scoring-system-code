import Vue from "vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faMars,
  faVenus,
  faLink,
  faAward
} from "@fortawesome/free-solid-svg-icons";
// import { faFontAwesome } from "@fortawesome/free-brands-svg-icons";
// import { faUserSecret } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(faMars);
library.add(faVenus);
library.add(faLink);
library.add(faAward);
Vue.component("font-awesome-icon", FontAwesomeIcon);
