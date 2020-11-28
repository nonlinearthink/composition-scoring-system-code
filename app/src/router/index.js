import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/home",
    name: "HomePage",
    component: () => import("../views/Home.vue")
  },
  {
    path: "/feed",
    name: "FeedPage",
    component: () => import("../views/Feed.vue")
  },
  {
    path: "/chat",
    name: "ChatPage",
    component: () => import("../views/Chat.vue")
  },
  {
    path: "/user",
    name: "UserPage",
    component: () => import("../views/User.vue")
  },
  {
    path: "/login",
    name: "LoginPage",
    component: () => import("../views/Login.vue")
  }
];

const router = new VueRouter({
  routes
});

export default router;
