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
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userPw">
              <b-form-input
                type="password"
                id="userpw"
                v-model="user.userPw"
                required
                placeholder="비밀번호"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호 확인:" label-for="userPwCk">
              <b-form-input
                type="password"
                id="userpwCk"
                v-model="user.userPwCk"
                required
                placeholder="비밀번호 확인"
                @keyup.enter="confirm"
              ></b-form-input>
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
export default {
  name: "UserRegister",
  data() {
    return {
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
    confirm() {},
    movePage() {
      this.$router.push({ name: "main" });
    },
  },
};
</script>

<style scoped>
.register-box {
  margin: 0 auto;
}
</style>
