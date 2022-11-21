<template>
  <div id="news">
    <h2>부동산 관련 뉴스</h2>
    <div id="news-detail" v-for="(item, index) in newsItems" :key="index">
      <b-card @click="handleClick(item)" tag="article" style="max-width: 20rem" class="mb-2" :title="item.title">
        <b-card-text v-html="item.description"></b-card-text>
      </b-card>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  name: "MainNews",
  components: {},
  data() {
    return {
      newsItems: [],
    };
  },
  created() {
    http.get(`/api/news`).then(({ data: { items } }) => {
      this.newsItems = items;
      for (const item in this.newsItems) {
        this.newsItems[item].title = this.newsItems[item].title.replace(/<(\/b|b)([^>]*)>/gi, "");
        this.newsItems[item].description = this.newsItems[item].description.replace(/<(\/b|b)([^>]*)>/gi, "");
      }
    });
  },
  methods: {
    handleClick(value) {
      const url = value.link;
      window.open(url);
    },
  },
};
</script>

<style scope>
#news {
  display: inline-block;
}

#news-detail {
  float: left;
  margin: 10px;
}
</style>
