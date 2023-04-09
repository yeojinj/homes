import "@/api/global";
import { Line } from "~/vue-chartjs-3.5.1/src/index";

export default {
  extends: Line,
  props: ["chartData", "options"],
  mounted() {
    this.renderChart(this.chartData, this.options);
  },
};
