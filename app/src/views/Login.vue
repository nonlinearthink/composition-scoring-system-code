<template>
  <div id="login-page">
    <van-nav-bar title="登录" fixed placeholder />
    <div id="logo">
      <van-image
        :src="require('../assets/images/icon.svg')"
        fit="scale-down"
        width="6rem"
      />
      <div class="app-name">
        批多多
      </div>
    </div>
    <van-form @submit="onSubmit">
      <van-field
        v-for="field in form.fields"
        :key="field.name"
        v-model="field.value"
        class="field"
        :name="field.name"
        :type="field.type"
        :placeholder="field.placeholder"
      >
        <template #left-icon>
          <van-icon
            :name="field.icon"
            class="icon"
            :size="form.iconSize"
            @click="onClickLeftIcon(field)"
          />
        </template>
      </van-field>
      <div class="login-button">
        <van-button
          :text="form.submit.text"
          color="#02A7F0"
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
    <van-row class="tool">
      <van-col span="12" class="link">注册新用户</van-col>
      <van-col span="12" class="link">忘记密码</van-col>
    </van-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      icon: {},
      form: {
        iconSize: "1.2rem",
        fields: [
          {
            name: "username",
            value: "",
            type: "text",
            placeholder: "用户名",
            icon: "user-circle-o"
          },
          {
            name: "password",
            value: "",
            type: "password",
            placeholder: "密码",
            icon: "closed-eye",
            iconToggle: "eye-o"
          }
        ],
        submit: {
          text: "登录",
          loading: false,
          loadingText: "登录中..."
        }
      }
    };
  },
  methods: {
    onClickLeftIcon(field) {
      // 密码加密解密处理
      if (field.name == "password") {
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
        this.$router.push("/");
      }, 3000);
    }
  }
};
</script>

<style lang="scss" scoped>
#login-page {
  padding: 0 $blank-default;
  .van-nav-bar {
    @include margin-horizontal(-$blank-default);
  }
}

#logo {
  @include margin-vertical($blank-default);
  text-align: center;
  .app-name {
    font-size: 1.2rem;
  }
}

.tool {
  text-align: center;
  .link {
    color: $theme-text-color;
  }
}

.field {
  font-size: 1rem;
  padding: $blank-default;
  .icon {
    margin-right: $blank-default;
  }
}

.login-button {
  @include margin-vertical($blank-default);
  button {
    font-size: 1.2rem;
    border-radius: 0.5rem;
  }
}
</style>
