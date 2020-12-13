import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  // 数据持久化插件
  plugins: [createPersistedState()],
  state: {
    isLogin: false,
    token: "",
    user: null,
    editor: {
      composition: {
        title: "",
        visibility: 1,
        compositionBody: "",
        description: ""
      }
    }
  },
  mutations: {
    updateUser(state, user) {
      state.user = user;
    },
    updateToken(state, token) {
      state.token = token;
    },
    login(state) {
      state.isLogin = true;
    },
    logout(state) {
      state.isLogin = false;
    },
    saveEditor(state) {
      state.editor.isSave = true;
    }
  },
  actions: {},
  modules: {}
});
