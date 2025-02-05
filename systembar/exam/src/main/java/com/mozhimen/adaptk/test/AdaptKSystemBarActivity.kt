package com.mozhimen.adaptk.test

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mozhimen.adaptk.systembar.annors.AAdaptKSystemBarProperty
import com.mozhimen.adaptk.systembar.annors.AAdaptKSystemBarPropertyAnd
import com.mozhimen.adaptk.systembar.cons.CProperty
import com.mozhimen.adaptk.systembar.cons.CPropertyAnd
import com.mozhimen.adaptk.systembar.initAdaptKSystemBar
import com.mozhimen.adaptk.test.databinding.ActivityAdaptkSystembarBinding
import com.mozhimen.uik.databinding.bases.viewdatabinding.activity.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.app.getContentView
import com.mozhimen.kotlin.utilk.android.app.getDecorView

//简单用法, 直接使用预制的属性
//@AAdaptKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
//高级用法自己组合
@AAdaptKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
@AAdaptKSystemBarPropertyAnd(CPropertyAnd.NO_OVERLAY_NAVIGATION_BAR,CPropertyAnd.NO_OVERLAY_STATUS_BAR)
class AdaptKSystemBarActivity : BaseActivityVDB<ActivityAdaptkSystembarBinding>() {
    override fun initFlag() {
        initAdaptKSystemBar()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        printViewInfo(hasFocus)
    }

    private fun printViewInfo(hasFocus: Boolean?){
        Log.d(TAG, "onWindowFocusChanged: hasFocus $hasFocus")
        Log.d(TAG, "onWindowFocusChanged: decorview height ${getDecorView<View>().height}")
        Log.d(TAG, "onWindowFocusChanged: contentView height ${getContentView<View>().height}")
        Log.d(TAG, "onWindowFocusChanged: rootView height ${findViewById<ConstraintLayout>(R.id.root)?.height}")
        Log.d(TAG, "onWindowFocusChanged: view1 y ${vdb.adaptkTxtLeftBottom.y}")
        Log.d(TAG, "onWindowFocusChanged: view2 y ${vdb.view.height}")
    }

    override fun initView(savedInstanceState: Bundle?) {
        vdb.adaptkTxt.post {
            printViewInfo(null)
        }
        vdb.adaptkTxt.setOnClickListener {
            AlertDialog.Builder(this).show()
        }
    }
}