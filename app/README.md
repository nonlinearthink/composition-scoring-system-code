# 批多多移动端应用

这是一个基于 vue+cordova 的跨平台解决方案。

## 快速开始

### 准备工作

*需要提前安装 npm 和 cordova 命令行*

```shell
# 安装依赖
npm install
# 添加编译平台
cordova add ios
cordova add android
```

### 编译Vue、打包成Android和iOS应用

```shell
npm run build-app
```

### 在网页中调试

```shell
npm run serve
```

### 在模拟器中调试

```shell
cordova run ios
cordova run android
```

> Android 需要先开启模拟器，再运行 `cordova run android`。iOS 可以直接运行，系统会帮你自动打开模拟器。

> iOS 如何指定模拟器？
> 默认情况下，cordova 运行你 xcode 指定的默认模拟器，如果你想要自定义，可以使用 xcode 来运行。

### 真机调试

#### Android

找到 `platforms/android/app/build/outputs/apk/debug/app-debug.apk`。

安装到你的安卓设备即可。

#### iOS

找到 `platforms/批多多.xcodeproj`，使用 xcode 打开。

你需要一个Apple ID。
