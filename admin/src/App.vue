<template>
  <div id="app">
    <a-layout v-if="!isLoginPage">
      <a-layout-sider
        collapsible
        breakpoint="lg"
        collapsed-width="0"
        width="200"
        :style="{ height: '100vh' }"
      >
        <div class="logo">
          <piduoduo-logo class="logo-icon" />
          <div class="logo-text">批多多后台管理系统</div>
        </div>
        <a-menu
          theme="dark"
          mode="inline"
          :default-selected-keys="['1']"
          :inlineIndent="0"
        >
          <a-menu-item key="1">
            <router-link to="/">首页</router-link>
          </a-menu-item>
          <a-sub-menu key="2" title="推送管理">
            <a-menu-item key="2-1">
              <router-link to="/article">系统推送</router-link>
            </a-menu-item>
            <a-menu-item key="2-2">
              <router-link to="/message">系统消息</router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="3" title="内容审查">
            <a-menu-item key="3-1">
              <router-link to="/review/composition">作文审查</router-link>
            </a-menu-item>
            <a-menu-item key="3-2">
              <router-link to="/review/comment">评论审查</router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="4" title="举报处理">
            <a-menu-item key="4-1">
              <router-link to="/report/composition">文章举报</router-link>
            </a-menu-item>
            <a-menu-item key="4-2">
              <router-link to="/report/comment">评论举报</router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="5" title="用户管理">
            <a-menu-item key="5-1">
              <router-link to="/user/defense">封号申诉</router-link>
            </a-menu-item>
            <a-menu-item key="5-2">
              <router-link to="/user/infraction">违规处理</router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="6" title="产品管理">
            <a-menu-item key="6-1">
              <router-link to="/feedback">用户反馈</router-link>
            </a-menu-item>
            <a-menu-item key="6-2">
              <router-link to="/help">帮助手册</router-link>
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header :style="{ background: '#fff', padding: 0 }">
          <a-row type="flex" justify="end">
            <a-col class="header-action-bar-item">
              <a-icon type="user" /> {{ admin.adminName }}
            </a-col>
            <a-col class="header-action-bar-item" @click="onLogout">
              <a-icon type="poweroff" />
            </a-col>
          </a-row>
        </a-layout-header>
        <a-layout-content :style="{ margin: '24px 16px 0' }">
          <div :style="{ padding: '24px', background: '#fff', height: '100%' }">
            <router-view />
          </div>
        </a-layout-content>
        <a-layout-footer class="footer-information">
          Copyright © www.piduoduo.xyz, All Rights Reserved.<br />
          助力英文写作学习
        </a-layout-footer>
      </a-layout>
    </a-layout>
    <div v-else>
      <router-view />
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import PiduoduoLogo from "@/assets/images/logo.svg";
export default {
  components: {
    PiduoduoLogo
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(["admin", "isLogin"]),
    isLoginPage() {
      return this.$route.path == "/login";
    }
  },
  created() {
    if (!this.isLogin) {
      this.$router.push("/login");
    }
  },
  methods: {
    ...mapMutations(["logout"]),
    onLogout() {
      this.logout();
      this.$router.replace("/login");
    }
  },
  watch: {}
};
</script>

<style lang="scss">
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

<style lang="scss" scoped>
.logo {
  text-align: left;
  color: white;
  padding: 1rem 0;
  .logo-icon {
    height: 2.5rem;
    height: 2.5rem;
    display: inline-block;
  }
  .logo-text {
    font-size: 1.2rem;
    text-align: center;
  }
}
.header-action-bar-item {
  font-size: 1.1rem;
  padding: 0 1.5rem;
  &:hover {
    background: #f6f6f6;
    cursor: pointer;
  }
}
.footer-information {
  text-align: center;
  color: #9e9d9d;
}
</style>
