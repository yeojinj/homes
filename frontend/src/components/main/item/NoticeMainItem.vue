<template>
  <b-tr>
    <b-td class="text-center item">
      <router-link :to="{ name: 'noticeview', params: { no: no } }">{{ subject }}</router-link>
    </b-td>
  </b-tr>
</template>

<script>
export default {
  name: "NoticeMainItem",
  props: {
    subject: String,
    no: Number,
  },
};
</script>

<style scoped>
.item {
  border: none;
}

a {
  color: black;
}
</style>
