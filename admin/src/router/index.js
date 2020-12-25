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
    path: "/review/composition",
    name: "ReviewCompositionPage",
    component: () => import("../views/ReviewComposition.vue")
  },
  {
    path: "/review/comment",
    name: "ReviewCommentPage",
    component: () => import("../views/ReviewComment.vue")
  },
  {
    path: "/report/composition",
    name: "ReportCompositionPage",
    component: () => import("../views/ReportComposition.vue")
  },
  {
    path: "/report/comment",
    name: "ReportCommentPage",
    component: () => import("../views/ReportComment.vue")
  },
  {
    path: "/user/infraction",
    name: "UserInfractionPage",
    component: () => import("../views/UserInfraction.vue")
  },
  {
    path: "/login",
    name: "LoginPage",
    component: () => import("../views/Login.vue")
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
