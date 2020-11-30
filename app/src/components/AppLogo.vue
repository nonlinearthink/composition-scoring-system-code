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
  @include margin-vertical($blank-size);
  text-align: center;
  .text {
    font-size: $text-size-title;
  }
}
</style>
