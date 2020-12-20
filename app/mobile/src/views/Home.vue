<template>
  <div id="home-page">
    <van-search
      v-model="searchString"
      placeholder="请输入搜索关键词"
      background="#02a7f0"
      shape="round"
      class="search-bar"
    />
    <v-touch
      @swipeleft="onSwipeLeft($event)"
      @swiperight="onSwipeRight($event)"
    >
      <van-tabs
        v-model="active"
        background="#02a7f0"
        title-inactive-color="white"
        title-active-color="white"
      >
        <van-tab title="推荐"> </van-tab>
        <van-tab title="关注">
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
                  :src="item.avatarUrl ? item.avatarUrl : defaultAvatar"
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
            :username="item.username"
            :update-time="item.releaseTime"
            :score="item.score"
            @click="onEnterComposition(item)"
          />
        </van-tab>
        <van-tab title="热榜">
          <piduoduo-hot-card
            :title="testData.title"
            :body="testData.body"
            :username="user.username"
            rank="2"
          />
        </van-tab>
        <van-tab title="新鲜">
          <piduoduo-fresh-card
            :title="testData.title"
            :body="testData.body"
            :username="user.username"
          />
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
      searchString: "",
      active: 0,
      testData: {
        title: "good day",
        body:
          "everything is ok, ,s ,ds,d,sd, ,, ,s,d,sd,s,d ,sd,s,dsdh sdgaudash gdauydgh agasid asdguadu aduadasdad adgfad adauaa ajag aa agay asyaugaa a  yagaaa ausag a asdas sa ds s afd   a s fsd  sd ds fsd fs fs fdsf s "
      },
      defaultAvatar: require("../assets/images/avatar.svg"),
      followList: [],
      followCompositions: [],
      composition: {
        follow: [],
        top: [],
        fresh: []
      }
    };
  },
  computed: {
    ...mapState(["user", "routeAnchor"])
  },
  created() {
    if (this.routeAnchor != -1) {
      this.active = this.routeAnchor;
      this.setRouteAnchor(-1);
    }
    this.axios
      .get(`/follow/${this.user.username}`)
      .then(res => {
        console.log(res);
        res.data.data.followList.forEach(follow => {
          this.followList.push({
            username: follow.username,
            avatarUrl: null
          });
        });
      })
      .catch(err => console.error(err.response.data));
    this.axios
      .get("/home/follow")
      .then(res => {
        console.log(res);
        this.followCompositions = res.data.data.followCompositionList;
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    onSwipeLeft() {
      if (this.active > 0) {
        this.active -= 1;
      }
    },
    onSwipeRight() {
      if (this.active <= 3) {
        this.active += 1;
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
    ...mapMutations(["setRouteAnchor"])
  }
};
</script>

<style lang="scss" scoped>
#home-page {
  height: 100vh;
}
.search-bar {
  width: 100vw;
  padding-right: 2rem;
  padding-left: 2rem;
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
</style>
