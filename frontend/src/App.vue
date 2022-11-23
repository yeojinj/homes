<template>
  <div id="app" class="app">
    <the-header-navbar></the-header-navbar>
    <router-view />
    <the-footer-navbar></the-footer-navbar>
  </div>
</template>

<script>
import TheHeaderNavbar from "@/components/TheHeaderNavbar";
import TheFooterNavbar from "@/components/TheFooterNavbar";

export default {
  name: "App",
  components: {
    TheHeaderNavbar,
    TheFooterNavbar,
  },
};
</script>

<style>
#app {
  font-family: "SUIT-Regular";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  /* color: #2c3e50; */

  position: relative;
  min-height: 100%;
  padding-bottom: 114px;
}

@font-face {
  font-family: "SUIT-Regular";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_suit@1.0/SUIT-Regular.woff2") format("woff2");
  font-weight: normal;
  font-style: normal;
}

nav {
  padding: 30px;
}
</style>
