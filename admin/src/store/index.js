import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isLogin: false,
    token: "",
    admin: {
      adminName: ""
    }
  },
  mutations: {
    login(state, { adminName, token }) {
      state.isLogin = true;
      state.admin.adminName = adminName;
      state.token = token;
    },
    logout(state) {
      state.isLogin = false;
      state.token = "";
    }
  },
  actions: {},
  modules: {}
});
