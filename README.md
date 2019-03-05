# PwdView
验证码输入框、密码输入框、正方形输入框
# 功能
  - 密码明文模式，样式包括线和方形
  - 设置输入框选中和非选中状态的颜色
  - 设置默认弹出键盘，数字键盘和字母键盘
  - 输入框尺寸
# 截图
<img src="https://github.com/zongzj/PwdView/blob/master/WX20190305-163543%402x.png" width="300">
# Gradle文件引入
```
     implementation 'com.zong:pwdview:1.0.0'
```
# 目前支持属性

- pwd_width
- pwd_height
- pwd_margin
- pwd_padding
- pwd_text_color
- pwd_focus_color
- pwd_normal_color
- isInputNum
- isPwdModle
- pwd_border_style
# 使用
```
    <com.zong.pwdview.PwdViewLayout
        android:id="@+id/pwdViewLayout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginTop="20dp"
        pwd:isInputNum="false"
        pwd:isPwdModle="false"
        pwd:pwd_border_style="line"
        pwd:pwd_padding="5dp" />
```
