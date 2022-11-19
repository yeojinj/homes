import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  console.log(user);
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  //헤더에다 담아서 감
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  api.defaults.headers["refresh-token"] =
    sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
  await api.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await api.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function join(user, success, fail) {
  console.log(user);
  await api.post(`/user/join`, JSON.stringify(user)).then(success).catch(fail);
}

export { login, findById, tokenRegeneration, logout, join };
