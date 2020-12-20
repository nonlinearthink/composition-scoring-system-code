<template>
  <div id="composition-page">
    <van-nav-bar
      title="浏览"
      fixed
      placeholder
      safe-area-inset-top
      left-arrow
      @click-left="onRouteBack"
    />
    <van-row class="title">{{ composition.title }}</van-row>
    <van-row class="release-time">
      {{ translateTime(composition.releaseTime) }}
    </van-row>
    <van-divider
      :style="{
        color: '#1989fa',
        borderColor: '#1989fa',
        padding: '0 16px',
        fontSize: '1.2rem'
      }"
    >
      正文
    </van-divider>
    <van-row>{{ composition.compositionBody }}</van-row>
    <van-divider
      :style="{
        color: '#1989fa',
        borderColor: '#1989fa',
        padding: '0 16px',
        fontSize: '1.2rem'
      }"
    >
      说明
    </van-divider>
    <van-row>{{ composition.description }}</van-row>
  </div>
</template>

<script>
import moment from "moment";
export default {
  data() {
    return {
      composition: {
        title: "我发布了一篇作文，快来看看啊",
        releaseTime: 1608224309000,
        score: 9,
        compositionBody:
          "This text tells us a child grows up to be a teenager and he wants to be independent. Parents do not allow their children to do something that parents consider it will be dangerous or it isn't good for the kids. Nevertheless, the teens don't care what dad or mom say. They maybe think they have already learnt the basic things what they can make a new life of their own. They regard parents even as obstacles to growth in. But parents sometimes have such an unnatural tight rein on their children. They are extremely possessive at times. Most young people may have this way of the idea that they want to do most of their own things independently. But this is teenagers' own immature views in most cases.In the text, the writer introduces to us some teenagers think their parents do not let their go free, but they want to work at their own will. However, the author also shows us some parents who do not let their children decide anything. I believe the writer don't in favour of any one of the two. I have the similar opinion with the author. In my opinion, teenager could decide something about his own life, but they should listen to parents' some advise as well. Parents have richer life experience, they know many situations we don't take care. However, parents should give their children more opportunities for independent work. The teenager will eventually leave their parents. If parents don't give the children independence, they won't know how to do in the community. As children, we should understand parents well. We should know most behaviors of parents are good for us. So, we need communicate with our parents more actively. And as parents, we need listen to voices of children more patiently. Sometimes, children's idea is pretty good. It's useful to us helping children grow up that we understand the children's point of view. It is the best way to solve adolescent problems establishing a good relationship between parents and children. So, children understand their parents more, and parents give their children more freedom.",
        description: "123"
      }
    };
  },
  created() {},
  methods: {
    translateTime(timestamp) {
      return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    },
    onRouteBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped>
#composition-page {
  padding: $blank-size;
  .title {
    font-size: $text-large;
    font-weight: 900;
  }
  .release-time {
    color: $color-fade;
    font-size: $text-small;
  }
}
</style>
