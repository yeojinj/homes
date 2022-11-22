<template>
  <b-container class="bv-example-row mt-3">
    <b-row> </b-row>
    <notice-input-item type="write" />
  </b-container>
</template>

<script>
import NoticeInputItem from "@/components/notice/item/NoticeInputItem.vue";
export default {
  name: "NoticeWrite",
  components: {
    NoticeInputItem,
  },
};
</script>

<style></style>
