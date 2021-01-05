import Vue from "vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faMars, faVenus, faLink } from "@fortawesome/free-solid-svg-icons";
// import { faFontAwesome } from "@fortawesome/free-brands-svg-icons";
// import { faUserSecret } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(faMars);
library.add(faVenus);
library.add(faLink);
Vue.component("font-awesome-icon", FontAwesomeIcon);
