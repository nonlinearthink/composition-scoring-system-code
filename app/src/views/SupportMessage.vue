<template>
  <div>
    <van-nav-bar
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      title="收到的赞"
      @click-left="onRouteBack"
    />
    <van-loading v-if="loading" color="#1989fa" style="text-align: center;" />
    <van-row
      v-for="item in supportList"
      v-else
      :key="item.supportId"
      class="support-card"
    >
      <van-col>
        <van-image
          width="3rem"
          height="3rem"
          fit="cover"
          round
          :src="item.avatarUrl == null ? defaultAvatar : item.avatarUrl"
          style="padding-right: 1rem;"
          @click="
            onRouteChange({
              path: '/user/home',
              query: { user: item.username }
            })
          "
        />
      </van-col>
      <van-col>
        <van-row>
          {{ item.nickname }}<span class="fade-text">点赞了你的作文</span>
        </van-row>
        <van-row class="card" @click="onEnterComposition(item)">
          {{ item.title }}
        </van-row>
        <van-row class="time">{{ translateTime(item.time) }}</van-row>
      </van-col>
    </van-row>
  </div>
</template>

<script>
import moment from "moment";
import { mapMutations, mapState } from "vuex";
export default {
  data() {
    return {
      supportList: null,
      defaultAvatar: require("../assets/images/avatar.svg"),
      loading: true
    };
  },
  computed: {
    ...mapState(["isLogin"])
  },
  created() {
    if (this.isLogin) {
      this.axios
        .get(`/support/all`)
        .then(res => {
          console.log(res.data);
          this.supportList = res.data.data.supportViewModelList;
        })
        .catch(err => console.error(err.response.data));
    } else {
      this.loading = false;
      this.$toast("此功能仅支持登录用户");
    }
  },
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onEnterComposition(item) {
      this.$router.push({
        path: "/composition",
        query: { compositionId: item.compositionId }
      });
    },
    onRouteBack() {
      this.$router.go(-1);
    },
    onRouteChange(to) {
      this.$router.push(to);
    },
    ...mapMutations(["setRouteAnchor"])
  }
};
</script>

<style lang="scss" scoped>
.support-card {
  background: white;
  margin-bottom: $blank-size;
  padding: $blank-size;
  .fade-text {
    color: $color-fade;
    margin-left: $blank-size;
  }
  .time {
    font-size: $text-small;
    color: $color-fade;
  }
  .card {
    background: #dce5ec;
    padding: $blank-size;
    margin: $blank-size/2 0;
    border-radius: $blank-size/2;
  }
}
</style>
