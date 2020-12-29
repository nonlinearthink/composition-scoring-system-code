<template>
  <div id="favorite-page">
    <van-nav-bar
      title="收藏夹"
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="onRouteBack"
    />
    <van-loading v-if="loading" color="#1989fa" style="text-align: center;" />
    <div v-else>
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
          <van-col class="time">{{ translateTime(item.releaseTime) }}</van-col>
        </van-row>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import { mapState } from "vuex";
export default {
  data() {
    return {
      compositions: null,
      loading: true
    };
  },
  computed: {
    ...mapState(["isLogin"])
  },
  created() {
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
  methods: {
    onRouteBack() {
      this.$router.go(-1);
    },
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onEnterComposition(composition) {
      this.$router.push({
        path: "/composition",
        query: { compositionId: composition.compositionId }
      });
      console.log(composition);
    }
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
