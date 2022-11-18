<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>1:1문의 수정</h3></b-alert>
      </b-col>
    </b-row>
    <qna-input-item type="modify" />
  </b-container>
</template>

<script>
import QnaInputItem from "@/components/qna/item/QnaInputItem.vue";
export default {
  name: "QnaModify",
  components: {
    QnaInputItem,
  },
};
</script>

<style></style>
