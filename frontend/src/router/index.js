import Vue from "vue";
import VueRouter from "vue-router";
import AppMain from "@/views/AppMain";

import store from "@/store";
Vue.use(VueRouter);




// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("memberStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "login" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};






const routes = [
  {
    path: "/",
    name: "main",
    component: AppMain,
  },
  {
    path: "/qna",
    name: "qna",
    beforeEnter: onlyAuthUser,
    component: () => import("@/views/AppQna"),
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "qnalist",
        component: () => import("@/components/qna/QnaList"),
      },
      {
        path: "view/:no",
        name: "qnaview",
        component: () => import("@/components/qna/QnaView"),
      },

      {
        path: "write",
        name: "qnawrite",
        component: () => import("@/components/qna/QnaWrite"),
      },
      {
        path: "modify/:no",
        name: "qnamodify",
        component: () => import("@/components/qna/QnaModify"),
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/AppUser"),
    children: [
      // {
      //   path: "join",
      //   name: "join",
      //   component: () => import("@/components/user/UserRegister"),
      // },
      {
        path: "login",
        name: "login",
        component: () => import("@/components/user/UserLogin"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
