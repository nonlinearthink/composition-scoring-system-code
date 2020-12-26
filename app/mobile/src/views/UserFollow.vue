<template>
  <div id="user-fans-page">
    <van-nav-bar
      title="关注和粉丝"
      left-arrow
      fixed
      placeholder
      safe-area-inset-top
      :border="false"
      @click-left="onRouteBack"
    />
    <van-tabs
      v-model="activeTab"
      background="#02a7f0"
      title-inactive-color="white"
      title-active-color="white"
    >
      <van-tab title="关注">
        <user-list-item
          v-for="item in followList"
          :key="item.username"
          name="0"
          :nickname="item.nickname"
          :signature="item.signature"
          :follow="item.follow"
          :avatar="item.avatarUrl"
          @follow="onChangeFollow(item)"
          @click-avatar="
            onRouteChange({
              path: '/user/home',
              query: { user: item.username }
            })
          "
        />
      </van-tab>
      <van-tab title="粉丝">
        <user-list-item
          v-for="item in fansList"
          :key="item.username"
          name="1"
          :nickname="item.nickname"
          :signature="item.signature"
          :follow="item.follow"
          :avatar="item.avatarUrl"
          @follow="onChangeFollow(item)"
        />
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import UserListItem from "../components/UserListItem";
import { mapState } from "vuex";
export default {
  components: {
    UserListItem
  },
  data() {
    return {
      activeTab: 0,
      originList: []
    };
  },
  computed: {
    fansList() {
      return this.originList.filter(item => item.fans);
    },
    followList() {
      return this.originList.filter(item => item.follow);
    },
    ...mapState(["user"])
  },
  created() {
    this.activeTab = Number(this.$route.query.tab);
    this.axios
      .get(`/follow/${this.user.username}`)
      .then(res => {
        console.log(res.data);
        res.data.data.followList.forEach(follow => {
          this.originList.push({ ...follow, follow: true, fans: false });
        });
        this.axios
          .get(`/follow/${this.user.username}/follower`)
          .then(res => {
            console.log(res.data);
            res.data.data.followList.forEach(fan => {
              let index = this.originList.findIndex(
                follow => follow.username == fan.username
              );
              if (index >= 0) {
                this.originList[index].fans = true;
              } else {
                this.originList.push({ ...fan, follow: false, fans: true });
              }
            });
          })
          .catch(err => {
            console.error(err.response.data);
          });
      })
      .catch(err => {
        console.error(err.response.data);
      });
  },
  methods: {
    onRouteBack() {
      this.$router.go(-1);
    },
    onRouteChange(to) {
      this.$router.push(to);
    },
    onChangeFollow(item) {
      item.follow = !item.follow;
      if (item.follow) {
        this.axios
          .post(`/follow/${item.username}`)
          .then(res => {
            console.log(res.data);
          })
          .catch(err => {
            console.error(err.response.data);
          });
      } else {
        this.axios
          .delete(`/follow/${item.username}`)
          .then(res => {
            console.log(res.data);
          })
          .catch(err => {
            console.error(err.response.data);
          });
      }
    }
  }
};
</script>

<style lang="scss" scoped></style>
