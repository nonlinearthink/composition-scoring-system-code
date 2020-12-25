<template>
  <div id="user-home-page">
    <van-nav-bar
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      :border="false"
      @click-left="onRouteBack"
    />
    <div class="avatar-background"></div>
    <div>
      <van-image
        width="4rem"
        height="4rem"
        fit="cover"
        round
        :src="defaultAvatar"
        class="piduoduo-avatar"
      />
      <div>
        {{ user.nickname }}
      </div>
      <div>
        {{ user.signature }}
      </div>
    </div>
    <div class="composition-list"></div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      defaultAvatar: require("../assets/images/avatar.svg")
    };
  },
  computed: {
    ...mapState(["user"])
  },
  created() {
    // this.axios
    //   .post(`/report/${this.editingTarget.username}`, this.editingTarget)
    //   .then(res => {
    //     console.log(res.data);
    //   })
    //   .catch(err => console.error(err.response.data));
  },
  methods: {
    onRouteBack() {
      this.$router.go(-1);
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
.composition-list {
}
</style>
