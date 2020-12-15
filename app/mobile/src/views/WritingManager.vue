<template>
  <div id="writing-manager-page">
    <van-nav-bar
      title="作文"
      fixed
      placeholder
      safe-area-inset-top
      :border="false"
    >
      <template #right>
        <van-icon
          name="plus"
          class="piduoduo-action-button"
          @click="onCreate"
        />
      </template>
    </van-nav-bar>
    <van-tabs
      v-model="activeTab"
      animated
      sticky
      swipeable
      background="#02a7f0"
      title-inactive-color="white"
      title-active-color="white"
    >
      <van-tab v-for="tab in layout.tabs" :key="tab.state" :title="tab.title">
        <div class="main-container">
          <rotate-card
            v-for="item in layout.compositions"
            :key="item.compositionId"
            class="composition-card"
          >
            <div class="front-side" @click="onEnterEditing(item)">
              <van-row class="title">
                <van-col>
                  {{ item.title ? item.title : "(未定义标题)" }}
                </van-col>
              </van-row>
              <van-row>
                <van-col class="update-time">
                  {{ timeIntervalString(item.releaseTime) }}
                </van-col>
                <van-col class="piduoduo-tag first-tag">
                  <van-tag plain :color="layout.statusColor[item.status - 1]">
                    {{ translateStatus(item.status) }}
                  </van-tag>
                </van-col>
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
              <van-row class="brief">{{ item.compositionBody }} </van-row>
              <van-row type="flex" justify="space-between" class="tool-bar">
                <van-col>
                  <div v-if="item.score">{{ item.score }}/{{ totalScore }}</div>
                </van-col>
                <van-col class="action-button-icon">
                  <van-icon name="delete" @click.stop="onDelete(item)" />
                </van-col>
              </van-row>
            </div>
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
      </van-tab>
    </van-tabs>
    <van-pull-refresh
      v-model="layout.loading"
      success-text="刷新成功"
      @refresh="onLoad"
    />
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
      enableDeleteConfirm: false,
      deleteCache: null,
      layout: {
        loading: false, //是否处于加载中
        // tab
        tabs: [
          { title: "全部", state: 0 },
          { title: "草稿", state: 1 },
          { title: "评分中", state: 2 },
          { title: "已发布", state: 3 }
        ],
        compositions: [], // 作文
        statusColor: ["#07c160", "#ee0a24", "#1989fa"],
        visibilityColor: ["#969799", "#646566", "#323233"]
      },
      totalScore: 10, // 总分
      visibilityList: [
        { value: 1, name: "私密" },
        { value: 2, name: "仅粉丝可见" },
        { value: 3, name: "公开" }
      ]
    };
  },
  computed: {
    ...mapState(["compositions", "user"])
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
    // 加载数据
    this.onLoad();
  },
  methods: {
    ...dateUtils,
    translateVisibility(value) {
      if (value) {
        let visibility = this.visibilityList.find(
          visibility => visibility.value == value
        );
        return visibility.name;
      }
      return this.visibilityList[0].name;
    },
    translateStatus(value) {
      if (value) {
        let tab = this.layout.tabs.find(tab => tab.state == value);
        return tab.title;
      }
      return this.layout.tabs[1].title;
    },
    /**
     * @description 界面重布局
     */
    restoreLayout() {
      // 清空作文布局
      this.layout.compositions = [];
      for (let composition of this.compositions) {
        // 根据当前的 tab 做筛选
        if (this.activeTab == 0 || composition.status == this.activeTab) {
          // 添加到作文布局
          this.layout.compositions.push({ ...composition });
        }
      }
    },
    /**
     * @description 路由跳转
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
            this.$store.commit("addComposition", new Composition(composition));
          });
          // 关闭加载动效
          this.layout.loading = false;
          // 成功回调
          if (typeof onSuccess == "function") {
            onSuccess();
          }
        })
        .catch(err => {
          console.error(err);
          // 关闭加载动效
          this.layout.loading = false;
          // 失败回调
          if (typeof onFail == "function") {
            onFail();
          }
        });
    },
    /**
     * @description 刷新
     */
    onRefresh() {
      this.onLoad(
        () => this.$toast("刷新成功"),
        () => this.$toast.fail("请检查网络")
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
      } else if (item.status == 3) {
        // 设置需要编辑的作文
        this.$store.commit("editingPublish", new Composition(item));
        // 跳转到发布编辑界面
        this.onRouteChange("/publish");
      } else {
        return;
      }
    },
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
