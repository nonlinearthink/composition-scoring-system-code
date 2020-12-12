<template>
  <v-touch @swipeleft="rotate" @swiperight="rotate">
    <div
      ref="wrapper"
      :class="
        isResersed
          ? 'piduoduo-rotate-card-reversed'
          : 'piduoduo-rotate-card-normal'
      "
      :style="{ height: wrapperHeight }"
    >
      <div
        ref="front"
        class="rotate-card-front-side"
        :style="{ width: contentWidth }"
      >
        <slot name="default"></slot>
      </div>
      <div
        ref="back"
        class="rotate-card-back-side"
        :style="{ width: contentWidth }"
      >
        <slot name="reverse"></slot>
      </div>
    </div>
  </v-touch>
</template>

<script>
export default {
  data() {
    return {
      isResersed: false,
      wrapperHeight: null,
      contentWidth: null
    };
  },
  mounted() {
    // 选择子元素中高度最高的作为整个组件的高度
    this.wrapperHeight = `${Math.max(
      this.$refs.front.clientHeight,
      this.$refs.back.clientHeight
    )}px`;
    this.contentWidth = `${this.$refs.wrapper.clientWidth}px`;
  },
  methods: {
    rotate() {
      this.isResersed = !this.isResersed;
    }
  }
};
</script>

<style lang="scss" scoped>
@keyframes rotate {
  0% {
    transform: rotateY(0deg);
  }
  100% {
    transform: rotateY(180deg);
  }
}
@keyframes rotate-reverse {
  100% {
    transform: rotateY(0deg);
  }
  0% {
    transform: rotateY(180deg);
  }
}
%piduoduo-rotate-card {
  border-radius: $blank-size;
  background: white;
  position: relative;
  transform-style: preserve-3d;
}
.piduoduo-rotate-card-normal {
  @extend %piduoduo-rotate-card;
  animation: rotate-reverse cubic-bezier(0.76, -0.51, 0.29, 1.5) 1s forwards;
}
.piduoduo-rotate-card-reversed {
  @extend %piduoduo-rotate-card;
  animation: rotate cubic-bezier(0.76, -0.51, 0.29, 1.5) 1s forwards;
}
.rotate-card-front-side {
  position: absolute;
  backface-visibility: hidden;
}
.rotate-card-back-side {
  transform: rotateY(180deg);
  backface-visibility: hidden;
  height: 100%;
}
</style>
