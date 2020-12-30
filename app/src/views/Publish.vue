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
      <van-cell-group title="评价结果">
        <van-tabs v-model="displayMode" swipeable color="#02a7f0">
          <van-tab title="原文" style="padding: 0 1rem;">
            <!-- eslint-disable-next-line vue/no-v-html -->
            <van-row v-html="renderText(composition.compositionBody)"></van-row>
          </van-tab>
          <van-tab title="语法建议" style="padding: 0 1rem;">
            <div v-for="paragraph in grammerErrorModel" :key="paragraph.paraId">
              <div
                v-for="sentence in paragraph.paragraph.filter(
                  item => item.error
                )"
                :key="sentence.senId"
                :style="{ margin: '0.5rem 0' }"
              >
                原文:
                <span v-if="sentence.error" @click.stop="changeDisplaySentence">
                  {{ sentence.origin }}
                </span>
                <br />
                修改建议:
                <span
                  v-if="sentence.error"
                  style="color: orange;"
                  @click.stop="changeDisplaySentence"
                >
                  {{ sentence.advice }}
                </span>
              </div>
            </div>
          </van-tab>
        </van-tabs>
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
      composition: null,
      testData: [],
      grammerErrorModel: null
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
    this.axios
      .get(`/error/${this.composition.compositionId}`)
      .then(res => {
        console.log(res.data);
        this.testData = res.data.data.JSONArray;
        this.grammerErrorModel = this.parseGrammerError(
          this.composition.compositionBody,
          res.data.data.JSONArray
        );
      })
      .catch(err => console.error(err.response.data));
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
    },
    renderText(origin) {
      // 文本分词
      let paragraphList = origin.split("\n");
      let result = "";
      for (let i = 0; i < paragraphList.length; i++) {
        result += "<p>" + paragraphList[i] + "</p>";
      }
      return result;
    },
    mergeSentence(splitedText) {
      // 合并预测结果，注意标点符号和普通单词的区别
      let mergedText = splitedText.reduce((total, item) => {
        return item.charAt(item.length - 1).match(/[,.!?]/) == null
          ? total + " " + item
          : total + item;
      });
      // 处理最后一个单词或标点后无空格的问题
      mergedText += " ";
      // 首字母替换成大写
      return mergedText.replace(mergedText[0], mergedText[0].toUpperCase());
    },
    parseGrammerError(text, errorInfo) {
      // 对数组执行深拷贝
      let correctResult = JSON.parse(JSON.stringify(errorInfo));
      // 对数组进行排序
      correctResult.sort((item1, item2) => item1.sen_id - item2.sen_id);
      // 文章分段
      let paragraphList = text.split("\n");
      let currentSentence = 0; // 记录句子ID
      let result = []; // 存放结果
      for (let paraId = 0; paraId < paragraphList.length; paraId++) {
        // 匹配分隔符
        let splitTokens = paragraphList[paraId].match(/[.?!]/g);
        // 按分隔符分句和格式化
        let sentenceList = paragraphList[paraId]
          .split(/[.?!]/)
          .map(
            (sentence, index) => `${sentence}${splitTokens[index]}`.trim() + " "
          );
        let paragraph = []; // 存放段落
        for (let senId = 0; senId < sentenceList.length - 1; senId++) {
          // 如果句子ID和纠错句子ID匹配上，则进入处理
          if (
            correctResult.length > 0 &&
            currentSentence == correctResult[0].sen_id
          ) {
            // 合并句子
            let advice = this.mergeSentence(correctResult[0].pred);
            // 判断是否相同
            if (advice != sentenceList[senId]) {
              // 不同则添加错误信息
              paragraph.push({
                senId: currentSentence,
                origin: sentenceList[senId],
                error: true,
                advice
              });
            } else {
              // 相同则直接记录
              paragraph.push({
                senId: currentSentence,
                origin: sentenceList[senId]
              });
            }
            // 删除一条纠错记录
            correctResult.splice(0, 1);
          } else {
            // 直接记录
            paragraph.push({
              senId: currentSentence,
              origin: sentenceList[senId]
            });
          }
          // 更新当前的句子ID
          currentSentence++;
        }
        // 添加段落
        result.push({ paraId, paragraph });
      }
      // 返回结果
      return result;
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
