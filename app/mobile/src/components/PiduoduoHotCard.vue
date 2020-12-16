<template>
  <div class="piduoduo-hot-card">
    <van-row>
      <van-col
        span="2"
        class="rankmark"
        :style="{ color: rankColor[rank - 1] }"
      >
        <van-icon v-if="rank <= 3" name="medal-o" size="1.5rem" class="icon" />
        <div :class="rank <= 3 ? 'rank' : 'rank-only'">
          {{ rank }}
        </div>
      </van-col>
      <van-col span="22">
        <van-row class="title">{{ title }}</van-row>
        <van-row class="body">{{ body }}</van-row>
        <van-row type="flex" justify="space-between" class="statusbar">
          <van-col class="username">{{ username }}</van-col>
          <van-col class="statistic">{{ hot }}热度</van-col>
        </van-row>
      </van-col>
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
    hot: {
      type: [Number, String],
      default: 0
    },
    rank: {
      type: [Number, String],
      default: 1
    }
  },
  data() {
    return {
      rankColor: ["red", "orange", "green", "#646566"]
    };
  }
};
</script>

<style lang="scss" scoped>
.piduoduo-hot-card {
  background: white;
  padding: $blank-size;
  padding-left: $blank-size/2;
  .rankmark {
    padding-right: $blank-size/2;
    margin-top: $blank-size/4;
    text-align: center;
    .icon {
      font-size: $text-normal * 1.5;
      width: 100%;
    }
    .rank {
      font-size: $text-normal;
      margin-top: -$blank-size/4;
    }
    .rank-only {
      font-size: $text-normal;
      margin-left: -1px;
    }
  }

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
