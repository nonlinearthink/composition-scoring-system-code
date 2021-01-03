<template>
  <div id="login-page">
    <van-nav-bar
      title="登录"
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="$router.go(-1)"
    />
    <app-logo class="logo-t" />
    <van-form @submit="onSubmit">
      <van-field
        v-for="field in layout.fields"
        :key="field.name"
        v-model="form[field.name]"
        class="field"
        :type="field.type"
        :name="field.name"
        :placeholder="field.placeholder"
      >
        <template #left-icon>
          <van-icon
            :name="field.icon"
            class="icon"
            :size="layout.iconSize"
            @click="onClickLeftIcon(field)"
          />
        </template>
      </van-field>
      <div class="login-button">
        <van-button
          :text="layout.submitBotton.text"
          :color="layout.submitBotton.color"
          square
          block
          type="info"
          native-type="submit"
          size="large"
          :loading="layout.submitBotton.loading"
          :loading-text="
            layout.submitBotton.loading ? layout.submitBotton.loadingText : ''
          "
        />
      </div>
    </van-form>
    <van-row class="options">
      <van-col span="8" class="a-text" @click="onChangeRoute('/signup')">
        注册新用户
      </van-col>
      <van-col span="8" class="a-text" @click="onChangeRoute('/')">
        游客模式
      </van-col>
      <van-col
        span="8"
        class="a-text"
        @click="onChangeRoute('/forget-password')"
      >
        忘记密码
      </van-col>
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
        username: "",
        password: ""
      },
      layout: {
        iconSize: "1.2rem",
        fields: [
          {
            name: "username",
            type: "text",
            placeholder: "用户名",
            icon: "user-circle-o"
          },
          {
            name: "password",
            type: "password",
            placeholder: "密码",
            icon: "closed-eye",
            iconToggle: "eye-o"
          }
        ],
        submitBotton: {
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
    onSubmit() {
      // 开启加载动效
      this.layout.submitBotton.loading = true;
      this.axios
        .post("user/session", this.form)
        .then(res => {
          // 设置登录状态和token
          this.$store.commit("login");
          this.$store.commit("updateToken", res.data.data.token);
          this.$store.commit("updateUser", res.data.data.user);
          this.$toast("登录成功");
          // 界面跳转
          this.$router.push("/");
          console.log(res.data);
        })
        .catch(err => {
          // 关闭加载动效
          this.layout.submitBotton.loading = false;
          console.error(err.response.data);
          this.$toast.fail(err.response.data.msg);
        });
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
