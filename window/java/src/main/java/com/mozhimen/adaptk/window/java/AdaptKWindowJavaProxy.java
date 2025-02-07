package com.mozhimen.adaptk.window.java;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.lifecycle.LifecycleOwner;
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter;
import androidx.window.layout.WindowInfoTracker;
import androidx.window.layout.WindowLayoutInfo;

import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver;
import com.mozhimen.basick.utils.UtilKLifecycleOwnerWrapper;
import com.mozhimen.java.elemk.java.functions.IA_Listener;
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle;
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle;
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy;

/**
 * @ClassName AdaptKWindowJavaProxy
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/2/6
 * @Version 1.0
 */
@OApiCall_BindViewLifecycle
@OApiCall_BindLifecycle
@OApiInit_ByLazy
public class AdaptKWindowJavaProxy extends BaseWakeBefDestroyLifecycleObserver {
    private WindowInfoTrackerCallbackAdapter _windowInfoTrackerCallbackAdapter;
    private final WindowLayoutInfoCallback _windowLayoutInfoCallback = new WindowLayoutInfoCallback();
    private IA_Listener<WindowLayoutInfo> _windowLayoutInfoListener = null;

    public AdaptKWindowJavaProxy() {
        super();
    }

    public void setWindowLayoutInfoListener(IA_Listener<WindowLayoutInfo> listener) {
        _windowLayoutInfoListener = listener;
    }

    @Override
    public void bindLifecycle(@NonNull LifecycleOwner owner) {
        super.bindLifecycle(owner);
        _windowInfoTrackerCallbackAdapter = new WindowInfoTrackerCallbackAdapter(WindowInfoTracker.getOrCreate(UtilKLifecycleOwnerWrapper.requireContext(owner)));
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        super.onStart(owner);
        if (_windowInfoTrackerCallbackAdapter != null) {
            _windowInfoTrackerCallbackAdapter.addWindowLayoutInfoListener(UtilKLifecycleOwnerWrapper.requireContext(owner), Runnable::run, _windowLayoutInfoCallback);
        }
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        super.onStop(owner);
        if (_windowInfoTrackerCallbackAdapter != null && _windowLayoutInfoCallback != null) {
            _windowInfoTrackerCallbackAdapter.removeWindowLayoutInfoListener(_windowLayoutInfoCallback);
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        super.onDestroy(owner);
        _windowLayoutInfoListener = null;
    }

    class WindowLayoutInfoCallback implements Consumer<WindowLayoutInfo> {
        @Override
        public void accept(WindowLayoutInfo newLayoutInfo) {
            if (_windowLayoutInfoListener != null) {
                _windowLayoutInfoListener.invoke(newLayoutInfo);
            }
//            SplitLayoutActivity.this.runOnUiThread(() -> {
//                // Use newLayoutInfo to update the layout.
//            });
        }
    }

}
