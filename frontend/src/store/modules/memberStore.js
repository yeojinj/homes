import jwtDecode from "jwt-decode";
import router from "@/router";
import { modify ,join,login, findById, tokenRegeneration, logout ,remove } from "@/api/member";
//import { join } from "core-js/core/array";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    isValidToken: false,
    userData: null,
  },
  getters: {
    //로그인 한 사람이냐
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    //토큰이 유효하냐
    checkToken: function (state) {
      return state.isValidToken;
    },

    getUserInfo : function(state){
      return state.userInfo;
    }
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    SET_USER_DATA: (state, userInfo) => {
      state.userInfo = userInfo;
    },
    SET_USER_DATA_NULL: (state) => {
      state.userInfo = null;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        ({ data }) => {
          //백엔드에서 success가 넘어왔다면
          if (data.message === "success") {
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            console.log(
              "login success token created!!!! >> ",
              accessToken,
              refreshToken
            );

            commit("SET_IS_LOGIN", true); //로그인 된 상태로 변경
            commit("SET_IS_LOGIN_ERROR", false); //에러는 없음 상태
            commit("SET_IS_VALID_TOKEN", true); //토큰 사용할 수 있다 상태

            // accessToken 과 refreshToken를 세션 스토리지에 저장한다.
            //이 두개가 있다고 로그인이 된걸로 치는거냐 ? 아니다 . 이게 있으면서 유효기간이 남아있어야 로그인이 되는거다.
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
          } else {
            console.log("실패");
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    //서버에 가서 이 사람이 정상적인 사람인지 판단
    async getUserInfo({ commit, dispatch }, token) {
      let decodeToken = jwtDecode(token);
      // console.log("2. getUserInfo() decodeToken :: ", decodeToken);
      await findById(
        decodeToken.userid,
        ({ data }) => {
          if (data.message === "success") {
            console.log(data);
            commit("SET_USER_INFO", data.userInfo);
            // console.log("3. getUserInfo data >> ", data);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.log(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          commit("SET_IS_VALID_TOKEN", false);
          await dispatch("tokenRegeneration");
        }
      );
    },
    async tokenRegeneration({ commit, state }) {
      console.log(
        "토큰 재발급 >> 기존 토큰 정보 : {}",
        sessionStorage.getItem("access-token")
      );
      await tokenRegeneration(
        JSON.stringify(state.userInfo),
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
            sessionStorage.setItem("access-token", accessToken);
            commit("SET_IS_VALID_TOKEN", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("갱신 실패");
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              state.userInfo.userid,
              ({ data }) => {
                if (data.message === "success") {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "login" });
              },
              (error) => {
                console.log(error);
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
              }
            );
          }
        }
      );
    },
    async userLogout({ commit }, userid) {
      await logout(
        userid,
        ({ data }) => {
          if (data.message === "success") {
            console.log("로그아웃 완료");
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_INFO", null);
            commit("SET_IS_VALID_TOKEN", false);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userJoin( {commit }, user) {
      await join(
        user,
        ({ data }) => {
          
          if (data === "success") {
            console.log("회원가입 성공!");
            commit();// 회원 가입이니까 store회원정보를 변경해줄필요가 없을거같음
          } else {
            console.log("회원가입 실패!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userModify({ commit }, user) {
      await modify(user, ({ data }) => {
        console.log()
        if (data.message === "success") {

          console.log("정보 수정 성공!");
          commit("SET_USER_DATA", data);
        } else {
          console.log("정보 수정 실패!");
        }
      });
    },


    async userDelete({ commit }, userId) {
      await remove(userId, ({ data }) => {
        console.log()
        if (data === "success") {

          console.log("회원 탈퇴 완료!");
          commit("SET_USER_DATA_NULL");
        } else {
          console.log("회원 탈퇴 실패 실패!");
        }
      });
    },
  },
};

export default memberStore;
