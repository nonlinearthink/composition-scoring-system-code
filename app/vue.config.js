module.exports = {
  publicPath: "./",
  devServer: {
    port: 9000
  },
  css: {
    loaderOptions: {
      sass: {
        prependData: `
          @import "@/assets/css/app/mixin.scss";
          @import "@/assets/css/app/variable.scss";
        `
      },
      less: {
        // 若 less-loader 版本小于 6.0，请移除 lessOptions 这一级，直接配置选项。
        lessOptions: {
          modifyVars: {
            hack: `true; @import "@/assets/css/vant/custom.less";`
          }
        }
      }
    }
  }
};
