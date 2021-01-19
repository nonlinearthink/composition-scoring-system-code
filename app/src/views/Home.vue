<template>
  <div id="home-page">
    <van-search
      placeholder="发现更多有趣的灵魂"
      background="#02a7f0"
      shape="round"
      class="search-bar"
      @click="
        $router.push({
          path: '/search'
        })
      "
    />
    <v-touch
      :swipe-options="{ direction: 'horizontal' }"
      @swipeleft="onSwipeLeft($event)"
      @swiperight="onSwipeRight($event)"
    >
      <van-tabs
        v-model="active"
        background="#02a7f0"
        title-inactive-color="white"
        title-active-color="white"
        animated
        sticky
      >
        <van-tab title="推荐">
          <van-pull-refresh
            v-model="loadingArticle"
            success-text="加载成功"
            @refresh="onRefresh"
          >
            <div
              v-for="item in articleList"
              :key="item.articleId"
              class="article-card"
              @click="onEnterArticle(item)"
            >
              <div class="title">
                {{ item.articleTitle }}
              </div>
              <div class="body">
                {{ item.articleBody }}
              </div>
              <van-image
                fit="cover"
                class="preview-image"
                :src="
                  item.avatarUrl
                    ? item.avatarUrl
                    : require(`../assets/images/background/background-${(item.articleId %
                        10) +
                        1}.png`)
                "
                radius="0.5rem"
              />
            </div>
          </van-pull-refresh>
        </van-tab>
        <van-tab v-if="user" title="关注">
          <van-pull-refresh
            v-model="loadingFollow"
            success-text="加载成功"
            @refresh="onRefresh"
          >
            <div class="follow-list">
              <div
                v-for="item in followList"
                :key="item.username"
                class="follow-list-item"
              >
                <van-row class="follow-list-avatar">
                  <van-image
                    width="3rem"
                    height="3rem"
                    fit="cover"
                    round
                    :src="
                      item.avatarUrl
                        ? `http://${item.avatarUrl}`
                        : defaultAvatar
                    "
                    @click="
                      onRouteChange({
                        path: '/user/home',
                        query: { user: item.username }
                      })
                    "
                  />
                </van-row>
                <van-row class="follow-list-username">
                  {{ item.username }}
                </van-row>
              </div>
            </div>
            <piduoduo-follow-card
              v-for="item in followCompositions"
              :key="item.compositionId"
              :title="item.title"
              :body="item.compositionBody"
              :username="item.nickname"
              :update-time="item.releaseTime"
              :score="item.score"
              :avatar="item.avatarUrl"
              :support="item.supportCount"
              :comment="item.commentCount"
              @click="onEnterComposition(item)"
            />
          </van-pull-refresh>
        </van-tab>
        <van-tab title="热榜">
          <van-pull-refresh
            v-model="loadingHot"
            success-text="加载成功"
            @refresh="onRefresh"
          >
            <piduoduo-hot-card
              v-for="(item, index) in hotCompositions"
              :key="item.compositionId"
              :title="item.title"
              :body="item.compositionBody"
              :username="item.nickname"
              :hot="item.hotCount.toFixed(2)"
              :rank="index + 1"
              @click="onEnterComposition(item)"
            />
          </van-pull-refresh>
        </van-tab>
        <van-tab title="新鲜">
          <van-pull-refresh
            v-model="loadingNew"
            success-text="加载成功"
            @refresh="onRefresh"
          >
            <piduoduo-fresh-card
              v-for="item in freshCompositions"
              :key="item.compositionId"
              :title="item.title"
              :body="item.compositionBody"
              :username="item.nickname"
              :read="item.historyCount"
              :comment="item.commentCount"
              :avatar="item.avatarUrl"
              @click="onEnterComposition(item)"
            />
          </van-pull-refresh>
        </van-tab>
      </van-tabs>
    </v-touch>
  </div>
</template>

<script>
import PiduoduoFreshCard from "@/components/PiduoduoFreshCard";
import PiduoduoHotCard from "@/components/PiduoduoHotCard";
import PiduoduoFollowCard from "@/components/PiduoduoFollowCard";

