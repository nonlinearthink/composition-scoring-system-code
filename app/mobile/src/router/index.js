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
    path: "/writing",
    name: "WritingPage",
    meta: { index: 2 },
    component: () => import("../views/Writing.vue")
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
  }
];

const router = new VueRouter({
  routes
});

export default router;
