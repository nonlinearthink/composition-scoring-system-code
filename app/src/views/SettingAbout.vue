<template>
  <div id="setting-user-about">
    <van-nav-bar
      title="设置"
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
        :is-link="item.name == 'agreement'"
        @click="showAgreementPopup(item)"
      >
        <template #label>
          <div v-if="item.name == 'version'">
            {{ appVersion }}
          </div>
          <div v-else-if="item.name == 'team'">
            浙大城市学院 SE2020 G11小组
          </div>
        </template>
        <template #title>
          {{ item.text }}
        </template>
      </van-cell>
    </van-cell-group>
    <van-popup
      v-model="showAgreement"
      style="width: 100vw; height: 95vh"
      position="bottom"
      round
      close-on-popstate
    >
      <iframe
        name="userAgreement"
        width="100%"
        height="100%"
        src="user-agreement.html"
        frameborder="0"
      />
    </van-popup>
  </div>
</template>

<script>
export default {
  data() {
    return {
      layout: {
        settingGroupItem: [
          {
            text: "版本号",
            name: "version"
          },
          {
            text: "开发团队",
            name: "team"
          },
          {
            text: "用户服务协议",
            name: "agreement"
          }
        ]
      },
      showAgreement: false,
      appVersion: "1.3.0-beta"
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    showAgreementPopup(item) {
      if (item.name == "agreement") this.showAgreement = true;
    }
  },
  beforeRouteLeave(to, from, next) {
    if (this.showAgreement) {
      this.showAgreement = false;
      next(false);
      return;
    }
    next();
  }
};
</script>

<style lang="scss" scoped></style>
