<template>
  <div>
    <b-navbar class="nav-custom shadow-sm" type="light" variant="light">
      <b-navbar-brand href="#" class="item">
        <router-link :to="{ name: 'main' }">
          <b-img :src="require('@/assets/logo.png')" id="logo" class="d-inline-block align-top" alt="logo"></b-img>
        </router-link>
      </b-navbar-brand>

      <b-navbar-nav class="item mx-auto">
        <b-nav-item href="#">
          <router-link :to="{ name: 'house' }" class="m-2 link">실거래가</router-link>
          <router-link :to="{ name: 'notice' }" class="m-2 link">공지사항</router-link>
          <router-link :to="{ name: 'qna' }" class="m-2 link">QnA</router-link>
        </b-nav-item>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <!-- after login -->
      <b-navbar-nav v-if="userInfo">
        <b-nav-item class="align-self-center user-info">
          <!-- <b-avatar
            variant="primary"
            v-text="userInfo.userId.charAt(0).toUpperCase()"
          ></b-avatar> -->
          <p>{{ userInfo.name }}({{ userInfo.userId }})님 환영합니다.</p>
        </b-nav-item>

        <b-nav-item class="align-self-center">
          <router-link :to="{ name: 'mypage' }" class="link align-self-center">마이페이지</router-link>
        </b-nav-item>

        <b-nav-item class="link align-self-center" @click.prevent="onClickLogout">로그아웃</b-nav-item>

        <!-- <b-nav-item href="#" class="item align-self-center">
          <b-button
            class="link align-self-center"
            v-if="userInfo"
            @click.prevent="onClickLogout"
          >
            로그아웃
          </b-button>
        </b-nav-item> -->
      </b-navbar-nav>

      <!-- before login -->
      <b-navbar-nav v-else>
        <b-nav-item class="align-self-center">
          <router-link :to="{ name: 'join' }" class="link">
            <b-icon icon="person-circle"></b-icon> 회원가입
          </router-link>
        </b-nav-item>
        <b-nav-item class="align-self-center">
          <router-link :to="{ name: 'login' }" class="link"> <b-icon icon="key"></b-icon> 로그인 </router-link>
        </b-nav-item>
      </b-navbar-nav>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";
const memberStore = "memberStore";

export default {
  name: "TheHeaderNavbar",
  data() {
    return {};
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      // sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "main" });
      console.log(this.userInfo.userId);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.userId);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "main" });
    },
  },
};
</script>

<style scoped>
#logo {
  width: 100px;
}

.link {
  text-decoration: none;
}

.link:hover {
  text-decoration: underline;
}

nav {
  padding-left: 10px;
  padding-right: 10px;
}

nav a {
  color: black;
}

.navbar-light .navbar-nav .nav-link {
  color: black;
}

.navbar.navbar-light.bg-light {
  background-color: white !important;
}

p {
  margin: 0;
  color: gray;
}

button {
  background-color: white !important;
  border: none;
}
</style>
