package com.mozhimen.adaptk.test

import android.view.View
import com.mozhimen.adaptk.test.databinding.ActivityMainBinding
import com.mozhimen.bindk.bases.viewdatabinding.activity.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.content.startContext

class MainActivity : BaseActivityVDB<ActivityMainBinding>() {

    fun goSystemBar(view: View) {
        startContext<AdaptKSystemBarActivity>()
    }
}