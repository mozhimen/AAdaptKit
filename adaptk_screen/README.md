# 最大纵横比
```
<!--在全屏的时候，避免出现一些屏幕黑边-->
<meta-data
     android:name="android.max_aspect"
     android:value="2.4" />
```

# 刘海屏适配

```
<!--适配华为（huawei）刘海屏-->
<meta-data
    android:name="android.notch_support"
    android:value="true" />

<!--适配小米（xiaomi）刘海屏-->
<meta-data
    android:name="notch.config"
    android:value="portrait|landscape" />
```