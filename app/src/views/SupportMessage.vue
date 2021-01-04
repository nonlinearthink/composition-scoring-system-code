<template>
  <div>
    <van-nav-bar
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      title="收到的赞"
      @click-left="$router.go(-1)"
    />
    <van-pull-refresh
      v-model="loading"
      :success-text="first ? '请求成功' : '刷新成功'"
      @refresh="onRefresh"
    >
      <van-row
        v-for="item in supportList"
        :key="item.supportId"
        class="support-card"
        type="flex"
      >
        <van-col :style="{ flex: 0 }">
          <van-image
            width="3rem"
            height="3rem"
            fit="cover"
            round
            :src="item.avatarUrl == null ? defaultAvatar : item.avatarUrl"
            style="padding-right: 1rem;"
            @click="
              $router.push({
                path: '/user/home',
                query: { user: item.username }
              })
            "
          />
        </van-col>
        <van-col :style="{ flex: 1 }">
          <van-row>
            <span :style="{ fontWeight: '600' }">{{ item.nickname }}</span>
            <span class="fade-text">点赞了你的作文</span>
          </van-row>
          <van-row class="card" @click="onEnterComposition(item)">
            <font-awesome-icon icon="link" color="#92c8e0" />
            {{ item.title }}
          </van-row>
          <van-row class="time">{{ formatTime(item.time) }}</van-row>
        </van-col>
      </van-row>
    </van-pull-refresh>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
import dateUtils from "../assets/js/common/dateUtils";
export default {
  data() {
    return {
      supportList: null,
      defaultAvatar: require("../assets/images/avatar.svg"),
      loading: true,
      first: true
    };
  },
  computed: {
    ...mapState(["isLogin", "user"])
  },
  created() {
    this.onRefresh();
  },
  methods: {
    onEnterComposition(item) {
      this.$router.push({
        path: "/composition",
        query: { compositionId: item.compositionId }
      });
    },
    onRefresh() {
      this.first = false;
      if (this.isLogin) {
        this.loading = true;
        this.axios
          .get(`/support/all`)
          .then(res => {
            console.log(res.data);
            this.supportList = res.data.data.supportViewModelList
              .sort((a, b) => b.time - a.time)
              .filter(item => item.username != this.user.username);
            this.loading = false;
          })
          .catch(err => console.error(err.response.data));
      } else {
        this.loading = false;
        this.$toast("此功能仅支持登录用户");
      }
    },
    ...mapMutations(["setRouteAnchor"]),
    ...dateUtils
  }
};
</script>

<style lang="scss" scoped>
.support-card {
  background: white;
  border-bottom: 1px #e7e7e7 solid;
  padding: $blank-size;
  .fade-text {
    color: $color-fade;
    margin-left: $blank-size/2;
    font-size: 0.8rem;
  }
  .time {
    font-size: $text-small;
    color: $color-fade;
  }
  .card {
    background: #f4f4f4;
    padding: $blank-size/2;
    margin: $blank-size/2 0;
    border-radius: $blank-size/2;
  }
}
</style>
