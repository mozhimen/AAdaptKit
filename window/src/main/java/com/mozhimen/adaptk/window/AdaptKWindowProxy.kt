package com.mozhimen.adaptk.window

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.utils.requireContext
import com.mozhimen.kotlin.elemk.commons.IA_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName AdaptKWindowProxy
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/2/6
 * @Version 1.0
 */
@OApiInit_ByLazy
@OApiCall_BindLifecycle
@OApiCall_BindViewLifecycle
class AdaptKWindowProxy : BaseWakeBefDestroyLifecycleObserver() {
    private var _windowLayoutInfoListener: IA_Listener<WindowLayoutInfo>? = null

    fun setWindowLayoutInfoListener(listener: IA_Listener<WindowLayoutInfo>) {
        _windowLayoutInfoListener = listener
    }

    override fun bindLifecycle(owner: LifecycleOwner) {
        super.bindLifecycle(owner)
        owner.lifecycleScope.launch(Dispatchers.Main) {
            owner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                WindowInfoTracker.getOrCreate(owner.requireContext())
                    .windowLayoutInfo(owner.requireContext())
                    .collect { windowLayoutInfo ->
                        // Use newLayoutInfo to update the layout.
                        _windowLayoutInfoListener?.invoke(windowLayoutInfo)
                    }
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        _windowLayoutInfoListener = null
    }
}