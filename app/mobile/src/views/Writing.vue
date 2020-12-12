<template>
  <div id="writing-page">
    <van-nav-bar title="写作" fixed placeholder safe-area-inset-top />
    <div class="main-container">
      <rotate-card
        v-for="item in composition"
        :key="item.id"
        class="composition-card"
      >
        <div class="front-side" @click="showDelete(item)">
          <van-row class="title">{{ item.title }}</van-row>
          <van-row class="update-time">{{ item.updateTime }}</van-row>
          <van-row class="brief">{{ item.content }} </van-row>
          <van-row type="flex" justify="space-between" class="tool-bar">
            <van-col> {{ item.score }}/{{ totalScore }} </van-col>
            <van-col>
              <div>
                <van-icon
                  v-show="item.showDelete"
                  name="delete"
                  tag="div"
                  @click="deleteItem(item)"
                />
              </div>
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
                  {{ item.statistic.highlightWord + "个" }}
                </span>
              </van-col>
              <van-col span="8">
                亮点句:
                <span class="highlight">
                  {{ item.statistic.highlightSentence + "个" }}
                </span>
              </van-col>
            </van-row>
            <van-row class="row">
              <van-col span="8">错误内容</van-col>
              <van-col span="8">
                拼写错误:
                <span class="error">{{ item.statistic.errorWord + "个" }}</span>
              </van-col>
              <van-col span="8">
                语法错误:
                <span class="error">
                  {{ item.statistic.errorSentence + "个" }}
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
  </div>
</template>

<script>
import RotateCard from "@/components/RotateCard";
export default {
  components: {
    RotateCard
  },
  data() {
    return {
      totalScore: 10,
      composition: [
        {
          id: 0,
          title: "我写了一篇作文，快来看看啊",
          updateTime: "2020-11-20 20:45:57",
          content:
            "Nowadays, a hot and essential controversy centering on the concerns whether Network real-name system. A fair proportion of people contend that the network have to use the real name, other people, nevertheless, advocate that network not necessary use you real name. As far as I am concerned, I side with the latter. Of the countless reasons or facts that strengthen my viewpoint, I will present the most consequential and conspicuous ones here. The first plain truth I am presenting here is that thousands people were used unreal name posted vary points on the network that is instrumental to freedom. Today, nothing is more important than freedom and on no account can we ignore the importance of privacy. A man without freedom express, most of us believe, is an unfortunate victim of an adversary environment, deprived of a wide assortment of twentieth-century opportunities. For example, convinced of the paramount value of network freedom, I devoted myself to doing the network unreal-name system, and got back a millions support in the from of the big group. A fair proportion of people, however, have not clear what is the unreal-name system, and consequently, has not enjoyed the same level of success as I have. Suppose of all of us do use the real-name system at network. How can we get real point of view from people? Therefore, the most striking conclusion is obvious that network keep use the unreal-name system will keep the freedom.",
          showDelete: false,
          score: 9,
          statistic: {
            highlightWord: 0,
            highlightSentence: 0,
            errorWord: 0,
            errorSentence: 0
          }
        },
        {
          id: 1,
          title: "我写了一篇作文，快来看看啊",
          updateTime: "2020-11-20 20:45:57",
          content: "Nowadays",
          showDelete: false,
          score: 5,
          statistic: {
            highlightWord: 0,
            highlightSentence: 0,
            errorWord: 0,
            errorSentence: 0
          }
        }
      ]
    };
  },
  methods: {
    showDelete(item) {
      item.showDelete = !item.showDelete;
    },
    deleteItem(item) {
      this.composition.splice(this.composition.indexOf(item), 1);
      this.$forceUpdate();
    }
  }
};
</script>

RotateCard<style lang="scss" scoped>
.main-container {
  @include padding-horizontal($blank-size);
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
