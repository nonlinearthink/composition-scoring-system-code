import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [createPersistedState()],
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
    },
    init(state, adminName) {
      state.token = "";
      state.isLogin = false;
      if (adminName) {
        state.admin.adminName = adminName;
      }
    }
  },
  actions: {},
  modules: {}
});
