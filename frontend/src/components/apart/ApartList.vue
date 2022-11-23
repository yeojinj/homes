<template>
  <b-list-group flush class="list my-3">
    <b-list-group-item
      v-for="apart in apartments"
      :key="apart.aptCode"
      class="list-group-item px-0"
      :to="{ name: 'apartDetailView', params: { apartCode: apart.aptCode, apartDto: apart } }"
      action
    >
      <b-row align-v="center">
        <b-col md="auto">
          <!-- Avatar -->
          <!-- <a href="javascript:;" class="avatar"> 1111</a> -->
          <!-- <img id="apartLogo" src="@/assets/building.png" /> -->
        </b-col>
        <b-col class="ml--2">
          <!-- <h4 class="mb-0">
            <a href="javascript:;">123213</a>
          </h4> -->
          <span :class="`text-${apart.apartCode}`"
            ><span class="dong-name">{{ apart.dongName }}</span> {{ apart.apartmentName }}</span
          >
          <small></small>
        </b-col>
        <b-col md="auto">
          <b-button type="button" size="sm" variany="primary">상세</b-button>
        </b-col>
      </b-row>
    </b-list-group-item>
  </b-list-group>
</template>

<script>
//import ApartListItemVue from "@/components/apart/ApartListItem.vue";
export default {
  props: ["apartments"],
  name: "ApartList",
  components: {
    // ApartListItemVue,
  },

  data() {
    return {};
  },
};
</script>

<style scoped>
#apartLogo {
  width: 25px;
  height: 25px;
}

.my-3 {
  display: inline-block;
}

.list-group-item {
  max-width: 370px;
  max-height: 900px;
  padding-left: 10px !important;
  padding-right: 10px !important;
  margin: 0 auto;
}

.dong-name {
  color: #7a4c51;
  text-align: left;
}

button {
  background-color: #3f667a;
}
</style>
