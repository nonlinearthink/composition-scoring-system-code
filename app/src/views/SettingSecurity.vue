<template>
  <div id="setting-user-security">
    <van-nav-bar
      title="安全隐私"
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
        <template #label>
          <div v-if="item.name == 'email'">
            {{ safeEmail }}
          </div>
        </template>
        <template #title>
          {{ item.text }}
        </template>
      </van-cell>
    </van-cell-group>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      layout: {
        settingGroupItem: [
          {
            text: "更改注册邮箱",
            name: "email",
            to: "/setting/security/email"
          },
          {
            text: "更改密码",
            name: "password",
            to: "/setting/security/password"
          }
        ]
      }
    };
  },
  computed: {
    ...mapState(["user"]),
    safeEmail() {
      let str = this.user.email.split("@");
      return (
        str[0].substring(0, 3) +
        "*****" +
        str[1].substring(str[1].length - 4, str[0].length)
      );
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped></style>
