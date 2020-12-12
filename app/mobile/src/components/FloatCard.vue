<template>
  <div class="piduoduo-float-card-wrapper">
    <div class="piduoduo-float-card">
      <slot name="default" />
    </div>
    <div class="piduoduo-float-card-extra">
      <slot name="extra" />
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.piduoduo-float-card-wrapper {
  // 让行内或类行内元素居中
  text-align: center;
  .piduoduo-float-card {
    // 设置垂直 padding
    @include padding-vertical($blank-size);
    // 负外边框，产生浮动效果
    margin-top: -$blank-size * 2;
    // 转换为行内元素
    display: inline-block;
    // 取消内部的行内元素居中
    text-align: initial;
    // 设置圆角
    border-radius: $blank-size;
    // 类线条阴影
    box-shadow: 0px 1px 4px -2px;
    // 继承父类的背景色，用于支持自定义背景
    background: inherit;
  }
  .piduoduo-float-card-extra {
    // 取消内部的行内元素居中
    text-align: initial;
  }
}
</style>
