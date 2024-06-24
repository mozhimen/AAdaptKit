package com.mozhimen.adaptk.splashscreen.test

import androidx.lifecycle.viewModelScope
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ClassName SplashViewModel
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/24
 * @Version 1.0
 */
class SplashViewModel : BaseViewModel() {
    var _somethingDelay: Boolean = false

    fun somethingDelay(): Boolean {
        viewModelScope.launch {
            delay(2500)
            _somethingDelay = true
        }
        return _somethingDelay
    }
}