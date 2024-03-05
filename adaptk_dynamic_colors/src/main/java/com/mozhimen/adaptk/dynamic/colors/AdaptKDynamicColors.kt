package com.mozhimen.adaptk.dynamic.colors

import android.annotation.TargetApi
import android.app.Application
import com.google.android.material.color.DynamicColors
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.xmlk.optins.OTheme_ThemeMaterial3DayNoAction

/**
 * @ClassName AdaptKDynamicColors
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/3 23:11
 * @Version 1.0
 */
object AdaptKDynamicColors {
    @JvmStatic
    @TargetApi(CVersCode.V_31_12_S)
    @OTheme_ThemeMaterial3DayNoAction
    fun init(application: Application) {
        DynamicColors.applyToActivitiesIfAvailable(application)
    }
}