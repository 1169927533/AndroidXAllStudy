# AndroidXAllStudy
第一个版本
## 版本1
新增库：lib_customview

> 新增自定义View： SoftViewGroup.kt

使用规则:

* 父类里面必须有两个子类。
* 在每次需要更新状态的时候需要手动调用SoftViewGroup.kt里面的方法：

```kotlin
 soft_viewgroup.mEditText = editText
 soft_viewgroup.setValeToShowMore(state) //state: 0：默认第一次进入 1：展示更多页面 2：不展示更多页面
```

**具体案例见：库（comp_im）里的ImActivity.kt页面。**

效果如下:

<img src="resourcepackage/1599708909110.gif" alt="1599708909110" style="zoom: 50%;" />





## 版本2

仿照躺平实现Activity的切换转场动画

> 见底部导航栏Demo

实现过程：

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用了自定义Visibility实现Activity的转场动画。为什么要继承Visibility呢而不是继承Transition呢。

因为Visibility帮我们实现Activity可见状态和不可见状态的情况事件处理，我们只需要重写方法

onApper 和 onDIsapper方法就可以了。这两个方法可以实现去创建我们的自定义动画。详见类：Fade2.java类。

**注意的知识点**

> 想使用转场动画那么在开启一个Activity的时候需要使用方法：
>
> ```kotlin
> val intent = Intent(this, CopyTangApp::class.java)
> val pairs: Array<androidx.core.util.Pair<View, String>> = TransitionHelper.createSafeTransitionParticipants(this, true)
> var bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this@BottomTabActivity, *pairs).toBundle()
> startActivity(intent, bundle)
> ```
>还有 如果我们的转场动画如果想顺序执行的话需要在style里面添加：
>```xml
>  <item name="android:windowAllowEnterTransitionOverlap">true</item>
>  <item name="android:windowAllowReturnTransitionOverlap">true</item>
>```
>相反不想顺序执行的话那就将上面两个属性设置为false
>给Activity设置透明度度的话我们需要给指定的Acvitiy设置them。这里我给CopyTangApp设置了fade的主题
>```xml
>    <style name="fade" parent="AppTheme">
>        <item name="android:backgroundDimAmount">0.0</item><!-- 灰度 -->
>        <item name="android:windowBackground">@android:color/transparent</item>
>        <item name="android:windowFrame">@null</item>
>        <item name="android:windowNoTitle">true</item>
>        <item name="android:windowIsTranslucent">true</item>
>        <item name="android:windowContentOverlay">@null</item>
>        <item name="android:windowAnimationStyle">@null</item>
>        <item name="android:windowFullscreen">false</item>
>        <item name="android:backgroundDimEnabled">false</item>
>        <item name="android:colorBackgroundCacheHint">@null</item>
>        <item name="android:windowActionBar">false</item>
>        <item name="android:windowCloseOnTouchOutside">true</item>
>    </style>
>```
**具体案例见：app里的BottomTabActivity.kt页面。**
效果如下：
<img src="resourcepackage/g1.gif" alt="g1" style="zoom: 10%;" />
