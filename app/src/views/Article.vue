<template>
  <div id="article-page">
    <van-nav-bar
      title="精选文章"
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="$router.go(-1)"
    />
    <van-image
      fit="cover"
      class="preview-image"
      :src="
        article.avatarUrl
          ? article.avatarUrl
          : require(`../assets/images/background/background-${(article.articleId %
              10) +
              1}.png`)
      "
    />
    <div class="content">
      <van-row class="title">
        {{ article.articleTitle }}
      </van-row>
      <van-row class="time">{{ formatTime(article.time) }}</van-row>
      <van-row class="body">
        {{ article.articleBody }}
      </van-row>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import dateUtils from "../assets/js/common/dateUtils";
export default {
  data() {
    return {};
  },
  computed: {
    ...mapState({ article: state => state.view.article })
  },
  methods: {
    ...dateUtils
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
  .body {
    white-space: pre-line;
  }
}
</style>
