<template>
  <div id="report-page">
    <van-nav-bar
      title="举报"
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="onRouteBack"
    >
      <template #right>
        <div style="color: white;" @click="onSubmit">
          提交
        </div>
      </template>
    </van-nav-bar>
    <van-cell-group title="举报理由">
      <van-field
        v-model="report.reportBody"
        type="textarea"
        placeholder="请说明你的举报理由"
        :border="false"
        autosize
      />
    </van-cell-group>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showSelect: false,
      report: {
        reportBody: ""
      }
    };
  },
  methods: {
    onRouteBack() {
      this.$router.go(-1);
    },
    onSelected(value) {
      this.showSelect = false;
      this.selectType = value;
    },
    onSubmit() {
      let submit = {
        compositionReportBody:
          this.$route.query.type == 0 ? this.report.reportBody : null,
        commentReportBody:
          this.$route.query.type == 1 ? this.report.reportBody : null,
        time: new Date().getTime()
      };
      let url = `/report/${
        this.$route.query.type == 0 ? "composition" : "comment"
      }/${
        this.$route.query.type == 0
          ? this.$route.query.compositionId
          : this.$route.query.commentId
      }`;
      this.axios
        .post(url, submit)
        .then(res => {
          console.log(res.data);
          this.$toast("添加成功");
          this.onRouteBack();
        })
        .catch(err => console.error(err.response.data));
    }
  }
};
</script>

<style lang="scss" scoped></style>
