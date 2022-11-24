<template>
  <div id="allDiv" style="display: flex">
    <div id="detailInfo">
      <fieldset class="search-group except-content">
        <div class="header-info">
          <router-link class="btn-back" data-ga-event="apt,backBtn" :to="{ name: 'apartMain' }"><span>뒤로가기</span></router-link>
          <h1 style="font-size: 23px">
            <span>{{ apartInfo.apartmentName }} </span>
          </h1>
        </div>

        <div class="address-info">
          <h2 class="address" style="font-size: 11px">지번 : {{ sidoName + " " + gugunName + " " + apartInfo.dongName + " " + apartInfo.jibun }}</h2>
        </div>
        <div class="address-info">
          <h2 class="address" style="font-size: 11px">
            도로명:
            {{ sidoName + " " + apartInfo.roadName + " " + parseInt(apartInfo.roadNameBonbun) }}
          </h2>
        </div>
        <div class="address-info">
          <h2 class="address" style="font-size: 11px">지번 : {{ sidoName + " " + gugunName + " " + apartInfo.dongName + " " + apartInfo.jibun }}</h2>
        </div>
        <div class="apart-buildYear">
          <h2 class="buildYear" style="font-size: 11px">준공년도 : {{ apartInfo.buildYear }}</h2>
        </div>

        <div style="display: flex">
          <div class="roadViewDiv" style="width: 50%" @click="showRoadView">로드뷰</div>
          <div class="areaDiv" style="width: 50%">
            <b-form-select class="area-select" v-model="apartArea" :options="areas" @change="changeArea"></b-form-select>
          </div>
        </div>
      </fieldset>

      <div class="averDealDiv">
        최근 <span class="highlight">3개월 평균</span> 거래가 : <span class="highlight">{{ averDeal }}</span>
      </div>

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
            <div style="overflow: auto; max-height: 25vh">
              <table class="w-100">
                <thead class="table-bg text-white" style="position: sticky; top: 0">
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
        <div class=""><h5 class="p-3 m-0">주변 정보</h5></div>
        <div>
          <ul id="category">
            <li id="MT1" data-order="5" @click="onClickCategory">
              <span class="category_bg market"></span>
              대형마트
            </li>
            <li id="CS2" data-order="4" @click="onClickCategory">
              <span class="category_bg store"></span>
              편의점
            </li>
            <li id="CE7" data-order="0" @click="onClickCategory">
              <span class="category_bg cafe"></span>
              카페
            </li>
            <li id="SC4" data-order="3" @click="onClickCategory">
              <span class="category_bg school"></span>
              학교
            </li>
            <li id="HP8" data-order="1" @click="onClickCategory">
              <span class="category_bg hospital"></span>
              병원
            </li>
            <li id="AG2" data-order="2" @click="onClickCategory">
              <span class="category_bg agency"></span>
              부동산
            </li>
          </ul>
        </div>
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
import { mapState } from "vuex";
const apartStore = "apartStore";

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
      default: 350,
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
      sidoName: "",
      gugunName: "",
      isRoadViewOn: false,

      chartData: {
        labels: [],
        datasets: [
          {
            label: "Data One",
            backgroundColor: "#7A4C51",
            borderColor: "#7A4C51",

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
      ///// 지도 관련 /////
      map: null,
      marker: null,
      markers: [],
      ///// 주변 상권 관련 /////
      placeOverlay: null,
      ps: null,
      contentNode: document.createElement("div"), // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
      markerCategory: null,
      markersCategory: [], // 주변 상권 마커를 담을 배열입니다
      currCategory: "", // 현재 선택된 카테고리를 가지고 있을 변수입니다
    };
  },

  created() {
    console.log("created");
    // this.init();
    //this.initRoadView();
    //현재 년월
    var now = new Date(); // 현재 날짜 및 시간
    this.date.nowYear = now.getFullYear();
    this.date.nowMonth = now.getMonth();

    //기준이 될 년월
    var oneMonthAgo = new Date(now.setMonth(now.getMonth() - 3)); // 한달 전
    this.date.oldYear = oneMonthAgo.getFullYear();
    this.date.oldMonth = oneMonthAgo.getMonth();

    //시도 구군 이름
    this.sidoName = this.searchData.sidoName;
    console.log(this.searchData.sidoName + "시도");
    this.gugunName = this.searchData.gugunName;
  },

  computed: {
    ...mapState(apartStore, ["searchData"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },

  mounted() {
    console.log("mounted()...");
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
      await this.getApartInfo();
      await this.getApartDealWithArea();
      await this.getDealTable();
    },

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

        /////////////////////////////// 로드뷰 /////////////////////////////

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
        this.initCategory();
      });

      //this.createMarker();
    },

    //로드뷰 보여주기 유무

    showRoadView() {
      this.isRoadViewOn = !this.isRoadViewOn;
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
          let deal = Math.round(this.averDeal / this.count);

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

        // this.createMarker();
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
      var imageSrc = "https://cdn-icons-png.flaticon.com/512/197/197722.png";

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

    /////////////////////////////// 주변 상권 관련 //////////////////////////////////////////////////////////
    initCategory() {
      console.log("[주변 상권] 카테코리 초기화...");

      // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
      this.placeOverlay = new window.kakao.maps.CustomOverlay({ zIndex: 1 });

      // 장소 검색 객체를 생성합니다
      this.ps = new window.kakao.maps.services.Places(this.map);

      // 지도에 idle 이벤트를 등록합니다
      window.kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);

      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      this.contentNode.className = "placeinfo_wrap";

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(this.contentNode, "mousedown", window.kakao.maps.event.preventMap);
      this.addEventHandle(this.contentNode, "touchstart", window.kakao.maps.event.preventMap);

      // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setContent(this.contentNode);

      // 각 카테고리에 클릭 이벤트를 등록합니다
      // this.addCategoryClickEvent();
    },

    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },

    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      if (!this.currCategory) {
        return;
      }

      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      this.ps.categorySearch(this.currCategory, this.placesSearchCB, {
        useMapBounds: true,
      });
    },

    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB(data, status) {
      console.log("[주변 상권] 선택하신 카테코리에 대한 장소 검색이 완료되었습니다.");
      // pagination
      if (status === window.kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces(data);
      } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
        console.log("[주변 상권] 검색 결과가 없습니다.");
        alert("현재 지도 내에 선택하신 상권이 존재하지 않습니다.");
      } else if (status === window.kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
        console.log("[주변 상권] 검색 결과를 불러오는 중 에러가 발생했습니다.");
      }
    },

    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      console.log("[주변 상권] 마커를 지도에 표출합니다.");
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order = document.getElementById(this.currCategory).getAttribute("data-order");

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker(new window.kakao.maps.LatLng(places[i].y, places[i].x), order);

        console.log("==========");
        var displayPlaceInfo = this.displayPlaceInfo;
        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        (function (marker, place) {
          window.kakao.maps.event.addListener(marker, "click", function () {
            displayPlaceInfo(place);
          });
        })(marker, places[i]);
      }
    },

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, order) {
      console.log("[주변 상권] 마커를 생성하고 표시합니다.");
      var imageSrc = "https://ifh.cc/g/SNW11K.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new window.kakao.maps.Size(42, 42), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new window.kakao.maps.Size(252, 42), // 스프라이트 이미지의 크기
          spriteOrigin: new window.kakao.maps.Point(order * 42, 0), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new window.kakao.maps.Point(21, 21), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new window.kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markersCategory.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      console.log("[주변 상권] 마커를 모두 제거합니다.");
      for (var i = 0; i < this.markersCategory.length; i++) {
        this.markersCategory[i].setMap(null);
      }
      this.markersCategory = [];
    },

    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place) {
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content += '    <span title="' + place.address_name + '">' + place.address_name + "</span>";
      }

      content += '    <span class="tel">' + place.phone + "</span>" + "</div>" + '<div class="after"></div>';

      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new window.kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.map);
    },

    // 각 카테고리에 클릭 이벤트를 등록합니다
    /*addCategoryClickEvent() {
      var category = document.getElementById("category"),
        children = category.children;

      for (var i = 0; i < children.length; i++) {
        children[i].onclick = this.onClickCategory;
      }
    },*/

    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory(event) {
      // var id = this.id,
      //   className = this.className;

      this.placeOverlay.setMap(null);

      if (event.currentTarget.className === "on") {
        console.log("=====클릭했던 카테고리");
        this.currCategory = "";
        this.changeCategoryClass();
        this.removeMarker();
      } else {
        // console.log("=====처음 클릭하는 카테고리 : " + event.currentTarget.id);
        this.currCategory = event.currentTarget.id;
        this.changeCategoryClass(event.currentTarget);
        this.searchPlaces();
      }
    },

    // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
    changeCategoryClass(el) {
      var category = document.getElementById("category"),
        children = category.children,
        i;

      for (i = 0; i < children.length; i++) {
        children[i].className = "";
      }

      if (el) {
        el.className = "on";
      }
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
  height: 105vh;
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
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 350px;
}
#category {
  /* position: absolute; */
  /* top: 10px; */
  /* left: 10px; */
  /* border-radius: 5px; */
  /* border: 1px solid #909090; */
  /* box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4); */
  /* background: #fff; */
  overflow: hidden;
  z-index: 2;
}
#category li {
  /* float: left; */
  display: inline-block;
  list-style: none;
  width: 50px;
  /* border-right: 1px solid #acacac; */
  padding: 6px 0;
  font-size: 12px;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  /* border-left: 1px solid #acacac; */
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
  background: url(https://ifh.cc/g/SNW11K.png) no-repeat;
}
#category li .cafe {
  width: 32px;
  height: 32px;
  background-position: -5px -5px;
}
#category li .hospital {
  width: 32px;
  height: 32px;
  background-position: -47px -5px;
}
#category li .agency {
  width: 32px;
  height: 32px;
  background-position: -89px -5px;
}
#category li .school {
  width: 32px;
  height: 32px;
  background-position: -131px -5px;
}
#category li .store {
  width: 32px;
  height: 32px;
  background-position: -173px -5px;
}
#category li .market {
  width: 32px;
  height: 32px;
  background-position: -215px -5px;
}
/* #category li.on .category_bg {
  background-position-x: -46px;
} */
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

