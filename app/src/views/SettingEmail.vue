<template>
  <div id="setting-email-page">
    <van-nav-bar
      title="修改邮箱"
      fixed
      placeholder
      left-arrow
      safe-area-inset-top
      @click-left="onRouteBack"
    />
    <div class="content-wrapper">
      <div class="content">
        <van-row class="text">验证码将发送到邮箱 {{ safeEmail }}</van-row>
        <van-field
          v-model="verifyCode"
          placeholder="请填写验证码"
          :rules="[
            { required: true, message: '必须填写验证码' },
            {
              pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)+$/,
              message: '非法邮箱'
            }
          ]"
          class="input"
        >
          <template #right-icon>
            <div
              :class="enableSend ? 'a-text' : 'a-text-disabled'"
              @click="sendVerifyCode"
            >
              {{ layout.verifyText }}
            </div>
          </template>
        </van-field>
        <div style="margin: 2rem 1rem;">
          <van-button type="info" block @click="onSubmit">下一步</van-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      verifyCode: "",
      enableSend: true,
      timer: null,
      layout: {
        verifyText: "发送验证码",
        verifyTextCache: null
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
    /**
     * @description 路由返回
     */
    onRouteBack() {
      this.$router.go(-1);
    },
    onSubmit() {
      this.axios
        .post(`/user/email`, {
          username: this.user.username,
          oldEmail: this.user.email,
          oldVerifyCode: this.verifyCode
        })
        .then(res => {
          console.log(res.data);
          this.$router.push("/setting/security/new-email");
          this.$toast("验证成功");
        })
        .catch(err => {
          console.error(err.response.data);
          this.$toast(err.response.data.msg);
        });
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
            params: { email: this.user.email }
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
    }
  }
};
</script>

<style lang="scss" scoped>
.action-button {
  color: white;
}
#setting-email-page {
  background: white;
}
.content-wrapper {
  .content {
    .text {
      padding: 0 $blank-size;
      margin: $blank-size * 2 0;
    }
  }
}
</style>
