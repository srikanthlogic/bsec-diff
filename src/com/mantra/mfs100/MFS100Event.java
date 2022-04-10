package com.mantra.mfs100;
/* loaded from: classes3.dex */
public interface MFS100Event {
    void OnDeviceAttached(int i, int i2, boolean z);

    void OnDeviceDetached();

    void OnHostCheckFailed(String str);
}
