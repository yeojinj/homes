<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col>
        <b-alert variant="light" show><h3>정보 수정</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card
          class="text-center mt-3 modify-box"
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
                placeholder="이름"
                
                v-model="user.name"
                readonly
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                placeholder="이메일"
                v-model="user.email"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="전화번호:" label-for="phone">
              <b-form-input
                id="phone"
                placeholder="전화번호"
                value="123"
                v-model="user.phone"
              ></b-form-input>
            </b-form-group>
            <b-button variant="dark" class="mr-1" @click="confirm"
              >수정</b-button
            >
            <b-button variant="dark" @click="cancel">취소</b-button>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
    <br />
  </b-container>
</template>

<script>
import { mapState ,mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserModify",
  data() {
    
    return {
      user: {
        userId: null,
        userPw: null,
        // userPwCk: null,
        name: null,
      //  email: this.userInfo.email,
        phone: null,
      },
    };
  },
  created(){
    
    this.user= this.userInfo;
  },

  computed: {
    ...mapState(memberStore, ["userInfo"]),
    

    
    
  },
  methods: {
    ...mapActions(memberStore, ["userModify"]),
   async confirm() {
    console.log(this.user);
        await this.userModify(this.user);
      this.$router.push({ name: "mypage" });
    },
    cancel() {
      this.$router.push({ name: "mypage" });
    },
  },
};
</script>

<style scoped>
.modify-box {
  margin: 0 auto;
}
</style>
