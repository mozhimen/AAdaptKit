//package com.mozhimen.xmlk.adaptk.autosize
//
//import android.app.Activity
//import android.util.Log
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
//import com.mozhimen.kotlin.lintk.optin.ALintKO_ApiDeclare_InManifest
//import com.mozhimen.kotlin.lintk.optin.ALintKO_ApiInit_InApplication
//import com.mozhimen.manifestk.cons.CMetaData
//import com.mozhimen.kotlin.utilk.android.view.UtilKScreen
//import com.mozhimen.kotlin.utilk.commons.IUtilK
//import me.jessyan.autosize.AutoSizeConfig
//import me.jessyan.autosize.onAdaptListener
//import java.lang.Integer.max
//import java.lang.Integer.min
//
//
///**
// * @ClassName AdaptKMgr
// * @Description TODO
// * @Author Mozhimen & Kolin Zhao
// * @Version 1.0
// */
//@OMetaData_DESIGN_WIDTH_IN_DP
//@OMetaData_DESIGN_HEIGHT_IN_DP
//@ALintKO_ApiInit_InApplication
//@ALintKO_ApiDeclare_InManifest
//class AdaptKAutoSize : IUtilK {
//    companion object {
//        @JvmStatic
//        val instance = INSTANCE.holder
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////
//
//    private var _length: Int
//    private var _width: Int
//
//    init {
//        val length = UtilKScreen.getRealHeight()
//        val width = UtilKScreen.getRealWidth()
//        _length = max(length, width)
//        _width = min(length, width)
//    }
//
//    /**
//     * 在application中进行初始化. 请先设置
//
//     * Application
//
//    <meta-data
//    android:name="design_width_in_dp"
//    android:value="1280"/>
//    <meta-data
//    android:name="design_height_in_dp"
//    android:value="800"/>
//
//     * @param length Int 设备的长
//     * @param width Int 设备的宽
//     */
//    fun init(length: Int = _length, width: Int = _width) {
//        UtilKLogWrapper.d(TAG, "init: length $length width $width")
//        AutoSizeConfig.getInstance().onAdaptListener = object : onAdaptListener {
//            override fun onAdaptBefore(target: Any, activity: Activity) {
//                AutoSizeConfig.getInstance().screenWidth = length
//                AutoSizeConfig.getInstance().screenHeight = width
//                if (UtilKScreen.isOrientationLandscape()) {                //根据屏幕方向，设置适配基准
//                    AutoSizeConfig.getInstance().designWidthInDp = length.also { _length = length }                //设置横屏基准
//                } else {
//                    AutoSizeConfig.getInstance().designWidthInDp = width.also { _width = width }        //设置竖屏基准
//                }
//            }
//
//            override fun onAdaptAfter(target: Any?, activity: Activity?) {}
//        }
//    }
//
//    /**
//     * 获取长
//     * @return Int
//     */
//    fun getLength(): Int = _length
//
//    /**
//     * 获取宽
//     * @return Int
//     */
//    fun getWidth(): Int = _width
//
//    ////////////////////////////////////////////////////////////////////////////////////
//
//    private object INSTANCE {
//        val holder = AdaptKAutoSize()
//    }
//}