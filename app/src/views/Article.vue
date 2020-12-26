<template>
  <div id="article-page">
    <van-nav-bar
      title="浏览推送"
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="onRouteBack"
    />
    <van-image
      fit="cover"
      class="preview-image"
      :src="require('../assets/images/default-background.jpeg')"
    />
    <div class="content">
      <van-row class="title">
        {{ article.articleTitle }}
      </van-row>
      <van-row class="time">{{ translateTime(article.time) }}</van-row>
      <van-row class="body">{{ article.articleBody }}</van-row>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import moment from "moment";
export default {
  data() {
    return {
      title: "ksakjsa sdsd s  ds ds s fds   sd s",
      time: "sdsdsdsd",
      body:
        "sdssd s ds d sd sd s ds d s d  sd s ds d  sd sd sd s d sd  d fr gf d s asf s sd a  f sdds"
    };
  },
  computed: {
    ...mapState({ article: state => state.view.article })
  },
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onRouteBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped>
.preview-image {
  height: 200px;
}
.content {
  padding: 0 $blank-size;
  .title {
    font-size: $text-normal * 1.5;
    font-weight: 900;
    margin: $blank-size 0;
  }
  .time {
    color: $color-fade;
    margin: $blank-size 0;
  }
}
</style>
