<template>
  <div id="writing-page">
    <van-nav-bar
      title="写作"
      fixed
      placeholder
      left-arrow
      safe-area-inset-top
      @click-left="goBack"
    >
      <template #right>
        <van-icon name="success" class="action-button" @click="submit" />
      </template>
    </van-nav-bar>
    <div class="composition">
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
            <div class="composition-visibility" @click="selectVisibility">
              {{ visibilityText }}
            </div>
          </template>
        </van-field>
      </van-sticky>
      <van-cell-group title="写作">
        <van-field
          v-model="composition.compositionBody"
          type="textarea"
          placeholder="输入作文"
          class="composition-body"
          :border="false"
          autosize
        />
      </van-cell-group>
      <van-cell-group>
        <template #title>
          <van-row
            v-if="composition.description == '' && !showDescription"
            class="composition-extra"
            @click="startDescription"
          >
            <van-col>
              <van-icon name="add" class="add-description-icon" />
            </van-col>
            <van-col>
              <div class="icon-hint">
                添加描述
              </div>
            </van-col>
          </van-row>
          <span v-else>
            描述
          </span>
        </template>
        <van-field
          v-if="showDescription"
          v-model="composition.description"
          type="textarea"
          placeholder="输入作文描述"
          class="composition-description"
          :border="false"
          autosize
        />
      </van-cell-group>
      <div></div>
    </div>
    <van-action-sheet
      v-model="showSelectVisibility"
      :actions="visibilityList"
      close-on-click-action
      cancel-text="取消"
      close-on-popstate
      @select="changeVisibility"
    />
    <van-dialog
      v-model="editor.saveConfirm"
      title="是否缓存更改"
      message="缓存的内容在你下次进入写作页面的时候会自动加载"
      show-cancel-button
      confirm-button-text="缓存写作"
      cancel-button-text="退出写作"
      close-on-click-overlay
      @confirm="saveConfirm"
      @cancel="saveCancel"
      @close="cancelSave"
    />
    <van-popup v-model="howSelectPublish" position="bottom">
      <van-row>
        <van-col></van-col>
        <van-col></van-col>
      </van-row>
    </van-popup>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      showSelectVisibility: false,
      showDescription: false,
      showSelectPublish: false,
      passSave: false,
      toCache: null,
      visibilityList: [
        { id: 1, name: "私密" },
        { id: 2, name: "仅粉丝可见" },
        { id: 3, name: "公开" }
      ],
      composition: {
        title: "",
        visibility: 1,
        compositionBody: "",
        description: ""
      }
    };
  },
  computed: {
    visibilityText() {
      return this.visibilityList[this.composition.visibility - 1].name;
    },
    isSave() {
      if (this.composition.title != this.editor.composition.title) return false;
      if (this.composition.visibility != this.editor.composition.visibility)
        return false;
      if (
        this.composition.compositionBody !=
        this.editor.composition.compositionBody
      )
        return false;
      if (this.composition.description != this.editor.composition.description)
        return false;
      return true;
    },
    ...mapState(["editor"])
  },
  created() {
    // 加载缓存，注意不能使用引用赋值
    this.composition = Object.assign({}, this.editor.composition);
    if (this.composition.description != "") {
      this.showDescription = true;
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    selectVisibility() {
      this.showSelectVisibility = true;
    },
    changeVisibility(value) {
      this.showSelectVisibility = false;
      this.composition.visibility = value.id;
      this.$toast(`${value.name}`);
    },
    saveConfirm() {
      // 保存编辑器
      this.$store.commit("saveEditor", this.composition);
      // 关闭保存确认按钮
      this.$store.commit("closeSaveConfirm");
      // 跳转到之前缓存的路径
      this.$router.push(this.toCache);
    },
    saveCancel() {
      // 设置不保存
      this.passSave = true;
      // 关闭保存确认按钮
      this.$store.commit("closeSaveConfirm");
      // 跳转到之前缓存的路径
      this.$router.push(this.toCache);
    },
    cancelSave() {
      this.editor.saveConfirm = false;
    },
    submit() {
      this.showPopup = true;
    },
    startDescription() {
      this.showDescription = true;
    }
  },
  beforeRouteLeave(to, from, next) {
    this.toCache = to;
    if (this.passSave) next();
    else {
      if (!this.isSave) {
        next(false);
        setTimeout(() => {
          // 设置一个时间缓存，防止状态更新在路由刷新之前发生
          this.$store.commit("openSaveConfirm");
        }, 500);
      } else {
        next();
      }
    }
  }
};
</script>

<style lang="scss" scoped>
#writing-page {
  height: 100vh;
}
.action-button {
  font-size: $text-large;
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
