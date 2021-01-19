<template>
  <div class="piduoduo-follow-card" @click="$emit('click')">
    <van-row type="flex" justify="space-between">
      <van-col>
        <van-row>
          <van-col class="avatar">
            <van-image
              width="3rem"
              height="3rem"
              fit="cover"
              round
              :src="
                avatar
                  ? `http://${avatar}`
                  : require('../assets/images/avatar.svg')
              "
            />
          </van-col>
          <van-col>
            <van-row class="username">{{ username }}</van-row>
            <van-row class="update-time">
              {{ timeIntervalString(updateTime) }}
            </van-row>
          </van-col>
        </van-row>
      </van-col>
      <van-col class="score"> {{ score }}分 </van-col>
    </van-row>
    <van-row class="title">{{ title }}</van-row>
    <van-row class="body">{{ body }}</van-row>
    <van-row type="flex" justify="end" class="statusbar">
      <van-col class="first-status"> 点赞数 {{ support }} </van-col>
      <van-col> 评论数 {{ comment }} </van-col>
    </van-row>
  </div>
</template>

<script>
import dateUtils from "../assets/js/common/dateUtils";
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
    updateTime: {
      type: [Number, String],
      required: true
    },
    score: {
      type: [Number, String],
      default: 0
    },
    avatar: {
      type: [String],
      default: null
    },
    support: {
      type: [Number, String],
      default: 0
    },
    comment: {
      type: [Number, String],
      default: 0
    }
  },
  data() {
    return {};
  },
  methods: {
    ...dateUtils
  }
};
</script>

<style lang="scss" scoped>
.piduoduo-follow-card {
  background: white;
  padding: 1rem;
  margin: 1px;
  .avatar {
    margin-right: $blank-size/2;
  }
  .username {
    font-size: $text-large;
  }
  .title {
    font-size: $text-large;
    font-weight: 600;
  }
  .update-time {
    font-size: $text-small;
    color: $color-fade;
  }
  .score {
    font-size: $text-normal * 1.5;
    color: orange;
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
    .first-status {
      margin-right: 10vw;
    }
  }
}
</style>
