<template>
  <div id="notice">
    <h3>공지사항</h3>
    <div id="notice-detail">
      <b-table-simple class="shadow notice-table" hover responsive>
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
  animation: fadeInUp 2s;
  /* animation-delay: 2s; */
}

h3 {
  color: #3f667a;
}

#notice-detail {
  float: left;
  margin: 20px;
  margin-left: 40px;
  min-width: 20rem;
  border: none;
}

.notice-table {
  border-radius: 5px;
  height: 280px;
  display: flex;
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
