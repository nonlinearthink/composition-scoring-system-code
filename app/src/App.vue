<template>
  <div id="app">
    <transition name="slide-right">
      <div v-if="setup" class="setup-page">
        <van-swipe class="swipe" indicator-color="white" :loop="false">
          <van-swipe-item>英语学习</van-swipe-item>
          <van-swipe-item>自动批改作文</van-swipe-item>
          <van-swipe-item>丰富的社区活动</van-swipe-item>
          <van-swipe-item>
            <div>
              Let's Go
              <font-awesome-icon
                icon="sign-in-alt"
                :style="{ marginLeft: '1rem' }"
                @click="onEnter"
              />
            </div>
          </van-swipe-item>
        </van-swipe>
      </div>
    </transition>
    <transition :name="transitionName">
      <router-view />
    </transition>
    <van-tabbar
      v-if="tabbar.enable && !setup"
      v-model="tabbar.active"
      route
      fixed
      placeholder
    >
      <van-tabbar-item
        v-for="item in tabbar.items"
        :key="item.name"
        :name="item.name"
        :to="item.route"
      >
        <template #icon>
          <van-icon
            :name="item.icon"
            :size="item.text ? tabbar.iconSizeWithText : tabbar.iconSizeDefault"
            :color="item.iconColor"
            :badge="item.note"
          />
        </template>
        {{ item.text }}
      </van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  data() {
    return {
      tabbar: {
        enable: false,
        active: "home",
        iconSizeDefault: "2rem",
        iconSizeWithText: "1.5rem",
        items: [
          { name: "home", route: "/", icon: "home-o", text: "首页" },
          { name: "feed", route: "/manager", icon: "edit", text: "作文" },
          {
            name: "chat",
            route: "/chat",
            icon: "chat-o",
            text: "消息"
          },
          { name: "user", route: "/user", icon: "user-circle-o", text: "我的" }
        ]
      },
      transitionName: ""
    };
  },
  computed: {
    ...mapState(["appVersion", "setup"])
  },
  watch: {
    $route: function(to, from) {
      // 设置 tabbar 在那些页面可见
      let tabbarWhiteList = this.tabbar.items.map(item => item.route);
      if (tabbarWhiteList.includes(to.path)) {
        this.tabbar.enable = true;
      } else {
        this.tabbar.enable = false;
      }
      // 设置转场动画
      if (to.meta.index > from.meta.index) {
        this.transitionName = "slide-left";
      } else {
        this.transitionName = "slide-right";
      }
    }
  },
  methods: {
    ...mapMutations(["reverseSetup"]),
    onEnter() {
      setTimeout(() => {
        this.reverseSetup();
      }, 200);
    }
  }
};
</script>

<style lang="scss">
:root {
  background-color: $color-app-background;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  width: 100vw;
  height: 100vh;
}

.slide-right-enter-active,
.slide-right-leave-active,
.slide-left-enter-active,
.slide-left-leave-active {
  will-change: transform;
  transition: all 250ms;
  width: 100%;
  position: absolute;
}
.slide-right-enter {
  opacity: 0;
  transform: translate3d(-100%, 0, 0);
}
.slide-right-leave-active {
  opacity: 0;
  transform: translate3d(100%, 0, 0);
}
.slide-left-enter {
  opacity: 0;
  transform: translate3d(100%, 0, 0);
}
.slide-left-leave-active {
  opacity: 0;
  transform: translate3d(-100%, 0, 0);
}
.setup-page {
  height: 100vh;
  width: 100vw;
  .swipe .van-swipe-item {
    color: #fff;
    font-size: 20px;
    line-height: 100vh;
    text-align: center;
    background-color: #39a9ed;
  }
}
</style>
