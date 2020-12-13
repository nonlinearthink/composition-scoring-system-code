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
        <van-icon name="success" class="action-button" />
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
      <van-field
        v-model="composition.compositionBody"
        type="textarea"
        placeholder="输入作文"
        class="composition-body"
        :border="false"
        autosize
      />
    </div>
    <van-action-sheet
      v-model="showSelectVisibility"
      :actions="visibilityList"
      close-on-click-action
      cancel-text="取消"
      close-on-popstate
      @select="changeVisibility"
    />
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      showSelectVisibility: false,
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
    }
  },
  beforeRouteLeave(to, from, next) {
    if (!this.isSave) next(false);
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
  margin-bottom: $blank-size;
}
.composition-visibility {
  margin-left: $blank-size;
  color: $color-primary;
  font-size: $text-normal;
}
</style>
