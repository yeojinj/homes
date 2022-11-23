<template>
  <div>
    <div id="map"></div>
    <div id="search-box" class="card">
      <div>
        <!-- 하위 컴포넌트의 이벤트를 수신 -->
        <apart-search v-on:show-apart-list="showApartList"></apart-search>
      </div>
    </div>
    <div id="list-box" class="card">
      <!-- 검색 결과 아파트 리스트 -->
      <apart-list v-bind:apartments="apartments"></apart-list>
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
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },

  created() {
    //   console.log(this.apart + "아파트당");
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
    // 마커 초기화
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
      for (var i = 0; i < this.apart.length; i++) {
        // 마커를 생성합니다
        this.marker = new window.kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          // position: positions[i].latlng,
          position: new window.kakao.maps.LatLng(this.apart[i].lat, this.apart[i].lng), // 마커를 표시할 위치
          title: this.apart[i].apartmentName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        });

        this.markers.push(this.marker);

        // 지도 중심 좌표 설정
        var moveLatLon = new window.kakao.maps.LatLng(this.apart[0].lat, this.apart[0].lng);

        this.map.setCenter(moveLatLon);
      }
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
  margin-top: 80px;
  position: absolute;
  top: 150px;
  left: 20px;
  width: 400px;
  padding: 10px;
  z-index: 100;
  background-color: rgba(255, 255, 255, 0.7);
  overflow-y: auto;
}
</style>
