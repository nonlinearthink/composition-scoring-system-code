<template>
  <div class="home">
    <a-row :gutter="16">
      <a-col :span="12">
        <a-statistic
          title="用户总数"
          :value="userCount"
          style="margin-right: 50px"
        >
          <template #suffix>
            <a-icon type="user" />
          </template>
        </a-statistic>
      </a-col>
      <a-col :span="12">
        <a-statistic
          title="作文总数"
          :value="compositionCount"
          class="demo-class"
        >
          <template #suffix>
            <a-icon type="edit" />
          </template>
        </a-statistic>
      </a-col>
    </a-row>
    <a-row :gutter="[16, 16]">
      <a-col :span="12">
        <a-card>
          <a-statistic
            title="昨日新增用户"
            :value="11"
            suffix="位"
            :value-style="{ color: '#3f8600' }"
            style="margin-right: 50px"
          >
            <template #prefix>
              <a-icon type="arrow-up" />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card>
          <a-statistic
            title="昨日新增作文"
            :value="9"
            suffix="篇"
            class="demo-class"
            :value-style="{ color: '#3f8600' }"
          >
            <template #prefix>
              <a-icon type="arrow-up" />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
    </a-row>
    <line-chart :chartdata="datacollection"></line-chart>
  </div>
</template>

<script>
import LineChart from "../components/LineChart";
import moment from "moment";
export default {
  name: "Home",
  components: { LineChart },
  data() {
    return {
      datacollection: {
        labels: this.lastSevenDate(),
        datasets: [
          {
            label: "活跃用户数",
            backgroundColor: "#f87979",
            data: [40, 39, 10, 40, 39, 80, 40]
          }
        ]
      },
      userCount: 0,
      compositionCount: 0
    };
  },
  created() {
    this.axios
      .get("/admin/count")
      .then(res => {
        console.log(res);
        this.userCount = res.data.data.dataCount.userCount;
        this.compositionCount = res.data.data.dataCount.compositionCount;
      })
      .catch(err => console.error(err));
  },
  methods: {
    lastSevenDate() {
      let dateList = [];
      for (let i = 6; i >= 0; i--) {
        dateList.push(
          moment()
            .subtract(i, "days")
            .format("MM-DD")
        );
      }
      return dateList;
    }
  }
};
</script>
