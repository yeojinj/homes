<template>
  <div id="notice">
    <h2>공지사항</h2>
    <div id="notice-detail">
      <b-table-simple class="shadow" hover responsive>
        <b-tbody>
          <notice-main-item v-for="item in noticeItems" :key="item.key" v-bind="item" />
        </b-tbody>
      </b-table-simple>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import NoticeMainItem from "@/components/main/item/NoticeMainItem.vue";

export default {
  name: "MainNotice",
  components: {
    NoticeMainItem,
  },
  data() {
    return {
      noticeItems: [],
      fields: [{ key: "subject", label: "제목", tdClass: "tdSubject" }],
    };
  },
  created() {
    http.get(`/notice?pgno=1&key=&word=`).then(({ data }) => {
      this.noticeItems = data;
    });
  },
  methods: {
    // handleClick(value) {
    // },
  },
};
</script>

<style scope>
#notice {
  display: inline-block;
}

#notice-detail {
  float: left;
  margin: 10px;
  min-width: 20rem;
  border: none;
}
</style>
