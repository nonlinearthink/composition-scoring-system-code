<template>
  <div class="signup-page">
    <van-nav-bar
      title="注册"
      placeholder
      left-arrow
      safe-area-inset-top
      @click-left="goBack"
    />
    <van-form
      ref="signupForm"
      validate-trigger="onBlur"
      :show-error="false"
      :submit-on-enter="false"
      @submit="onSubmit"
    >
      <van-field
        v-for="field in layout.fields"
        :key="field.name"
        v-model="form[field.name]"
        class="field"
        :rules="field.rules"
        :type="field.type"
        :name="field.name"
        :placeholder="field.placeholder"
        autosize
      >
        <template #left-icon>
          <van-icon
            :name="field.icon"
            class="icon"
            :size="layout.defaultIconSize"
          />
        </template>
        <template #right-icon>
          <div
            v-if="field.rightIcon && field.rightIcon.aText"
            :class="field.rightIcon.enable ? 'a-text' : 'a-text-disabled'"
            @click="onClickRightIcon(field)"
          >
            {{ field.rightIcon.aText }}
          </div>
        </template>
      </van-field>
      <van-checkbox v-model="layout.userAgreement.agree" class="agreement">
        同意《
        <div class="a-text" @click.stop="showAgreementPopup">
          {{ layout.userAgreement.aText }}
        </div>
        》
      </van-checkbox>
      <div class="signup-button">
        <van-button
          :text="layout.submitButton.text"
          :color="layout.submitButton.color"
          square
          block
          type="info"
          native-type="submit"
          size="large"
          :disabled="!layout.userAgreement.agree"
          :loading="layout.submitButton.loading"
          :loading-text="
            layout.submitButton.loading ? layout.submitButton.loadingText : ''
          "
        />
      </div>
    </van-form>
    <van-popup
      v-model="showAgreement"
      style="width: 100vw; height: 95vh"
      position="bottom"
      round
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
      timer: null,
      form: {
        username: "",
        password: "",
        passwordComfirm: "",
        phone: "",
        email: "",
        verifyCode: ""
      },
      layout: {
        defaultIconSize: "1.2rem",
        fields: [
          {
            name: "username",
            type: "text",
            placeholder: "登录用户名",
            icon: "user-circle-o",
            rules: [
              {
                required: true,
                message: "必须填写用户名"
              },
              {
                pattern: /^[A-Za-z0-9]*$/,
                message: "用户名只能包含英文字母和数字"
              }
            ]
          },
          {
            name: "password",
            type: "password",
            placeholder: "登录密码",
            icon: "closed-eye",
            rules: [
              {
                required: true,
                message: "必须填写密码"
              }
            ]
          },
          {
            name: "passwordComfirm",
            type: "password",
            placeholder: "确认登录密码",
            icon: "closed-eye",
            rules: [
              {
                required: true,
                message: "必须填写确认密码"
              },
              {
                validator: value => this.form["password"] == value,
                message: "密码不一致"
              }
            ]
          },
          {
            name: "phone",
            type: "text",
            placeholder: "电话",
            icon: "phone-o",
            rules: [
              {
                pattern: /^1[345789]\d{9}$|^$/,
                message: "非法手机号"
              }
            ]
          },
          {
            name: "email",
            value: "",
            type: "text",
            placeholder: "邮箱",
            icon: "envelop-o",
            rules: [
              {
                required: true,
                message: "必须填写邮箱"
              },
              {
                pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)+$/,
                message: "非法邮箱"
              }
            ]
          },
          {
            name: "verifyCode",
            value: "",
            type: "text",
            placeholder: "验证码",
            icon: "question-o",
            rightIcon: {
              enable: true,
              aText: "发送验证码",
              aTextCache: null
            }
          }
        ],
        userAgreement: {
          agree: false,
          aText: "用户服务协议"
        },
        submitButton: {
          text: "注册",
          color: "#02a7f0",
          loading: false,
          loadingText: "跳转到登录..."
        }
      },
      showAgreement: false
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    showAgreementPopup() {
      this.showAgreement = true;
    },
    onSubmit() {
      // 开启加载动效
      this.layout.submitButton.loading = true;
      let formData = { ...this.form };
      delete formData["passwordComfirm"];
      this.axios
        .post("user", formData)
        .then(res => {
          // 界面跳转
          this.$router.push("/login");
          this.$toast("注册成功");
          console.log(res.data);
        })
        .catch(err => {
          // 关闭加载特效
          this.layout.submitButton.loading = false;
          console.error(err.response.data);
        });
    },
    onClickRightIcon(field) {
      if (field.name == "verifyCode") {
        // 调用组件实例方法验证邮箱是否正确
        this.$refs.signupForm
          .validate("email")
          .then(() => {
            // 发送验证码
            this.sendVerifyCode(field);
          })
          .catch(reject => console.error(reject));
      }
    },
    updateState(field, count) {
      // 缓存不存在的时候创建缓存
      if (!field.rightIcon.aTextCache)
        field.rightIcon.aTextCache = new String(field.rightIcon.aText);
      // 根据缓存更新文字
      field.rightIcon.aText = `${field.rightIcon.aTextCache}(${count})`;
    },
    resetState(field) {
      // 把原本的缓存重新更新到文字
      field.rightIcon.aText = field.rightIcon.aTextCache;
      // 清空缓存
      field.rightIcon.aTextCache = null;
      // 清空计时器
      clearInterval(this.timer);
      this.timer = null;
      // 设置按钮可用
      field.rightIcon.enable = true;
    },
    sendVerifyCode(field) {
      // 设置最大倒计时
      const TIME_OUT = 60;
      if (!this.timer) {
        // 设置按钮不可用
        field.rightIcon.enable = false;
        this.axios
          .get("user/verify-code", { params: { email: this.form.email } })
          .then(res => {
            console.log(res.data);
          })
          .catch(err => {
            console.error(err.response.data);
          });
        // 创建计时器
        this.timer = (() => {
          // 属性闭包，用于保存倒计时
          let count = TIME_OUT;
          return setInterval(() => {
            if (count > 1 && count <= TIME_OUT) {
              // 计数减一，并且更新状态
              count--;
              this.updateState(field, count);
            } else {
              // 重置状态
              this.resetState(field);
            }
          }, 1000);
        })();
      }
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

<style lang="scss" scoped>
.field {
  font-size: $text-size-main;
  padding: $blank-size;
  .icon {
    margin-right: $blank-size;
  }
}

.agreement {
  margin: $blank-size;
}

.signup-button {
  margin: $blank-size;
  button {
    font-size: $text-size-main;
    border-radius: 0.5rem;
  }
}
</style>
