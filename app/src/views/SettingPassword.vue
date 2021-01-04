<template>
  <div id="setting-password-page">
    <van-nav-bar
      title="修改密码"
      placeholder
      left-arrow
      safe-area-inset-top
      @click-left="onRouteBack"
    >
      <template #right>
        <div class="action-button" @click="onSubmit">修改</div>
      </template>
    </van-nav-bar>
    <van-form ref="passwordForm" @submit="onSubmitForm">
      <van-field
        v-model="form.oldPassword"
        label="旧密码"
        placeholder="请填写旧密码"
        type="password"
        :rules="[{ required: true, message: '必须填写旧密码' }]"
      />
      <van-field
        v-model="form.newPassword"
        label="新密码"
        placeholder="请填写新密码"
        type="password"
        :rules="[{ required: true, message: '必须填写新密码' }]"
      />
      <van-field
        v-model="form.passwordConfirm"
        label="确认新密码"
        placeholder="请确认新密码"
        type="password"
        :rules="[
          { required: true, message: '必须确认新密码' },
          {
            validator: value => form.newPassword == value,
            message: '密码不一致'
          }
        ]"
      />
    </van-form>
    <!-- 内容未保存确认 -->
    <van-dialog
      v-model="enableSubmit"
      title="确认修改"
      confirm-button-text="确认"
      close-on-click-overlay
      @confirm="onSubmitConfirm"
    />
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      enableSubmitCancel: false,
      enableSubmit: false,
      routePassport: false,
      form: {
        newPassword: "",
        oldPassword: "",
        passwordConfirm: ""
      }
    };
  },
  computed: {
    ...mapState(["user"])
  },
  methods: {
    /**
     * @description 路由返回
     */
    onRouteBack() {
      this.$router.go(-1);
    },
    onSubmit() {
      this.$refs.passwordForm.submit();
    },
    onSubmitForm() {
      this.enableSubmit = true;
    },
    onSubmitConfirm() {
      this.axios
        .put(`/user/account/password`, this.form)
        .then(res => {
          console.log(res);
          this.$router.push("/login");
        })
        .catch(err => {
          console.error(err.response.data);
          this.$toast(err.response.data.msg);
        });
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
.action-button {
  color: white;
}
</style>
