<template>
  <div id="news">
    <h3>관련 뉴스</h3>
    <div id="news-detail" v-for="(item, index) in newsItems" :key="index">
      <b-card
        @click="handleClick(item)"
        tag="article"
        style="max-width: 20rem; border: none"
        class="mb-2 shadow news-table"
        :title="item.title"
      >
        <hr />
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
  animation: fadeInUp 2s;
  /* animation-delay: 2s; */
}

h3 {
  color: #3f667a;
}

#news-detail {
  float: left;
  margin: 10px;
}

.news-table {
  border-radius: 10px;
}

@keyframes fadeInUp {
  0% {
    opacity: 0;
    transform: translate3d(0, 10%, 0);
  }
  60% {
    opacity: 0;
    transform: translate3d(0, 10%, 0);
  }
  to {
    opacity: 1;
    transform: translateZ(0);
  }
}
</style>
