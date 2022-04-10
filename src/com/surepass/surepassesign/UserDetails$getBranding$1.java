package com.surepass.surepassesign;

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
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"com/surepass/surepassesign/UserDetails$getBranding$1", "Lokhttp3/Callback;", "onFailure", "", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "e", "Ljava/io/IOException;", "onResponse", "response", "Lokhttp3/Response;", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class UserDetails$getBranding$1 implements Callback {
    final /* synthetic */ UserDetails this$0;

    public UserDetails$getBranding$1(UserDetails $outer) {
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
        ResponseBody body = response.body();
        String responseData = body != null ? body.string() : null;
        if (response.isSuccessful()) {
            try {
                JSONObject data = new JSONObject(responseData).getJSONObject(UriUtil.DATA_SCHEME);
                this.this$0.setBRAND_NAME(data.getString("brand_name"));
                this.this$0.setBRAND_LOGO_URL(data.getString("brand_image_url"));
                this.this$0.runOnUiThread(new Runnable() { // from class: com.surepass.surepassesign.UserDetails$getBranding$1$onResponse$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        String brand_logo_url = UserDetails$getBranding$1.this.this$0.getBRAND_LOGO_URL();
                        boolean z = false;
                        if (!(brand_logo_url == null || brand_logo_url.length() == 0)) {
                            UserDetails$getBranding$1.this.this$0.getEditor().putString("brand_image_url", UserDetails$getBranding$1.this.this$0.getBRAND_LOGO_URL());
                            UserDetails$getBranding$1.this.this$0.setAppLogo();
                            return;
                        }
                        String brand_name = UserDetails$getBranding$1.this.this$0.getBRAND_NAME();
                        if (brand_name == null || brand_name.length() == 0) {
                            z = true;
                        }
                        if (!z) {
                            UserDetails$getBranding$1.this.this$0.getEditor().putString("brand_name", UserDetails$getBranding$1.this.this$0.getBRAND_NAME());
                            UserDetails$getBranding$1.this.this$0.getEditor().commit();
                            UserDetails$getBranding$1.this.this$0.setAppName();
                            return;
                        }
                        UserDetails$getBranding$1.this.this$0.setDefaultLogo();
                    }
                });
                this.this$0.getDialog().closeDialog();
            } catch (Exception e) {
                e.printStackTrace();
                this.this$0.getDialog().closeDialog();
            }
        } else {
            this.this$0.onCaughtWrongResponse(response.code());
            this.this$0.getDialog().closeDialog();
        }
    }
}
