# MeUtils
根据日常工作需求，整合了一个Android项目快速开发库，节省开发时间🔥🤝。
## 环境说明
```groovy
android {
    //1. SDK编译版本>=31
    compileSdkVersion 31
    defaultConfig {        
        //2. 最低支持Android 5.0 Lollipop （API 21）
        minSdkVersion 21
    }
    
    
    //3. JDK编译版本
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```
## 功能结构
![](https://cdn.nlark.com/yuque/0/2022/jpeg/480768/1654141438396-94dd7be8-fa1e-4318-a133-82ec66d3a76b.jpeg)

## Gradle集成
![](https://jitpack.io/v/hepingdev/MeUtils.svg#crop=0&crop=0&crop=1&crop=1&id=jdikR&originHeight=20&originWidth=125&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)

- 在工程根目录`build.gradle`文件中添加`jitpack`仓库地址：
```groovy
allprojects {
    repositories {
        ...
        //jitpack仓库地址
        maven { url 'https://jitpack.io' }
    }
}
```

- 在工程`app`模块`build.gradle`文件中添加此项目依赖：
```groovy
implementation 'com.github.hepingdev:MeUtils:0.0.1-alpha'
```

## 使用步骤

## 依赖库说明

## 参考文章
[BigDecimal - 用于加、减、乘、除计算的工具类](https://www.jianshu.com/p/8f52256843b2)

