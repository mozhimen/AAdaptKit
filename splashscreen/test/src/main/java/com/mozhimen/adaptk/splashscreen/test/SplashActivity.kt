package com.mozhimen.adaptk.splashscreen.test

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mozhimen.adaptk.splashscreen.test.databinding.ActivitySplashBinding
import com.mozhimen.uik.databinding.bases.viewbinding.activity.BaseActivityVBVM
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper

/**
 * @ClassName SplashActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/23 16:34
 * @Version 1.0
 */
class SplashActivity : BaseActivityVBVM<ActivitySplashBinding, SplashViewModel>() {
    override fun initFlag() {
        val splashScreen = installSplashScreen()
//        keepSplashScreenLonger(splashScreen)
        customizeSplashScreenExit(splashScreen)
    }

    // Customize splash screen exit animator.
    private fun customizeSplashScreenExit(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            UtilKLogWrapper.d(TAG, "SplashScreen#onSplashScreenExit view:$splashScreenViewProvider" + " view:${splashScreenViewProvider.view}" + " icon:${splashScreenViewProvider.iconView}")

            ////////////////////////////////////////////////////////////////////////////////////////////

            // val onExit = {
            //     splashScreenViewProvider.remove()
            // }

            // defaultExitDuration = getRemainingDuration(splashScreenViewProvider)

            // hookViewLayout(splashScreenViewProvider)

            startAnimatorViewExit(splashScreenViewProvider.view, 500) {
                splashScreenViewProvider.remove()
            }

            startAnimatorIconViewExit(splashScreenViewProvider.iconView, 500) {
                splashScreenViewProvider.remove()
            }
        }
    }

    // Show exit animator for splash screen view.
    private fun startAnimatorViewExit(splashScreenView: View, defaultExitDuration: Long, onExit: () -> Unit = {}) {
        UtilKLogWrapper.d(TAG, "showSplashExitAnimator() splashScreenView:$splashScreenView context:${splashScreenView.context} parent:${splashScreenView.parent}")

        // Create your custom animation set.
        val alphaOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.ALPHA,
            1f,
            0f
        )

        // Slide up to center.
        val slideUp = ObjectAnimator.ofFloat(
            splashScreenView,
            View.TRANSLATION_Y,
            0f,
            // iconView.translationY,
            -(splashScreenView.height).toFloat()
        ).apply {
            addUpdateListener {
                UtilKLogWrapper.d(TAG, "showSplashIconExitAnimator() translationY:${splashScreenView.translationY}")
            }
        }

        // Slide down to center.
        val slideDown = ObjectAnimator.ofFloat(
            splashScreenView,
            View.TRANSLATION_Y,
            0f,
            // iconView.translationY,
            (splashScreenView.height).toFloat()
        ).apply {
            addUpdateListener {
                UtilKLogWrapper.d(TAG, "showSplashIconExitAnimator() translationY:${splashScreenView.translationY}")
            }
        }

        val scaleOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0f, 0f)
            }
        )

        AnimatorSet().run {
            duration = defaultExitDuration
            interpolator = AccelerateInterpolator()
            UtilKLogWrapper.d(TAG, "showSplashExitAnimator() duration:$duration")

            // playTogether(alphaOut, slideUp)
            // playTogether(scaleOut)
            // playTogether(alphaOut)
            // playTogether(scaleOut, slideUp, alphaOut)
            // playTogether(slideUp, alphaOut)
            playTogether(slideDown, alphaOut)

            doOnEnd {
                UtilKLogWrapper.d(TAG, "showSplashExitAnimator() onEnd")
                UtilKLogWrapper.d(TAG, "showSplashExitAnimator() onEnd remove")
                onExit()
            }

            start()
        }
    }

    // Show exit animator for splash icon.
    private fun startAnimatorIconViewExit(iconView: View, defaultExitDuration: Long, onExit: () -> Unit = {}) {
        UtilKLogWrapper.d(
            TAG, "showSplashIconExitAnimator()" +
                    " iconView[:${iconView.width}, ${iconView.height}]" +
                    " translation[:${iconView.translationX}, ${iconView.translationY}]"
        )

        val alphaOut = ObjectAnimator.ofFloat(
            iconView,
            View.ALPHA,
            1f,
            0f
        )

        // Bird scale out animator.
        val scaleOut = ObjectAnimator.ofFloat(
            iconView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0.3f, 0.3f)
            }
        )

        // Bird slide up to center.
        val slideUp = ObjectAnimator.ofFloat(
            iconView,
            View.TRANSLATION_Y,
            0f,
            // iconView.translationY,
            -(iconView.height).toFloat() * 2.25f
        ).apply {
            addUpdateListener {
                UtilKLogWrapper.d(TAG, "showSplashIconExitAnimator() translationY:${iconView.translationY}")
            }
        }

        AnimatorSet().run {
            interpolator = AccelerateInterpolator()
            duration = defaultExitDuration
            UtilKLogWrapper.d(TAG, "showSplashIconExitAnimator() duration:$duration")

            // playTogether(alphaOut, slideUp)
            playTogether(alphaOut, scaleOut, slideUp)
            // playTogether(scaleOut, slideUp)
            // playTogether(slideUp)

            doOnEnd {
                UtilKLogWrapper.d(TAG, "showSplashIconExitAnimator() onEnd remove")
                onExit()
            }

            start()
        }
    }
}