package com.surepass.surepassesign;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.core.app.NotificationCompat;
import com.facebook.common.util.UriUtil;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
/* compiled from: UserDetails.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"com/surepass/surepassesign/UserDetails$getPrefillOptions$1", "Lokhttp3/Callback;", "onFailure", "", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "e", "Ljava/io/IOException;", "onResponse", "response", "Lokhttp3/Response;", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class UserDetails$getPrefillOptions$1 implements Callback {
    final /* synthetic */ UserDetails this$0;

    public UserDetails$getPrefillOptions$1(UserDetails $outer) {
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0073 A[Catch: Exception -> 0x0093, TryCatch #0 {Exception -> 0x0093, blocks: (B:8:0x001f, B:10:0x0067, B:16:0x0073, B:18:0x007d, B:22:0x0086), top: B:27:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    @Override // okhttp3.Callback
    /* Code decompiled incorrectly, please refer to instructions dump */
    public void onResponse(Call call, Response response) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(response, "response");
        ResponseBody body = response.body();
        String responseData = body != null ? body.string() : null;
        if (response.isSuccessful()) {
            try {
                JSONObject json = new JSONObject(responseData);
                JSONObject data = json.getJSONObject(UriUtil.DATA_SCHEME);
                JSONObject config = json.getJSONObject("config");
                Store store = this.this$0.getStore();
                Intrinsics.checkExpressionValueIsNotNull(config, "config");
                store.setConfig(config);
                this.this$0.setCLIENT_FULL_NAME(data.getString("full_name"));
                this.this$0.setCLIENT_EMAIL(data.getString("user_email"));
                this.this$0.setCLIENT_PHONE_NUMBER(data.getString("mobile_number"));
                String client_email = this.this$0.getCLIENT_EMAIL();
                boolean z2 = false;
                if (!(client_email == null || client_email.length() == 0)) {
                    z = false;
                    if (z) {
                        String client_full_name = this.this$0.getCLIENT_FULL_NAME();
                        if (client_full_name == null || client_full_name.length() == 0) {
                            z2 = true;
                        }
                        if (!z2) {
                            this.this$0.runOnUiThread(new Runnable() { // from class: com.surepass.surepassesign.UserDetails$getPrefillOptions$1$onResponse$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((EditText) UserDetails$getPrefillOptions$1.this.this$0._$_findCachedViewById(R.id.Name)).setText(UserDetails$getPrefillOptions$1.this.this$0.getCLIENT_FULL_NAME());
                                    ((EditText) UserDetails$getPrefillOptions$1.this.this$0._$_findCachedViewById(R.id.Email)).setText(UserDetails$getPrefillOptions$1.this.this$0.getCLIENT_EMAIL());
                                    Button button = (Button) UserDetails$getPrefillOptions$1.this.this$0._$_findCachedViewById(R.id.nextButton);
                                    Intrinsics.checkExpressionValueIsNotNull(button, "nextButton");
                                    button.setEnabled(true);
                                }
                            });
                            return;
                        }
                        return;
                    }
                    return;
                }
                z = true;
                if (z) {
                }
            } catch (Exception e) {
                Log.e(Constants.Companion.getESIGN_CONSOLE(), "Get options with message " + e.getMessage());
                e.printStackTrace();
                this.this$0.getDialog().closeDialog();
            }
        } else {
            Log.e(Constants.Companion.getESIGN_CONSOLE(), "else " + response.code());
            this.this$0.getDialog().closeDialog();
            this.this$0.onCaughtWrongResponse(response.code());
        }
    }
}
