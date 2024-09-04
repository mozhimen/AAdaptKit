package com.mozhimen.adaptk.screen

import com.mozhimen.adaptk.screen.mos.AdaptKScreenArgs
import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.kotlin.lintk.optins.OApiUse_BaseApplication
import com.mozhimen.stackk.callback.StackKCb
import com.mozhimen.kotlin.utilk.android.util.UtilKDisplayMetrics
import com.mozhimen.kotlin.utilk.bases.BaseUtilK

/**
 * @ClassName AdaptKScreen
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/8/13 22:20
 * @Version 1.0
 */
object AdaptKScreen : BaseUtilK() {
    private val _adaptKScreenArgs: AdaptKScreenArgs by lazy { AdaptKScreenArgs() }

    ///////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OptIn(OApiInit_InApplication::class)
    @OApiUse_BaseApplication
    fun restoreAdaptScreen() {
        val displayMetrics_ofSys = UtilKDisplayMetrics.get_ofSys()
        val displayMetrics_ofApp = UtilKDisplayMetrics.get_ofApp(_context)
        val activity = StackKCb.instance.getStackTopActivity()
        if (activity != null) {
            val displayMetrics_ofAct = activity.resources.displayMetrics
            if (_adaptKScreenArgs.isVerticalSlide) {
                displayMetrics_ofAct.density = displayMetrics_ofAct.widthPixels / _adaptKScreenArgs.sizeInPx.toFloat()
            } else {
                displayMetrics_ofAct.density = displayMetrics_ofAct.heightPixels / _adaptKScreenArgs.sizeInPx.toFloat()
            }
            displayMetrics_ofAct.scaledDensity = displayMetrics_ofAct.density * (displayMetrics_ofSys.scaledDensity / displayMetrics_ofSys.density)
            displayMetrics_ofAct.densityDpi = (160 * displayMetrics_ofAct.density).toInt()

            displayMetrics_ofApp.density = displayMetrics_ofAct.density
            displayMetrics_ofApp.scaledDensity = displayMetrics_ofAct.scaledDensity
            displayMetrics_ofApp.densityDpi = displayMetrics_ofAct.densityDpi
        } else {
            if (_adaptKScreenArgs.isVerticalSlide) {
                displayMetrics_ofApp.density = displayMetrics_ofApp.widthPixels / _adaptKScreenArgs.sizeInPx.toFloat()
            } else {
                displayMetrics_ofApp.density = displayMetrics_ofApp.heightPixels / _adaptKScreenArgs.sizeInPx.toFloat()
            }
            displayMetrics_ofApp.scaledDensity = displayMetrics_ofApp.density * (displayMetrics_ofSys.scaledDensity / displayMetrics_ofSys.density)
            displayMetrics_ofApp.densityDpi = (160 * displayMetrics_ofApp.density).toInt()
        }
    }

    @JvmStatic
    @OptIn(OApiInit_InApplication::class)
    @OApiUse_BaseApplication
    fun cancelAdaptScreen() {
        val displayMetrics_ofSys = UtilKDisplayMetrics.get_ofSys()
        val displayMetrics_ofApp = UtilKDisplayMetrics.get_ofApp(_context)
        val activity = StackKCb.instance.getStackTopActivity()
        if (activity != null) {
            val displayMetrics_ofAct = activity.resources.displayMetrics
            displayMetrics_ofAct.density = displayMetrics_ofSys.density
            displayMetrics_ofAct.scaledDensity = displayMetrics_ofSys.scaledDensity
            displayMetrics_ofAct.densityDpi = displayMetrics_ofSys.densityDpi
        }

        displayMetrics_ofApp.density = displayMetrics_ofSys.density
        displayMetrics_ofApp.scaledDensity = displayMetrics_ofSys.scaledDensity
        displayMetrics_ofApp.densityDpi = displayMetrics_ofSys.densityDpi
    }

    @JvmStatic
    fun isAdaptScreen(): Boolean {
        val displayMetrics_ofSys = UtilKDisplayMetrics.get_ofSys()
        val displayMetrics_ofApp = UtilKDisplayMetrics.get_ofApp(_context)
        return displayMetrics_ofSys.density != displayMetrics_ofApp.density
    }
}