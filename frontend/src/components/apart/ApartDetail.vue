<template>
  <div style="display: flex">
    <div id="detailInfo">
      <fieldset class="search-group except-content">
        <div class="header-info">
          <h1 style="font-size: 20px">
            <span>{{ apartInfo.dongName }} </span>
            <span>{{ apartInfo.apartmentName }} </span>
          </h1>
          <a href="#" class="btn-back" data-ga-event="apt,backBtn"><span>뒤로</span></a
          ><a href="#" class="btn-close" data-ga-event="apt,closeBtn"><span>X</span></a>
        </div>

        <div class="address-info">
          <h2 class="address" style="font-size: 10px">지번 : {{ apartInfo.dongName + " " + apartInfo.jibun }}</h2>
        </div>
        <div class="address-info">
          <h2 class="address" style="font-size: 10px">
            도로명:
            {{ apartInfo.roadName + " " + parseInt(apartInfo.roadNameBonbun) }}
          </h2>
        </div>
        <div class="apart-buildYear">
          <h2 class="buildYear" style="font-size: 10px">준공년도: {{ apartInfo.buildYear }}</h2>
        </div>

        <ul class="search-select-group">
          <li class="type select"></li>
          <li class="area select"></li>
          <li class="roadViewButton">
            <button @click="showRoadView">로드뷰</button>
          </li>
          <li class="options">
            <div>
              <b-form-select v-model="apartArea" :options="areas" @change="changeArea"></b-form-select>
            </div>
          </li>
        </ul>
      </fieldset>

      <div class="averDeal">최근 3개월 평균 거래가격 : {{ averDeal }}</div>

      <!--로드뷰-->
      <div id="roadview" style="width: 100%; height: 300px" v-show="isRoadViewOn"></div>

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
                    <td class="ps-3 py-2">
                      {{ item.dealYear + "." + item.dealMonth + "." + item.dealDay }}
                    </td>
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
//import { throws } from "assert";
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
      apartInfo: {
        apartmentName: "",
        dongName: "",
        jibun: "",
        buildYear: "",
        roadName: "",
        roadNameBonbun: "",
        lat: "",
        lng: "",
      },
      areas: [],
      dealList: [],
      averDeal: 0,
      count: 0,
      date: {
        nowYear: "",
        oldYear: "",
        nowMonth: "",
        oldMonth: "",
      },
      isRoadViewOn: false,

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
                //여긴왜 calUnitAmount를 하면 안먹힐까....error
                let length = value.toString().length;

                if (length >= 5) {
                  return value.toString().substring(0, length - 4) + "억 " + value.toString().substring(length - 4, length) + "만원";
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
      map: null,
      marker: null,
      markers: [],
    };
  },

  created() {
    console.log("created");
    // this.init();
    //this.initRoadView();
    //현재 년월
    var now = new Date(2022, 4); // 현재 날짜 및 시간
    this.date.nowYear = now.getFullYear();
    this.date.nowMonth = now.getMonth();

    //기준이 될 년월
    var oneMonthAgo = new Date(now.setMonth(now.getMonth() - 3)); // 한달 전
    this.date.oldYear = oneMonthAgo.getFullYear();
    this.date.oldMonth = oneMonthAgo.getMonth();
  },

  mounted() {
    // kakao map 초기화
    this.init();
  },
  methods: {
    hi() {
      if (window.kakao && window.kakao.maps) {
        console.log("initmap mounted");
        this.initMap();
      } else {
        const script = document.createElement("script");

        console.log("initmap mounted  else");
        script.onload = () => window.kakao.maps.load(this.initMap);

        script.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=25a670a3c5b2cb026eddd631f8e2eaad&libraries=services&autoload=false";
        document.head.appendChild(script);
      }
    },
    async init() {
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
            this.areas.push({
              value: area,
              text: Math.round(area / 3.30579) + "평(" + area + ")",
            });
          });

          this.apartArea = this.areas[0].value;
        });
      await this.getApartInfo(); //얘가
      // await this.hi();
      await this.getApartDealWithArea();
      await this.getDealTable();
    },

    /////////////////////////////// 지도 관련 /////////////////////////////
    // 지도 초기화
    /////////////////////////////// 지도 관련 /////////////////////////////
    // 지도 초기화
    initMap() {
      console.log("initMpap");
      window.kakao.maps.load(() => {
        var mapContainer = document.getElementById("map");

        // 아파트 위치 좌표
        var mapOption = {
          center: new window.kakao.maps.LatLng(35.205314, 126.811552),
          level: 3,
        };
        // 지도를 생성한다
        this.map = new window.kakao.maps.Map(mapContainer, mapOption);
        // 지도에 확대 축소 컨트롤을 생성한다
        var zoomControl = new window.kakao.maps.ZoomControl();
        // 지도의 우측에 확대 축소 컨트롤을 추가한다
        this.map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);
        this.map.setMapTypeId(window.kakao.maps.MapTypeId.ROADMAP);

        ////로드뷰@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@//

        var roadviewContainer = document.getElementById("roadview");
        var roadview = new window.kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
        var roadviewClient = new window.kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

        //지도를 생성할 때 필요한 기본 옵션
        var position = new window.kakao.maps.LatLng(this.apartInfo.lat, this.apartInfo.lng); //지도의 중심좌표.

        roadviewClient.getNearestPanoId(position, 50, function (panoId) {
          roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
        }); //지도 생성 및 객체 리턴
        //마커찍기
        this.createMarker();
      });

      //this.createMarker();
    },

    //로드뷰 보여주기 유무

    showRoadView() {
      this.isRoadViewOn = !this.isRoadViewOn;
    },

    getApartDealWithArea() {
      http.get(`apart/view/${this.$route.params.apartCode}/${this.apartArea}`).then(({ data }) => {
        console.log("getApart");
        //console.log("아파트거래정보");
        for (var i = 0; i < data.length; i++) {
          this.chartData.labels.push(data[i].dealYear.toString().substr(2, 2) + "년 " + data[i].dealMonth + "월");

          this.chartData.datasets[0].data.push(data[i].amount);
        }
      });
    },
    // 실거래가 테이블 정보 불러오기
    getDealTable() {
      http.get(`apart/dealList/${this.$route.params.apartCode}/${this.apartArea}`).then(({ data }) => {
        console.log("getDeal");
        console.log("아파트거래 리스트 정보");

        this.count = 0;
        this.averDeal = 0;
        for (var i = 0; i < data.length; i++) {
          //최근 3개월 평균 거래금액
          let year = data[i].dealYear;
          let month = data[i].dealMonth;

          if (year >= this.date.oldYear && year <= this.date.nowYear && month >= this.date.oldMonth && month <= this.date.nowMonth) {
            //최근 3개월 거래 개수

            this.count++;
            //총 금액
            this.averDeal += parseInt(data[i].amount);
          }
          //억,천만 단위 찍어주는 함수 호출
          data[i].amount = this.calUnitAmount(data[i].amount);

          this.dealList.push(data[i]);
        }

        //거래한 개수가 존재할때만 평균 금액을 구한다.
        if (this.count != 0) {
          //거래 횟수 만큼 나눔
          let deal = this.averDeal / this.count;

          //억,천만 단위 찍어주는 함수 호출
          this.averDeal = this.calUnitAmount(deal);
        } else {
          this.averDeal = "-";
        }
      });
    },

    //거래금액 (숫자)을 억,천만 단위로 자르는 함수
    calUnitAmount(amount) {
      let length = amount.toString().length;
      if (length >= 5) {
        return amount.toString().substring(0, length - 4) + "억 " + amount.toString().substring(length - 4, length) + "만원";
      } else {
        return amount + "만원";
      }
    },
    //선택한 아파트 정보(aptCode)를 가져오는 함수 (비동기)
    getApartInfo() {
      console.log("getApartInfo");
      const params = {
        apt: this.$route.params.apartCode,
      };
      http.get(`apart/apartInfo`, { params }).then(({ data }) => {
        //    console.log(data.lat);
        this.apartInfo.apartmentName = data.apartmentName;
        this.apartInfo.dongName = data.dongName;
        this.apartInfo.jibun = data.jibun;

        this.apartInfo.buildYear = data.buildYear;
        this.apartInfo.roadName = data.roadName;
        this.apartInfo.roadNameBonbun = data.roadNameBonbun;
        this.apartInfo.lat = data.lat;
        this.apartInfo.lng = data.lng;

        //initmap
        this.hi();
      });
    },

    //평수가 변경되면 불리는 함수
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

    //차트에 넣을 데이터를 초기화하는 함수
    initChartData() {
      //label 초기화
      this.chartData.labels = [];
      //datasets.date 초기화
      this.chartData.datasets[0].data = [];
    },

    //거래 테이블 데이터 초기화하는 함수
    initDealTable() {
      this.dealList = [];
    },

    // createRoadMap() {

    // },

    // 마커 생성, 지도 범위 재설정
    createMarker() {
      // 마커 및 지도 위치
      var pos = new window.kakao.maps.LatLng(this.apartInfo.lat, this.apartInfo.lng);

      // 마커 이미지의 이미지 주소입니다
      var imageSrc = "https://cdn-icons-png.flaticon.com/512/4970/4970769.png"; // https://cdn-icons-png.flaticon.com/512/6917/6917662.png

      // 마커 이미지의 이미지 크기 입니다
      var imageSize = new window.kakao.maps.Size(40, 40);

      // 마커 이미지를 생성합니다
      var markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);

      // 마커를 생성합니다
      this.marker = new window.kakao.maps.Marker({
        position: pos,
        image: markerImage, // 마커 이미지
      });

      // 마커에 커서가 오버됐을 때 마커 위에 표시할 인포윈도우를 생성합니다
      var iwContent = '<div id="info-box" style="padding: 5px; font-weight: bold; ">' + this.apartInfo.apartmentName + "</div>"; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다

      // 인포윈도우를 생성합니다
      var infowindow = new window.kakao.maps.InfoWindow({
        content: iwContent,
      });

      // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
      // 이벤트 리스너로는 클로저를 만들어 등록합니다
      // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
      window.kakao.maps.event.addListener(this.marker, "mouseover", this.makeOverListener(this.map, this.marker, infowindow));

      window.kakao.maps.event.addListener(this.marker, "mouseout", this.makeOutListener(infowindow));

      // 마커가 지도 위에 표시되도록 설정합니다
      this.marker.setMap(this.map);

      // 지도 중심을 이동 시킵니다
      this.map.setCenter(pos);
    },

    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다 - 마우스 오버
    makeOverListener(map, marker, infowindow) {
      return function () {
        infowindow.open(map, marker);
      };
    },

    // 인포윈도우를 닫는 클로저를 만드는 함수입니다 - 마우스 아웃
    makeOutListener(infowindow) {
      return function () {
        infowindow.close();
      };
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
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;
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
