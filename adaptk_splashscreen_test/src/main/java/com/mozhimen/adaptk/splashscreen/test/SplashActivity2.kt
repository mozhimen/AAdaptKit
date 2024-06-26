package com.mozhimen.adaptk.splashscreen.test

import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.mozhimen.adaptk.splashscreen.test.databinding.ActivitySplashBinding
import com.mozhimen.adaptk.splashscreen.utils.AdaptKSplashscreenUtil
import com.mozhimen.basick.elemk.androidx.appcompat.bases.viewbinding.BaseActivityVB
import com.mozhimen.basick.elemk.androidx.appcompat.bases.viewbinding.BaseActivityVBVM
import com.mozhimen.basick.utilk.android.content.startActivityAndFinish
import com.mozhimen.basick.utilk.android.transition.UtilKSlide
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.androidx.core.UtilKActivityOptionsCompat
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.wrapper.UtilKAnim
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.hypot

/**
 * @ClassName SplashActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/23 16:34
 * @Version 1.0
 */
class SplashActivity2 : BaseActivityVBVM<ActivitySplashBinding>(), IUtilK {
    var _somethingDelay: Boolean = true

    private var legacyDisplayHypotenuse: Float = 0f

    override fun initFlag() {
        UtilKAnim.applyActivityTransition(this, null, UtilKSlide.get(Gravity.BOTTOM, 1000), true)
    }
    override fun initView(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        AdaptKSplashscreenUtil.keepSplashScreenLonger(splashScreen) {
            lifecycleScope.launch {
                delay(6000)
                _somethingDelay = false
            }
            return@keepSplashScreenLonger _somethingDelay
        }
        customizeSplashScreenExit(splashScreen)
            .setOnApplyWindowInsetsListener { _, insets ->
                val displayMetrics = resources.displayMetrics
                val height =
                    displayMetrics.heightPixels + insets.systemWindowInsetTop + insets.systemWindowInsetBottom
                val width =
                    displayMetrics.widthPixels + insets.systemWindowInsetLeft + insets.systemWindowInsetRight
                legacyDisplayHypotenuse = hypot(width.toFloat(), height.toFloat())
                insets
            }
    }

    // Customize splash screen exit animator.
    private fun customizeSplashScreenExit(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            UtilKLogWrapper.d(TAG, "SplashScreen#onSplashScreenExit view:$splashScreenViewProvider" + " view:${splashScreenViewProvider.view}" + " icon:${splashScreenViewProvider.iconView}")

            startActivityAndFinish<MainActivity>(
                UtilKActivityOptionsCompat.makeCustomAnimation(
                    this,
                    com.mozhimen.animk.R.anim.animk_alpha_show,
                    com.mozhimen.animk.R.anim.animk_set_trans_bottom_alpha_hide
                ).toBundle()
            )
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

    private fun revealActivity(iconView: View) {
        // make FAB un-clickable
        binding.floatingActionButton.isClickable = false

        // Get durations from resources
        val translateDuration =
            resources.getInteger(R.integer.fab_ripple_translate_duration).toLong()
        val iconFadeDuration =
            resources.getInteger(R.integer.fab_ripple_icon_fade_duration).toLong()
        val scaleDownDuration =
            resources.getInteger(R.integer.fab_ripple_scale_down_duration).toLong()
        val explodeDuration = resources.getInteger(R.integer.fab_ripple_explode_duration).toLong()
        val fabScaleDownFactor =
            resources.getInteger(R.integer.fab_ripple_scale_down_factor).toFloat()

        // The idea here is that to cover the full screen,
        // the radius of the FAB would have to be half of the diagonal of the screen
        // but displayMetrics doesn't include the size of system bars - which we need if our activity is transparent.
        // So we use WindowMetrics.getBounds() instead. https://developer.android.com/reference/android/view/WindowMetrics#getBounds()
        val fabScaleUp = getWindowHypotenuse() * 2 / binding.floatingActionButton.height

        // Fade the FAB icon by changing transparency to 0
        ObjectAnimator.ofArgb(binding.floatingActionButton.drawable, "alpha", 0).apply {
            duration = iconFadeDuration
            interpolator = AccelerateInterpolator()
            start()
        }

        // Move the FAB to the center of the screen
        val displayCenter =
            resources.displayMetrics.heightPixels / 2 - binding.floatingActionButton.height / 2
        ObjectAnimator.ofFloat(binding.floatingActionButton, View.Y, displayCenter.toFloat())
            .apply {
                duration = translateDuration
                interpolator = DecelerateInterpolator()
                startDelay = iconFadeDuration
                start()
            }

        // Shrink and then explode the FAB to fill the screen
        val shrinkXHolder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f / fabScaleDownFactor)
        val shrinkYHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f / fabScaleDownFactor)
        ObjectAnimator.ofPropertyValuesHolder(
            binding.floatingActionButton,
            shrinkXHolder,
            shrinkYHolder
        ).apply {
            duration = scaleDownDuration
            interpolator = DecelerateInterpolator()
            start()
        }

        val explodeXHolder = PropertyValuesHolder.ofFloat(View.SCALE_X, fabScaleUp)
        val explodeYHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, fabScaleUp)
        ObjectAnimator.ofPropertyValuesHolder(
            binding.floatingActionButton,
            explodeXHolder,
            explodeYHolder
        ).apply {
            duration = explodeDuration
            interpolator = AccelerateInterpolator()
            startDelay = scaleDownDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    this@SplashActivity2.finish()
                    overridePendingTransition(0, 0)
                }
            })
            start()
        }
    }