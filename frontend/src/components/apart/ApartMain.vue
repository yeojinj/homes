<template>
  <div>
    <div id="map"></div>
    <div id="search-box" class="card">
      <div>
        <!-- 하위 컴포넌트의 이벤트를 수신 -->
        <apart-search v-on:show-apart-list="showApartList"></apart-search>
      </div>
    </div>
    <div id="list-box" class="card" v-if="visible">
      <!-- 검색 결과 아파트 리스트 -->
      <apart-list v-bind:apartments="apart"></apart-list>
    </div>
  </div>
</template>

<script>
import ApartSearch from "@/components/apart/ApartSearch.vue";
import ApartList from "@/components/apart/ApartList.vue";
import { mapState } from "vuex";
const apartStore = "apartStore";
export default {
  name: "AppHouse",
  components: {
    ApartSearch,
    ApartList,
  },
  data() {
    return {
      visible: false,
      apart: [],
      map: null,
      marker: null,
      markers: [],
    };
  },

  computed: {
    ...mapState(apartStore, ["apartments"]),
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
    // 지도 초기화
    initMap() {
      window.kakao.maps.load(() => {
        var mapContainer = document.getElementById("map");
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
      });
    },

    showApartList() {
      //state의 값을 넘겨줌
      console.log("이벤트 넘어옴");
      this.apart = this.apartments;
      this.visible = true;
      this.initMarker();
    },

    // 마커 초기화(삭제)
    initMarker() {
      console.log("initMarker()...");
      if (this.marker != null) {
        console.log("마커 초기화 해야함");
        for (var i = 0; i < this.markers.length; i++) {
          console.log("=== " + this.markers[i]);
          this.markers[i].setMap(null);
        }
      }
      this.createMarker();
    },

    // 마커 생성
    createMarker() {
      // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
      var bounds = new window.kakao.maps.LatLngBounds();

      // 마커 이미지의 이미지 주소입니다
      var imageSrc = "https://cdn-icons-png.flaticon.com/512/4970/4970769.png"; // https://cdn-icons-png.flaticon.com/512/6917/6917662.png

      // 마커 이미지의 이미지 크기 입니다
      var imageSize = new window.kakao.maps.Size(40, 40);

      for (var i = 0; i < this.apart.length; i++) {
        var point = new window.kakao.maps.LatLng(this.apart[i].lat, this.apart[i].lng);

        // 마커 이미지를 생성합니다
        var markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        this.marker = new window.kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: point, // 마커를 표시할 위치
          // title: this.apart[i].apartmentName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커 이미지
        });

        // 마커에 커서가 오버됐을 때 마커 위에 표시할 인포윈도우를 생성합니다
        var iwContent =
          '<div id="info-box" style="padding: 5px; font-weight: bold; ">' + this.apart[i].apartmentName + "</div>"; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다

        // 인포윈도우를 생성합니다
        var infowindow = new window.kakao.maps.InfoWindow({
          content: iwContent,
        });

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        window.kakao.maps.event.addListener(
          this.marker,
          "mouseover",
          this.makeOverListener(this.map, this.marker, infowindow),
        );
        window.kakao.maps.event.addListener(this.marker, "mouseout", this.makeOutListener(infowindow));

        let makeClickListener = this.makeClickListener;
        let apartCode = this.apart[i].apartCode;
        // 마커에 클릭이벤트를 등록합니다.
        window.kakao.maps.event.addListener(this.marker, "click", function () {
          makeClickListener(apartCode);
        });

        this.markers.push(this.marker);

        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(point);
      }

      this.map.setBounds(bounds);
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

    makeClickListener(aptCode) {
      this.$router.push({ name: "apartDetailView", params: { apartCode: aptCode } });
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 100vh;
}

#search-box {
  /* margin-top: 10px; */
  position: absolute;
  top: 70px;
  left: 20px;
  width: 400px;
  padding: 10px;
  z-index: 100;
  background-color: rgba(255, 255, 255, 0.7);
  overflow-y: auto;
}

#list-box {
  margin-top: 60px;
  position: absolute;
  top: 150px;
  left: 20px;
  width: 400px;
  padding: 10px;
  z-index: 100;
  background-color: rgba(255, 255, 255, 0.7);
  overflow-y: auto;
  max-height: 60vh;
}
</style>
