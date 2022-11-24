<template>
  <div class="qna-list" style="width: 1000px">
    <div id="list">
      <b-table-simple class="shadow" hover responsive outlined="outlined">
        <b-thead head-variant="light">
          <b-tr>
            <b-th>답변상태</b-th>
            <b-th>제목</b-th>
            <b-th>작성자</b-th>
            <b-th>작성일</b-th>
          </b-tr>
        </b-thead>
        <b-tbody>
          <!-- 하위 component인 QnaListItem 데이터 전달(props) -->
          <qna-list-item v-for="item in qnaItems" :key="item.key" v-bind="item" />
        </b-tbody>
      </b-table-simple>
    </div>
    <br />
    <template v-if="userInfo.rule == 'G'">
      <div id="buttons">
        <b-button class="button-write" variant="outline-dark" @click="moveWrite()">질문하기</b-button>
      </div>
    </template>
  </div>
</template>

<script>
import http from "@/api/http";
import QnaListItem from "@/components/qna/item/QnaListItem.vue";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "QnaList",
  components: {
    QnaListItem,
  },
  data() {
    return {
      qnaItems: [],
      fields: [
        { key: "state", label: "답변상태", tdClass: "tdClass" },
        { key: "subject", label: "제목", tdClass: "tdSubject" },
        { key: "userId", label: "작성자", tdClass: "tdClass" },
        { key: "regTime", label: "작성일", tdClass: "tdClass" },
      ],
    };
  },
  created() {
    http.get(`/qna`).then(({ data }) => {
      for (var i = 0; i < data.length; i++) {
        //답변완료 상태로 변경하여준다.

        console.log(data[i].state);
        if (data[i].state === "Y") {
          data[i].state = "답변완료";
          //미완료로 변경한다.
        } else {
          data[i].state = "미완료";
        }
        this.qnaItems.push(data[i]);
      }

      // this.qnaItems = data;
      console.log(this.qnaItems + "데이터");
    });
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "qnawrite" });
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

/* .write {
  margin: 0 auto;
}

#buttons,
#list {
  margin: 0 auto;
  text-align: center;
} */

.qna-list {
  margin: 0 auto;
}
</style>
