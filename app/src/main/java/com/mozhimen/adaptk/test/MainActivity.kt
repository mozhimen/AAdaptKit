package com.mozhimen.adaptk.test

import android.view.View
import com.mozhimen.adaptk.test.databinding.ActivityMainBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.content.startContext

class MainActivity : BaseActivityVDB<ActivityMainBinding>() {

    fun goSystemBar(view: View) {
        startContext<AdaptKSystemBarActivity>()
    }
}