<template>
  <div class="logo">
    <van-image
      :src="require('../assets/images/icon.svg')"
      fit="scale-down"
      width="6rem"
    />
    <div class="text">
      批多多
    </div>
  </div>
</template>

<style lang="scss" scoped>
.logo {
  @include padding-vertical($blank-size);
  // 兼容iOS顶部安全区域适配
  padding-top: calc(#{$blank-size} + constant(safe-area-inset-top));
  padding-top: calc(#{$blank-size} + env(safe-area-inset-top));
  text-align: center;
  .text {
    font-size: $text-size-title;
  }
}
</style>
