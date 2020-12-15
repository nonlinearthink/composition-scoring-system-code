<template>
  <div id="home-page">
    <van-search
      v-model="searchString"
      placeholder="请输入搜索关键词"
      background="#02a7f0"
      shape="round"
      class="search-bar"
    />
    <van-tabs
      v-model="active"
      background="#02a7f0"
      swipeable
      title-inactive-color="white"
      title-active-color="white"
    >
      <van-tab title="推荐">内容 1</van-tab>
      <van-tab title="关注">内容 2</van-tab>
      <van-tab title="热榜">内容 3</van-tab>
      <van-tab title="新鲜">内容 4</van-tab>
    </van-tabs>
  </div>
</template>

<script>
export default {
  name: "Home",
  components: {},
  data() {
    return {
      searchString: "",
      active: 0
    };
  }
};
</script>

<style lang="scss" scoped>
.search-bar {
  width: 100vw;
  padding-right: 2rem;
  padding-left: 2rem;
}
</style>
