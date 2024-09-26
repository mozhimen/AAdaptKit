package com.mozhimen.adaptk.splashscreen.utils

import android.animation.Animator
import android.os.SystemClock
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.KeepOnScreenCondition
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.postDelayed
import com.mozhimen.kotlin.elemk.commons.I_AListener
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK

/**
 * @ClassName AdaptKSplashscreenUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/24
 * @Version 1.0
 */
object AdaptKSplashscreenUtil : IUtilK {
    // Keep splash screen showing till data initialized.
    fun keepSplashScreenLonger(splashScreen: SplashScreen, condition: KeepOnScreenCondition) {
        UtilKLogWrapper.d(TAG, "SplashActivity#keepSplashScreenLonger()")
//        splashScreen.setKeepVisibleCondition(condition)
        splashScreen.setKeepOnScreenCondition(condition)
    }

    fun keepSplashScreenLonger(contentView: View, onLoadFinish: I_AListener<Boolean>) {
        //让启动画面在屏幕上停留更长时间
        contentView.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean =
                when {
                    onLoadFinish.invoke()/*mainViewModel.mockDataLoading()*/ -> {
                        contentView.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }

                    else -> false
                }
        })
    }

    //add ad
    fun adAdView() {
        //有需要显示广告的可以参考下面的写法:
        /*if(splashScreenViewProvider.view is ViewGroup){
            //显示一个广告或者启动页推广,自己实践玩耍吧,建议把mainViewModel.mockDataLoading()延时降低，然后测试
            val composeView = ComposeView(this@SplashScreenCompatActivity).apply {
                setContent {
                    SplashAdScreen {
                        splashScreenViewProvider.remove()
                    }
                }
            }
            (splashScreenViewProvider.view as ViewGroup).addView(composeView)
            return
        }*/
    }

    fun hookViewLayout(splashScreenViewProvider: SplashScreenViewProvider) {
        UtilKLogWrapper.d(TAG, "hookViewLayout()")
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            val rootWindowInsets = splashScreenViewProvider.view.rootWindowInsets

            UtilKLogWrapper.d(
                TAG, "hookViewLayout() rootWindowInsets:$rootWindowInsets" +
                        // "\n systemWindowInsets:${rootWindowInsets.systemWindowInsets}" +
                        " top:${rootWindowInsets.systemWindowInsetTop}" +
                        " bottom${rootWindowInsets.systemWindowInsetBottom}" +
                        " icon translationY:${splashScreenViewProvider.iconView.translationY}"
            )
        }
    }

    fun getRemainingDuration(splashScreenView: SplashScreenViewProvider, defaultExitDuration: Long): Long {
        // Get the duration of the animated vector drawable.
        val animationDuration = splashScreenView.iconAnimationDurationMillis

        // Get the start time of the animation.
        val animationStart = splashScreenView.iconAnimationStartMillis

        // Calculate the remaining duration of the animation.
        return if (animationDuration == 0L || animationStart == 0L)
            defaultExitDuration
        else (animationDuration - SystemClock.uptimeMillis() + animationStart)
            .coerceAtLeast(0L)
    }

    fun waitForAnimatedIconAnimationToFinish(splashScreenViewProvider: SplashScreenViewProvider, view: View, animator: Animator) {
        val delayMillis: Long = (splashScreenViewProvider.iconAnimationStartMillis + splashScreenViewProvider.iconAnimationDurationMillis) - System.currentTimeMillis()
        view.postDelayed(delayMillis) { animator.start() }
    }
}