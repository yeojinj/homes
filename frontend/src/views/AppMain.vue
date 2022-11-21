<template>
  <div>
    <div id="main-image">
      <b-card
        overlay
        img-src="https://picsum.photos/900/250/?image=3"
        img-alt="Card Image"
        text-variant="white"
        title="Where is my home"
        sub-title="내 집 구하기"
      >
        <b-card-text>
          <hr class="my-4" />
          <p>홈페이지 이용 관련 문의사항을 <b>QnA</b>에 남겨주세요.</p>
        </b-card-text>
      </b-card>
    </div>

    <div id="main-boards">
      <main-news></main-news>
      <!-- <div id="notice">
        <h2>공지사항</h2>
      </div> -->
    </div>
  </div>
</template>

<script>
import MainNews from "@/components/main/MainNews.vue";

export default {
  name: "AppMain",
  props: {
    msg: String,
  },
  components: {
    MainNews,
  },
};
</script>

<style scoped>
#main-image {
  margin-top: 80px;
}

#main-boards {
  margin-top: 80px;
  margin-bottom: 80px;
  display: inline-block;
}
</style>
