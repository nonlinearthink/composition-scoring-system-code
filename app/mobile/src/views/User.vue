<template>
  <div id="user-page">
    <div class="user-card">
      <van-row type="flex">
        <van-col span="6">
          <van-image
            width="5rem"
            height="5rem"
            fit=""
            round
            :src="require('../assets/images/default-avatar.svg')"
            :style="{ background: 'white -1rem 0' }"
          />
        </van-col>
        <van-col span="18">
          <van-row>
            <div class="nickname">
              {{ nickname }}
            </div>
            <van-icon name="edit" class="edit-icon" />
          </van-row>
          <van-row> "{{ signature }}" </van-row>
        </van-col>
      </van-row>
      <van-row class="statistic-bar">
        <van-col v-for="statistic in statistics" :key="statistic.name" span="8">
          <van-row>{{ statistic.count }}{{ statistic.unit }}</van-row>
          <van-row>{{ statistic.name }}</van-row>
        </van-col>
      </van-row>
    </div>
    <div class="tool-card"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nickname: "闪光绝对天龙人",
      signature: "我难道不是最强的吗？",
      statistics: [
        {
          count: 0,
          unit: "人",
          name: "粉丝"
        },
        {
          count: 0,
          unit: "个",
          name: "关注"
        },
        {
          count: 0,
          unit: "篇",
          name: "累计创作"
        }
      ]
    };
  }
};
</script>

<style lang="scss" scoped>
.user-card {
  background: linear-gradient(to right bottom, $color-primary, $color-gradient);
  padding: $blank-size;
  color: $color-text-primary;
  padding-bottom: $blank-size * 2;
  // 兼容iOS顶部安全区域适配
  padding-top: calc(#{$blank-size} + constant(safe-area-inset-top));
  padding-top: calc(#{$blank-size} + env(safe-area-inset-top));
  .nickname {
    font-size: $text-size-title;
    display: inline-block;
    font-weight: 900;
  }
  .edit-icon {
    @include margin-horizontal($blank-size);
    font-weight: 900;
  }
  .statistic-bar {
    @include margin-vertical($blank-size);
    text-align: center;
    font-size: $text-size-title;
  }
}
.tool-card {
  margin-top: -$blank-size * 2;
  width: 90vw;
  height: 20vh;
  background: white;
  border-radius: $text-size-main;
  text-align: center;
}
</style>