import { mapState, mapMutations } from "vuex";
export default {
  name: "Home",
  components: {
    PiduoduoFreshCard,
    PiduoduoHotCard,
    PiduoduoFollowCard
  },
  data() {
    return {
      active: 0,
      defaultAvatar: require("../assets/images/avatar.svg"),
      followList: null,
      followCompositions: null,
      hotCompositions: null,
      freshCompositions: null,
      articleList: null,
      composition: {
        follow: [],
        top: [],
        fresh: []
      },
      loadingArticle: true,
      loadingFollow: true,
      loadingHot: true,
      loadingNew: true
    };
  },
  computed: {
    ...mapState(["user", "routeAnchor", "isLogin"])
  },
  created() {
    if (this.routeAnchor != -1) {
      this.active = this.routeAnchor;
      this.setRouteAnchor(-1);
    }
    this.onRefresh();
  },
  methods: {
    onSwipeLeft() {
      if (this.active >= 0) {
        this.active += 1;
      }
    },
    onRouteChange(to) {
      this.$router.push(to);
    },
    onSwipeRight() {
      let maxActive = this.isLogin ? 3 : 2;
      if (this.active == 0) {
        this.active = maxActive;
        return;
      }
      if (this.active <= 3) {
        this.active -= 1;
      }
    },
    onEnterComposition(composition) {
      this.$router.push({
        path: "/composition",
        query: { compositionId: composition.compositionId }
      });
      this.setRouteAnchor(this.active);
      console.log(composition);
    },
    onEnterArticle(article) {
      this.viewArticle(article);
      this.$router.push("/article");
    },
    onRefresh() {
      this.loadingArticle = true;
      this.loadingFollow = true;
      this.loadingHot = true;
      this.loadingNew = true;
      if (this.user) {
        this.axios
          .get(`/follow/${this.user.username}`)
          .then(res => {
            console.log(res);
            this.followList = [];
            res.data.data.followList.forEach(follow => {
              this.followList.push({
                username: follow.username,
                avatarUrl: follow.avatarUrl
              });
            });
            this.axios
              .get("/home/follow")
              .then(res => {
                console.log(res);
                this.followCompositions = res.data.data.followCardModelList.sort(
                  (a, b) => b.releaseTime - a.releaseTime
                );
                this.loadingFollow = false;
              })
              .catch(err => console.error(err.response.data));
          })
          .catch(err => console.error(err.response.data));
      }
      this.axios
        .get("/home/article")
        .then(res => {
          console.log(res);
          this.articleList = res.data.data.articleModelList.sort(
            (a, b) => b.articleId - a.articleId
          );
          this.loadingArticle = false;
        })
        .catch(err => console.error(err.response.data));
      this.axios
        .get("/home/hot")
        .then(res => {
          console.log(res);
          this.hotCompositions = res.data.data.hotCardModelList;
          this.loadingHot = false;
        })
        .catch(err => console.error(err.response.data));
      this.axios
        .get("/home/new")
        .then(res => {
          console.log(res);
          this.freshCompositions = res.data.data.newCardModelList;
          this.freshCompositions.sort((a, b) => b.releaseTime - a.releaseTime);
          this.loadingNew = false;
        })
        .catch(err => console.error(err.response.data));
    },
    ...mapMutations(["setRouteAnchor", "viewArticle"])
  }
};
</script>

<style lang="scss" scoped>
.search-bar {
  width: 100vw;
  padding-right: 2rem;
  padding-left: 2rem;
  // 兼容iOS顶部安全区域适配
  padding-top: calc(#{$blank-size} + constant(safe-area-inset-top));
  padding-top: calc(#{$blank-size} + env(safe-area-inset-top));
}
.follow-list {
  overflow-x: auto;
  overflow-y: hidden;
  white-space: nowrap;
  .follow-list-item {
    margin: $blank-size/2;
    display: inline-block;
    .follow-list-username {
      font-size: $text-normal/2;
      text-align: center;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      width: 3rem;
    }
  }
}
.article-card {
  width: 100vw;
  margin: 2px 0;
  background: white;
  text-align: center;
  padding: 0.5rem 0;
  .preview-image {
    width: calc(100vw - 2 * #{$blank-size});
    height: 200px;
  }
  .title {
    text-align: initial;
    font-size: $text-large;
    padding: 0.25rem 1rem;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .body {
    text-align: initial;
    display: -webkit-box;
    padding: 0.25rem 1rem;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
  }
}
</style>
