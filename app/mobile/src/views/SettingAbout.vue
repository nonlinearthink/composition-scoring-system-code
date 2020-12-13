<template>
  <div id="setting-user-about">
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
        size="large"
        center
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
            text: "版本号"
          },
          {
            text: "开发团队"
          },
          {
            text: "用户服务协议"
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
