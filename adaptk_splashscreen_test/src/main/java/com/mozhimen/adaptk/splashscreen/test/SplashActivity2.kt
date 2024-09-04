package com.mozhimen.adaptk.splashscreen.test

import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import com.mozhimen.adaptk.splashscreen.test.databinding.ActivitySplashBinding
import com.mozhimen.adaptk.splashscreen.utils.AdaptKSplashscreenUtil
import com.mozhimen.mvvmk.bases.activity.viewbinding.BaseActivityVBVM
import com.mozhimen.kotlin.elemk.commons.IAB_Listener
import com.mozhimen.kotlin.elemk.commons.I_Listener
import com.mozhimen.kotlin.utilk.android.app.getContentView
import com.mozhimen.kotlin.utilk.android.content.startActivityAndFinish
import com.mozhimen.kotlin.utilk.android.transition.UtilKSlide
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.androidx.core.UtilKActivityOptionsCompat
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.kotlin.utilk.wrapper.UtilKAnim
import kotlin.math.hypot

/**
 * @ClassName SplashActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/23 16:34
 * @Version 1.0
 */
class SplashActivity2 : BaseActivityVBVM<ActivitySplashBinding, SplashViewModel>(), IUtilK {
    private var legacyDisplayHypotenuse: Float = 0f

    private lateinit var splashScreen: SplashScreen
    override fun initFlag() {
//        UtilKAnim.applyActivityTransition(this, null, UtilKSlide.get(Gravity.BOTTOM, 1000), true)
        splashScreen = installSplashScreen()
    }

    override fun initView(savedInstanceState: Bundle?) {
        AdaptKSplashscreenUtil.keepSplashScreenLonger(splashScreen) {
            return@keepSplashScreenLonger vm.somethingDelay()
        }
        customizeSplashScreenExit(splashScreen) { view, iconView ->
//            revealActivity(iconView) {
                startActivityAndFinish<MainActivity>(
                    UtilKActivityOptionsCompat.makeCustomAnimation(
                        this,
                        com.mozhimen.animk.R.anim.animk_trans_bottom_show,
                        com.mozhimen.animk.R.anim.animk_set_alpha_no
                    ).toBundle()
                )
//            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(getContentView()) { _, insets ->
            val displayMetrics = resources.displayMetrics
            val height = displayMetrics.heightPixels + insets.systemWindowInsetTop + insets.systemWindowInsetBottom
            val width = displayMetrics.widthPixels + insets.systemWindowInsetLeft + insets.systemWindowInsetRight
            legacyDisplayHypotenuse = hypot(width.toFloat(), height.toFloat())
            insets
        }
    }

    // Customize splash screen exit animator.
    private fun customizeSplashScreenExit(splashScreen: SplashScreen, onEnd: IAB_Listener<View, View>) {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            UtilKLogWrapper.d(TAG, "SplashScreen#onSplashScreenExit view:$splashScreenViewProvider" + " view:${splashScreenViewProvider.view}" + " icon:${splashScreenViewProvider.iconView}")

            onEnd.invoke(splashScreenViewProvider.view, splashScreenViewProvider.iconView)
//            startActivityAndFinish<MainActivity>(
//                UtilKActivityOptionsCompat.makeCustomAnimation(
//                    this,
//                    com.mozhimen.animk.R.anim.animk_alpha_show,
//                    com.mozhimen.animk.R.anim.animk_set_trans_bottom_alpha_hide
//                ).toBundle()
//            )
//            startActivityAndFinishAnimation_ofActivityOptions<MainActivity>()
//            overridePendingTransition(R.anim.animk_trans_top_show, R.anim.animk_set_trans_bottom_alpha_hide)
        }
    }

    private fun getWindowHypotenuse(): Float {
        // The idea here is that to cover the full screen,
        // the radius of the FAB would have to be half of the diagonal of the screen
        // but displayMetrics doesn't include the size of system bars - which we need if our activity is transparent.
        // So we use WindowMetrics.getBounds() instead. https://developer.android.com/reference/android/view/WindowMetrics#getBounds()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = windowManager.currentWindowMetrics.bounds
            return hypot(bounds.width().toFloat(), bounds.height().toFloat())
        }

        return legacyDisplayHypotenuse
    }

    private fun revealActivity(iconView: View, onEnd: I_Listener) {
        // Get durations from resources
        val translateDuration =
            resources.getInteger(R.integer.fab_ripple_translate_duration).toLong()
//        val iconFadeDuration =
//            resources.getInteger(R.integer.fab_ripple_icon_fade_duration).toLong()
        val scaleDownDuration =
            resources.getInteger(R.integer.fab_ripple_scale_down_duration).toLong()
        val explodeDuration =
            resources.getInteger(R.integer.fab_ripple_explode_duration).toLong()
//        val fabScaleDownFactor =
//            resources.getInteger(R.integer.fab_ripple_scale_down_factor).toFloat()

        // The idea here is that to cover the full screen,
        // the radius of the FAB would have to be half of the diagonal of the screen
        // but displayMetrics doesn't include the size of system bars - which we need if our activity is transparent.
        // So we use WindowMetrics.getBounds() instead. https://developer.android.com/reference/android/view/WindowMetrics#getBounds()
        val fabScaleUp = getWindowHypotenuse() * 2 / iconView.height

        // Fade the FAB icon by changing transparency to 0
//        ObjectAnimator.ofArgb(iconView, "alpha", 0).apply {
//            duration = iconFadeDuration
//            interpolator = AccelerateInterpolator()
//            start()
//        }

        // Move the FAB to the center of the screen
//        val displayCenter =
//            resources.displayMetrics.heightPixels / 2 - iconView.height / 2
//        ObjectAnimator.ofFloat(iconView, View.Y, displayCenter.toFloat())
//            .apply {
//                duration = translateDuration
//                interpolator = DecelerateInterpolator()
//                startDelay = iconFadeDuration
//                start()
//            }

        // Shrink and then explode the FAB to fill the screen
//        val shrinkXHolder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f / fabScaleDownFactor)
//        val shrinkYHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f / fabScaleDownFactor)
//        ObjectAnimator.ofPropertyValuesHolder(
//            iconView,
//            shrinkXHolder,
//            shrinkYHolder
//        ).apply {
//            duration = scaleDownDuration
//            interpolator = DecelerateInterpolator()
//            start()
//        }

        val explodeXHolder = PropertyValuesHolder.ofFloat(View.SCALE_X,1f, fabScaleUp)
        val explodeYHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f,fabScaleUp)
        ObjectAnimator.ofPropertyValuesHolder(
            iconView,
            explodeXHolder,
            explodeYHolder
        ).apply {
            duration = 500
            interpolator = AnticipateInterpolator(1f)
            startDelay = scaleDownDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    onEnd.invoke()
                }
            })
            start()
        }
    }
}