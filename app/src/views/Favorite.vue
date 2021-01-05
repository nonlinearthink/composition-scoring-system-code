<template>
  <div id="favorite-page">
    <van-nav-bar
      title="收藏夹"
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="$router.go(-1)"
    />
    <van-pull-refresh
      v-model="loading"
      :success-text="first ? '请求成功' : '刷新成功'"
      @refresh="onRefresh"
    >
      <div>
        <div
          v-for="item in compositions"
          :key="item.compositionId"
          class="favorite-card"
          @click="onEnterComposition(item)"
        >
          <van-row class="title">{{ item.title }}</van-row>
          <van-row class="body">{{ item.compositionBody }}</van-row>
          <van-row type="flex" justify="space-between" class="statusbar">
            <van-col class="username">{{ item.username }}</van-col>
            <van-col class="time">{{ formatTime(item.releaseTime) }}</van-col>
          </van-row>
        </div>
      </div>
    </van-pull-refresh>
  </div>
</template>

<script>
import { mapState } from "vuex";
import dateUtils from "../assets/js/common/dateUtils";
export default {
  data() {
    return {
      compositions: null,
      loading: true,
      first: true
    };
  },
  computed: {
    ...mapState(["isLogin"])
  },
  created() {
    this.onRefresh();
  },
  methods: {
    onEnterComposition(composition) {
      this.$router.push({
        path: "/composition",
        query: { compositionId: composition.compositionId }
      });
      console.log(composition);
    },
    onRefresh() {
      this.first = false;
      if (this.isLogin) {
        this.axios
          .get(`/favorite`)
          .then(res => {
            console.log(res.data);
            this.compositions = res.data.data.compositionList;
            this.loading = false;
          })
          .catch(err => console.error(err.response.data));
      } else {
        this.loading = false;
        this.$toast("此功能仅支持登录用户");
      }
    },
    ...dateUtils
  }
};
</script>

<style lang="scss" scoped>
.favorite-card {
  background: white;
  padding: 1rem;
  margin-bottom: 1rem;
  .title {
    font-size: $text-large;
    font-weight: 600;
  }
  .body {
    @include margin-vertical($blank-size/2);
    font-size: $text-normal;
    display: -webkit-box;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
  }
  .statusbar {
    color: $color-fade;
    font-size: $text-small;
  }
}
</style>
