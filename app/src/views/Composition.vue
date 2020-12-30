<template>
  <!-- eslint-disable vue/no-v-html -->
  <div id="composition-page">
    <van-nav-bar
      title="浏览"
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="onRouteBack"
    />
    <van-loading
      v-if="!composition"
      color="#1989fa"
      style="text-align: center;"
    />
    <div v-else>
      <div class="composition-info">
        <van-row class="title">{{ composition.title }}</van-row>
        <van-row class="release-time">
          {{ translateTime(composition.releaseTime) }}
        </van-row>
        <van-row class="user">
          <van-col>
            <van-image
              width="3.8rem"
              height="3.8rem"
              fit="cover"
              :src="
                composition.avatarUrl == null
                  ? defaultAvatar
                  : composition.avatarUrl
              "
              class="avatar"
              @click="
                onRouteChange({
                  path: '/user/home',
                  query: { user: composition.username }
                })
              "
            />
          </van-col>
          <van-col>
            <van-row class="nickname">{{ composition.nickname }}</van-row>
            <van-row>
              <van-tag color="#1989fa" plain class="button">私信</van-tag>
              <van-tag
                v-if="!isFollow && user.username != composition.username"
                color="red"
                plain
                class="button"
                @click="onClickFollow"
              >
                关注
              </van-tag>
              <van-tag
                v-else-if="user.username != composition.username"
                color="#444"
                plain
                class="button"
                @click="onDiscardFollow"
              >
                已关注
              </van-tag>
            </van-row>
          </van-col>
        </van-row>
        <van-tabs
          v-model="displayMode"
          type="card"
          swipeable
          color="#02a7f0"
          :style="{ marginTop: '2rem', marginRight: '0' }"
        >
          <van-tab title="原文">
            <van-row v-html="renderText(composition.compositionBody)"></van-row>
          </van-tab>
          <van-tab title="语法建议">
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
        <div
          :style="{
            color: '#1989fa',
            borderColor: '#1989fa',
            padding: '0.5rem 0',
            fontSize: '1.5rem'
          }"
        >
          <van-icon name="chat-o" />
        </div>
        <van-row>{{ composition.description }}</van-row>
      </div>
      <div class="comment">
        <van-badge :content="commentList.length">
          评论列表
        </van-badge>
        <van-row
          v-for="comment in commentList.slice(0, 3)"
          :key="comment.commentId"
          class="comment-item"
        >
          <van-col class="avatar">
            <van-image
              width="2rem"
              height="2rem"
              fit="cover"
              round
              :src="
                comment.avatarUrl == null ? defaultAvatar : comment.avatarUrl
              "
              @click="
                onRouteChange({
                  path: '/user/home',
                  query: { user: comment.username }
                })
              "
            />
          </van-col>
          <van-col class="info">
            <van-row type="flex" justify="space-between">
              <van-col class="username">{{ comment.username }}</van-col>
              <van-col class="time">
                {{ timeIntervalString(comment.time) }}
                <div class="a-text" @click="onClickCommentReport(comment)">
                  举报
                </div>
              </van-col>
            </van-row>
            <van-row type="flex" justify="space-between">
              <van-col>{{ comment.commentBody }}</van-col>
            </van-row>
          </van-col>
        </van-row>
        <van-row type="flex" justify="center">
          <van-button small color="red" @click="onShowComment">
            查看更多
          </van-button>
        </van-row>
      </div>
      <div class="action-bar-placeholder">
        <van-row class="action-bar">
          <van-col class="comment-button" @click="onShowComment">
            <van-icon name="edit" />写评论
          </van-col>
          <van-col class="other-button">
            <van-row type="flex" justify="center">
              <van-col
                v-if="!isFavorite"
                class="action-button"
                @click="onClickFavorite"
              >
                <van-row>
                  <van-icon name="star-o" size="1.2rem" />
                  {{ formatCount(composition.favoriteCount) }}
                </van-row>
                <van-row>收藏</van-row>
              </van-col>
              <van-col
                v-else
                class="action-button"
                style="color: red;"
                @click="onDiscardFavorite"
              >
                <van-row>
                  <van-icon name="star-o" size="1.2rem" />
                  {{ formatCount(composition.favoriteCount) }}
                </van-row>
                <van-row>收藏</van-row>
              </van-col>
              <van-col
                v-if="!isSupport"
                class="action-button"
                @click="onClickSupport"
              >
                <van-row>
                  <van-icon name="good-job-o" size="1.2rem" />
                  {{ formatCount(composition.supportCount) }}
                </van-row>
                <van-row>点赞</van-row>
              </van-col>
              <van-col
                v-else
                class="action-button"
                style="color: red;"
                @click="onDiscardSupport"
              >
                <van-row>
                  <van-icon name="good-job-o" size="1.2rem" />
                  {{ formatCount(composition.supportCount) }}
                </van-row>
                <van-row>点赞</van-row>
              </van-col>
              <van-col class="action-button" @click="onClickCompositionReport">
                <van-row>
                  <van-icon name="warn-o" size="1.2rem" />
                </van-row>
                <van-row>
                  举报
                </van-row>
              </van-col>
            </van-row>
          </van-col>
        </van-row>
      </div>
      <van-popup
        v-model="showComment"
        position="bottom"
        round
        closeable
        close-icon-position="top-left"
        safe-area-inset-bottom
        class="popup"
      >
        <div class="title">{{ `全部${commentList.length}条评论` }}</div>
        <div class="comment">
          <van-row
            v-for="comment in commentList"
            :key="comment.commentId"
            class="comment-item"
          >
            <van-col class="avatar">
              <van-image
                width="2rem"
                height="2rem"
                fit="cover"
                round
                :src="
                  comment.avatarUrl == null ? defaultAvatar : comment.avatarUrl
                "
                @click="
                  onRouteChange({
                    path: '/user/home',
                    query: { user: comment.username }
                  })
                "
              />
            </van-col>
            <van-col class="info">
              <van-row type="flex" justify="space-between">
                <van-col class="username">{{ comment.username }}</van-col>
                <van-col class="time">
                  {{ timeIntervalString(comment.time) }}
                </van-col>
              </van-row>
              <van-row>{{ comment.commentBody }}</van-row>
            </van-col>
          </van-row>
        </div>
        <div class="writing-bar-placeholder">
          <div ref="writingBar" class="writing-bar">
            <van-row class="editor-wrapper">
              <van-field
                v-model="editingComment"
                type="textarea"
                placeholder="输入评论"
                :border="false"
                autosize
                class="editor"
              />
            </van-row>
            <van-row type="flex" justify="end">
              <van-button
                small
                plain
                :disabled="editingComment == ''"
                :style="{ marginRight: '1rem' }"
                @click="onAddComment"
              >
                发送
              </van-button>
            </van-row>
          </div>
        </div>
      </van-popup>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import dateUtils from "../assets/js/common/dateUtils";
