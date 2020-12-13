<template>
  <div id="setting-main-page">
    <van-nav-bar
      title="注册"
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
    </van-cell-group>
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
      }
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped></style>
