package com.mozhimen.adaptk.window.rxjava3;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.window.layout.WindowInfoTracker;
import androidx.window.layout.WindowLayoutInfo;
import androidx.window.rxjava3.layout.WindowInfoTrackerRx;

import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver;
import com.mozhimen.basick.utils.UtilKLifecycleOwnerWrapper;
import com.mozhimen.java.elemk.java.functions.IA_Listener;
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle;
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle;
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * @ClassName AdaptKWindowRxjava2Proxy
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/2/6
 * @Version 1.0
 */
@OApiInit_ByLazy
@OApiCall_BindLifecycle
@OApiCall_BindViewLifecycle
public class AdaptKWindowRxjava3Proxy extends BaseWakeBefDestroyLifecycleObserver {
    public AdaptKWindowRxjava3Proxy() {
        super();
    }

    private Disposable _disposable = null;
    private Observable<WindowLayoutInfo> _observable = null;
    private IA_Listener<WindowLayoutInfo> _windowLayoutInfoListener = null;

    public void setWindowLayoutInfoListener(IA_Listener<WindowLayoutInfo> listener) {
        _windowLayoutInfoListener = listener;
    }

    @Override
    public void bindLifecycle(@NonNull LifecycleOwner owner) {
        super.bindLifecycle(owner);
        // Create a new observable.
        _observable = WindowInfoTrackerRx.windowLayoutInfoObservable(WindowInfoTracker.getOrCreate(UtilKLifecycleOwnerWrapper.requireContext(owner)), UtilKLifecycleOwnerWrapper.requireContext(owner));
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        super.onStart(owner);
        // Subscribe to receive WindowLayoutInfo updates.
        if (_disposable != null) {
            _disposable.dispose();
        }
        if (_observable != null) {
            _disposable = _observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<WindowLayoutInfo>() {
                        @Override
                        public void accept(WindowLayoutInfo windowLayoutInfo) throws Exception {
                            if (_windowLayoutInfoListener != null) {
                                _windowLayoutInfoListener.invoke(windowLayoutInfo);
                            }
                        }
                    });
        }
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        super.onStop(owner);
        // Dispose of the WindowLayoutInfo observable.
        if (_disposable != null) {
            _disposable.dispose();
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        super.onDestroy(owner);
        _windowLayoutInfoListener = null;
    }
}