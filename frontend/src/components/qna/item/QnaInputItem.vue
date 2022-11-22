<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group id="subject-group" label="제목:" label-for="subject">
          <b-form-input
            id="subject"
            v-model="qnaItem.subject"
            type="text"
            required
            placeholder="제목 입력"
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="qnaItem.content"
            placeholder="내용 입력"
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <b-button type="submit" variant="outline-primary" class="m-1" v-if="this.type === 'write'">작성</b-button>
        <b-button type="submit" variant="outline-primary" class="m-1" v-else>수정</b-button>
        <b-button type="reset" variant="outline-danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "QnaInputItem",
  data() {
    return {
      qnaItem: {
        no: 0,
        state: "N",
        userId: "",
        subject: "",
        content: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      http.get(`/qna/${this.$route.params.no}`).then(({ data }) => {
        // this.article.articleno = data.article.articleno;
        // this.article.userid = data.article.userid;
        // this.article.subject = data.article.subject;
        // this.article.content = data.article.content;
        this.qnaItem = data;
      });
      this.isUserid = true;
    }
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      err && !this.qnaItem.subject && ((msg = "제목 입력해주세요"), (err = false), this.$refs.subject.focus());
      err && !this.qnaItem.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "write" ? this.writeQna() : this.modifyQna();
    },
    onReset(event) {
      event.preventDefault();
      this.qnaItem.no = 0;
      this.qnaItem.subject = "";
      this.qnaItem.content = "";
      this.moveList();
    },
    writeQna() {
      http
        .post(`/qna`, {
          userId: this.userInfo.userId, // 현재 로그인한 사용자의 아이디
          subject: this.qnaItem.subject,
          content: this.qnaItem.content,
          state: this.qnaItem.state,
        })
        .then(({ data }) => {
          let msg = "등록 시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    modifyQna() {
      http
        .put(`/qna`, {
          no: this.qnaItem.no,
          userId: this.qnaItem.userid,
          subject: this.qnaItem.subject,
          content: this.qnaItem.content,
          state: this.qnaItem.state,
        })
        .then(({ data }) => {
          let msg = "수정 시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.moveList();
        });
    },
    moveList() {
      this.$router.push({ name: "qnalist" });
    },
  },
};
</script>

<style></style>
