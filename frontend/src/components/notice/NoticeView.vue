<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-dark" size="sm" @click="moveNoticeList">목록</b-button>
      </b-col>
      <template v-if="userInfo.rule == 'A'">
        <b-col class="text-right">
          <b-button variant="outline-primary" size="sm" @click="moveModifyNotice" class="mr-2">수정</b-button>
          <b-button variant="outline-danger" size="sm" @click="deleteNotice">삭제</b-button>
        </b-col>
      </template>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${noticeItem.subject}</h3>
          <div style='text-align: right;'><h6>관리자</div><div style='text-align: right;'>${noticeItem.regTime}</h6></div>`"
          class="shadow-sm text-left"
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "NoticeView",
  data() {
    return {
      noticeItem: {},
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    message() {
      if (this.noticeItem.content) return this.noticeItem.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    http.get(`/notice/${this.$route.params.no}`).then(({ data }) => {
      this.noticeItem = data;
    });
  },

  methods: {
    moveModifyNotice() {
      this.$router.replace({
        name: "noticemodify",
        params: { no: this.noticeItem.no },
      });
    },
    deleteNotice() {
      if (confirm("정말 삭제하시겠습니까??")) {
        http.delete(`/notice/${this.$route.params.no}`).then(({ data }) => {
          let msg = "삭제 시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "삭제되었습니다.";
          }
          alert(msg);
          this.moveNoticeList();
        });
      }
    },
    moveNoticeList() {
      this.$router.push({ name: "noticelist" });
    },
  },
};
</script>

<style></style>
