<template>
  <div id="help-page">
    <van-nav-bar
      title="帮助手册"
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="onRouteBack"
    />
    <van-collapse v-model="activeNames">
      <van-collapse-item
        v-for="item in helpList"
        :key="item.helpId"
        :title="item.helpTitle"
        :name="item.helpId"
        class="pre-text"
      >
        {{ item.helpBody }}
      </van-collapse-item>
    </van-collapse>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeNames: [],
      helpList: []
    };
  },
  created() {
    this.axios
      .get(`/help`)
      .then(res => {
        console.log(res.data);
        this.helpList = res.data.data.helpEntityList;
        this.activeNames.push(this.helpList[0].helpId);
      })
      .catch(err => console.error(err.response.data));
  },
  methods: {
    onRouteBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped>
.pre-text {
  white-space: pre-line;
}
</style>
