<template>
  <div id="system-message-page">
    <!-- 导航栏 -->
    <van-nav-bar
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      title="系统消息"
      @click-left="$router.go(-1)"
    />
    <!-- 刷新请求 -->
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
        <!-- 正文 -->
        <van-row>{{ item.systemMessageBody }}</van-row>
        <!-- 发布时间 -->
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
      // 系统消息列表
      systemMessages: null,
      // 加载特效
      loading: false,
      // 第一次加载界面
      first: true
    };
  },
  created() {
    this.onRefresh();
  },
  methods: {
    /**
     * @description 刷新时调用
     */
    onRefresh() {
      // 取消第一次
      this.first = false;
      // 开启加载特效
      this.loading = true;
      // 请求系统消息
      this.axios
        .get(`/system-message`)
        .then(res => {
          console.log(res.data);
          // 时间倒序排序
          this.systemMessages = res.data.data.systemMessageEntityList.sort(
            (a, b) => b.time - a.time
          );
          // 关闭加载特效
          this.loading = false;
        })
        .catch(err => console.error(err.response.data));
    },
    /**
     * @description 格式化字符串
     * @return {String} 格式化后的字符串
     */
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
