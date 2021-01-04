<template>
  <div id="writing-manager-page">
    <!-- 顶部导航栏 -->
    <van-nav-bar title="作文" placeholder safe-area-inset-top :border="false">
      <template #right>
        <van-icon
          name="plus"
          class="piduoduo-action-button"
          @click="onCreate"
        />
      </template>
    </van-nav-bar>
    <!-- 主体部分，tab组 -->
    <van-tabs
      v-model="activeTab"
      animated
      sticky
      background="#02a7f0"
      title-inactive-color="white"
      title-active-color="white"
    >
      <!-- 单个tab的主体内容 -->
      <van-tab v-for="tab in layout.tabs" :key="tab.state" :title="tab.title">
        <van-pull-refresh v-model="loading" @refresh="onRefresh">
          <div class="main-container">
            <!-- 翻转卡片列表 -->
            <rotate-card
              v-for="item in layout.compositions"
              :key="item.compositionId"
              class="composition-card"
            >
              <!-- 卡片正面 -->
              <div class="front-side" @click="onEnterEditing(item)">
                <!-- 标题 -->
                <van-row v-if="item.title" class="title">
                  <van-col>
                    {{ item.title }}
                  </van-col>
                </van-row>
                <van-row>
                  <!-- 更新时间 -->
                  <van-col class="update-time">
                    {{ timeIntervalString(item.releaseTime) }}
                  </van-col>
                  <!-- 状态码标签 -->
                  <van-col class="piduoduo-tag first-tag">
                    <van-tag plain :color="layout.statusColor[item.status - 1]">
                      {{ translateStatus(item.status) }}
                    </van-tag>
                  </van-col>
                  <!-- 可见性标签 -->
                  <van-col class="piduoduo-tag">
                    <van-tag
                      v-show="item.visibility"
                      plain
                      :color="layout.visibilityColor[item.visibility - 1]"
                    >
                      {{ translateVisibility(item.visibility) }}
                    </van-tag>
                  </van-col>
                </van-row>
                <!-- 正文(作文)概述 -->
                <van-row class="brief">{{ item.compositionBody }} </van-row>
                <van-row type="flex" justify="space-between" class="tool-bar">
                  <!-- 评分 -->
                  <van-col>
                    <div v-show="item.score">
                      {{ item.score }}/{{ totalScore }}
                    </div>
                  </van-col>
                  <!-- 删除按键 -->
                  <van-col v-if="item.status != 2" class="action-button-icon">
                    <van-icon name="delete" @click.stop="onDelete(item)" />
                  </van-col>
                </van-row>
              </div>
              <!-- 反面 -->
              <template #reverse>
                <div class="back-side">
                  <van-row class="row">
                    <van-col span="8">亮点内容</van-col>
                    <van-col span="8">
                      亮点词:
                      <span class="highlight">
                        {{ 0 + "个" }}
                      </span>
                    </van-col>
                    <van-col span="8">
                      亮点句:
                      <span class="highlight">
                        {{ 0 + "个" }}
                      </span>
                    </van-col>
                  </van-row>
                  <van-row class="row">
                    <van-col span="8">错误内容</van-col>
                    <van-col span="8">
                      拼写错误:
                      <span class="error">{{ 0 + "个" }}</span>
                    </van-col>
                    <van-col span="8">
                      语法错误:
                      <span class="error">
                        {{ 0 + "个" }}
                      </span>
                    </van-col>
                  </van-row>
                  <van-row class="row">
                    <van-col span="8">高频错误</van-col>
                    <van-col span="16"></van-col>
                  </van-row>
                </div>
              </template>
            </rotate-card>
          </div>
        </van-pull-refresh>
      </van-tab>
    </van-tabs>
    <van-dialog
      v-model="enableDeleteConfirm"
      title="删除确认"
      show-cancel-button
      confirm-button-text="确认"
      cancel-button-text="取消"
      close-on-click-overlay
      @confirm="onApplyDelete"
    />
  </div>
</template>

