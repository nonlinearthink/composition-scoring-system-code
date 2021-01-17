<template>
  <div id="publish-page">
    <!-- 顶部导航栏 -->
    <van-nav-bar
      title="编辑信息"
      placeholder
      left-arrow
      safe-area-inset-top
      @click-left="$router.go(-1)"
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
        />
      </van-sticky>
      <van-field
        v-model="composition.description"
        type="textarea"
        placeholder="输入作文描述"
        class="composition-body"
        :border="false"
        autosize
      />
      <van-cell
        title="谁可以看"
        is-link
        center
        :value="translate(composition.visibility)"
        @click="enableSelectVisibility"
      >
        <template #icon>
          <van-icon
            name="friends-o"
            color="#02a7f0"
            size="1rem"
            :style="{ marginRight: '0.5rem' }"
          />
        </template>
      </van-cell>
      <van-cell-group title="评价报告" :border="false">
        <van-tabs
          v-model="displayMode"
          :ellipsis="false"
          swipeable
          animated
          color="#02a7f0"
        >
          <van-tab title="单词建议" style="padding: 0 1rem;">
            <!-- eslint-disable-next-line vue/no-v-html -->
            <van-row v-html="wordErrorModel"></van-row>
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
            <div
              v-if="grammerErrorModel.length == 0"
              :style="{
                textAlign: 'center',
                marginTop: '1rem',
                color: 'orange'
              }"
            >
              🎉这就是学霸的世界吗？爱了爱了
            </div>
          </van-tab>
          <van-tab title="评分" style="padding: 0 1rem;">
            <v-chart :options="radar"></v-chart>
            <van-row
              style="font-size: 1.2rem; color: #888;"
              type="flex"
              justify="center"
            >
              总分:
              <span style="color: orange; margin-left: 0.25rem;">
                {{ composition.score }}
              </span>
            </van-row>
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
import errorUtils from "../assets/js/common/errorUtils";
import { mapState } from "vuex";
import ECharts from "vue-echarts";
import "echarts/lib/chart/radar";
import "echarts/lib/component/radar";
import "echarts/lib/component/tooltip";
export default {
  components: {
    "v-chart": ECharts
  },
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
      grammerErrorModel: null,
      wordErrorModel: null,
      displayMode: 0,
      radar: null
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
      .get(`/error/${this.composition.compositionId}/0`)
      .then(res => {
        console.log(res.data);
        this.wordErrorModel = this.parseWordError(
          this.composition.compositionBody,
          res.data.data.JSONArray
        );
      })
      .catch(err => {
        console.error(err.response.data);
        this.$toast("拼写错误数据不完整");
      });
    this.axios
      .get(`/error/${this.composition.compositionId}/1`)
      .then(res => {
        console.log(res.data);
        this.grammerErrorModel = this.parseGrammerError(
          this.composition.compositionBody,
          res.data.data.JSONArray
        );
      })
      .catch(err => {
        console.error(err.response.data);
        this.$toast("语法错误数据不完整");
      });
    this.axios
      .get(`/composition/${this.composition.compositionId}`)
      .then(res => {
        console.log(res.data);
        let data = res.data.data.compositionCountModel;
        this.radar = {
          radar: {
            indicator: [
              { text: "拼写", max: 100 },
              { text: "语法", max: 100 },
              { text: "流畅度", max: 100 },
              { text: "长度", max: 100 },
              { text: "丰富度", max: 100 }
            ],
            radius: 80,
            center: ["50%", "50%"]
          },
          tooltip: {
            trigger: "axis"
          },
          series: [
            {
              type: "radar",
              areaStyle: {},
              data: [
                {
                  value: [
                    data.wordScore,
                    data.grammarScore,
                    data.sentenceFluencyScore,
                    data.lengthScore,
                    data.richnessScore
                  ],
                  name: "各个评分项"
                }
              ],
              tooltip: {
                trigger: "item",
                position: ["50%", "50%"]
              }
            }
          ],
          animationDuration: 1000
        };
      })
      .catch(err => {
        console.error(err.response.data);
      });
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
    ...errorUtils
  }
};
</script>

<style lang="scss" scoped>
#publish-page {
  height: 100vh;
  background: white;
}
.composition-title {
  font-size: $text-normal;
  margin-bottom: 1px;
}
.composition-visibility {
  margin-left: $text-normal / 4;
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
.echarts {
  width: calc(100vw - 2rem);
  height: 35vh;
}
.error-text {
  text-decoration: line-through;
  text-decoration-color: red;
}
</style>
