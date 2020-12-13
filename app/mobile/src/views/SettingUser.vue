<template>
  <div id="setting-user-page">
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
        is-link
      >
        <template #default>
          <div class="cell-value">
            <div v-if="item.name == 'avatar'">
              <van-image
                width="1rem"
                height="1rem"
                fit="cover"
                round
                :src="user.avatarUrl ? user.avatarUrl : layout.defaultAvatar"
              />
            </div>
            <div v-else-if="item.name == 'nickname'">
              {{ user.nickname }}
            </div>
            <div v-else-if="item.name == 'sex'">
              {{ sex }}
            </div>
            <div v-else-if="item.name == 'phone'">
              {{ user.phone ? user.phone : "未设置" }}
            </div>
            <div v-else-if="item.name == 'signature'">
              {{ user.signature ? user.signature : "未设置" }}
            </div>
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
        defaultAvatar: require("../assets/images/avatar.svg"),
        settingGroupItem: [
          {
            icon: "coupon-o",
            text: "头像",
            name: "avatar"
          },
          {
            icon: "envelop-o",
            text: "昵称",
            name: "nickname"
          },
          {
            icon: "setting-o",
            text: "性别",
            name: "sex"
          },
          {
            icon: "setting-o",
            text: "手机",
            name: "phone"
          },
          {
            icon: "setting-o",
            text: "个性签名",
            name: "signature"
          }
        ]
      }
    };
  },
  computed: {
    sex() {
      if (this.user.isMale === true) {
        return "男";
      } else if (this.user.isMale === false) {
        return "女";
      }
      return "未设置";
    },
    ...mapState(["user"])
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped>
.setting-group {
  .cell-value {
    margin-right: $blank-size/2;
  }
}
</style>
