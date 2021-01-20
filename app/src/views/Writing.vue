<template>
  <div id="writing-page">
    <!-- 顶部导航栏 -->
    <van-nav-bar
      title="写作"
      placeholder
      left-arrow
      safe-area-inset-top
      @click-left="onRouteBack"
    >
      <!-- 提交按钮 -->
      <template #right>
        <van-icon
          name="success"
          class="piduoduo-actionbar-item"
          @click="onSubmit"
        />
      </template>
    </van-nav-bar>
    <!-- 写作状态栏 -->
    <div id="writing-statebar">
      <van-row type="flex" justify="space-between">
        <!-- 单词统计 -->
        <van-col>单词统计: {{ wordCount }} words</van-col>
        <!-- 编辑中提示 -->
        <van-col>{{ isEditing ? "编辑中" : "" }}</van-col>
      </van-row>
    </div>
    <!-- 作文编辑框 -->
    <van-field
      v-model="composition.compositionBody"
      type="textarea"
      placeholder="输入作文"
      :border="false"
      autosize
      @focus="onFocus"
      @blur="onBlur"
    />
    <!-- 内容未保存确认 -->
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
    <!-- 游客模式提交拦截 -->
    <van-dialog
      v-model="enableLoginRequire"
      title="请先登录"
      message="游客模式下不能提交作文"
      confirm-button-text="登录"
      close-on-click-overlay
      @confirm="onRouteChange('login')"
    />
    <!-- 提交确认 -->
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
      // 即将跳转的路由缓存，用于路由拦截
      toRouteCache: null,
      // 作文数据
      composition: null,
      // 是否进入提交选择
      isSubmit: false,
      // 是否进入退出选择
      enableQuitConfirm: false,
      // 是否提示登录
      enableLoginRequire: false,
      // 是否开启提交
      enableSubmit: false,
      // 路由护照，允许拥有护照的一切界面通行，用于路由拦截
      routePassport: false,
      // 是否在编辑中
      isEditing: false
    };
  },
  computed: {
    /**
     * @description 当前内容是否已缓存
     */
    isCache() {
      return (
        this.editing[this.editing.type].compositionBody ==
        this.composition.compositionBody
      );
    },
    /**
     * @description 字数统计
     * @return 可见性解析之后的字符串
     */
    wordCount() {
      let len;
      try {
        len = this.composition.compositionBody.match(/\b\w+\b/gm).length;
      } catch (e) {
        console.error(e);
        return 0;
      }
      return len;
    },
    // 获取本地存储的登录信息和编辑状态
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
    /**
     * @description 路由跳转
     * @param {String} route 路由字符串
     */
    onRouteChange(route) {
      this.$router.push(route);
    },
    /**
     * @description 保存缓存编辑
     */
    onConfirmCache() {
      // 保存编辑器
      this.$store.commit("doEditingCache", this.composition);
      // 跳转到之前缓存的路径
      this.$router.push(this.toRouteCache);
    },
    /**
     * @description 关闭缓存编辑
     */
    onCancelCache() {
      // 发放护照
      this.routePassport = true;
      // 跳转到之前缓存的路径
      this.$router.push(this.toRouteCache);
    },
    /**
     * @description 保存草稿编辑
     */
    onConfirmDraft() {
      this.onCreateComposition(1);
    },
    /**
     * @description 关闭草稿编辑
     */
    onCancelDraft() {
      // 设置不保存
      this.routePassport = true;
      // 跳转到之前缓存的路径
      this.$router.push(this.toRouteCache);
    },
    /**
     * @description 保存编辑
     */
    onConfirmSave() {
      if (this.editing.type == "cache") {
        this.onConfirmCache();
      } else {
        this.onConfirmDraft();
      }
    },
    /**
     * @description 关闭编辑
     */
    onCancelSave() {
      if (this.editing.type == "cache") {
        this.onCancelCache();
      } else {
        this.onCancelDraft();
      }
    },
    /**
     * @description 设置状态为编辑中
     */
    onFocus() {
      this.isEditing = true;
    },
    /**
     * @description 取消编辑中状态
     */
    onBlur() {
      this.isEditing = false;
    },
    /**
     * @description 提交
     */
    onSubmit() {
      // 文本判空
      if (this.composition.compositionBody.trim() == "") {
        this.$toast("不能为空");
        return;
      }
      // 非法字符检测
      if (
        this.composition.compositionBody.match(
          /[^\w\s`~!@#$%^&*()-_=+[{\]};:'",<.>/?]/
        ) != null
      ) {
        this.$toast("包含非法字符");
        return;
      }
      // 标点符号检测
      if (this.composition.compositionBody.match(/[!?.]$/g)) {
        this.enableSubmit = true;
      } else {
        this.$toast("缺少结尾标点符号");
      }
    },
    /**
     * @description 创建作文
     * @param {Number} type 创建的类型，即status
     */
    onCreateComposition(type) {
      // 创建一篇作文
      let composition = new Composition({
        compositionBody: this.composition.compositionBody,
        status: type,
        releaseDate: new Date().getTime()
      });
      // 发送创建作文请求
      this.axios.post("/composition", composition).then(res => {
        composition.compositionId = res.data.data.compositionId;
        // 同步到本地存储
        this.$store.commit("addComposition", composition);
        // 获取路由通行证
        this.routePassport = true;
        // 跳转
        this.$router.push("/manager");
        this.$toast("添加成功");
      });
    },
    /**
     * @description 更新作文
     */
    onUpdateComposition() {
      // 更新作文时间
      this.composition.releaseDate = new Date().getTime();
      // 发送更新作文请求
      this.axios
        .put(`/composition/${this.composition.compositionId}`, this.composition)
        .then(res => {
          console.log(res.data);
          // 同步到本地存储
          this.$store.commit("updateComposition", this.composition);
          // 获取路由通行证
          this.routePassport = true;
          // 跳转
          this.$router.push("/manager");
        })
        .catch(err => {
          console.error(err.response.data);
        });
    },
    onPublish() {
      // 获取路由通行证
      this.routePassport = true;
      // 设置需要编辑的作文
      this.$store.commit("editingPublish", new Composition(this.composition));
      // 跳转到发布编辑界面
      this.onRouteChange("/publish");
    },
    /**
     * @description 提交作文确认
     * @param {Number} type 创建的类型，即status
     */
    onSubmitConfirm(type) {
      if (this.isLogin) {
        if (this.editing.type == "cache") {
          this.onCreateComposition(type);
          // 清理本地缓存
          this.$store.commit("clearCache");
        } else {
          this.composition.status = 2;
          this.onUpdateComposition();
        }
      } else {
        this.enableLoginRequire = true;
      }
    }
  },
  /**
   * @description 路由拦截
   * @param {String} to 路由跳转目的地
   * @param {String} from 路由跳转源
   * @param {Function} next
   */
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
