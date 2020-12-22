<template>
  <div id="setting-main-page">
    <van-nav-bar
      title="设置"
      fixed
      placeholder
      left-arrow
      safe-area-inset-top
      @click-left="goBack"
    />
    <van-cell-group class="setting-group">
      <van-cell
        v-for="item in layout.settingGroupItem"
        :key="item.text"
        is-link
        size="large"
        center
        :to="item.to"
      >
        <template #title>
          {{ item.text }}
        </template>
      </van-cell>
      <van-cell size="large" center clickable>
        <van-row type="flex" justify="space-around">
          <van-col style="color: red; font-size: 1.2rem;" @click="onQuit()">
            退出账号
          </van-col>
        </van-row>
      </van-cell>
    </van-cell-group>
    <van-dialog
      v-model="enableQuitConfirm"
      title="退出登录"
      message="是否确认退出账号"
      show-cancel-button
      close-on-click-overlay
      @confirm="onConfirmQuit"
    />
  </div>
</template>

<script>
export default {
  data() {
    return {
      layout: {
        settingGroupItem: [
          {
            icon: "coupon-o",
            text: "账号资料",
            to: "/setting/user"
          },
          {
            icon: "envelop-o",
            text: "安全隐私",
            to: "/setting/security"
          },
          {
            icon: "setting-o",
            text: "关于批多多",
            to: "/setting/about"
          }
        ]
      },
      enableQuitConfirm: false
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    onQuit() {
      this.enableQuitConfirm = true;
    },
    onConfirmQuit() {
      localStorage.clear();
      this.$router.replace("/login");
    }
  }
};
</script>

<style lang="scss" scoped></style>
