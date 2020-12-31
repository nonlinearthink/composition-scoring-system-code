<template>
  <div id="comment-page">
    <van-nav-bar
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      title="评论"
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
              $router.push({
                path: '/user/home',
                query: { user: item.username }
              })
            "
          />
        </van-col>
        <van-col class="other">
          <van-row>
            {{ item.nickname }}<span class="fade-text">评论了你的作文</span>
          </van-row>
          <van-row>{{ item.commentBody }}</van-row>
          <van-row
            class="card"
            @click="
              $router.push({
                path: '/composition',
                query: { compositionId: item.compositionId }
              })
            "
          >
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
    ...mapState(["isLogin"])
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
            this.loading = false;
            this.commentList = res.data.data.commentViewModelList;
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
  .other {
    max-width: calc(100vw - 3rem - #{$blank-size * 3});
  }
}
</style>
