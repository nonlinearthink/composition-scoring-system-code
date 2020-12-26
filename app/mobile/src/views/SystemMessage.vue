<template>
  <div id="system-message-page">
    <van-nav-bar
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      title="系统消息"
      @click-left="onRouteBack"
    />
    <div
      v-for="item in systemMessages"
      :key="item.smessageId"
      class="system-message-card"
    >
      <van-row>{{ item.smessageBody }}</van-row>
      <van-row type="flex" justify="end" class="time">
        {{ translateTime(item.time) }}
      </van-row>
    </div>
  </div>
</template>

<script>
import moment from "moment";
export default {
  data() {
    return {
      systemMessages: null
    };
  },
  created() {
    this.axios
      .get(`/system-message`)
      .then(res => {
        console.log(res.data);
        this.systemMessages = res.data.data.systemMessageEntityList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    onRouteBack() {
      this.$router.go(-1);
    },
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    }
  }
};
</script>

<style lang="scss" scoped>
.system-message-card {
  margin-bottom: $blank-size;
  background: white;
  padding: $blank-size;
  .time {
    color: $color-fade;
  }
}
</style>
