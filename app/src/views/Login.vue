<template>
  <div id="login-page">
    <van-nav-bar title="登录" fixed placeholder safe-area-inset-top/>
    <app-logo class="logo-t"/>
    <van-form @submit="onSubmit">
      <van-field
        v-for="field in form.fields"
        :key="field.name"
        v-model="field.value"
        class="field"
      >
        <template #left-icon>
          <van-icon
            :name="field.icon"
            class="icon"
            :size="form.iconSize"
            @click="onClickLeftIcon(field)"
          />
        </template>
        <template #input>
          <input
            :type="field.type"
            :name="field.name"
            :placeholder="field.placeholder"
            class="van-field__control"
            autocapitalize="off"
          />
        </template>
      </van-field>
      <div class="login-button">
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
    <van-row class="options">
      <van-col span="12" class="a-text" @click="onChangeRoute('/signup')"
        >注册新用户</van-col
      >
      <van-col span="12" class="a-text">忘记密码</van-col>
    </van-row>
  </div>
</template>

<script>
import AppLogo from "@/components/AppLogo.vue";
export default {
  components: {
    AppLogo
  },
  data() {
    return {
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
          color: "#02a7f0",
          loading: false,
          loadingText: "登录中..."
        }
      }
    };
  },
  methods: {
    onChangeRoute(to) {
      this.$router.push(to);
    },
    onClickLeftIcon(field) {
      // 密码显示和隐藏
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
  @include padding-horizontal($blank-size);
  .van-nav-bar {
    @include margin-horizontal(-$blank-size);
  }
}

.field {
  font-size: $text-size-main;
  padding: $blank-size;
  .icon {
    margin-right: $blank-size;
  }
}

.login-button {
  @include margin-vertical($blank-size);
  button {
    font-size: $text-size-main;
    border-radius: 0.5rem;
  }
}

.options {
  text-align: center;
}
</style>
