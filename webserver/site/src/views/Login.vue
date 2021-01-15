<template>
  <div id="login-page">
    <a-form :form="form" class="login-form" @submit="onSubmit">
      <div class="logo">
        <piduoduo-logo class="logo-icon" />
        <div class="logo-text">批多多后台管理系统</div>
      </div>
      <a-form-item>
        <a-input
          v-decorator="[
            'adminName',
            {
              initialValue: admin.adminName,
              rules: [
                { required: true, message: 'Please input your username!' }
              ]
            }
          ]"
          placeholder="管理员名"
        >
          <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)" />
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-input
          v-decorator="[
            'password',
            {
              rules: [
                { required: true, message: 'Please input your Password!' }
              ]
            }
          ]"
          type="password"
          placeholder="密码"
        >
          <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)" />
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">
          登录
        </a-button>
      </a-form-item>
    </a-form>
    <div class="footer-info">
      Copyright © www.piduoduo.xyz, All Rights Reserved.<br />
      助力英文写作学习
    </div>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
import PiduoduoLogo from "@/assets/images/logo.svg";
export default {
  components: {
    PiduoduoLogo
  },
  computed: {
    ...mapState(["admin"])
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.axios
            .post("/admin/session", values)
            .then(res => {
              console.log(res);
              this.login({
                adminName: res.data.data.admin.adminName,
                token: res.data.data.token
              });
              this.$router.push("/");
            })
            .catch(err => {
              console.error(err.response.data);
              this.$message.error(err.response.data.msg, 1);
            });
        }
      });
    },
    ...mapMutations(["login"])
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  }
};
</script>

<style lang="scss" scoped>
#login-page {
  height: 100vh;
  width: 100vw;
  background: url(../assets/images/background.png);
  background-color: #0066cc;
  background-size: cover;
  display: flex;
  align-items: center;
}
.login-form {
  width: 20rem;
  background: white;
  margin: 0 auto;
  border-radius: 1rem;
  padding: 2rem;
}
.footer-info {
  width: 100%;
  padding-bottom: 1rem;
  position: absolute;
  bottom: 0;
  color: #c5c5c5;
  font-size: 0.5rem;
}
.ant-form-item:last-child {
  margin-bottom: 0;
}
.logo {
  text-align: center;
  padding-bottom: 1rem;
  .logo-icon {
    height: 4rem;
    height: 4rem;
    display: inline-block;
  }
  .logo-text {
    font-size: 1rem;
    text-align: center;
  }
}
</style>
