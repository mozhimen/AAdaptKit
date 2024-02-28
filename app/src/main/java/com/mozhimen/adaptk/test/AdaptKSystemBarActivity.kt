package com.mozhimen.adaptk.test

import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.adaptk.systembar.annors.AAdaptKSystemBarProperty
import com.mozhimen.adaptk.systembar.annors.AAdaptKSystemBarPropertyOr
import com.mozhimen.adaptk.systembar.cons.CProperty
import com.mozhimen.adaptk.systembar.cons.CPropertyOr
import com.mozhimen.adaptk.systembar.initAdaptKSystemBar
import com.mozhimen.adaptk.test.databinding.ActivityAdaptkSystembarBinding

//简单用法, 直接使用预制的属性
//@ASenseKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
//高级用法自己组合
@AAdaptKSystemBarProperty(CProperty.NORMAL)
@AAdaptKSystemBarPropertyOr(CPropertyOr.IMMERSED_OPEN, CPropertyOr.HIDE_STATUS_BAR, CPropertyOr.HIDE_NAVIGATION_BAR)
class AdaptKSystemBarActivity : BaseActivityVB<ActivityAdaptkSystembarBinding>() {
    override fun initFlag() {
        initAdaptKSystemBar()
    }
}