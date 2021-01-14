<template>
  <div id="setting-user-page">
    <van-nav-bar
      title="编辑个人资料"
      placeholder
      left-arrow
      safe-area-inset-top
      @click-left="onRouteBack"
    >
      <template #right>
        <div class="action-button" @click="onSubmit">保存</div>
      </template>
    </van-nav-bar>
    <van-form>
      <van-field
        v-model="form.nickname"
        label="昵称"
        placeholder="请填写昵称"
        :rules="[{ required: true, message: '昵称不能为空' }]"
      />
      <van-field name="radio" label="性别">
        <template #input>
          <van-radio-group v-model="form.isMale" direction="horizontal">
            <van-radio name="true" icon-size="1rem">
              <font-awesome-icon id="mars-icon" icon="mars" />
            </van-radio>
            <van-radio name="false" icon-size="1rem">
              <font-awesome-icon id="venus-icon" icon="venus" />
            </van-radio>
          </van-radio-group>
        </template>
      </van-field>
      <van-field
        v-model="form.phone"
        label="手机"
        placeholder="请填写手机号"
        :rules="[{ pattern: /^1[34578]\d{9}$|^$/, message: '非法手机号' }]"
      />
      <van-field
        v-model="form.signature"
        autosize
        label="个性签名"
        type="textarea"
        placeholder="请输入个性签名"
      />
    </van-form>
    <!-- 内容未保存确认 -->
    <van-dialog
      v-model="enableSubmit"
      title="是否保存修改"
      :show-cancel-button="enableSubmitCancel"
      confirm-button-text="确认"
      cancel-button-text="直接退出"
      close-on-click-overlay
      @confirm="onSubmitConfirm"
      @cancel="onSubmitCancel"
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
        nickname: "",
        isMale: null,
        signature: null,
        phone: null
      }
    };
  },
  computed: {
    isChange() {
      if (
        this.user.nickname != this.form.nickname ||
        this.user.signature != this.form.signature ||
        this.user.phone != this.form.phone
      ) {
        return true;
      } else if (
        (this.user.isMale == true && this.form.isMale == "false") ||
        (this.user.isMale == false && this.form.isMale == "true")
      ) {
        return true;
      } else {
        return false;
      }
    },
    ...mapState(["user"])
  },
  created() {
    this.form = Object.assign({}, this.user);
    if (this.user.isMale) {
      this.form.isMale = "true";
    } else {
      this.form.isMale = "false";
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
      if (this.isChange) {
        this.enableSubmit = true;
      }
    },
    onSubmitConfirm() {
      this.axios
        .put(`/user/account/details`, this.form)
        .then(res => {
          console.log(res);
          if (this.form.isMale == "true") {
            this.form.isMale = true;
          } else {
            this.form.isMale = false;
          }
          this.$store.commit("updateUser", this.form);
          this.routePassport = true;
          this.onRouteBack();
        })
        .catch(err => {
          console.error(err.response.data);
          this.$toast(err.response.data.msg);
        });
    },
    onSubmitCancel() {
      this.routePassport = true;
      this.onRouteBack();
    }
  },
  /**
   * @description 路由拦截
   * @param {String} to 路由跳转目的地
   * @param {String} from 路由跳转源
   * @param {Function} next
   */
  beforeRouteLeave(to, from, next) {
    if (this.routePassport || !this.isChange) {
      next();
    } else {
      next(false);
      setTimeout(() => {
        // 设置一个时间缓存，防止状态更新在路由刷新之前发生
        this.enableSubmit = true;
        this.enableSubmitCancel = true;
      }, 500);
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
#mars-icon {
  color: #1989fa;
}
#venus-icon {
  color: #ee0a24;
}
.action-button {
  color: white;
}
</style>
