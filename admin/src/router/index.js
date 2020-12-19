import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("../views/Home.vue")
  },
  {
    path: "/feedback",
    name: "FeedbackPage",
    component: () => import("../views/Feedback.vue")
  },
  {
    path: "/help",
    name: "HelpPage",
    component: () => import("../views/Help.vue")
  },
  {
    path: "/article",
    name: "ArticlePage",
    component: () => import("../views/Article.vue")
  },
  {
    path: "/message",
    name: "SystemMessagePage",
    component: () => import("../views/SystemMessage.vue")
  },
  {
    path: "/review/normal",
    name: "ReviewNormalPage",
    component: () => import("../views/ReviewNormal.vue")
  },
  {
    path: "/review/report",
    name: "ReviewReportPage",
    component: () => import("../views/ReviewReport.vue")
  },
  {
    path: "/user/defense",
    name: "UserDefensePage",
    component: () => import("../views/UserDefense.vue")
  },
  {
    path: "/user/infraction",
    name: "UserInfractionPage",
    component: () => import("../views/UserInfraction.vue")
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
