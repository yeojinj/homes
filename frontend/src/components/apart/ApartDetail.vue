<template>
  <div style="display: flex">
    <div id="detailInfo">
      <fieldset class="search-group except-content">
        <div class="header-info">
          <h1 style="font-size: 20px">
            <span>{{ this.$route.params.apartDto.apartmentName }}</span>
          </h1>
          <a href="#" class="btn-back" data-ga-event="apt,backBtn"><span>뒤로</span></a
          ><a href="#" class="btn-close" data-ga-event="apt,closeBtn"><span>X</span></a>
        </div>

        <div class="address-info">
          <h2 class="address" style="font-size: 10px">지번: 경기도 부천시 괴안동 162-2</h2>
        </div>
        <div class="address-info">
          <h2 class="address" style="font-size: 10px">도로명: 경기도 부천시 괴안동 162-2</h2>
        </div>

        <ul class="search-select-group">
          <li class="type select">
            <div>
              <button data-ga-event="select,tradeType">매매</button>
              <ul class="list-layer" style="display: none">
                <li><a href="#" data-value="0">매매</a></li>
                <li><a href="#" data-value="1">전월세</a></li>
              </ul>
            </div>
          </li>
          <li class="area select">
            <button type="button" class="areaSelector" data-ga-event="select,area">29평</button>
          </li>
          <li class="comment">
            <a href="/apt/5wW15/0/2/review" data-ga-event="apt,viewReviewOnHeader">38</a>
          </li>
          <li class="options">
            <div>
              <b-form-select v-model="apartArea" :options="areas" @change="changeArea"></b-form-select>
            </div>
          </li>
        </ul>
      </fieldset>

      <div class="chart">
        <LineChartGenerator
          :chart-options="chartOptions"
          :chart-data="chartData"
          :chart-id="chartId"
          :dataset-id-key="datasetIdKey"
          :plugins="plugins"
          :css-classes="cssClasses"
          :styles="styles"
          :width="width"
          :height="height"
        />
      </div>

      <div class="dealTabel">
        <div id="list-box" class="card">
          <div class="bg-white mb-2">
            <div class="border-bottom"><h5 class="p-3 m-0">실거래가</h5></div>
            <div style="overflow: auto; max-height: 25vh">
              <table class="w-100">
                <thead class="bg-secondary text-white" style="position: sticky; top: 0">
                  <tr>
                    <td class="ps-3 py-1">거래일</td>
                    <td>가격</td>
                    <td>면적</td>
                    <td>층</td>
                  </tr>
                </thead>
                <tbody class="px-2">
                  <tr v-for="(item, index) in dealList" :key="index" class="border-bottom">
                    <td class="ps-3 py-2">{{ item.dealYear + "." + item.dealMonth + "." + item.dealDay }}</td>
                    <td>{{ item.amount }}</td>
                    <td>{{ Math.round(apartArea / 3.30579) + "평" }}</td>
                    <td>{{ item.floor + "층" }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <div class="map_wrap">
        <ul id="category">
          <li id="BK9" data-order="0">
            <span class="category_bg bank"></span>
            은행
          </li>
          <li id="MT1" data-order="1">
            <span class="category_bg mart"></span>
            마트
          </li>
          <li id="PM9" data-order="2">
            <span class="category_bg pharmacy"></span>
            약국
          </li>
          <li id="OL7" data-order="3">
            <span class="category_bg oil"></span>
            주유소
          </li>
          <li id="CE7" data-order="4">
            <span class="category_bg cafe"></span>
            카페
          </li>
          <li id="CS2" data-order="5">
            <span class="category_bg store"></span>
            편의점
          </li>
        </ul>
      </div>
    </div>
    <div id="map"></div>
  </div>
</template>

<script>
import http from "@/api/http";
import { Chart, registerables } from "chart.js";
import { Line as LineChartGenerator } from "vue-chartjs/legacy";
Chart.register(...registerables);

export default {
  name: "apartDetailView",
  type: "line",

  components: {
    LineChartGenerator,
  },
  props: {
    chartId: {
      type: String,
      default: "line-chart",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 400,
    },
    height: {
      type: Number,
      default: 400,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      apartArea: null,
      areas: [],
      dealList: [],
      chartData: {
        labels: [],
        datasets: [
          {
            label: "Data One",
            backgroundColor: "#3f667a",
            borderColor: "#3f667a",

            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,

        scales: {
          y: {
            grid: {
              display: false,
            },
            ticks: {
              callback: function (value) {
                let length = value.toString().length;

                if (length >= 5) {
                  return (
                    value.toString().substring(0, length - 4) +
                    "억 " +
                    value.toString().substring(length - 4, length) +
                    "만원"
                  );
                } else {
                  return value + "만원";
                }
              },
            },
          },
        },
        plugins: {
          legend: {
            display: false,
          },
        },
      },
    };
  },

  created() {
    this.init();
  },

  mounted() {
    // kakao map 초기화
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      script.onload = () => window.kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=25a670a3c5b2cb026eddd631f8e2eaad&libraries=services&autoload=false";
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      window.kakao.maps.load(() => {
        var mapContainer = document.getElementById("map");
        var mapOption = {
          center: new window.kakao.maps.LatLng(37.56666, 126.978),
          level: 3,
        };
        // 지도를 생성한다
        var map = new window.kakao.maps.Map(mapContainer, mapOption);
        // 지도에 확대 축소 컨트롤을 생성한다
        var zoomControl = new window.kakao.maps.ZoomControl();
        // 지도의 우측에 확대 축소 컨트롤을 추가한다
        map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);
      });
    },
    showApartList() {
      //state의 값을 넘겨줌
      console.log("이벤트 넘어옴");
      this.apart = this.apartments;
    },
    async init() {
      console.log(this.$route.params.apartCode + " " + "create");
      // this.chartData.labels = null;
      //반드시 변수명 params로 선언
      const params = {
        apt: this.$route.params.apartCode,
      };
      await http
        .get(`apart/area`, {
          params,
        })
        .then(({ data }) => {
          data.forEach((area) => {
            this.areas.push({ value: area, text: Math.round(area / 3.30579) });
          });

          this.apartArea = this.areas[0].value;
        });

      await this.getApartDealWithArea();
      await this.getDealTable();
    },

    getApartDealWithArea() {
      http.get(`apart/view/${this.$route.params.apartCode}/${this.apartArea}`).then(({ data }) => {
        console.log("아파트거래정보");
        for (var i = 0; i < data.length; i++) {
          this.chartData.labels.push(data[i].dealYear.toString().substr(2, 2) + "년 " + data[i].dealMonth + "월");

          this.chartData.datasets[0].data.push(data[i].amount);
        }
      });
    },

    getDealTable() {
      http.get(`apart/dealList/${this.$route.params.apartCode}/${this.apartArea}`).then(({ data }) => {
        console.log("아파트거래 리스트 정보");
        //  console.log(data);
        this.dealList = data;
      });
    },

    changeArea() {
      //차트 데이터 초기화
      this.initChartData();
      //거래 테이블 초기화
      this.initDealTable();

      //변경된 평수 가지고 다시 차트 데이터 얻어오기
      this.getApartDealWithArea();

      //변경된 평수 가지고 다시 거래 테이블 정보 얻어오기
      this.getDealTable();
    },

    initChartData() {
      this.chartData.labels = [];
      this.chartData.datasets[0].data = [];
    },

    initDealTable() {
      this.dealList = [];
    },
  },
};
</script>

<style scope>
#detailInfo {
  width: 430px;
  height: 100vh;
}

#map {
  width: 100%;
  height: 100vh;
}

.search-group {
  top: 0;
  left: 0;
  width: 430px;
  background-color: #3f667a;
  padding-top: 12px;
  color: #ffffff;
}
.search-select-group {
  display: table;
}
.search-select-group > li {
  display: table-cell;
  vertical-align: top;
}

.header-info {
  position: relative;
  height: 58px;
  padding: 0 21px 0 20px;
  text-align: center;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 350px;
}
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png) no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px
    center;
}
.placeinfo .tel {
  color: #3f667a;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
