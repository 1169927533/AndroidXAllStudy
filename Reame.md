## 版本1

---

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

<video src="resourcepackage/softviewgroup.mp4"></video>