<template>
  <div id="app">
    <router-view />
    <!-- 标签栏，搭配van-tabbar适配器使用 -->
    <van-tabbar v-model="tabbar.active" route v-show="tabbar.enable">
      <van-tabbar-item
        :name="item.name"
        :to="item.route"
        v-for="item in tabbar.items"
        :key="item.name"
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
// van-tabbar适配器
let tabbarAdapter = {
  enable: false,
  active: "home",
  iconSizeDefault: "2rem",
  iconSizeWithText: "1.5rem",
  items: [
    { name: "home", route: "/home", icon: "home-o", text: "首页" },
    { name: "feed", route: "/feed", icon: "fire-o", text: "动态" },
    { name: "add", icon: "add", iconColor: "#1989fa" },
    { name: "chat", route: "/chat", icon: "chat-o", text: "消息", note: "3" },
    { name: "user", route: "/user", icon: "user-circle-o", text: "我的" }
  ]
};
export default {
  data() {
    return {
      tabbar: tabbarAdapter
    };
  },
  beforeCreate() {
    if (!localStorage.getItem("isLogin")) {
      this.$router.push("/login");
    }
  },
  watch: {
    $route: function(to) {
      let tabbarWhiteList = ["/home", "/feed", "/chat", "/user"];
      if (tabbarWhiteList.includes(to.path)) {
        this.tabbar.enable = true;
      } else {
        this.tabbar.enable = false;
      }
    }
  }
};
</script>

<style lang="scss">
:root {
  background-color: #f6f6f6;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
  a {
    font-weight: bold;
    color: #2c3e50;
    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
