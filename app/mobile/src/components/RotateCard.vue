<template>
  <v-touch @swipeleft="rotate" @swiperight="rotate">
    <div
      ref="wrapper"
      :class="`piduoduo-rotate-card-${state}`"
      :style="{ height: wrapperHeight, animation: animation }"
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
      state: "init",
      first: true,
      isResersed: false,
      wrapperHeight: null,
      contentWidth: null,
      animation: null
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
      if (this.first) {
        this.first = false;
        this.isResersed = true;
        this.state = "reversed";
      } else if (!this.first && !this.isResersed) {
        this.isResersed = true;
        this.state = "reversed";
      } else {
        this.isResersed = false;
        this.state = "normal";
      }
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
.piduoduo-rotate-card-init {
  @extend %piduoduo-rotate-card;
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
