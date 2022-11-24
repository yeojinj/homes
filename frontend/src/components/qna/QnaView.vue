<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h5 style='color: gray'>질문</h5><h3>${qnaItem.subject}</h3>
          <div style='text-align: right;'><h6>${qnaItem.userId}</div><div style='text-align: right;'>${qnaItem.regTime}</h6></div>`"
          class="shadow-sm text-left"
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
    <b-row class="mb-1" v-if="qnaItem.state == 'Y'">
      <b-col>
        <b-card
          :header-html="`<h5 style='color: gray'>답변</h5>
          <div style='text-align: right;'><h6>관리자</div><div style='text-align: right;'>${qnaItem.comTime}</h6></div>`"
          class="shadow-sm text-left"
        >
          <b-card-body class="text-left">
            <div v-html="comment"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-dark" size="sm" @click="moveQnaList">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button
          variant="outline-primary"
          size="sm"
          @click="moveModifyQna"
          class="mr-2"
          v-if="qnaItem.userId === userInfo.userId && qnaItem.state == 'N'"
          >수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="deleteQna" v-if="qnaItem.userId === userInfo.userId">삭제</b-button>
      </b-col>
    </b-row>
    <template v-if="userInfo.rule == 'A' && qnaItem.state == 'N'">
      <div id="buttons">
        <b-button class="button-write" variant="outline-dark" @click="moveComment()">답변하기</b-button>
      </div>
    </template>
  </b-container>
</template>

<script>
// import moment from "moment";
import http from "@/api/http";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "QnaDetail",
  data() {
    return {
      qnaItem: {},
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    message() {
      if (this.qnaItem.content) return this.qnaItem.content.split("\n").join("<br>");
      return "";
    },
    comment() {
      if (this.qnaItem.comment) return this.qnaItem.comment.split("\n").join("<br>");
      return "";
    },
  },
  //만들어질 때 qna 게시글 정보 가져오기 (GET)
  created() {
    http.get(`/qna/${this.$route.params.no}`).then(({ data }) => {
      this.qnaItem = data;
    });
  },

  methods: {
    //수정하기
    moveModifyQna() {
      this.$router.replace({
        name: "qnamodify",
        params: { no: this.qnaItem.no },
      });
    },
    //삭제하기
    deleteQna() {
      if (confirm("정말 삭제하시겠습니까??")) {
        http.delete(`/qna/${this.$route.params.no}`).then(({ data }) => {
          let msg = "삭제 시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "삭제되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.moveQnaList();
        });
      }
    },
    //Qna 전체 보기로 이동
    moveQnaList() {
      this.$router.push({ name: "qnalist" });
    },
    moveComment() {
      this.$router.replace({
        name: "qnacomment",
        params: { no: this.qnaItem.no },
      });
    },
  },
};
</script>

<style></style>
