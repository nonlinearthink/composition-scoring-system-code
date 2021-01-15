<template>
  <div class="piduoduo-hot-card" @click="$emit('click')">
    <van-row>
      <van-col
        span="2"
        class="rankmark"
        :style="{ color: rankColor[rank - 1] }"
      >
        <font-awesome-icon
          v-if="rank <= 3"
          icon="award"
          class="icon"
          :style="{ marginBottom: '0.2rem' }"
        />
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
      rankColor: ["#FF5252", "#FF9800", "#FFC107", "#646566"]
    };
  }
};
</script>

<style lang="scss" scoped>
.piduoduo-hot-card {
  background: white;
  padding: $blank-size;
  padding-left: $blank-size/2;
  margin: 1px;
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
