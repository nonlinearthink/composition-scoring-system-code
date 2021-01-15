<template>
  <div id="comment-page">
    <van-nav-bar
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      title="回复我的"
      @click-left="$router.go(-1)"
    />
    <van-pull-refresh
      v-model="loading"
      success-text="刷新成功"
      @refresh="onRefresh"
    >
      <van-row
        v-for="item in commentList"
        :key="item.commentId"
        class="comment-card"
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
        <van-col class="other" :style="{ flex: 1 }">
          <van-row>
            <span :style="{ fontWeight: '600' }">{{ item.nickname }}</span>
            <span class="fade-text">评论了你的作文</span>
          </van-row>
          <van-row
            :style="{ color: '#555', fontSize: '0.9rem', margin: '0.25rem 0' }"
          >
            {{ item.commentBody }}
          </van-row>
          <van-row
            class="card"
            @click="
              $router.push({
                path: '/composition',
                query: { compositionId: item.compositionId, openComment: true }
              })
            "
          >
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
import { mapState } from "vuex";
import dateUtils from "../assets/js/common/dateUtils";
export default {
  data() {
    return {
      commentList: null,
      defaultAvatar: require("../assets/images/avatar.svg"),
      loading: true
    };
  },
  computed: {
    ...mapState(["isLogin", "user"])
  },
  created() {
    this.loadData();
  },
  methods: {
    ...dateUtils,
    loadData() {
      if (this.isLogin) {
        this.axios
          .get("/comment/all")
          .then(res => {
            console.log(res.data);
            this.commentList = res.data.data.commentViewModelList
              .sort((a, b) => b.time - a.time)
              .filter(item => item.username != this.user.username);
            this.loading = false;
          })
          .catch(err => {
            console.error(err.response.data);
          });
      } else {
        this.loading = false;
        this.$toast("此功能仅支持登录用户");
      }
    },
    onRefresh() {
      this.loading = true;
      this.loadData();
    }
  }
};
</script>

<style lang="scss" scoped>
.comment-card {
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
  .other {
    max-width: calc(100vw - 3rem - #{$blank-size * 3});
  }
}
</style>
