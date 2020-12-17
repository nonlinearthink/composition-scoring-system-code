<template>
  <div class="user-list-item">
    <van-row>
      <van-col span="19">
        <van-row>
          <van-col span="5">
            <van-image
              width="2.5rem"
              height="2.5rem"
              fit="cover"
              round
              :src="avatar"
              class="avatar"
            />
          </van-col>
          <van-col span="19">
            <van-row class="nickname">{{ nickname }}</van-row>
            <van-row class="signature">{{ signature }}</van-row>
          </van-col>
        </van-row>
      </van-col>
      <van-col span="5">
        <div>
          <van-tag
            round
            plain
            :color="follow ? '646566' : 'red'"
            class="button"
          >
            {{ follow ? "已关注" : "关注" }}
          </van-tag>
        </div>
      </van-col>
    </van-row>
  </div>
</template>

<script>
export default {
  props: {
    nickname: {
      type: String,
      required: true
    },
    avatar: {
      type: [String],
      default: require("../assets/images/avatar.svg")
    },
    signature: {
      type: String,
      default: "此人很懒，什么都没有写"
    },
    follow: {
      type: Boolean,
      default: false
    }
  }
};
</script>

<style lang="scss" scoped>
.user-list-item {
  padding: $blank-size/2 $blank-size;
  background: white;
  .username {
    font-size: $text-large;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .signature {
    font-size: $text-normal/3;
    color: $color-fade;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    padding-right: $blank-size;
  }
  .avatar {
    padding-right: $blank-size;
    text-align: center;
  }
  .button {
    font-size: $text-small;
    padding: $blank-size/3 $blank-size/2;
  }
}
</style>
