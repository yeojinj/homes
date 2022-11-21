import { sido, gugun, dong, apartInfo } from "@/api/apart.js";

const apartStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    dongs: [{ value: null, text: "선택하세요" }],
    apartments: [],
    house: null,
  },

  mutations: {
    /////////////////////////////// Apart start /////////////////////////////////////
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_APT_LIST(state) {
      state.houses = [];
      state.house = null;
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [{ value: null, text: "선택하세요" }];
    },
    SET_APART_LIST(state, apartments) {
      state.apartments = apartments;
    },
    SET_DETAIL_HOUSE(state, house) {
      // console.log("Mutations", house);
      state.house = house;
    },
  },

  actions: {
    /////////////////////////////// House start /////////////////////////////////////
    async getSido({ commit }) {
      console.log("GetSido");
      await sido(
        ({ data }) => {
          //백엔드에서 success가 넘어왔다면
          if (data.message === "success") {
            console.log("시도 정보를 읽어왔습니다.");
            //시도 정보를  state 에 저장한다.
            commit("SET_SIDO_LIST", data.sido);
          } else {
            console.log("시도 정보를 읽어오는데 실패");
            commit("SET_SIDO_LIST", data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getGugun({ commit }, sidoCode) {
      console.log(sidoCode + "@@@@@@@@@@@@@@@@@@@@@@@@");
      await gugun(
        sidoCode,
        ({ data }) => {
          //백엔드에서 success가 넘어왔다면
          if (data) {
            console.log("구군 정보 성공 성공");

            commit("SET_GUGUN_LIST", data);
          } else {
            console.log("구군 정보 성공 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    async getDong({ commit }, gugunCode) {
      await dong(
        gugunCode,
        ({ data }) => {
          if (data) {
            console.log("동 정보  성공");

            commit("SET_DONG_LIST", data);
          } else {
            console.log("동 정보 성공 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    async getApartList({ commit }, searchInfo) {
      await apartInfo(
        searchInfo,
        ({ data }) => {
          if (data) {
            console.log("아파트 목록 정보  성공");

            commit("SET_APART_LIST", data);
          } else {
            console.log("아파트 목록 정보 성공 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );

      // // vue cli enviroment variables 검색
      // //.env.local file 생성.
      // // 반드시 VUE_APP으로 시작해야 한다.
      // const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;
      // // const SERVICE_KEY =
      // //   "######################## Service Key ########################";
      // const SERVICE_URL =
      //   "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
      // const params = {
      //   LAWD_CD: gugunCode,
      //   DEAL_YMD: "202207",
      //   serviceKey: decodeURIComponent(SERVICE_KEY),
      // };
      // http
      //   .get(SERVICE_URL, { params })
      //   .then(({ data }) => {
      //     // console.log(commit, data);
      //     commit("SET_HOUSE_LIST", data.response.body.items.item);
      //   })
      //   .catch((error) => {
      //     console.log(error);
      //   });
    },
    detailHouse({ commit }, house) {
      // 나중에 house.일련번호를 이용하여 API 호출
      // console.log(commit, house);
      commit("SET_DETAIL_HOUSE", house);
    },
  },
};

export default apartStore;
