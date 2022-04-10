package com.surepass.surepassesign;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ResponsiveDimensions.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0006R\u0014\u0010\u0005\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u0006X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u0004¨\u0006\u0011"}, d2 = {"Lcom/surepass/surepassesign/ResponsiveDimensions;", "", "windowManager", "Landroid/view/WindowManager;", "(Landroid/view/WindowManager;)V", "HEIGHT", "", "getHEIGHT", "()I", "WIDTH", "getWIDTH", "getWindowManager", "()Landroid/view/WindowManager;", "setWindowManager", "getResponsiveSize", "", "size", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class ResponsiveDimensions {
    public WindowManager windowManager;
    private final int WIDTH = 1125;
    private final int HEIGHT = 2436;

    public ResponsiveDimensions(WindowManager windowManager) {
        Intrinsics.checkParameterIsNotNull(windowManager, "windowManager");
        this.windowManager = windowManager;
    }

    public final WindowManager getWindowManager() {
        WindowManager windowManager = this.windowManager;
        if (windowManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        return windowManager;
    }

    public final void setWindowManager(WindowManager windowManager) {
        Intrinsics.checkParameterIsNotNull(windowManager, "<set-?>");
        this.windowManager = windowManager;
    }

    public final int getWIDTH() {
        return this.WIDTH;
    }

    public final int getHEIGHT() {
        return this.HEIGHT;
    }

    public final float getResponsiveSize(int size) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = this.windowManager;
        if (windowManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("windowManager");
        }
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int i = displayMetrics.heightPixels;
        int i2 = this.WIDTH;
        if (width > i2) {
            return (float) size;
        }
        if (size == 48) {
            return ((float) ((size * width) / i2)) + ((float) 10);
        }
        return ((float) ((size * width) / i2)) + ((float) 3);
    }
}
