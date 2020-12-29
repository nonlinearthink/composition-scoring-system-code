<template>
  <div id="user-page">
    <div class="user-card">
      <div class="user-info">
        <div
          class="user-info-avatar"
          @click="
            onRouteChange({
              path: '/user/home',
              query: { user: user.username }
            })
          "
        >
          <van-image
            width="4rem"
            height="4rem"
            fit="cover"
            round
            :src="layout.avatar"
            class="piduoduo-avatar"
          />
        </div>
        <div v-if="isLogin" class="user-info-other">
          <div class="nickname">
            {{ user.nickname }}
          </div>
          <van-row v-if="user.signature" class="signature">
            <van-tag color="orange">个性签名</van-tag>
            {{ user.signature }}
          </van-row>
        </div>
        <div v-else class="user-info-other" @click="goLogin">
          <div class="login-button-wrapper">点击登录</div>
        </div>
      </div>
      <van-row class="statistic-bar">
        <van-col
          v-for="item in layout.statisticBar"
          :key="item.name"
          :span="24 / layout.statisticBar.length"
          @click="onClickStatisticBar(item)"
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
          @click="onRouteChange(item.to)"
        >
          <van-row>
            <div class="icon-wrapper">
              <van-icon :name="item.icon" :size="layout.toolCardIconSize" />
            </div>
          </van-row>
          <van-row>
            {{ item.text }}
          </van-row>
        </van-col>
      </van-row>
      <template #extra>
        <van-cell is-link size="large" center @click="onBlock">
          <template #icon>
            <van-icon name="vip-card-o" class="piduoduo-setting-icon" />
          </template>
          <template #title>
            会员中心
          </template>
        </van-cell>
      </template>
    </float-card>
    <van-cell-group class="setting-group">
      <van-cell
        v-for="item in layout.settingGroupItem"
        :key="item.text"
        is-link
        size="large"
        center
        :to="item.to"
      >
        <template #icon>
          <van-icon :name="item.icon" class="piduoduo-setting-icon" />
        </template>
        <template #title>
          {{ item.text }}
        </template>
      </van-cell>
    </van-cell-group>
  </div>
</template>

<script>
import FloatCard from "@/components/FloatCard.vue";
import { mapState } from "vuex";
export default {
  components: {
    FloatCard
  },
  data() {
    return {
      statistic: {
        fan: 0,
        follow: 0,
        composition: 0
      },
      layout: {
        avatar: require("../assets/images/avatar.svg"),
        statisticBar: [
          {
            name: "follow",
            unit: "个",
            text: "关注"
          },
          {
            name: "fan",
            unit: "人",
            text: "粉丝"
          },

          {
            name: "composition",
            unit: "篇",
            text: "累计创作"
          }
        ],
        toolCardIconSize: "2rem",
        toolCardItem: [
          {
            icon: "star-o",
            text: "我的收藏",
            color: "#02a7f0",
            to: "/favorite"
          },
          {
            icon: "todo-list-o",
            text: "浏览记录",
            color: "blue",
            to: "/history"
          },
          {
            icon: "good-job-o",
            text: "我赞过的",
            color: "red",
            to: "/support"
          }
        ],
        settingGroupItem: [
          {
            icon: "question-o",
            text: "帮助手册",
            to: "/help"
          },
          {
            icon: "envelop-o",
            text: "意见反馈",
            to: "/feedback"
          },
          {
            icon: "setting-o",
            text: "设置",
            to: "/setting/main"
          }
        ]
      }
    };
  },
  computed: {
    ...mapState(["user", "token", "isLogin"])
  },
  created() {
    if (this.isLogin) {
      this.axios
        .get(`/follow/${this.user.username}`)
        .then(res => {
          console.log(res.data);
          this.statistic.follow = res.data.data.followList.length;
        })
        .catch(err => {
          console.error(err.response.data);
        });
      this.axios
        .get(`/follow/${this.user.username}/follower`)
        .then(res => {
          console.log(res.data);
          this.statistic.fan = res.data.data.followList.length;
        })
        .catch(err => {
          console.error(err.response.data);
        });
      this.axios
        .get("composition")
        .then(res => {
          console.log(res.data);
          this.statistic.composition = res.data.data.compositionList.filter(
            item => item.status == 4
          ).length;
        })
        .catch(err => {
          console.error(err.response.data);
        });
    }
  },
  methods: {
    goLogin() {
      this.$router.push("/login");
    },
    onClickStatisticBar(item) {
      if (item.name == "composition") {
        this.$store.commit("setRouteAnchor", 2);
        this.$router.push("/manager");
      } else if (item.name == "fan") {
        this.$router.push({ path: "/user/follow", query: { tab: 1 } });
      } else {
        this.$router.push({ path: "/user/follow", query: { tab: 0 } });
      }
    },
    onRouteChange(to) {
      this.$router.push(to);
    },
    onBlock() {
      this.$toast("待开放");
    }
  }
};
</script>

<style lang="scss" scoped>
.user-card {
  background: linear-gradient(
    180deg,
    $user-card-background-top,
    $user-card-background-bottom
  );
  padding: $blank-size;
  color: $user-card-text-color;
  padding-bottom: $blank-size * 2;
  // 兼容iOS顶部安全区域适配
  padding-top: calc(#{$blank-size} + constant(safe-area-inset-top));
  padding-top: calc(#{$blank-size} + env(safe-area-inset-top));
  .user-info {
    display: flex;
    .user-info-avatar {
      // 不抢占剩余空间
      flex: 0;
    }
    .user-info-other {
      // 抢占剩余空间
      flex: 1;
      // 不设置宽度，由子元素撑开
      width: 0;
      .nickname {
        font-size: $text-large;
        font-weight: 900;
        white-space: pre-wrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      .signature {
        font-size: $text-small;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      .login-button-wrapper {
        height: 100%;
        // 垂直居中
        display: flex;
        justify-content: center;
        flex-direction: column;
        // 调整文字底部到容器中线
        margin-top: -$text-large / 2;
        font-size: $text-large;
      }
    }
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
    width: 95vw;
    text-align: center;
  }
}
.setting-group {
  margin-top: $blank-size;
}
</style>
