package com.mozhimen.adaptk.splashscreen.test

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.mozhimen.adaptk.splashscreen.utils.AdaptKSplashscreenUtil
import com.mozhimen.basick.utilk.android.content.startActivityAndFinish
import com.mozhimen.basick.utilk.android.transition.UtilKSlide
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.androidx.core.UtilKActivityOptionsCompat
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.wrapper.UtilKAnim
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName SplashActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/23 16:34
 * @Version 1.0
 */
class SplashActivity2 : AppCompatActivity(), IUtilK {
    var _somethingDelay: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UtilKAnim.applyActivityTransition(this, null, UtilKSlide.get(Gravity.BOTTOM, 1000), true)
        val splashScreen = installSplashScreen()
        AdaptKSplashscreenUtil.keepSplashScreenLonger(splashScreen) {
            lifecycleScope.launch {
                delay(6000)
                _somethingDelay = false
            }
            return@keepSplashScreenLonger _somethingDelay
        }
        customizeSplashScreenExit(splashScreen)
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
}