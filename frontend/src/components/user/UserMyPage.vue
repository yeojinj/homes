<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col>
        <b-alert variant="light" show><h3>마이페이지</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card
          class="text-center mt-3 mypage-box"
          style="max-width: 40rem"
          align="center"
        >
          <b-form class="text-left">
            <b-form-group label="아이디:" label-for="userId">
              <b-form-input
                id="userid"
                placeholder="아이디"
                v-model="userInfo.userId"
                readonly
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이름:" label-for="name">
              <b-form-input
                id="name"
                placeholder="이름"
                v-model="userInfo.name"
                readonly
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                placeholder="이메일"
                v-model="userInfo.email"
                readonly
              ></b-form-input>
            </b-form-group>
            <b-form-group label="전화번호:" label-for="phone">
              <b-form-input
                id="phone"
                placeholder="전화번호"
                v-model="userInfo.phone"
                readonly
              ></b-form-input>
            </b-form-group>
            <b-button variant="dark" class="mr-1" @click="confirm"
              >수정</b-button
            >
            <b-button variant="dark" href="#">탈퇴</b-button>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
    <br />
  </b-container>
</template>

<script>
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserMyPage",
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    confirm() {
      this.$router.push({ name: "modify" });
    },
  },
};
</script>

<style scoped>
.mypage-box {
  margin: 0 auto;
}
</style>
