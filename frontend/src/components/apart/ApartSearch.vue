<template>
  <div id="sigungu">
    <b-row class="mt-3 mb-3 text-center">
      <b-col class="sm-3">
        <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList"></b-form-select>
      </b-col>
      <b-col class="sm-2">
        <b-form-select v-model="gugunCode" :options="guguns" @change="dongList"></b-form-select>
      </b-col>
      <b-col class="sm-3">
        <b-form-select v-model="dongCode" :options="dongs"></b-form-select>
      </b-col>
    </b-row>

    <div class="input-group pb-2 px-3">
      <input
        @keyup.enter="searchApt"
        type="text"
        v-model="apartName"
        class="form-control d-inline-block"
        placeholder="아파트 또는 동 이름으로 검색해보세요"
      />
      <b-button @click="searchApt" size="sm" class="btn btn-primary d-inline-block">
        <b-icon icon="search" aria-hidden="true"></b-icon>
      </b-button>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const apartStore = "apartStore";
export default {
  name: "ApartSearch",

  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      apartName: "",
    };
  },

  computed: {
    ...mapState(apartStore, ["sidos", "guguns", "dongs", "apartments"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    console.log("실거래가 페이지 Create ()");
    // this.$store.dispatch("getSido");
    // this.sidoList();

    //시도 리스트 초기화
    this.CLEAR_SIDO_LIST();

    //아파트 리스트 초기화
    this.CLEAR_APT_LIST();

    //시도 정보 불러오기
    this.sidoList();
  },

  methods: {
    ...mapActions(apartStore, ["getSido", "getGugun", "getDong", "getApartList"]),
    ...mapMutations(apartStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST", "CLEAR_APT_LIST"]),
    async sidoList() {
      console.log("@@@");
      await this.getSido();
    },
    //선택한 시도에 대한 구군 정보를 얻어온다.
    async gugunList() {
      console.log(this.sidoCode);
      //구군 리스트 를 지운다.
      this.CLEAR_GUGUN_LIST();
      //선택한 구군 코드 초기화
      this.gugunCode = null;
      if (this.sidoCode) {
        //시도 코드 2글자가 넘어간다.
        await this.getGugun(this.sidoCode);
      }
    },

    async dongList() {
      console.log(this.gugunCode + "구군 선택했음");
      //동 리스트를 지운다.
      this.CLEAR_DONG_LIST();
      //선택한 동 코드 초기화
      this.dongCode = null;
      if (this.gugunCode) {
        //구군 코드 5글자가   넘어간다.
        await this.getDong(this.gugunCode);
      }
    },

    async searchApt() {
      if (!this.dongCode) {
        alert("시/구/동은 반드시 선택해주세요");
        return;
      } else {
        console.log("검색할 동 코드:" + this.dongCode);
        console.log(this.dongCode + " " + this.apartName);

        const searchInfo = {
          dongCode: this.dongCode,
          apartName: this.apartName,
        };
        await this.getApartList(searchInfo);
        await console.log(this.apartments + "아파트");
        await this.$emit("show-apart-list");
      }
    },
  },
};
</script>

<style></style>
