# CCmdTool-JavaFX
CCmdTool is a simple GUI tool for Cocos2dx command line

### Preview

![CCmdTool v1.7](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/1.png)
![CCmdTool v1.7](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/2.png)


### Intro

CCmdTool是一款方便使用Cocos2dx命令行的小工具，基于javafx编写，运行前请先安装java8

适用平台：Windows

下载链接：[https://github.com/Nomango/CCmdTool-JavaFX/releases/latest](https://github.com/Nomango/CCmdTool-JavaFX/releases/latest)

***

# CCmdTool使用帮助

#### 一、概况

CCmdTool目前的功能有：

1. 预编译及生成预编译模版
2. 新建、编译、部署、运行Cocos2dx项目
3. 一键查看Cocos2dx、SDK、NDK、ANT环境变量

<br>

### 二、预编译

预编译就是提前将cocos2dx引擎编译好，目的是避免重复编译，节省编译时间。

预编译生成的项目大小只有4M左右，编译项目也只需要几十秒。而源代码项目有200M，编译需要20~30分钟。除非你有修改源码的需要，否则不建议使用源代码新建项目。

进行预编译后，要点击生成预编译模版，这样就可以新建预编译项目了。

**预编译和生成预编译模版只需要进行一次。**

<br>

### 三、新建项目

* 填写项目名称、输出路径、包名、项目语言、引擎类型，可以在指定文件夹中新建一个Cocos2dx项目。

不填写输出路径时，项目会在工具所在目录新建。

PS：包名（Package Name）是应用的唯一标识，一个包名代表一个应用。包名主要用于系统识别应用，一般不会被用户看到。

<br>

### 四、编译项目

* 填写项目路径、编译模式和发布平台来编译Cocos2dx项目。

编译生成的项目文件保存在项目目录下的`bin`文件夹里。

PS：可以不填写项目路径，直接将工具复制到项目目录下运行。

<br>

### 五、运行项目

* 填写项目路径、输出路径（选填）、目标平台和编译模式来运行Cocos2dx项目。

可以直接在网页、电脑或与电脑连接的手机上运行你的Cocos2dx项目。

<br>

### 六、部署项目

* 填写项目路径、输出路径（选填）、目标平台和编译模式来部署Cocos2dx项目。

打开手机设置，连续点击几次版本号进入开发者模式，并将手机连接到电脑，就可以直接将你的Cocos2dx游戏安装到手机。

<br>

### 七、获取环境变量

点击刷新可以看到当前环境变量，点击“打开”可以打开相应文件夹。


***

# 常见问题 和 Tips

* 更改和Cocos相关的环境变量后，要重启Windows资源管理器并重新打开CCmdTool才能正常运行
* 显示控制台是可选项，不勾选时输出信息显示在“输出信息”标签页
* 编译项目时默认显示控制台，因为这样有助于查看编译中产生的错误，没有隐藏控制台的必要
* （待修复BUG）如果未显示控制台时，输出信息长时间卡住，很有可能是在等待用户输入。建议取消这次任务，勾选显示控制台后重新运行
* （待修复BUG）运行后按取消实际上并不能完全阻止后台程序的工作，要在任务管理器手动结束python.exe、make.exe等相关程序才能完全停止


***

# 更新日志

### v1.7 - 2016.12.25

* 新功能：Lua文件的加密和编译为字节码
* 新功能：js文件的加密和压缩
* 添加了编译项目中的Android选项、lua/js选项和lua选项
* 为大部分功能添加了工具提示

![](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/v1.7.png)


### v1.6 - 2016.12.23

* 适应了窗口大小，拖拽至大窗口可以看到更多的输出信息
* 有新的输出消息时，文本框会自动显示最底行
* 添加了预编译的高级选项
* 修复了部分bug

![](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/v1.6.png)


### v1.5 - 2016.9.14

* 新功能：取消当前任务
* 添加了web相关参数设置
* 添加了run命令
* 工程路径支持浏览选择文件夹
* 修复了部分bug

![](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/v1.5.png)


### v1.5 beta - 2016.9.13

* 新界面：运行模式统一放到了菜单栏，按钮移到了标签选项卡上方
* 新功能：隐藏和显示控制台
* 新功能：在“输出信息”选项卡显示运行时的输出信息
* 新功能：查看Cocos环境变量
* 添加了编译项目高级选项

![](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/v1.5 beta.png)


### v1.4 - 2016.8.31

* 新功能：预编译和生成预编译模版
* 修复了部分bug

![](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/v1.4.png)


### v1.3 - 2016.8.22

* 新界面：使用JavaFX Scene Builder设计，界面更紧凑
* 新功能：在“输出信息”显示运行的命令行内容
* 新功能：查看Cocos环境变量
* 完善了各个模式的基本选项，部分模式添加了高级选项

![](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/v1.3.png)


### v1.0 - 2016.8.6

* 实现了三个基本功能：新建、编译和运行项目

![](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/v1.0.png)


### v0.9 beta - 2016.8.4

* 测试版实现了新建项目和编译项目

![](https://github.com/Nomango/CCmdTool-JavaFX/raw/master/preview/v0.9 beta.png)