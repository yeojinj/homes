<template>
  <div class="notice-list" style="width: 1000px">
    <div id="list">
      <b-table-simple hover responsive outlined="outlined">
        <b-thead head-variant="dark">
          <b-tr>
            <b-th>제목</b-th>
            <b-th>작성일</b-th>
          </b-tr>
        </b-thead>
        <b-tbody>
          <notice-list-item v-for="item in noticeItems" :key="item.key" v-bind="item" />
        </b-tbody>
      </b-table-simple>
    </div>
    <br />
    <div id="buttons">
      <b-button class="button-write" variant="outline-dark" @click="moveWrite()">글쓰기</b-button>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import NoticeListItem from "@/components/notice/item/NoticeListItem.vue";

export default {
  name: "NoticeList",
  components: {
    NoticeListItem,
  },
  data() {
    return {
      noticeItems: [],
      fields: [
        { key: "subject", label: "제목", tdClass: "tdSubject" },
        { key: "regTime", label: "작성일", tdClass: "tdClass" },
      ],
    };
  },
  created() {
    http.get(`/notice?pgno=1&key=&word=`).then(({ data }) => {
      this.noticeItems = data;
    });
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "noticeWrite" });
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}

.tdSubject {
  width: 300px;
  text-align: left;
}

.notice-list {
  margin: 0 auto;
}
</style>
