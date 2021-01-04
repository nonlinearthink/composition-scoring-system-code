<template>
  <div id="chat-page">
    <van-nav-bar title="消息" placeholder safe-area-inset-top />
    <div class="toolbar">
      <van-row type="flex" justify="space-between">
        <van-col
          v-for="item in layout.toolbarItem"
          :key="item.name"
          :span="24 / layout.toolbarItem.length"
          class="toolbar-item"
          @click="$router.push(item.to)"
        >
          <van-row class="toolbar-item-icon">
            <van-icon :name="item.icon" />
          </van-row>
          <van-row class="toolbar-item-title">{{ item.text }}</van-row>
        </van-col>
      </van-row>
    </div>
    <van-cell-group title="消息列表" class="message-list">
      <div style="text-align: center; color: red;">暂不支持使用</div>
      <van-row
        v-for="item in layout.messageListItem"
        :key="item.id"
        type="flex"
        align="center"
      >
        <van-col :span="4">
          <van-image
            height="3rem"
            width="3rem"
            fit="fill"
            round
            :src="item.avatarUrl ? item.avatarUrl : layout.defaultAvatar"
            class="piduoduo-avatar"
          />
        </van-col>
        <van-col :span="15" class="message-list-item-main">
          <van-row class="message-list-item-title">
            {{ item.userNickame }}
          </van-row>
          <van-row class="message-list-item-content">
            {{ item.lastMessage.content }}
          </van-row>
        </van-col>
        <van-col :span="5" class="message-list-item-date">
          {{ timeIntervalString(item.lastMessage.sendTime) }}
        </van-col>
      </van-row>
    </van-cell-group>
  </div>
</template>

<script>
import dateUtils from "../assets/js/common/dateUtils";
export default {
  data() {
    return {
      layout: {
        defaultAvatar: require("../assets/images/avatar.svg"),
        toolbarItem: [
          {
            name: "comment",
            text: "回复我的",
            icon: "comment-o",
            to: "/comment"
          },
          {
            id: "fan",
            text: "新的粉丝",
            icon: "like-o",
            to: { path: "/user/follow", query: { tab: 1 } }
          },
          {
            id: "support",
            text: "收到的赞",
            icon: "good-job-o",
            to: "/message/support"
          },
          {
            id: "systemMessage",
            text: "系统通知",
            icon: "volume-o",
            to: "/system/message"
          }
        ],
        messageListItem: []
      }
    };
  },

  methods: {
    ...dateUtils
  }
};
</script>

<style lang="scss" scoped>
.toolbar {
  @include padding-vertical($blank-size);
  background: white;
  .toolbar-item {
    text-align: center;
    .toolbar-item-icon {
      font-size: $text-normal * 1.8;
      color: $color-primary;
    }
    .toolbar-item-title {
      font-size: $text-small;
    }
  }
}
.message-list {
  @include padding-vertical($blank-size/2);
  .message-list-item-main {
    @include padding-horizontal($blank-size/2);
    .message-list-item-title {
      margin-bottom: $blank-size/4;
    }
    .message-list-item-content {
      font-size: $text-small;
      color: $color-fade;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .message-list-item-date {
    padding-right: $blank-size;
    text-align: end;
    font-size: $text-small;
    color: $color-fade;
    height: 100%;
  }
}
</style>
