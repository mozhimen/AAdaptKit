package com.mozhimen.adaptk.autosize.helpers//package com.mozhimen.xmlk.adaptk.autosize.helpers
//
//import android.content.Context
//import android.content.ContextWrapper
//import android.content.res.Resources
//import com.mozhimen.kotlin.lintk.optin.ALintKOptIn_ApiDeclare_InManifest
//import com.mozhimen.kotlin.lintk.optin.ALintKOptIn_ApiInit_InApplication
//import com.mozhimen.kotlin.utilk.android.content.UtilKConfiguration
//import com.mozhimen.xmlk.adaptk.autosize.AdaptKAutoSize
//import me.jessyan.autosize.AutoSizeCompat
//
//
///**
// * @ClassName AdaptK
// * @Description TODO
// * @Author Mozhimen & Kolin Zhao
// * @Version 1.0
// */
//object AdaptKHelper {
//    @OptIn(ALintKOptIn_ApiInit_InApplication::class, ALintKOptIn_ApiDeclare_InManifest::class)
//    @JvmStatic
//    fun genContextResource(context: Context): Context {
//        return object : ContextWrapper(context) {
//            private var _oldResources: Resources = Resources(super.getResources().assets, super.getResources().displayMetrics, super.getResources().configuration)
//
//            override fun getResources(): Resources {
//                if (UtilKConfiguration.isOrientationLandscape(_oldResources)) {
//                    AutoSizeCompat.autoConvertDensityBaseOnWidth(_oldResources, AdaptKAutoSize.instance.getLength().toFloat())
//                } else {
//                    AutoSizeCompat.autoConvertDensityBaseOnHeight(_oldResources, AdaptKAutoSize.instance.getLength().toFloat())
//                }
//                return _oldResources
//            }
//        }
//    }
//}