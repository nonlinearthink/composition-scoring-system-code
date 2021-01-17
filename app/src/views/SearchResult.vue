<template>
  <div>
    <van-nav-bar
      title="搜索结果"
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="$router.go(-2)"
    />
    <van-list v-model="loading" :finished="finished" finished-text="没有更多了">
      <van-row
        v-for="item in userList"
        :key="item.username"
        :style="{ background: 'white' }"
        type="flex"
        justify="space-between"
        align="center"
      >
        <van-col>
          <van-row>
            <van-col :style="{ margin: '0.8rem' }">
              <van-image
                width="3rem"
                height="3rem"
                fit="cover"
                round
                :src="require('../assets/images/avatar.svg')"
                @click="
                  $router.push({
                    path: '/user/home',
                    query: { user: item.username }
                  })
                "
              />
            </van-col>
            <van-col :style="{ margin: '0.8rem 0' }">
              <van-row :style="{ fontWeight: '600' }">
                {{ item.nickname }}
              </van-row>
              <van-row :style="{ color: '#888', fontSize: '0.6rem' }">
                {{ item.signature ? item.signature : "此人很懒，什么都没有写" }}
              </van-row>
              <van-row :style="{ color: '#888', fontSize: '0.6rem' }">
                <van-col>创作 {{ item.compositionCount }}</van-col>
                <van-col :style="{ marginLeft: '1rem' }">
                  粉丝 {{ item.followCount }}
                </van-col>
              </van-row>
            </van-col>
          </van-row>
        </van-col>
        <van-col :style="{ marginRight: '0.8rem' }">
          <van-tag
            round
            plain
            :color="item.isFollow ? '#646566' : 'red'"
            class="follow-button"
            @click="onClickFollow(item)"
          >
            {{ item.isFollow ? "已关注" : "关注" }}
          </van-tag>
        </van-col>
      </van-row>
    </van-list>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userList: [],
      loading: true,
      finished: true
    };
  },
  created() {
    this.axios
      .post(`/home/search/detail/${this.$route.query.string}`)
      .then(res => {
        console.log(res.data);
        this.userList = res.data.data.usernameCardModelList;
        this.loading = false;
        this.finished = true;
      });
  },
  methods: {
    onClickFollow(item) {
      if (!item.isFollow) {
        this.axios
          .post(`/follow/${item.username}`)
          .then(res => {
            console.log(res.data);
            this.$toast("添加关注");
            item.isFollow = !item.isFollow;
          })
          .catch(err => {
            console.error(err.response.data);
            this.$toast.fail(err.response.data.msg);
          });
      } else {
        this.axios
          .delete(`/follow/${item.username}`)
          .then(res => {
            console.log(res.data);
            this.$toast("取消关注");
            item.isFollow = !item.isFollow;
          })
          .catch(err => {
            console.error(err.response.data);
            this.$toast.fail(err.response.data.msg);
          });
      }
    }
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
.follow-button {
  font-size: $text-small;
  padding: $blank-size/3 $blank-size/2;
}
</style>
