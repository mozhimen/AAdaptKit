package com.mozhimen.adaptk.systembar.annors

import androidx.annotation.Keep

/**
 * @ClassName ASenseKSystemBarColor
 * @Description TODO
 * @Author mozhimen
 * @Version 1.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AAdaptKSystemBarTheme(
    val colorLight: Int = android.R.color.white, //状态栏背景色(浅色主题)
    val colorDark: Int = android.R.color.black //状态栏背景色(深色主题)
)
