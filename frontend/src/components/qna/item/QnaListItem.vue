<template>
  <b-tr>
    <b-td>{{ state }}</b-td>
    <b-th class="text-left">
      <router-link :to="{ name: 'qnaview', params: { no: no } }">{{ subject }}</router-link>
    </b-th>
    <b-td>{{ userId }}</b-td>
    <b-td>{{ regTime }}</b-td>
  </b-tr>
</template>

<script>
export default {
  name: "QnaListItem",
  props: {
    state: String,
    subject: String,
    userId: String,
    regTime: String,
    no: Number,
  },
};
</script>

<style></style>
