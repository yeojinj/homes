<template>
  <div style="display: flex">
    <div id="detailInfo">
      <fieldset class="search-group except-content">
        <div class="header-info">
          <h1 style="font-size: 20px">
            <span>{{ this.$route.params.apartCode }}</span>
          </h1>
          <a href="#" class="btn-back" data-ga-event="apt,backBtn"><span> 뒤로 </span></a
          ><a href="#" class="btn-close" data-ga-event="apt,closeBtn"><span>X</span></a>
        </div>

        <div class="address-info">
          <h2 class="address" style="font-size: 10px">지번 :경기도 부천시 괴안동 162-2</h2>
        </div>
        <div class="address-info">
          <h2 class="address" style="font-size: 10px">도로명 :경기도 부천시 괴안동 162-2</h2>
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
          <li class="comment"><a href="/apt/5wW15/0/2/review" data-ga-event="apt,viewReviewOnHeader">38</a></li>
          <li class="options">
            <div>
              <b-form-select v-model="apartArea" :options="areas"></b-form-select>
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
      chartData: {
        labels: [],
        datasets: [
          {
            label: "Data One",
            backgroundColor: "#f87979",
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
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

      await http.get(`apart/area/${this.$route.params.apartCode}`).then(({ data }) => {
        data.forEach((area) => {
          this.areas.push({ value: area, text: area / 3.30579 });
        });

        this.apartArea = this.areas[0];
        console.log(this.areas[0]);

        console.log(this.apartArea.value + "평");
      });

      await http.get(`apart/view/${this.$route.params.apartCode}/${this.apartArea.value}`).then(({ data }) => {
        for (var i = 0; i < data.length; i++) {
          this.chartData.labels.push(data[i].dealYear + "년 " + data[i].dealMonth + "월");
          this.chartData.datasets[0].data.push(data[i].amount);
        }
      });
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
  background-color: orange;
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

ul {
  list-style: none;
  margin: 0;
  padding: 0;
  border: 0;
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  padding-inline-start: 40px;
}
li {
  display: list-item;
  text-align: -webkit-match-parent;
  list-style: none;
}
</style>
