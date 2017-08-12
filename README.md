# Uiautomator_SignIn
SignIn tool fully automatic implemented by Uiautomator, which can help us to auto sign in for 3rd party apps.  

## Requirements
Since this app based on Uiautomator, it should run with adb shell command like:  
```adb shell am instrument -w -r   -e debug false -e class com.pyy.uitest.Sign com.pyy.uitest.test/android.support.test.runner.AndroidJUnitRunner```  

## Usage
Setup the project then run the app with IDE, it will build 2 apks, one is app-debug.apk and one is app-debug-androidTest.apk.  
You should install these two apks.  
You can run it with the script run.sh for Linux or run.bat for Windows.
When you set the gesture-lock in JD Finance, you should run the app with icon named ```自动签到助手``` to save your unlock gestrue.  

## Notice
Nowadays the app only supports 4 3rd party apps: ```JD```, ```JD Finance```, ```SMZDM```, ```Tencent Comics```.  

If you want more, you can create issue to me.  
Fork, stars, issues & PRs are welcome!  

# 中文说明(README.md in Chinese version)
本工具是全自动化的针对第三方应用的签到工具，基于Android Uiautomator实现。

## 要求
需要用户在PC端执行如下命令来运行签到工具：
```adb shell am instrument -w -r   -e debug false -e class com.pyy.uitest.Sign com.pyy.uitest.test/android.support.test.runner.AndroidJUnitRunner```  
或者root手机之后，直接在手机端执行上述命令。  

## 用法
把本工程导入IDE工具后运行会编译出来两个apk，分别是```app-debug.apk```和```app-debug-androidTest.apk```，需要安装这两个apk。  
可以在Linux系统中直接运行run.sh或者在Windows系统中运行run.bat。  
如果你设置了京东金融的手势密码，请先运行带图标的```自动签到助手```app来设置你的解锁手势密码，以确保工具能够自动解锁手势密码。  

## 注意
目前只支持了4款第三方应用：```京东```, ```京东金融```, ```什么值得买```, ```腾讯动漫```.  
需要其他的任何第三方app，请提issue给我。  
欢迎各种fork, 点亮星星， 提issue和PRs给我。