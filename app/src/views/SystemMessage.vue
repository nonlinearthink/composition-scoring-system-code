<template>
  <div id="system-message-page">
    <van-nav-bar
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      title="系统消息"
      @click-left="$router.go(-1)"
    />
    <van-pull-refresh
      v-model="loading"
      :success-text="first ? '请求成功' : '刷新成功'"
      @refresh="onRefresh"
    >
      <div
        v-for="item in systemMessages"
        :key="item.systemMessageId"
        class="system-message-card"
      >
        <van-row>{{ item.systemMessageBody }}</van-row>
        <van-row type="flex" justify="end" class="time">
          {{ translateTime(item.time) }}
        </van-row>
      </div>
    </van-pull-refresh>
  </div>
</template>

<script>
import moment from "moment";
export default {
  data() {
    return {
      systemMessages: null,
      loading: false,
      first: true
    };
  },
  created() {
    this.onRefresh();
  },
  methods: {
    onRefresh() {
      this.first = false;
      this.loading = true;
      this.axios
        .get(`/system-message`)
        .then(res => {
          console.log(res.data);
          this.systemMessages = res.data.data.systemMessageEntityList.sort(
            (a, b) => b.time - a.time
          );
          this.loading = false;
        })
        .catch(err => console.error(err.response.data));
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
  white-space: pre-line;
  .time {
    color: $color-fade;
  }
}
</style>
