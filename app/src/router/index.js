import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "HomePage",
    meta: { index: 1, scrollToTop: true },
    component: () => import("../views/Home.vue")
  },
  {
    path: "/manager",
    name: "WritingManagerPage",
    meta: { index: 2, scrollToTop: false },
    component: () => import("../views/WritingManager.vue")
  },
  {
    path: "/user",
    name: "UserPage",
    meta: { index: 4, scrollToTop: true },
    component: () => import("../views/User.vue")
  },
  {
    path: "/login",
    name: "LoginPage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/Login.vue")
  },
  {
    path: "/signup",
    name: "SignupPage",
    meta: { index: 6, scrollToTop: true },
    component: () => import("../views/Signup.vue")
  },
  {
    path: "/setting/main",
    name: "SettingMainPage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/SettingMain.vue")
  },
  {
    path: "/setting/user",
    name: "SettingUserPage",
    meta: { index: 6, scrollToTop: true },
    component: () => import("../views/SettingUser.vue")
  },
  {
    path: "/setting/security",
    name: "SettingSecurityPage",
    meta: { index: 6, scrollToTop: true },
    component: () => import("../views/SettingSecurity.vue")
  },
  {
    path: "/setting/security/password",
    name: "SettingPasswordPage",
    meta: { index: 7, scrollToTop: true },
    component: () => import("../views/SettingPassword.vue")
  },
  {
    path: "/setting/security/email",
    name: "SettingEmailPage",
    meta: { index: 7, scrollToTop: true },
    component: () => import("../views/SettingEmail.vue")
  },
  {
    path: "/setting/security/new-email",
    name: "SettingNewEmailPage",
    meta: { index: 8, scrollToTop: true },
    component: () => import("../views/SettingNewEmail.vue")
  },
  {
    path: "/setting/about",
    name: "SettingAboutPage",
    meta: { index: 6, scrollToTop: true },
    component: () => import("../views/SettingAbout.vue")
  },
  {
    path: "/writing",
    name: "WritingPage",
    meta: { index: 3, scrollToTop: true },
    component: () => import("../views/Writing.vue")
  },
  {
    path: "/publish",
    name: "PublishPage",
    meta: { index: 3, scrollToTop: true },
    component: () => import("../views/Publish.vue")
  },
  {
    path: "/user/follow",
    name: "UserFollowPage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/UserFollow.vue")
  },
  {
    path: "/composition",
    name: "CompositionPage",
    meta: { index: 2, scrollToTop: true },
    component: () => import("../views/Composition.vue")
  },
  {
    path: "/article",
    name: "ArticlePage",
    meta: { index: 2, scrollToTop: true },
    component: () => import("../views/Article.vue")
  },
  {
    path: "/comment",
    name: "CommentPage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/Comment.vue")
  },
  {
    path: "/user/home",
    name: "UserHomePage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/UserHome.vue")
  },
  {
    path: "/feedback",
    name: "FeedbackPage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/Feedback.vue")
  },
  {
    path: "/help",
    name: "HelpPage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/Help.vue")
  },
  {
    path: "/favorite",
    name: "FavoritePage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/Favorite.vue")
  },
  {
    path: "/history",
    name: "HistoryPage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/History.vue")
  },
  {
    path: "/support",
    name: "SupportPage",
    meta: { index: 5, scrollToTop: true },
    component: () => import("../views/Support.vue")
  },
  {
    path: "/report",
    name: "ReportPage",
    meta: { index: 3, scrollToTop: true },
    component: () => import("../views/Report.vue")
  },
  {
    path: "/system/message",
    name: "SystemMessagePage",
    meta: { index: 4, scrollToTop: true },
    component: () => import("../views/SystemMessage.vue")
  },
  {
    path: "/message/support",
    name: "SupportMessagePage",
    meta: { index: 4, scrollToTop: true },
    component: () => import("../views/SupportMessage.vue")
  },
  {
    path: "/forget-password",
    name: "ForgetPasswordPage",
    meta: { index: 6, scrollToTop: true },
    component: () => import("../views/ForgetPassword.vue")
  },
  {
    path: "/search",
    name: "SearchPage",
    meta: { index: 2, scrollToTop: true },
    component: () => import("../views/Search.vue")
  },
  {
    path: "/search/result",
    name: "SearchResultPage",
    meta: { index: 3, scrollToTop: true },
    component: () => import("../views/SearchResult.vue")
  }
];

const router = new VueRouter({
  routes
});

router.beforeEach((to, from, next) => {
  if (from.meta.scrollToTop == false) {
    store.commit("recordScrollTop", document.documentElement.scrollTop);
  }
  next();
});

router.afterEach(to => {
  if (to.meta.scrollToTop == true) {
    setTimeout(() => {
      document.documentElement.scrollTop = 0;
    }, 10);
  } else {
    setTimeout(() => {
      document.documentElement.scrollTop = store.state.scrollTop;
    }, 500);
  }
});

export default router;
