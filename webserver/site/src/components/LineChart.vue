<script>
import { Line, mixins } from "vue-chartjs";

export default {
  extends: Line,
  mixins: [mixins.reactiveProp, mixins.reactiveData],
  props: {
    // chartdata: {
    //   type: Object,
    //   default: null
    // },
    options: {
      type: Object,
      default: null
    }
  },
  mounted() {
    //是在配置线形图的渐变效果
    this.gradient = this.$refs.canvas
      .getContext("2d")
      .createLinearGradient(0, 0, 0, 450);
    this.gradient.addColorStop(0, "rgba(255, 0,0, 0.5)");
    this.gradient.addColorStop(0.5, "rgba(255, 0, 0, 0.25)");
    this.gradient.addColorStop(1, "rgba(255, 0, 0, 0)");
    this.renderChart(
      this.chartData, //相当于data配置
      this.options
      // {
      // responsive: true, // 长宽，100%.如果要单设长和宽的话，要将responsive 设为false
      // maintainAspectRatio: false
      // }
    );
  }
};
</script>
