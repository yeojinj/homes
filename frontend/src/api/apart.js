import { apiInstance } from "./index.js";
const api = apiInstance();

//GET : 시도 정보 읽어오기
async function sido(success, fail) {
  await api.get(`/apart/sido`).then(success).catch(fail);
}

async function gugun(sido, success, fail) {
  // const o = sido;
  const params = { sido: sido };
  await api.get(`/apart/gugun/`, { params }).then(success).catch(fail);
}

async function dong(gugun, success, fail) {
  const params = { gugun: gugun };
  await api.get(`/apart/dong`, { params }).then(success).catch(fail);
}

async function apartInfo(searchInfo, success, fail) {
  const params = {
    dongCode: searchInfo.dongCode,
    apartName: searchInfo.apartName,
  };
  await api.get(`/apart/apartList`, { params }).then(success).catch(fail);
}

export { sido, gugun, dong, apartInfo };
