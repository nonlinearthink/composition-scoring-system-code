<template>
  <div id="feedback-page">
    <van-nav-bar
      title="反馈"
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="onRouteBack"
    >
      <template #right>
        <div style="color: white;" @click="onSubmit">
          提交
        </div>
      </template>
    </van-nav-bar>
    <van-row style="padding: 1rem;">
      <span>
        选择反馈类型:
      </span>
      <span class="a-text" @click="select">{{ selectType.name }} </span>
    </van-row>
    <van-cell-group title="反馈内容">
      <van-field
        v-model="feedback.feedbackBody"
        type="textarea"
        placeholder="请描述你的问题，我们会尽快处理"
        :border="false"
        autosize
      />
    </van-cell-group>
    <van-action-sheet
      v-model="showSelect"
      :actions="actions"
      cancel-text="取消"
      close-on-popstate
      close-on-click-action
      @select="onSelected"
    />
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    const actions = [
      { name: "BUG反馈", value: 0 },
      { name: "改进意见", value: 1 }
    ];
    return {
      showSelect: false,
      actions,
      feedback: {
        feedbackBody: "",
        feedbackType: 0,
        time: null
      },
      selectType: actions[0]
    };
  },
  computed: {
    ...mapState(["isLogin"])
  },
  methods: {
    onRouteBack() {
      this.$router.go(-1);
    },
    select() {
      this.showSelect = true;
    },
    onSelected(value) {
      this.showSelect = false;
      this.selectType = value;
    },
    onCancel() {
      this.showSelect = false;
    },
    onSubmit() {
      this.feedback.feedbackType = this.selectType.value;
      if (!this.isLogin) {
        this.$toast("请先登录");
        return;
      }
      if (this.feedback.feedbackBody.trim() == "") {
        this.$toast("请输入内容");
        return;
      }
      this.axios
        .post(`/feedback`, this.feedback)
        .then(res => {
          console.log(res.data);
          this.$toast("添加成功");
          this.$router.push("/user");
        })
        .catch(err => console.error(err.response.data));
    }
  }
};
</script>

<style lang="scss" scoped>
.tips {
}
</style>
