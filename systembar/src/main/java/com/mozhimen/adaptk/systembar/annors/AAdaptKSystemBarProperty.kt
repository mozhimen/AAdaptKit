package com.mozhimen.adaptk.systembar.annors

import androidx.annotation.Keep
import com.mozhimen.adaptk.systembar.cons.CProperty

/**
 * @ClassName StatusBarAnnor
 * @Description TODO
 * @Author mozhimen
 * @Version 1.0
 */
/**
 * 原设计参考, 此为废弃方案
 * @Target(AnnotationTarget.CLASS)
 * @Retention(AnnotationRetention.RUNTIME)
 * annotation class ASenseKSystemBar(
 *     @ASenseKSystemBarType
 *     val systemBarType: Int = CSystemBarType.NORMAL,
 *     val isFollowSystem: Boolean = false, //状态栏主题是否跟随系统
 *     val isFontIconDark: Boolean = true,//状态栏文字和Icon是否是深色(黑/白)
 *     val bgColorLight: Int = android.R.color.white, //状态栏背景色(浅色主题)
 *     val bgColorDark: Int = android.R.color.black //状态栏背景色(深色主题)
 * )
 */
@Keep
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AAdaptKSystemBarProperty(
    @APropertyFilter
    val property: Int = CProperty.NORMAL,
)
