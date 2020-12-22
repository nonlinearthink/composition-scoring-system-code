import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
import Composition from "../assets/js/types/composition";

Vue.use(Vuex);

export default new Vuex.Store({
  // 数据持久化插件
  plugins: [createPersistedState()],
  state: {
    isLogin: false,
    token: "",
    user: null,
    editing: {
      type: "cache",
      cache: null,
      draft: null,
      publish: null
    },
    compositions: [],
    setting: {
      cacheEditingComposition: true
    },
    routeAnchor: -1,
    view: {
      article: null
    }
  },
  mutations: {
    viewArticle(state, article) {
      state.view.article = article;
    },
    setRouteAnchor(state, routerAnchor) {
      state.routeAnchor = routerAnchor;
    },
    editingCache(state) {
      if (!state.editing.cache) {
        state.editing.cache = new Composition({ compositionBody: "" });
      }
      state.editing.type = "cache";
    },
    editingDraft(state, composition) {
      state.editing.draft = composition;
      state.editing.type = "draft";
    },
    editingPublish(state, composition) {
      state.editing.publish = composition;
      state.editing.type = "publish";
    },
    doEditingCache(state, composition) {
      state.editing.cache = composition;
    },
    clearCache(state) {
      state.editing.cache = null;
    },
    addComposition(state, composition) {
      state.compositions.push(composition);
    },
    clearCompositions(state) {
      state.compositions = [];
    },
    deleteComposition(state, compositionId) {
      state.compositions = state.compositions.filter(
        composition => composition.compositionId != compositionId
      );
    },
    updateComposition(state, composition) {
      let index = state.compositions.findIndex(
        item => item.compositionId == composition.compositionId
      );
      state.compositions.splice(index, 1, composition);
    },
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
    }
  },
  actions: {},
  modules: {}
});
