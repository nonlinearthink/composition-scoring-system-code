<template>
  <div id="user-fans-page">
    <!-- 导航栏 -->
    <van-nav-bar
      title="关注和粉丝"
      left-arrow
      placeholder
      safe-area-inset-top
      :border="false"
      @click-left="$router.go(-1)"
    />
    <van-tabs
      v-model="activeTab"
      background="#02a7f0"
      title-inactive-color="white"
      title-active-color="white"
    >
      <!-- 我关注的人 -->
      <van-tab title="关注">
        <!-- 刷新机制 -->
        <van-pull-refresh
          v-model="loading"
          success-text="刷新成功"
          @refresh="onRefresh"
        >
          <!-- 用户列表 -->
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
              $router.push({
                path: '/user/home',
                query: { user: item.username }
              })
            "
          />
        </van-pull-refresh>
      </van-tab>
      <!-- 我的粉丝 -->
      <van-tab title="粉丝">
        <!-- 刷新机制 -->
        <van-pull-refresh
          v-model="loading"
          success-text="刷新成功"
          @refresh="onRefresh"
        >
          <!-- 用户列表 -->
          <user-list-item
            v-for="item in fansList"
            :key="item.username"
            name="1"
            :nickname="item.nickname"
            :signature="item.signature"
            :follow="item.follow"
            :avatar="item.avatarUrl"
            @follow="onChangeFollow(item)"
            @click-avatar="
              $router.push({
                path: '/user/home',
                query: { user: item.username }
              })
            "
          />
        </van-pull-refresh>
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
      // 当前选中的标签
      activeTab: 0,
      // 原本的用户列表
      originList: [],
      // 是否开启加载动画
      loading: false
    };
  },
  computed: {
    /**
     * @description 获取粉丝列表
     * @return 粉丝列表
     */
    fansList() {
      return this.originList.filter(item => item.fans);
    },
    /**
     * @description 获取关注的人的列表
     * @return 粉丝列表
     */
    followList() {
      return this.originList.filter(item => item.follow);
    },
    // 获取本地的用户信息、登录信息
    ...mapState(["user", "isLogin"])
  },
  created() {
    // 根据路由参数改变当前选中的元素
    this.activeTab = Number(this.$route.query.tab);
    // 开启加载
    this.onLoad();
  },
  methods: {
    /**
     * @description 改变关注信息的时候
     * @param {Object} item 关注信息
     */
    onChangeFollow(item) {
      // 关注状态取反
      item.follow = !item.follow;
      if (item.follow) {
        // 请求添加关注
        this.axios
          .post(`/follow/${item.username}`)
          .then(res => {
            console.log(res.data);
            this.$toast("添加关注");
          })
          .catch(err => {
            console.error(err.response.data);
          });
      } else {
        // 请求取消关注
        this.axios
          .delete(`/follow/${item.username}`)
          .then(res => {
            console.log(res.data);
            this.$toast("取消关注");
          })
          .catch(err => {
            console.error(err.response.data);
          });
      }
    },
    /**
     * @description 加载界面
     */
    onLoad() {
      // 是否登录
      if (this.isLogin) {
        // 开启加载动效
        this.loading = true;
        // 初始化用户列表
        this.originList = [];
        // 请求关注信息
        this.axios
          .get(`/follow/${this.user.username}`)
          .then(res => {
            console.log(res.data);
            // 把关注的人放到用户列表中
            res.data.data.followList.forEach(follow => {
              this.originList.push({ ...follow, follow: true, fans: false });
            });
            this.axios
              .get(`/follow/${this.user.username}/follower`)
              .then(res => {
                console.log(res.data);
                // 把粉丝放到用户列表中
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
                // 关闭加载动效
                this.loading = false;
              })
              .catch(err => {
                console.error(err.response.data);
              });
          })
          .catch(err => {
            console.error(err.response.data);
          });
      }
    },
    /**
     * @description 刷新
     */
    onRefresh() {
      this.onLoad();
    }
  }
};
</script>
