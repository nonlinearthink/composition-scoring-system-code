<template>
  <div id="setting-user-security">
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
      >
        <template #label>
          <div v-if="item.name == 'email'">
            {{ user.email }}
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
            name: "email"
          },
          {
            text: "更改密码",
            name: "password"
          }
        ]
      }
    };
  },
  computed: {
    ...mapState(["user"])
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped></style>
