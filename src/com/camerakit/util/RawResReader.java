package com.camerakit.util;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
/* compiled from: RawResReader.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/camerakit/util/RawResReader;", "", "context", "Landroid/content/Context;", "resId", "", "(Landroid/content/Context;I)V", "value", "", "getValue", "()Ljava/lang/String;", "camerakit_release"}, k = 1, mv = {1, 1, 13})
/* loaded from: classes.dex */
public final class RawResReader {
    private final String value;

    /* JADX WARN: Finally extract failed */
    public RawResReader(Context context, int resId) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        InputStream inputStream = context.getResources().openRawResource(resId);
        Intrinsics.checkExpressionValueIsNotNull(inputStream, "inputStream");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
        BufferedReader it = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        Throwable th = null;
        try {
            String readText = TextStreamsKt.readText(it);
            CloseableKt.closeFinally(it, th);
            this.value = readText;
        } finally {
            try {
                throw th;
            } catch (Throwable th2) {
            }
        }
    }

    public final String getValue() {
        return this.value;
    }
}
