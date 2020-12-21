<template>
  <div class="piduoduo-fresh-card" @click="$emit('click')">
    <van-row class="title">{{ title }}</van-row>
    <van-row class="body">{{ body }}</van-row>
    <van-row type="flex" justify="space-between" class="statusbar">
      <van-col class="username">{{ username }}</van-col>
      <van-col class="statistic">{{ read }}阅读 · {{ comment }}评论</van-col>
    </van-row>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true
    },
    body: {
      type: String,
      required: true
    },
    username: {
      type: String,
      required: true
    },
    read: {
      type: [Number, String],
      default: 0
    },
    comment: {
      type: [Number, String],
      default: 0
    }
  }
};
</script>

<style lang="scss" scoped>
.piduoduo-fresh-card {
  background: white;
  padding: 1rem;
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
