<template>
  <div id="user-home-page">
    <van-nav-bar
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      @click-left="onRouteBack"
    />
    <van-loading
      v-if="isLoadding"
      color="#1989fa"
      style="text-align: center;"
    />
    <div v-else>
      <div class="avatar-background"></div>
      <div class="user-info">
        <van-image
          width="4rem"
          height="4rem"
          fit="cover"
          round
          :src="owner.avatarUrl ? owner.avatarUrl : defaultAvatar"
          class="avatar"
        />
        <div>
          <van-row type="flex" justify="space-between">
            <van-col>
              <span class="username">{{ owner.nickname }}</span>
              <font-awesome-icon
                v-if="owner.isMale"
                id="mars-icon"
                icon="mars"
              />
              <font-awesome-icon v-else id="venus-icon" icon="venus" />
            </van-col>
            <van-col>
              <van-tag color="#1989fa" plain class="button">私信</van-tag>
              <van-tag
                v-if="!isFollow && user.username != owner.username"
                color="red"
                plain
                class="button"
                @click="onClickFollow"
              >
                关注
              </van-tag>
              <van-tag
                v-else-if="user.username != owner.username"
                color="#444"
                plain
                class="button"
                @click="onDiscardFollow"
              >
                已关注
              </van-tag>
            </van-col>
          </van-row>
        </div>
        <div class="signature">
          {{ showSignature(owner.signature) }}
        </div>
      </div>
      <div
        v-if="compositions && compositions.length != 0"
        class="composition-list"
      >
        <div
          v-for="item in compositions"
          :key="item.compositionId"
          class="favorite-card"
          @click="onEnterComposition(item)"
        >
          <van-row class="title">{{ item.title }}</van-row>
          <van-row class="body">{{ item.compositionBody }}</van-row>
          <van-row type="flex" justify="space-between" class="statusbar">
            <van-col class="username">{{ item.username }}</van-col>
            <van-col class="time">
              {{ translateTime(item.releaseTime) }}
            </van-col>
          </van-row>
          <van-divider />
        </div>
      </div>
      <div
        v-else
        style="text-align: center; background: white; padding: 2rem 0;"
      >
        <van-row>这里还什么都没有呢～</van-row>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import moment from "moment";
export default {
  data() {
    return {
      defaultAvatar: require("../assets/images/avatar.svg"),
      owner: null,
      compositions: null,
      isFollow: false,
      isLoadding: true
    };
  },
  computed: {
    ...mapState(["user"])
  },
  created() {
    this.axios
      .get(`/zone/${this.$route.query.user}`)
      .then(res => {
        console.log(res.data);
        this.owner = res.data.data.zoneModel;
        this.compositions = res.data.data.compositionEntityList;
        this.isFollow = res.data.data.isFollow;
        this.isLoadding = false;
      })
      .catch(err => {
        console.error(err.response.data);
        this.$toast("请先登录");
        this.isLoadding = false;
      });
  },
  methods: {
    onRouteBack() {
      this.$router.go(-1);
    },
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onClickFollow() {
      this.axios
        .post(`/follow/${this.owner.username}`)
        .then(res => {
          console.log(res.data);
          this.isFollow = true;
          this.$toast("关注成功");
        })
        .catch(err => console.error(err.response.data));
    },
    onDiscardFollow() {
      this.axios
        .delete(`/follow/${this.owner.username}`)
        .then(res => {
          console.log(res.data);
          this.isFollow = false;
          this.$toast("取消关注");
        })
        .catch(err => console.error(err.response.data));
    },
    showSignature(text) {
      return text ? text : "这位天龙人很懒，什么都没写呢";
    },
    onEnterComposition(item) {
      this.$router.push({
        path: "/composition",
        query: { compositionId: item.compositionId }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.avatar-background {
  background: $user-card-background-top;
  height: 5vh;
}
.piduoduo-avatar {
  margin-top: -2.5vh;
}
.user-info {
  background: white;
  padding: 0 $blank-size;
  .avatar {
    margin-top: -2rem;
  }
  .username {
    font-weight: 800;
    font-size: $text-large;
    margin-right: $blank-size;
  }
  .signature {
    color: $color-fade;
    padding: $blank-size/2 0;
  }
  .button {
    padding: $blank-size/2 $blank-size;
    margin-right: $blank-size;
    border-radius: $blank-size/3;
  }
}
#mars-icon {
  color: #1989fa;
}
#venus-icon {
  color: #ee0a24;
}
.favorite-card {
  background: white;
  padding: 1rem;
  margin-bottom: 1rem;
  .title {
    font-size: $text-large;
    font-weight: 600;
  }
  .body {
    @include margin-vertical($blank-size/2);
    font-size: $text-normal;
    display: -webkit-box;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
  }
  .statusbar {
    color: $color-fade;
    font-size: $text-small;
  }
}
.module-title {
  font-size: $text-normal * 1.6;
  padding: 0 $blank-size;
  background: white;
}
</style>
