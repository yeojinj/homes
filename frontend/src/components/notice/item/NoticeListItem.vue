<template>
  <b-tr>
    <b-th class="text-left">
      <router-link :to="{ name: 'noticeview', params: { no: no } }">{{ subject }}</router-link>
    </b-th>
    <b-td>{{ regTime }}</b-td>
  </b-tr>
</template>

<script>
export default {
  name: "NoticeListItem",
  props: {
    subject: String,
    regTime: String,
    no: Number,
  },
};
</script>

<style></style>
