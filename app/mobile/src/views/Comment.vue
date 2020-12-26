<template>
  <div id="comment-page">
    <van-nav-bar
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      title="评论"
      @click-left="onRouteBack"
    />
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
            onRouteChange({
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
export default {
  data() {
    return {
      commentList: null,
      defaultAvatar: require("../assets/images/avatar.svg")
    };
  },
  created() {
    this.axios
      .get("/comment/all")
      .then(res => {
        console.log(res.data);
        this.commentList = res.data.data.commentViewModelList;
      })
      .catch(err => {
        console.error(err.response.data);
      });
  },
  methods: {
    onRouteBack() {
      this.$router.go(-1);
    },
    onRouteChange(to) {
      this.$router.push(to);
    },
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onEnterComposition(item) {
      this.$router.push({
        path: "/composition",
        query: { compositionId: item.compositionId }
      });
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
