<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-dark" size="sm" @click="moveQnaList">목록</b-button>
      </b-col>
      <b-col class="text-right">
        <b-button variant="outline-primary" size="sm" @click="moveModifyQna" class="mr-2">수정</b-button>
        <b-button variant="outline-danger" size="sm" @click="deleteQna">삭제</b-button>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${qnaItem.no}.
          ${qnaItem.subject}</h3><div><h6>${qnaItem.userId}</div><div>${qnaItem.regTime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
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
// import moment from "moment";
import http from "@/api/http";

export default {
  name: "QnaDetail",
  data() {
    return {
      qnaItem: {},
    };
  },
  computed: {
    message() {
      if (this.qnaItem.content) return this.qnaItem.content.split("\n").join("<br>");
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
    //수정하기로 이동한다.
    moveModifyQna() {
      this.$router.replace({
        name: "qnamodify",
        params: { no: this.qnaItem.no },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    //삭제하기
    deleteQna() {
      if (confirm("정말로 삭제하시겠습니꺼??")) {
        http.delete(`/qna/${this.$route.params.no}`).then(({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "삭제가 완료되었습니다.";
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
  },
};
</script>

<style></style>
