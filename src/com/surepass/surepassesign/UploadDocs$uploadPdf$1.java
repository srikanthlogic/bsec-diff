package com.surepass.surepassesign;

import androidx.core.app.NotificationCompat;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/* compiled from: UploadDocs.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"com/surepass/surepassesign/UploadDocs$uploadPdf$1", "Lokhttp3/Callback;", "onFailure", "", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "e", "Ljava/io/IOException;", "onResponse", "response", "Lokhttp3/Response;", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class UploadDocs$uploadPdf$1 implements Callback {
    final /* synthetic */ UploadDocs this$0;

    public UploadDocs$uploadPdf$1(UploadDocs $outer) {
        this.this$0 = $outer;
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, IOException e) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(e, "e");
        e.printStackTrace();
        this.this$0.getDialog().closeDialog();
        this.this$0.onCaughtFailureException(e);
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(response, "response");
        if (response.isSuccessful()) {
            try {
                this.this$0.getHandler().postDelayed(new Runnable() { // from class: com.surepass.surepassesign.UploadDocs$uploadPdf$1$onResponse$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (Intrinsics.areEqual(UploadDocs$uploadPdf$1.this.this$0.getSIGN_TYPE(), "suresign")) {
                            UploadDocs$uploadPdf$1.this.this$0.getSuresign();
                        } else {
                            UploadDocs$uploadPdf$1.this.this$0.getXmlRequestPerformed();
                        }
                    }
                }, 5000);
            } catch (Exception e) {
                e.printStackTrace();
                this.this$0.getDialog().closeDialog();
                this.this$0.onCaughtFailureException(e);
            }
        } else {
            this.this$0.getDialog().closeDialog();
            this.this$0.onCaughtWrongResponse(response.code());
        }
    }
}