<script>
import RotateCard from "@/components/RotateCard";
import { mapState } from "vuex";
import dateUtils from "../assets/js/common/dateUtils";
import Composition from "../assets/js/types/composition";
export default {
  components: {
    RotateCard
  },
  data() {
    return {
      activeTab: "", // 当前选中的tab
      enableDeleteConfirm: false, // 打开删除确认
      deleteCache: null, // 需要删除的数据的暂存
      loading: false, // 是否处于加载中
      layout: {
        // tab
        tabs: [
          { title: "草稿", state: 1 },
          { title: "已提交", state: 3 },
          { title: "已发布", state: 4 }
        ],
        compositions: [], // 作文
        statusColor: ["#07c160", "#ee0a24", "#1989fa", "#ff976a"], // 状态标签颜色
        visibilityColor: ["#969799", "#646566", "#323233"] // 可见性标签颜色
      },
      totalScore: 100, // 总分
      visibilityList: [
        { value: 1, name: "私密" },
        { value: 2, name: "仅粉丝可见" },
        { value: 3, name: "公开" }
      ]
    };
  },
  computed: {
    ...mapState(["compositions", "user", "routeAnchor", "isLogin"])
  },
  watch: {
    activeTab() {
      this.restoreLayout();
    },
    compositions() {
      this.restoreLayout();
    }
  },
  created() {
    this.activeTab = Number(this.$route.query.tab);
    if (this.routeAnchor >= 0) {
      this.activeTab = this.routeAnchor;
      this.$store.commit("setRouteAnchor", -1);
    }
    this.loading = true;
    // 加载数据
    this.onLoad(
      () => (this.loading = false),
      () => (this.loading = false)
    );
  },
  methods: {
    ...dateUtils,
    /**
     * @description 翻译可见性
     * @param {Number} value 可见性
     * @return 可见性解析之后的字符串
     */
    translateVisibility(value) {
      if (value) {
        let visibility = this.visibilityList.find(
          visibility => visibility.value == value
        );
        return visibility.name;
      }
      return this.visibilityList[0].name;
    },
    /**
     * @description 翻译状态码
     * @param {Number} value 状态码
     * @return 状态码解析之后的字符串
     */
    translateStatus(value) {
      let status = ["草稿", "评价中", "评价完成", "已发布"];
      return status[value - 1];
    },
    /**
     * @description 界面重布局
     */
    restoreLayout() {
      // 清空作文布局
      this.layout.compositions = [];
      for (let composition of this.compositions) {
        // 根据当前的 tab 做筛选
        if (
          (composition.status == 1 && this.activeTab == 0) ||
          (composition.status == 2 && this.activeTab == 1) ||
          (composition.status == 3 && this.activeTab == 1) ||
          (composition.status == 4 && this.activeTab == 2)
        ) {
          // 添加到作文布局
          this.layout.compositions.push({ ...composition });
        }
      }
    },
    /**
     * @description 路由跳转
     * @param {String} route 路由字符串
     */
    onRouteChange(route) {
      this.$router.push(route);
    },
    /**
     * @description 通过后端加载数据
     * @param {Function} onSuccess 加载成功回调函数
     * @param {Function} onFail 加载失败回调函数
     */
    onLoad(onSuccess = null, onFail = null) {
      if (this.isLogin) {
        // 开启加载动效
        this.layout.loading = true;
        // 请求数据
        this.axios
          .get("composition")
          .then(res => {
            console.log(res.data);
            // 清空本地缓存
            this.$store.commit("clearCompositions");
            // 后端数据同步到本地缓存
            res.data.data.compositionList.forEach(composition => {
              this.$store.commit(
                "addComposition",
                new Composition(composition)
              );
            });
            // 成功回调
            if (typeof onSuccess == "function") {
              onSuccess();
            }
          })
          .catch(err => {
            console.error(err.response);
            // 失败回调
            if (typeof onFail == "function") {
              onFail();
            }
          });
      }
    },
    /**
     * @description 刷新
     */
    onRefresh() {
      this.loading = true;
      this.onLoad(
        () => {
          this.loading = false;
          this.$toast("刷新成功");
        },
        () => {
          this.loading = false;
          this.$toast.fail("请检查网络");
        }
      );
    },
    /**
     * @description 删除作文
     * @param {Object} item 执行动作的卡片
     */
    onDelete(item) {
      this.enableDeleteConfirm = true;
      this.deleteCache = item;
    },
    /**
     * @description 应用删除事件
     */
    onApplyDelete() {
      // 请求删除作文
      this.axios
        .delete(`composition/${this.deleteCache.compositionId}`)
        .then(res => {
          console.log(res.data);
          // 删除对应的本地缓存
          this.$store.commit(
            "deleteComposition",
            this.deleteCache.compositionId
          );
          this.$toast("删除成功");
        })
        .catch(err => {
          console.error(err.response.data);
        });
    },
    /**
     * @description 单击卡片进入编辑界面
     * @param {Object} item 执行动作的卡片
     */
    onEnterEditing(item) {
      if (item.status == 1) {
        this.$store.commit("editingDraft", new Composition(item));
        this.onRouteChange("/writing");
      } else if (item.status == 3 || item.status == 4) {
        // 设置需要编辑的作文
        this.$store.commit("editingPublish", new Composition(item));
        this.$store.commit("setRouteAnchor", item.status - 2);
        // 跳转到发布编辑界面
        this.onRouteChange("/publish");
      } else {
        return;
      }
    },
    /**
     * @description 创建文章
     */
    onCreate() {
      this.$store.commit("editingCache");
      this.onRouteChange("/writing");
    }
  }
};
</script>

<style lang="scss" scoped>
.main-container {
  @include padding-horizontal($blank-size);
}
.action-button-icon {
  backface-visibility: hidden;
  padding: 0.25rem 1rem;
}
.composition-card {
  @include margin-vertical($blank-size);
  .front-side {
    margin: $blank-size;
    .title {
      font-size: $text-large;
      @include margin-vertical($text-normal * 0.4);
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .update-time {
      @include margin-vertical($text-normal * 0.4);
      font-size: $text-small;
      color: $color-fade;
    }
    .first-tag {
      margin-left: $blank-size/2;
    }
    .brief {
      @include margin-vertical($text-normal * 0.4);
      display: -webkit-box;
      white-space: pre-wrap;
      overflow: hidden;
      text-overflow: ellipsis;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
    }
    .tool-bar {
      @include margin-vertical($text-normal * 0.4);
      width: 100%;
      color: $color-danger;
    }
  }
  .back-side {
    height: 100%;
    margin: $blank-size;
    text-align: center;
    display: flex;
    justify-content: center;
    flex-direction: column;
    .highlight {
      color: orange;
    }
    .error {
      color: red;
    }
    .row {
      @include margin-vertical($text-normal * 0.5);
    }
  }
}
</style>
