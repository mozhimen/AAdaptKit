package com.mozhimen.adaptk.splashscreen.test

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.postDelayed
import androidx.lifecycle.lifecycleScope
import com.mozhimen.adaptk.splashscreen.test.databinding.ActivitySplashBinding
import com.mozhimen.adaptk.splashscreen.utils.AdaptKSplashscreenUtil
import com.mozhimen.basick.elemk.androidx.appcompat.bases.viewbinding.BaseActivityVBVM
import com.mozhimen.basick.utilk.android.content.startActivityAndFinish
import com.mozhimen.basick.utilk.android.content.startActivityAndFinishAnimation_ofActivityOptions
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.mozhimen.animk.R

/**
 * @ClassName SplashActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/23 16:34
 * @Version 1.0
 */
class SplashActivity2 : AppCompatActivity(), IUtilK {
    var _somethingDelay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        AdaptKSplashscreenUtil.keepSplashScreenLonger(splashScreen) {
            lifecycleScope.launch {
                delay(6000)
                _somethingDelay =  true
            }
            return@keepSplashScreenLonger _somethingDelay
        }
        customizeSplashScreenExit(splashScreen)
    }

    // Customize splash screen exit animator.
    private fun customizeSplashScreenExit(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            UtilKLogWrapper.d(TAG, "SplashScreen#onSplashScreenExit view:$splashScreenViewProvider" + " view:${splashScreenViewProvider.view}" + " icon:${splashScreenViewProvider.iconView}")

            startActivityAndFinish<MainActivity>()
            overridePendingTransition(R.anim.animk_trans_top_show,R.anim.animk_set_trans_bottom_alpha_hide)
        }
    }
}