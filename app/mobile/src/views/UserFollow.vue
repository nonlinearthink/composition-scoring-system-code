<template>
  <div id="user-fans-page">
    <van-tabs
      v-model="activeTab"
      background="#02a7f0"
      title-inactive-color="white"
      title-active-color="white"
    >
      <van-tab title="关注">
        <user-list-item
          v-for="item in fansList"
          :key="item.username"
          :nickname="item.nickname"
          :signature="item.signature"
          :follow="item.follow"
        />
      </van-tab>
      <van-tab title="粉丝"> </van-tab>
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
      activeTab: "",
      followList: [],
      fansList: [
        {
          nickname: "hello1",
          avatar: null,
          username: "1",
          signature:
            "此人很懒，什么都没有写ssdsdsdsds sdsds sdsdsd sdsds sdsds sdsd sdsd ",
          follow: true
        },
        {
          nickname: "hello2",
          avatar: null,
          username: "2",
          signature: "此人很懒，什么都没有写",
          follow: true
        },
        {
          nickname: "hello3",
          avatar: null,
          username: "3",
          signature: "此人很懒，什么都没有写",
          follow: true
        },
        {
          nickname: "hello4",
          avatar: null,
          username: "4",
          signature: "此人很懒，什么都没有写",
          follow: true
        }
      ]
    };
  },
  computed: {
    ...mapState(["user"])
  },
  created() {
    this.axios
      .get(`/follow/${this.user.username}`)
      .then(res => {
        console.log(res.data);
      })
      .catch(err => {
        console.error(err.response.data);
      });
    this.axios
      .get(`/follow/${this.user.username}/follower`)
      .then(res => {
        console.log(res.data);
      })
      .catch(err => {
        console.error(err.response.data);
      });
  },
  methods: {}
};
</script>

<style lang="scss" scoped></style>
