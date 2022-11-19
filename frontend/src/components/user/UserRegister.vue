<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="light" show><h3>회원가입</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card
          class="text-center mt-3 register-box"
          style="max-width: 40rem"
          align="center"
        >
          <b-form class="text-left">
            <b-form-group label="아이디:" label-for="userId">
              <b-form-input
                id="userid"
                v-model="user.userId"
                required
                placeholder="아이디"
                @keyup="idCheck"
              ></b-form-input>
              <div :style="{ color: idColor }">{{ idCheckDiv }}</div>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userPw">
              <b-form-input
                type="password"
                id="userpw"
                v-model="user.userPw"
                required
                placeholder="비밀번호"
                @keyup="pwCheck"
              ></b-form-input>
              <div :style="{ color: pwColor }">{{ pwCheckDiv }}</div>
            </b-form-group>
            <b-form-group label="비밀번호 확인:" label-for="userPwCk">
              <b-form-input
                type="password"
                id="userpwCk"
                v-model="user.userPwCk"
                required
                placeholder="비밀번호 확인"
                @keyup="pwSameCheck"
              ></b-form-input>
              <div :style="{ color: pwSameColor }">{{ pwSameCheckDiv }}</div>
            </b-form-group>
            <b-form-group label="이름:" label-for="name">
              <b-form-input
                id="name"
                v-model="user.name"
                required
                placeholder="이름"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                v-model="user.email"
                required
                placeholder="이메일"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="전화번호:" label-for="phone">
              <b-form-input
                id="phone"
                v-model="user.phone"
                required
                placeholder="전화번호"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>

            <b-button type="button" variant="dark" class="" @click="confirm"
              >회원가입</b-button
            >

            <b-button
              type="button"
              variant="light"
              class="m-1"
              @click="movePage"
              >취소</b-button
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
import http from "@/api/http";
import { mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  name: "UserRegister",
  data() {
    return {
      idColor: "black",
      pwColor: "black",
      pwSameColor: "black",
      canUseId: false,
      canUserPw: false,
      idCheckDiv: "",
      pwCheckDiv: "",
      pwSameCheckDiv: "",
      user: {
        userId: null,
        userPw: null,
        userPwCk: null,
        name: null,
        email: null,
        phone: null,
      },
    };
  },
  methods: {
    ...mapActions(memberStore, ["userJoin", "userIdCheck"]),
    async confirm() {
      if (this.userPw === this.userPwCk && this.canUseId && this.canUserPw) {
        console.log("front :" + this.user);
        await this.userJoin(this.user);

        alert(this.user.name + "님의 회원가입 완료!");
        this.$router.push({ name: "login" });
      } else {
        alert("회원 정보를 확인해주세요 ");
      }
    },
    movePage() {
      this.$router.push({ name: "main" });
    },

    idCheck: function (e) {
      let userid = e.target.value;
      console.log(userid.length + "@@@@@길이");

      if (userid.length == 0) {
        this.idCheckDiv = "";
        this.canUseId = false;
      } else if (userid.length < 6 || userid.length > 16) {
        this.idColor = "red";
        this.idCheckDiv = "아이디는 6자 이상 16자 이하 입니다.";
        this.canUseId = false;
      } else {
        http.get(`/user/${userid}`).then(({ data }) => {
          if (data === "success") {
            this.idColor = "blue";
            this.canUseId = true;
            this.idCheckDiv = "사용가능한  아이디 입니다.";
          } else {
            this.idColor = "red";
            this.canUseId = false;
            this.idCheckDiv = "사용할 수 없는 아이디 입니다.";
          }
        });
      }
    },

    pwCheck: function (e) {
      let password = e.target.value;

      console.log(password);

      if (password.length == 0) {
        this.pwCheckDiv = "";
      } else if (password.length < 8 || password.length > 16) {
        this.pwColor = "red";
        this.pwCheckDiv = "비밀번호 8 자 이상 16자 이하 입니다.";
      } else {
        this.pwColor = "blue";
        this.pwCheckDiv = "사용할 수 있는 비밀번호 입니다.";
      }
    },

    pwSameCheck: function (e) {
      let passwordSame = e.target.value;
      if (passwordSame.length == 0) {
        this.pwSameCheckDiv = " ";
        this.canUserPw = false;
      } else if (passwordSame === this.user.userPw) {
        this.pwSameColor = "blue";
        this.pwSameCheckDiv = "비밀번호가 일치합니다.";
        this.canUserPw = true;
      } else {
        this.pwSameColor = "red";
        this.pwSameCheckDiv = "비밀번호가 일치하지 않습니다.";
        this.canUserPw = false;
      }
    },
  },
};
</script>

<style scoped>
.register-box {
  margin: 0 auto;
}
</style>
