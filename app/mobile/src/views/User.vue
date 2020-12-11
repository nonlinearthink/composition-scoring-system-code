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
        <van-col
          v-for="item in layout.statisticBar"
          :key="item.name"
          :span="24 / layout.statisticBar.length"
        >
          <van-row>{{ statistic[item.name] }}{{ item.unit }}</van-row>
          <van-row>{{ item.text }}</van-row>
        </van-col>
      </van-row>
    </div>
    <float-card class="float-card">
      <van-row class="card-view">
        <van-col
          v-for="(item, index) in layout.toolCardItem"
          :key="index"
          :span="24 / layout.toolCardItem.length"
        >
          <van-row>
            <van-icon :name="item.icon" :size="layout.toolCardIconSize" />
          </van-row>
          <van-row>
            {{ item.text }}
          </van-row>
        </van-col>
      </van-row>
      <template #extra>
        <van-cell is-link>
          <template #title>
            <van-icon name="gem-o" />
            会员中心
          </template>
        </van-cell>
      </template>
    </float-card>
    <van-cell-group class="setting-group">
      <van-cell></van-cell>
    </van-cell-group>
  </div>
</template>

<script>
import FloatCard from "@/components/FloatCard.vue";
export default {
  components: {
    FloatCard
  },
  data() {
    return {
      nickname: "闪光绝对天龙人",
      signature: "我难道不是最强的吗？",
      statistic: {
        fan: 0,
        follow: 0,
        compositionTotal: 0
      },
      layout: {
        statisticBar: [
          {
            name: "fan",
            unit: "人",
            text: "粉丝"
          },
          {
            name: "follow",
            unit: "个",
            text: "关注"
          },
          {
            name: "compositionTotal",
            unit: "篇",
            text: "累计创作"
          }
        ],
        toolCardIconSize: "2rem",
        toolCardItem: [
          {
            icon: "star-o",
            text: "我的收藏"
          },
          {
            icon: "todo-list-o",
            text: "浏览记录"
          },
          {
            icon: "good-job-o",
            text: "我赞过的"
          }
        ],
        settingGroupItem: [
          {
            icon: "",
            text: ""
          },
          {
            icon: "",
            text: ""
          },
          {
            icon: "",
            text: ""
          }
        ]
      }
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
.float-card {
  width: 100vw;
  background: white;
  .card-view {
    text-align: center;
  }
}
.setting-group {
  margin-top: $blank-size;
}
</style>
