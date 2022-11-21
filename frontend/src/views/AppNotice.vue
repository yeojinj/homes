<template>
  <div>
    <br />
    <b-alert variant="light" show><h3>공지사항</h3></b-alert>
    <br />
    <router-view></router-view>
    <br />
  </div>
</template>

<script>
export default {
  name: "AppNotice",
};
</script>

<style></style>
