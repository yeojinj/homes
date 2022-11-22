<template>
  <b-list-group flush class="list my-3">
    <b-list-group-item
      v-for="apart in apartments"
      :key="apart.aptCode"
      class="list-group-item px-0"
      :to="{ name: 'apartDetailView', params: { apartCode: apart.aptCode } }"
      action
    >
      <b-row align-v="center">
        <b-col md="auto">
          <!-- Avatar -->
          <!-- <a href="javascript:;" class="avatar"> 1111</a> -->
          <img id="apartLogo" src="@/assets/building.png" />
        </b-col>
        <b-col class="ml--2">
          <!-- <h4 class="mb-0">
            <a href="javascript:;">123213</a>
          </h4> -->
          <span :class="`text-${apart.apartCode}`"
            >{{ apart.dongName }} {{ apart.apartmentName }}</span
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
    return {
      membersSearch: "",
      members: [
        {
          name: "John Michael",
          status: "Online",
          statusType: "success",
          image:
            "https://demos.creative-tim.com/argon-dashboard-pro-bs4/assets/img/theme/team-1.jpg",
        },
        {
          name: "Alex Smith",
          status: "In a meeting",
          statusType: "danger",
          image:
            "https://demos.creative-tim.com/argon-dashboard-pro-bs4/assets/img/theme/team-2.jpg",
        },
        {
          name: "Samantha Ivy",
          status: "Offline",
          statusType: "danger",
          image:
            "https://demos.creative-tim.com/argon-dashboard-pro-bs4/assets/img/theme/team-3.jpg",
        },
        {
          name: "John Michael",
          status: "Online",
          statusType: "success",
          image:
            "https://demos.creative-tim.com/argon-dashboard-pro-bs4/assets/img/theme/team-4.jpg",
        },
        {
          name: "John Snow",
          status: "Online",
          statusType: "success",
          image:
            "https://demos.creative-tim.com/argon-dashboard-pro-bs4/assets/img/theme/team-5.jpg",
        },
      ],
    };
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
</style>
