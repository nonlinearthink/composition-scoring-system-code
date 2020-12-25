<template>
  <div id="publish-page">
    <!-- 顶部导航栏 -->
    <van-nav-bar
      title="编辑发布信息"
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
    <!-- 作文元信息编辑区 -->
    <div class="composition-meta">
      <van-sticky>
        <van-field
          ref="compositionTitle"
          v-model="composition.title"
          placeholder="输入发布标题"
          center
          size="large"
          class="composition-title"
        >
          <template #extra>
            <div class="composition-visibility" @click="enableSelectVisibility">
              {{ translate(composition.visibility) }}
            </div>
          </template>
        </van-field>
      </van-sticky>
      <van-cell-group title="作文描述">
        <van-field
          v-model="composition.description"
          type="textarea"
          placeholder="输入作文描述"
          class="composition-body"
          :border="false"
          autosize
        />
      </van-cell-group>
    </div>
    <van-action-sheet
      v-model="isSelectVisibility"
      :actions="visibilityList"
      close-on-click-action
      cancel-text="取消"
      close-on-popstate
      @select="onSelectVisibility"
    />
    <van-dialog
      v-model="submitConfirm"
      :title="composition.status == 4 ? '是否更新发布信息' : '是否发布'"
      confirm-button-text="确认"
      close-on-click-overlay
      @confirm="onPublish"
    />
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      isSelectVisibility: false,
      submitConfirm: false,
      passSave: false,
      toCache: null,
      visibilityList: [
        { value: 1, name: "私密" },
        { value: 2, name: "仅粉丝可见" },
        { value: 3, name: "公开" }
      ],
      composition: null
    };
  },
  computed: {
    ...mapState({ publish: state => state.editing.publish }),
    isUpdated() {
      if (
        this.composition.title == this.publish.title &&
        this.composition.description == this.publish.description &&
        this.composition.visibility == this.publish.visibility
      ) {
        return false;
      }
      return true;
    }
  },
  created() {
    // 加载需要发布的文章
    this.composition = Object.assign({}, this.publish);
  },
  methods: {
    /**
     * @description 翻译可见性
     * @param {Number} value 可见性
     * @return 可见性解析之后的字符串
     */
    translate(value) {
      if (value) {
        let visibility = this.visibilityList.find(
          visibility => visibility.value == value
        );
        return visibility.name;
      }
      return this.visibilityList[0].name;
    },
    /**
     * @description 路由跳转
     * @param {String} route 路由字符串
     */
    onRouteChange(route) {
      this.$router.push(route);
    },
    /**
     * @description 路由返回
     */
    onRouteBack() {
      this.$router.go(-1);
    },
    /**
     * @description 开启可见性选择
     */
    enableSelectVisibility() {
      this.isSelectVisibility = true;
    },
    /**
     * @description 选择可见性
     * @param {Object} visibility 可见性，见data中的visibilityList
     */
    onSelectVisibility(visibility) {
      this.isSelectVisibility = false;
      this.composition.visibility = visibility.value;
      this.$toast(`${visibility.name}`);
    },
    /**
     * @description 确认发布
     */
    onSubmit() {
      if (!this.composition.title) {
        this.$toast.fail("请输入发布标题");
        return;
      }
      if (this.isUpdated) {
        this.submitConfirm = true;
      } else {
        this.onRouteChange("/manager");
      }
    },
    /**
     * @description 发布作文
     */
    onPublish() {
      this.composition.releaseTime = new Date().getTime();
      this.composition.status = 4;
      this.axios
        .put(`/composition/${this.composition.compositionId}`, this.composition)
        .then(res => {
          console.log(res.data);
          this.$store.commit("updateComposition", this.composition);
          this.onRouteChange("/manager");
        })
        .catch(err => {
          console.error(err.response.data);
        });
    }
  }
};
</script>

<style lang="scss" scoped>
#publish-page {
  height: 100vh;
}
.composition-title {
  font-size: $text-large;
}
.composition-visibility {
  margin-left: $blank-size;
  color: $color-primary;
  font-size: $text-normal;
}
.composition-extra {
  .add-description-icon {
    color: $color-primary;
    font-size: $text-normal;
  }
  .icon-hint {
    color: $color-fade;
    font-size: $text-normal;
    margin-left: $blank-size/4;
  }
}
</style>
