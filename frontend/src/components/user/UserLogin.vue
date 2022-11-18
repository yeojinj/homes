<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="light" show><h3>로그인</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card
          class="text-center mt-3 login-box"
          style="max-width: 40rem"
          align="center"
        >
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="아이디:" label-for="userId">
              <b-form-input
                id="userid"
                v-model="user.userId"
                required
                placeholder="아이디"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userPw">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userPw"
                required
                placeholder="비밀번호"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>

            <b-button type="button" variant="dark" class="" @click="confirm"
              >로그인</b-button
            >

            <b-button
              type="button"
              variant="success"
              class="m-1"
              @click="movePage"
              >회원가입</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
    <br />
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

//"memberStore" 문자로 actions 메소드를 접근하기 위해 미리 선언
const memberStore = "memberStore";
export default {
  name: "UserLogin",
  data() {
    return {
      //   isLoginError: false,
      user: {
        userId: null,
        userPw: null,
      },
    };
  },

  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    // confirm() {

    // http
    //   .post(`/user/login`, {
    //     userId: this.user.userid,
    //     userPw: this.user.userpwd,

    //   })
    //   .then(({ data }) => {
    //     let msg = "등록 처리시 문제가 발생했습니다.";
    //     if (data.rule === "success") {
    //       msg = "등록이 완료되었습니다.";

    //     }
    //     alert(msg);
    //     this.moveList();
    //   });

    // alert("로그인되었습니다.");

    // },
    //"memberStore"의 actions인 "userConfirm", "getUserInfo"로 접근하겠다.
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    //로그인 버튼을 누르면 confirm 함수가 실행된다.

    async confirm() {
      console.log(this.user);
      //입력된 회원 정보를 가지고 actions의 userConfirm 으로 이동한다.

      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      // console.log("1. confirm() token >> " + token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        // console.log("4. confirm() userInfo :: ", this.userInfo);
        this.$router.push({ name: "main" });
      }
    },

    movePage() {
      this.$router.push({ name: "join" });
    },
  },
};
</script>

<style scoped>
.login-box {
  margin: 0 auto;
}
</style>
