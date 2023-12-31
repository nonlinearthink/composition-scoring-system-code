<template>
  <div class="forget-password-page">
    <van-nav-bar
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      title="重置邮箱"
      @click-left="onRouteBack"
      @click-right="onSubmit"
    >
      <template #right>
        <van-icon name="success" color="white" size="1.2rem" />
      </template>
    </van-nav-bar>
    <van-form ref="forgetPasswordForm" validate-trigger="onBlur">
      <van-field
        v-model="form.email"
        label="邮箱"
        placeholder="请填写新邮箱"
        name="email"
        :rules="[
          { required: true, message: '必须填写新邮箱' },
          {
            pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)+$/,
            message: '非法邮箱'
          }
        ]"
      />
      <van-field
        v-model="form.verifyCode"
        label="验证码"
        placeholder="请填写验证码"
        name="verifyCode"
        :rules="[{ required: true, message: '必须填写验证码' }]"
      >
        <template #right-icon>
          <div
            :class="enableSend ? 'a-text' : 'a-text-disabled'"
            @click="onSend"
          >
            {{ layout.verifyText }}
          </div>
        </template>
      </van-field>
    </van-form>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      form: {
        email: null,
        verifyCode: ""
      },
      enableSend: true,
      timer: null,
      layout: {
        verifyText: "发送验证码",
        verifyTextCache: null
      }
    };
  },
  computed: {
    ...mapState(["user"])
  },
  methods: {
    onRouteBack() {
      this.$router.go(-1);
    },
    onSend() {
      // 调用组件实例方法验证邮箱是否正确
      this.$refs.forgetPasswordForm
        .validate("email")
        .then(() => {
          // 发送验证码
          this.sendVerifyCode();
        })
        .catch(reject => console.error(reject));
    },
    updateState(count) {
      // 缓存不存在的时候创建缓存
      if (!this.layout.verifyTextCache)
        this.layout.verifyTextCache = new String(this.layout.verifyText);
      // 根据缓存更新文字
      this.layout.verifyText = `${this.layout.verifyTextCache}(${count})`;
    },
    resetState() {
      // 把原本的缓存重新更新到文字
      this.layout.verifyText = this.layout.verifyTextCache;
      // 清空缓存
      this.layout.verifyTextCache = null;
      // 清空计时器
      clearInterval(this.timer);
      this.timer = null;
      // 设置按钮可用
      this.enableSend = true;
    },
    sendVerifyCode() {
      // 设置最大倒计时
      const TIME_OUT = 60;
      if (!this.timer) {
        // 设置按钮不可用
        this.enableSend = false;
        this.axios
          .get("/user/mail/verify-code", {
            params: { email: this.form.email }
          })
          .then(res => {
            console.log(res.data);
            // 创建计时器
            this.timer = (() => {
              // 属性闭包，用于保存倒计时
              let count = TIME_OUT;
              return setInterval(() => {
                if (count > 1 && count <= TIME_OUT) {
                  // 计数减一，并且更新状态
                  count--;
                  this.updateState(count);
                } else {
                  // 重置状态
                  this.resetState();
                }
              }, 1000);
            })();
          })
          .catch(err => {
            console.error(err.response.data);
            this.$toast(err.response.data.msg);
            this.enableSend = true;
          });
      }
    },
    onSubmit() {
      this.axios
        .put("/user/email", {
          username: this.user.username,
          newEmail: this.form.email,
          newVerifyCode: this.form.verifyCode
        })
        .then(res => {
          // 界面跳转
          console.log(res.data);
          this.$router.push("/user");
          this.$toast("更换成功");
        })
        .catch(err => {
          // 关闭加载特效
          console.error(err.response.data);
          this.$toast(err.response.data.msg);
        });
    }
  }
};
</script>

<style lang="scss" scoped></style>
