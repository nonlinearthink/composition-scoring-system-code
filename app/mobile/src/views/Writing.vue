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
      v-model="composition.compositionBody"
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
      @confirm="onRouteChange('login')"
    />
    <van-dialog
      v-model="enableSubmit"
      :title="editing.type == 'cache' ? '选择提交类型' : '确认评价'"
      :show-cancel-button="editing.type == 'cache'"
      confirm-button-text="生成评价"
      :cancel-button-text="editing.type == 'cache' ? '提交草稿' : ''"
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
      composition: null,
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
      return (
        this.editing[this.editing.type].compositionBody ==
        this.composition.compositionBody
      );
    },
    wordCount() {
      if (this.composition && this.composition.compositionBody) {
        return this.composition.compositionBody.match(/\b\w+\b/gm).length;
      } else {
        return 0;
      }
    },
    ...mapState(["isLogin", "editing"])
  },
  created() {
    // 加载缓存，注意不能使用引用赋值
    this.composition = Object.assign({}, this.editing[this.editing.type]);
  },
  methods: {
    /**
     * @description 路由返回
     */
    onRouteBack() {
      this.$router.go(-1);
    },
    onRouteChange(route) {
      this.routePassport = true;
      this.$router.push(route);
    },
    onConfirmCache() {
      // 保存编辑器
      this.$store.commit("doEditingCache", this.composition);
      // 跳转到之前缓存的路径
      this.$router.push(this.toRouteCache);
    },
    onCancelCache() {
      // 设置不保存
      this.routePassport = true;
      // 跳转到之前缓存的路径
      this.$router.push(this.toRouteCache);
    },
    onConfirmDraft() {
      this.onCreateComposition();
    },
    onCancelDraft() {
      // 设置不保存
      this.routePassport = true;
      // 跳转到之前缓存的路径
      this.$router.push(this.toRouteCache);
    },
    onConfirmSave() {
      if (this.editing.type == "cache") {
        this.onConfirmCache();
      } else {
        this.onConfirmDraft();
      }
    },
    onCancelSave() {
      if (this.editing.type == "cache") {
        this.onCancelCache();
      } else {
        this.onCancelDraft();
      }
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
    onCreateComposition(type) {
      let composition = new Composition({
        compositionBody: this.composition.compositionBody,
        status: type,
        releaseDate: new Date().getTime()
      });
      this.axios.post("/composition", composition).then(res => {
        composition.compositionId = res.data.data.compositionId;
        this.$store.commit("addComposition", composition);
        this.routePassport = true;
        this.$router.push("/manager");
        this.$toast("添加成功");
      });
    },
    onUpdateComposition() {
      this.axios
        .put(`/composition/${this.composition.compositionId}`, this.composition)
        .then(res => {
          console.log(res.data);
          this.$store.commit("updateComposition", this.composition);
          this.routePassport = true;
          this.$router.push("/manager");
        })
        .catch(err => {
          console.error(err.response.data);
        });
    },
    onSubmitConfirm(type) {
      if (this.isLogin) {
        if (this.editing.type == "cache") {
          if (type == 2) {
            this.composition.status += 1;
          }
          this.onCreateComposition(type);
          this.$store.commit("clearCache");
        } else {
          this.composition.status += 1;
          this.onUpdateComposition();
        }
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
