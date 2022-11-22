<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>답변하기</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col style="text-align: left">
        <b-form @submit="onSubmit" @reset="onReset">
          <b-form-group id="subject-group" label="질문 제목:" label-for="subject">
            <b-form-input id="subject" v-model="qnaItem.subject" type="text" required readonly></b-form-input>
          </b-form-group>

          <b-form-group id="content-group" label="질문 내용:" label-for="content">
            <b-form-textarea id="content" v-model="qnaItem.content" readonly></b-form-textarea>
          </b-form-group>

          <b-form-group id="content-group" label="답변:" label-for="content">
            <b-form-textarea
              id="content"
              v-model="qnaItem.comment"
              placeholder="답변 입력"
              rows="10"
              max-rows="15"
            ></b-form-textarea>
          </b-form-group>

          <b-button type="submit" variant="outline-primary" class="m-1">답변 등록</b-button>
          <b-button type="reset" variant="outline-danger" class="m-1">초기화</b-button>
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http";

export default {
  name: "QnaComment",
  data() {
    return {
      qnaItem: {
        no: "",
        state: "N",
        userId: "",
        subject: "",
        content: "",
        comment: "",
      },
      isUserid: false,
    };
  },
  created() {
    http.get(`/qna/${this.$route.params.no}`).then(({ data }) => {
      console.log("data 받아옴 : " + data);
      this.qnaItem = data;
    });
    this.isUserid = true;
  },
  methods: {
    onSubmit() {
      http
        .put(`/qna/comment`, {
          no: this.qnaItem.no,
          userId: this.qnaItem.userid,
          subject: this.qnaItem.subject,
          content: this.qnaItem.content,
          state: "Y",
          comment: this.qnaItem.comment,
        })
        .then(({ data }) => {
          let msg = "답변 등록 시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "답변 등록이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.moveList();
        });
    },
    onReset(event) {
      event.preventDefault();
      this.qnaItem.comment = "";
      this.moveList();
    },
    moveList() {
      this.$router.push({ name: "qnalist" });
    },
  },
};
</script>

<style></style>
