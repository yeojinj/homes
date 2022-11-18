import axios from "axios";

// local vue api axios instance
function apiInstance() {
  const instance = axios.create({
    //baseURL: process.env.VUE_APP_API_BASE_URL,
    baseURL:"http://localhost:8080/homes",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

// house deal API axios instance
function houseInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_HOUSE_DEAL_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { apiInstance, houseInstance };
