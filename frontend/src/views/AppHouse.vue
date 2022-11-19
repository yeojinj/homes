<template>
  <div>
    <div id="map"></div>
    <div id="search-box" class="card">
      <div>
        <div class="input-group pb-2 px-3">
          <input
            @keyup.enter="onKeywordSearch"
            type="text"
            v-model="inputKeyword"
            class="form-control d-inline-block"
            placeholder="아파트 또는 동 이름으로 검색해보세요"
          />
          <b-button
            @click="search"
            size="sm"
            class="btn btn-primary d-inline-block"
          >
            <b-icon icon="search" aria-hidden="true"></b-icon>
          </b-button>
        </div>
      </div>
    </div>
    <div id="list-box" class="card">
      <!-- 검색 결과 아파트 리스트 -->
      <div class="bg-white mb-2">
        <div class="border-bottom"><h5 class="p-3 m-0">실거래가</h5></div>
        <div>
          <table class="w-100">
            <thead class="bg-secondary text-white">
              <tr>
                <td class="ps-3 py-1">거래일</td>
                <td>가격</td>
                <td>면적</td>
                <td>층</td>
              </tr>
            </thead>
            <tbody class="px-2">
              <!-- <tr
                v-for="(item, index) in dealInfo"
                :key="index"
                class="border-bottom"
              >
                <td class="ps-3 py-2">{{ item.dealYear }}</td>
                <td>{{ item.dealAmount }}</td>
                <td>{{ item.area }}</td>
                <td>{{ item.floor }}</td>
              </tr> -->
            </tbody>
          </table>
        </div>
      </div>

      <!-- 아파트 세부 정보 -->
      <div class="bg-white mb-2">
        <div class="border-bottom">
          <h5 class="p-3 m-0">00아파트 세부 정보</h5>
        </div>
        <div>
          <table class="w-100">
            <tbody class="px-2">
              <!-- <tr
                v-for="(item, index) in dealInfo"
                :key="index"
                class="border-bottom"
              >
                <td class="ps-3 py-2">{{ item.dealYear }}</td>
                <td>{{ item.dealAmount }}</td>
                <td>{{ item.area }}</td>
                <td>{{ item.floor }}</td>
              </tr> -->
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AppHouse",
  data() {
    return {
      map: null,
    };
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
