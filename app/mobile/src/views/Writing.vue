<template>
  <div id="writing-page">
    <van-nav-bar
      title="写作"
      fixed
      placeholder
      left-arrow
      safe-area-inset-top
      @click-left="onRouteBack"
    >
      <template #right>
        <van-icon
          name="success"
          class="piduoduo-actionbar-item"
          @click="onSubmit"
        />
      </template>
    </van-nav-bar>
    <div id="writing-statebar">
      <van-row type="flex" justify="space-between">
        <van-col>单词统计: {{ wordCount }} words</van-col>
        <van-col>{{ isEditing ? "编辑中..." : "" }}</van-col>
      </van-row>
    </div>
    <van-field
      v-model="composition.body"
      type="textarea"
      placeholder="输入作文"
      :border="false"
      autosize
      @focus="onFocus"
      @blur="onBlur"
    />
    <van-dialog
      v-model="enableQuitConfirm"
      title="内容未保存"
      show-cancel-button
      confirm-button-text="保存"
      cancel-button-text="退出"
      close-on-click-overlay
      @confirm="onConfirmSave"
      @cancel="onCancelSave"
    />
    <van-dialog
      v-model="enableLoginRequire"
      title="请先登录"
      message="游客模式下不能提交作文"
      confirm-button-text="登录"
      close-on-click-overlay
      @confirm="onRouteToLogin"
    />
    <van-dialog
      v-model="enableSubmit"
      title="选择提交类型"
      show-cancel-button
      confirm-button-text="生成评价"
      cancel-button-text="提交草稿"
      close-on-click-overlay
      @confirm="onSubmitConfirm(2)"
      @cancel="onSubmitConfirm(1)"
    />
  </div>
</template>

<script>
import { mapState } from "vuex";
import Composition from "../assets/js/types/composition";
export default {
  data() {
    return {
      toRouteCache: null,
      composition: {
        body: ""
      },
      isSubmit: false,
      enableQuitConfirm: false,
      enableLoginRequire: false,
      enableSubmit: false,
      routePassport: false,
      isEditing: false
    };
  },
  computed: {
    // 当前内容是否已缓存
    isCache() {
      return this.editing.composition.body == this.composition.body;
    },
    wordCount() {
      if (this.composition && this.composition.body) {
        return this.composition.body.match(/\b\w+\b/gm).length;
      } else {
        return 0;
      }
    },
    ...mapState(["editing", "isLogin"])
  },
  watch: {
    enableQuitConfirm(value) {
      console.log(value);
    }
  },
  created() {
    // 加载缓存，注意不能使用引用赋值
    this.composition = Object.assign({}, this.editing.composition);
  },
  methods: {
    /**
     * @description 路由返回
     */
    onRouteBack() {
      this.$router.go(-1);
    },
    onRouteToLogin() {
      this.routePassport = true;
      this.$router.push("/login");
    },
    onConfirmSave() {
      // 保存编辑器
      this.$store.commit("updateEditingComposition", this.composition.body);
      // 跳转到之前缓存的路径
      this.$router.push(this.toRouteCache);
    },
    onCancelSave() {
      // 设置不保存
      this.routePassport = true;
      // 跳转到之前缓存的路径
      this.$router.push(this.toRouteCache);
    },
    onFocus() {
      this.isEditing = true;
    },
    onBlur() {
      this.isEditing = false;
    },
    onSubmit() {
      this.enableSubmit = true;
    },
    onSubmitConfirm(type) {
      if (this.isLogin) {
        this.routePassport = true;
        this.$store.commit(
          "addComposition",
          new Composition({
            compositionId: this.$store.state.compositions.length,
            compositionBody: this.composition.body,
            status: type,
            releaseDate: new Date().getTime()
          })
        );
        this.$router.push("/manager");
        this.$toast("添加成功");
      } else {
        this.enableLoginRequire = true;
      }
    }
  },
  beforeRouteLeave(to, from, next) {
    if (this.routePassport || this.isCache) {
      next();
    } else {
      this.toRouteCache = to;
      next(false);
      setTimeout(() => {
        // 设置一个时间缓存，防止状态更新在路由刷新之前发生
        this.enableQuitConfirm = true;
      }, 500);
    }
  }
};
</script>

<style lang="scss" scoped>
#writing-page {
  height: 100vh;
  background: white;
  #writing-statebar {
    margin: $blank-size/2 $blank-size;
    font-size: $text-small;
  }
}
</style>
