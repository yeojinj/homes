<template>
  <div class="footer">

  
    <nav>
      <a href="#" target="_blank">공지사항</a> |
      <a href="#" target="_blank">QnA</a>
    </nav>
    <div><span>&copy;SSAFY 8기 광주 5반 정여진 홍금비</span><br /><br /></div>
  </div>

</template>

<script>
export default {
  name: "TheFooterNavbar",
  data() {
    return {};
  },
};
</script>

<style scoped>
.footer {
  background-color: lightgray;
  width: 100%;
  position: absolute;
  bottom: 0;

}

nav > * {
  color: gray;
}
</style>
