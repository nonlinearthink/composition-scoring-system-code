<template>
  <div id="app">
    <a-layout id="components-layout-demo-responsive">
      <a-layout-sider
        collapsible
        breakpoint="lg"
        collapsed-width="0"
        @collapse="onCollapse"
        width="200"
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
          <a-sub-menu key="2" title="系统推送">
            <a-menu-item key="2-1">
              <router-link to="/message/editor">新建推送</router-link>
            </a-menu-item>
            <a-menu-item key="2-2">
              <router-link to="/message/table">管理推送</router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="3" title="内容审查">
            <a-menu-item key="3-1">
              <router-link to="/review/normal">文本审查</router-link>
            </a-menu-item>
            <a-menu-item key="3-2">
              <router-link to="/review/report">举报处理</router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="4" title="用户管理">
            <a-menu-item key="4-1">
              <router-link to="/user/defense">用户申诉</router-link>
            </a-menu-item>
            <a-menu-item key="4-2">
              <router-link to="/user/infraction">违规处理</router-link>
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="5" title="产品管理">
            <a-menu-item key="5-1">
              <router-link to="/feedback">用户反馈</router-link>
            </a-menu-item>
            <a-menu-item key="5-2">
              <router-link to="/help">帮助手册</router-link>
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header :style="{ background: '#fff', padding: 0 }" />
        <a-layout-content :style="{ margin: '24px 16px 0' }">
          <div
            :style="{ padding: '24px', background: '#fff', minHeight: '360px' }"
          >
            <router-view />
          </div>
        </a-layout-content>
        <a-layout-footer style="textAlign: center">
          Ant Design ©2018 Created by Ant UED
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
import PiduoduoLogo from "@/assets/logo.svg";
export default {
  components: {
    PiduoduoLogo
  },
  data() {
    return {};
  },
  methods: {
    onCollapse(collapsed, type) {
      console.log(collapsed, type);
    },
    onBreakpoint(broken) {
      console.log(broken);
    }
  }
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

// #components-layout-demo-responsive .logo {
//   height: 32px;
//   background: rgba(255, 255, 255, 0.2);
//   margin: 16px;
// }

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
  .logo-icon {
    height: 2rem;
    height: 2rem;
    display: inline-block;
  }
  .logo-text {
    text-align: center;
  }
}
</style>