/* houseInfo  start*/
h1 {
  display: block;
  font-size: 2em;
  margin-block-start: 0.67em;
  margin-block-end: 0.67em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  font-weight: bold;
}

.btn-back span {
  display: inline-block;
  overflow: hidden;
  width: 17px;
  height: 16px;
  background-image: url(https://static.hogangnono.com/dist/web/sp_pc.e76dd6e1fb046cb8a71ec352aca15fdd.png);
  background-position: -283px -383px;
  line-height: 999em;
  vertical-align: top;
}

.btn-back {
  position: absolute;
  left: 0px;
  top: 1px;
  padding-left: 20px;
  padding-top: 21px;
}

.areaDiv > .area-select {
  border: none;
  color: white;
  background: #3f667a
    url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='4' height='5' viewBox='0 0 4 5'%3e%3cpath fill='white' d='M2 0L0 2h4zm0 5L0 3h4z'/%3e%3c/svg%3e")
    no-repeat right 0.75rem center/8px 10px;
}

.roadViewDiv {
  line-height: 39px;
  border-right: solid 1px lightslategray;
  border-top: solid 1px lightslategray;
}

.areaDiv {
  border-top: solid 1px lightslategray;
}

.averDealDiv {
  padding-top: 10px;
  padding-bottom: 10px;
  /* border-bottom: solid 1px lightslategray; */
}

.averDealDiv > .highlight {
  color: #7a4c51;
  font-weight: bold;
}

.table-bg {
  background-color: #3f667a;
}
</style>
