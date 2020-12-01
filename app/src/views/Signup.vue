<template>
  <div class="signup-page">
    <van-nav-bar title="注册" fixed placeholder left-arrow safe-area-inset-top/>
    <van-form
      validate-trigger="onSubmit"
      :show-error="false"
      :submit-on-enter="false"
      @submit="onSubmit"
    >
      <van-field
        v-for="field in form.fields"
        :key="field.name"
        v-model="field.value"
        class="field"
        :rules="field.rules"
        :type="field.type"
        :name="field.name"
        :placeholder="field.placeholder"
        autosize
      >
        <template #left-icon>
          <van-icon :name="field.icon" class="icon" :size="form.iconSize" />
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
      <van-radio-group v-model="form.userAgreement.agree">
        <van-radio class="agreement" name="true" @click="onClickRadio">
          同意《
          <div class="a-text">
            用户服务协议
          </div>
          》
        </van-radio>
      </van-radio-group>
      <div class="signup-button">
        <van-button
          :text="form.submit.text"
          :color="form.submit.color"
          square
          block
          type="info"
          native-type="submit"
          size="large"
          :loading="form.submit.loading"
          :loading-text="form.submit.loading ? form.submit.loadingText : ''"
        />
      </div>
    </van-form>
  </div>
</template>

<script>
// import AppLogo from "@/components/AppLogo.vue";
export default {
  components: {
    // AppLogo
  },
  data() {
    return {
      form: {
        iconSize: "1.2rem",
        verification: {
          timer: null
        },
        fields: [
          {
            name: "username",
            value: "",
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
            value: "",
            type: "password",
            placeholder: "登录密码",
            icon: "closed-eye",
            rules: [
              {
                required: true,
                message: "必须填写密码"
              }
              // TODO
            ]
          },
          {
            name: "passwordComfirm",
            value: "",
            type: "password",
            placeholder: "确认登录密码",
            icon: "closed-eye",
            rules: [
              {
                required: true,
                message: "必须填写确认密码"
              },
              {
                validator: this.isSamePassword,
                message: "密码不一致"
              }
            ]
          },
          {
            name: "phone",
            value: "",
            type: "text",
            placeholder: "电话",
            icon: "phone-o",
            rules: [
              {
                pattern: /^1[34578]\d{9}$|^$/,
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
            name: "code",
            value: "",
            type: "text",
            placeholder: "验证码",
            icon: "question-o",
            rightIcon: {
              enable: true,
              aText: "发送验证码",
              aTextCache: null,
              onClick: "sendVerificationCode"
            }
          }
        ],
        userAgreement: {
          agree: "false",
          disabled: true,
          name: ""
        },
        submit: {
          text: "注册",
          color: "#02a7f0",
          loading: false,
          loadingText: "跳转到登录..."
        }
      }
    };
  },
  methods: {
    onClickLeftIcon(field) {
      // 密码显示和隐藏
      if (field.name == "password" || field.name == "passwordComfirm") {
        [field.icon, field.iconToggle] = [field.iconToggle, field.icon];
        field.type = field.type == "text" ? "password" : "text";
      }
    },
    onSubmit(values) {
      // 开启加载动效
      this.form.submit.loading = true;
      console.log(values);
      // 模拟登录耗时
      setTimeout(() => {
        // 设置登录状态
        localStorage.setItem("isLogin", true);
        // 界面跳转
        this.$router.push("/login");
      }, 3000);
    },
    onClickRadio() {
      this.form.userAgreement.agree =
        this.form.userAgreement.agree == "false" ? "true" : "false";
    },
    onClickRightIcon(field) {
      if (field.name == "code") this.sendVerificationCode(field);
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
      clearInterval(this.form.verification.timer);
      this.form.verification.timer = null;
      // 设置按钮可用
      field.rightIcon.enable = true;
    },
    sendVerificationCode(field) {
      // 设置最大倒计时
      const TIME_OUT = 60;
      if (!this.form.verification.timer) {
        // 设置按钮不可用
        field.rightIcon.enable = false;
        // 创建计时器
        this.form.verification.timer = (() => {
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
    },
    isSamePassword(value) {
      let field = this.form.fields.filter(field => "password" == field.name)[0];
      return field.value == value;
    }
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