import { mapState } from "vuex";
export default {
  data() {
    return {
      editingComment: "",
      showComment: false,
      composition: null,
      commentList: [],
      defaultAvatar: require("../assets/images/avatar.svg"),
      isFavorite: false,
      isFollow: false,
      isSupport: false,
      showMode: 0,
      testData: [],
      grammerErrorModel: null,
      spellErrorModel: null,
      displayMode: 0
    };
  },
  computed: {
    ...mapState(["user", "isLogin"]),
    writingBarHeight() {
      return this.$refs.writingBar.clientHeight;
    }
  },
  created() {
    this.axios
      .get(`/composition/${this.$route.query.compositionId}`)
      .then(res => {
        console.log(res.data);
        this.composition = res.data.data.compositionCountModel;
        this.commentList = res.data.data.commentEntityList;
        this.isSupport = res.data.data.isSupport;
        this.isFavorite = res.data.data.isFavorite;
        this.isFollow = res.data.data.isFollow;
        this.axios
          .get(`/error/${this.$route.query.compositionId}`)
          .then(res => {
            console.log(res.data);
            this.testData = res.data.data.JSONArray;
            this.grammerErrorModel = this.parseGrammerError(
              this.composition.compositionBody,
              res.data.data.JSONArray
            );
          })
          .catch(err => console.error(err.response.data));
        if (this.isLogin) {
          this.axios
            .post(`/history/${this.$route.query.compositionId}`)
            .then(res => console.log(res.data))
            .catch(err => console.error(err.response.data));
        }
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
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
    },
    formatCount(num) {
      if (num >= 10000) {
        return `${Math.floor(num / 10000)}w`;
      } else if (num >= 1000) {
        return `${Math.floor(num / 1000)}k`;
      } else {
        return num;
      }
    },
    onRouteBack() {
      this.$router.go(-1);
    },
    onRouteChange(to) {
      this.$router.push(to);
    },
    onShowComment() {
      this.showComment = true;
    },
    onAddComment() {
      let comment = {
        commentBody: this.editingComment
      };
      console.log(this.editingComment);
      this.axios
        .post(`/comment/${this.composition.compositionId}`, comment)
        .then(res => {
          console.log(res.data);
          comment.commentId = res.data.data.commentId;
          comment.username = this.user.username;
          comment.time = new Date().getTime();
          this.commentList.push(comment);
          this.$toast("添加评论成功");
        })
        .catch(err => {
          console.error(err.response.data);
        });
    },
    onClickFavorite() {
      this.axios
        .post(`/favorite/${this.composition.compositionId}`)
        .then(res => {
          console.log(res.data);
          this.composition.favoriteCount += 1;
          this.isFavorite = true;
          this.$toast("收藏成功");
        })
        .catch(err => console.error(err.response.data));
    },
    onClickSupport() {
      this.axios
        .post(`/support/${this.composition.compositionId}`)
        .then(res => {
          console.log(res.data);
          this.composition.supportCount += 1;
          this.isSupport = true;
          this.$toast("点赞成功");
        })
        .catch(err => console.error(err.response.data));
    },
    onClickFollow() {
      this.axios
        .post(`/follow/${this.composition.username}`)
        .then(res => {
          console.log(res.data);
          this.isFollow = true;
          this.$toast("关注成功");
        })
        .catch(err => console.error(err.response.data));
    },
    onDiscardFavorite() {
      this.axios
        .delete(`/favorite/${this.composition.compositionId}`)
        .then(res => {
          console.log(res.data);
          this.composition.favoriteCount -= 1;
          this.isFavorite = false;
          this.$toast("取消收藏");
        })
        .catch(err => console.error(err.response.data));
    },
    onDiscardSupport() {
      this.axios
        .delete(`/support/${this.composition.compositionId}`)
        .then(res => {
          console.log(res.data);
          this.composition.supportCount -= 1;
          this.isSupport = false;
          this.$toast("取消点赞");
        })
        .catch(err => console.error(err.response.data));
    },
    onDiscardFollow() {
      this.axios
        .delete(`/follow/${this.composition.username}`)
        .then(res => {
          console.log(res.data);
          this.isFollow = false;
          this.$toast("取消关注");
        })
        .catch(err => console.error(err.response.data));
    },
    onClickCompositionReport() {
      this.$router.push({
        path: "/report",
        query: { compositionId: this.composition.compositionId, type: 0 }
      });
    },
    onClickCommentReport(comment) {
      this.$router.push({
        path: "/report",
        query: { commentId: comment.commentId, type: 1 }
      });
    },
    ...dateUtils
  }
};
</script>

<style lang="scss" scoped>
.composition-info {
  background: white;
  padding: $blank-size;
  .title {
    font-size: $text-normal * 1.5;
    font-weight: 900;
    color: #575757;
  }
  .release-time {
    color: $color-fade;
    font-size: $text-small;
    margin: $blank-size/2 0;
  }
  .user {
    .avatar {
      margin-right: $blank-size * 0.8;
    }
    .nickname {
      color: #575757;
      font-weight: 500;
      margin-bottom: $blank-size/4;
      font-size: $text-normal * 1.1;
    }
    .button {
      padding: $blank-size/2 $blank-size;
      margin-right: $blank-size;
      border-radius: $blank-size/3;
    }
  }
}
.comment {
  margin-top: $blank-size;
  background: white;
  padding: $blank-size;
  .comment-item {
    display: flex;
    margin: $blank-size 0;
    .avatar {
      flex: 0;
      margin-right: $blank-size/2;
    }
    .info {
      flex: 1;
      width: 100%;
    }
    .username {
      font-weight: 800;
      color: #575757;
    }
    .time {
      color: $color-fade;
    }
  }
}
.action-bar-placeholder {
  height: 50px;
  .action-bar {
    position: fixed;
    left: 0;
    bottom: 0;
    width: 100%;
    background: white;
    height: 50px;
    border: 1px solid #e2e2e2;
    display: flex;
    align-items: center;
    text-align: center;
    .comment-button {
      flex: 0;
      min-width: 35%;
      background: #f6f8fd;
      padding: 0.2rem;
      border-radius: 1rem;
      color: #949494;
      text-align: center;
      margin-left: $blank-size;
    }
    .other-button {
      flex: 1;
      .action-button {
        margin: 0 $blank-size;
        color: #666666;
      }
    }
  }
}
.popup {
  height: 95vh;
  background: #f8f8f8;
  .title {
    font-size: $text-large;
    text-align: center;
    font-weight: 900;
    margin: $blank-size 0;
  }
  .writing-bar {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100vw;
    background: white;
    .editor-wrapper {
      padding: $blank-size/2 $blank-size;
      .editor {
        background: #f6f8fd;
        max-height: 25vh;
      }
    }
  }
  .comment {
    background: #f8f8f8;
  }
}
</style>
