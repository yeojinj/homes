<template>
  <div id="sigungu">
    <b-container>
      <b-row class="text-center pb-2 px-1">
        <b-col class="select-bar">
          <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList">
            <template #first>
              <b-form-select-option :value="null" disabled>광역시/도</b-form-select-option>
            </template>
          </b-form-select>
        </b-col>
        <b-col class="select-bar">
          <b-form-select v-model="gugunCode" :options="guguns" @change="dongList">
            <template #first>
              <b-form-select-option :value="null" disabled>시/구</b-form-select-option>
            </template>
          </b-form-select>
        </b-col>
        <b-col class="select-bar">
          <b-form-select v-model="dongCode" :options="dongs">
            <template #first>
              <b-form-select-option :value="null" disabled>동</b-form-select-option>
            </template>
          </b-form-select>
        </b-col>
      </b-row>
      <b-row>
        <div class="input-group pb-2 px-3">
          <input
            @keyup.enter="searchApt"
            type="text"
            v-model="apartName"
            class="form-control d-inline-block"
            placeholder="아파트 이름을 검색해보세요"
          />
          <b-button @click="searchApt" size="sm" class="btn btn-primary d-inline-block">
            <b-icon icon="search" aria-hidden="true"></b-icon>
          </b-button>
        </div>
      </b-row>
    </b-container>
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

<style scoped>
.mt-3 {
  width: fit-content;
  display: inline;
  margin: 0 auto;
}

.sm-2 {
  max-width: 120px;
  padding-left: 5px;
  padding-right: 5px;
  float: left;
}

.select-bar {
  padding-left: 10px;
  padding-right: 10px;
}

button {
  background-color: #3f667a;
}
</style>
