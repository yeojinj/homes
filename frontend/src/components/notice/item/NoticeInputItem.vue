<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group id="subject-group" label="제목:" label-for="subject">
          <b-form-input
            id="subject"
            v-model="noticeItem.subject"
            type="text"
            required
            placeholder="제목 입력"
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="noticeItem.content"
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
  name: "NoticeInputItem",
  data() {
    return {
      noticeItem: {
        no: 0,
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
      http.get(`/notice/${this.$route.params.no}`).then(({ data }) => {
        this.noticeItem = data;
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
      err && !this.noticeItem.subject && ((msg = "제목 입력해주세요"), (err = false), this.$refs.subject.focus());
      err && !this.noticeItem.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "write" ? this.writeNotice() : this.modifyNotice();
    },
    onReset(event) {
      event.preventDefault();
      this.noticeItem.no = 0;
      this.noticeItem.subject = "";
      this.noticeItem.content = "";
      this.moveList();
    },
    writeNotice() {
      http
        .post(`/notice`, {
          userId: this.userInfo.userId, // 현재 로그인한 사용자의 아이디
          subject: this.noticeItem.subject,
          content: this.noticeItem.content,
          state: this.noticeItem.state,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    modifyNotice() {
      http
        .put(`/notice`, {
          no: this.noticeItem.no,
          userId: this.noticeItem.userid,
          subject: this.noticeItem.subject,
          content: this.noticeItem.content,
          state: this.noticeItem.state,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    moveList() {
      console.log("공지사항 리스트로 이동...");
      this.$router.replace({ name: "noticelist" });
    },
  },
};
</script>

<style></style>
