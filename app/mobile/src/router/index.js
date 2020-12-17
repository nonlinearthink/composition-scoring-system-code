import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "HomePage",
    meta: { index: 1 },
    component: () => import("../views/Home.vue")
  },
  {
    path: "/manager",
    name: "WritingManagerPage",
    meta: { index: 2 },
    component: () => import("../views/WritingManager.vue")
  },
  {
    path: "/chat",
    name: "ChatPage",
    meta: { index: 3 },
    component: () => import("../views/Chat.vue")
  },
  {
    path: "/user",
    name: "UserPage",
    meta: { index: 4 },
    component: () => import("../views/User.vue")
  },
  {
    path: "/login",
    name: "LoginPage",
    meta: { index: 5 },
    component: () => import("../views/Login.vue")
  },
  {
    path: "/signup",
    name: "SignupPage",
    meta: { index: 6 },
    component: () => import("../views/Signup.vue")
  },
  {
    path: "/setting/main",
    name: "SettingMainPage",
    meta: { index: 5 },
    component: () => import("../views/SettingMain.vue")
  },
  {
    path: "/setting/user",
    name: "SettingUserPage",
    meta: { index: 6 },
    component: () => import("../views/SettingUser.vue")
  },
  {
    path: "/setting/security",
    name: "SettingSecurityPage",
    meta: { index: 6 },
    component: () => import("../views/SettingSecurity.vue")
  },
  {
    path: "/setting/security/password",
    name: "SettingPasswordPage",
    meta: { index: 7 },
    component: () => import("../views/SettingPassword.vue")
  },
  {
    path: "/setting/about",
    name: "SettingAboutPage",
    meta: { index: 6 },
    component: () => import("../views/SettingAbout.vue")
  },
  {
    path: "/writing",
    name: "WritingPage",
    meta: { index: 3 },
    component: () => import("../views/Writing.vue")
  },
  {
    path: "/publish",
    name: "PublishPage",
    meta: { index: 3 },
    component: () => import("../views/Publish.vue")
  },
  {
    path: "/user/follow",
    name: "UserFollowPage",
    meta: { index: 5 },
    component: () => import("../views/UserFollow.vue")
  },
  {
    path: "/composition",
    name: "CompositionPage",
    meta: { index: 2 },
    component: () => import("../views/Composition.vue")
  }
];

const router = new VueRouter({
  routes
});

export default router;
