<template>
  <div>
    <van-search
      v-model="searchString"
      placeholder="发现更多有趣的灵魂"
      background="#02a7f0"
      shape="round"
      class="search-bar"
      @search="onSearch"
    />
    <van-list v-model="loading" :finished="finished" finished-text="没有更多了">
      <van-cell
        v-for="item in userList"
        :key="item"
        :title="item"
        @click="
          $router.push({
            path: '/user/home',
            query: { user: item }
          })
        "
      />
    </van-list>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchString: "",
      userList: [],
      loading: true,
      finished: true
    };
  },
  watch: {
    searchString(newValue) {
      if (newValue != "") {
        this.loading = true;
        this.finished = false;
        this.axios.post(`/home/search/${newValue}`).then(res => {
          console.log(res.data);
          this.userList = res.data.data.usernameList;
          this.loading = false;
          this.finished = true;
        });
      } else {
        this.userList = [];
      }
    }
  },
  methods: {
    onSearch() {
      this.$router.push({
        path: "/search/result",
        query: { string: this.searchString }
      });
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
</style>
