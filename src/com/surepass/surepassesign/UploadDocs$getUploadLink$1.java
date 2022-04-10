package com.surepass.surepassesign;

import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.core.app.NotificationCompat;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
/* compiled from: UploadDocs.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"com/surepass/surepassesign/UploadDocs$getUploadLink$1", "Lokhttp3/Callback;", "onFailure", "", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "e", "Ljava/io/IOException;", "onResponse", "response", "Lokhttp3/Response;", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class UploadDocs$getUploadLink$1 implements Callback {
    final /* synthetic */ UploadDocs this$0;

    public UploadDocs$getUploadLink$1(UploadDocs $outer) {
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
        Log.e(this.this$0.getTAG(), responseData);
        if (response.isSuccessful()) {
            this.this$0.getDialog().closeDialog();
            try {
                JSONObject dataFromOTP = new JSONObject(responseData).getJSONObject(UriUtil.DATA_SCHEME);
                UploadDocs uploadDocs = this.this$0;
                String string = dataFromOTP.getString(ImagesContract.URL);
                Intrinsics.checkExpressionValueIsNotNull(string, "dataFromOTP.getString(\"url\")");
                uploadDocs.setUrlForUploadingData(string);
                this.this$0.setUploaded(dataFromOTP.getBoolean("is_uploaded"));
                this.this$0.runOnUiThread(new Runnable(dataFromOTP) { // from class: com.surepass.surepassesign.UploadDocs$getUploadLink$1$onResponse$1
                    final /* synthetic */ JSONObject $dataFromOTP;

                    /* JADX INFO: Access modifiers changed from: package-private */
                    {
                        this.$dataFromOTP = r2;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        if (UploadDocs$getUploadLink$1.this.this$0.isUploaded()) {
                            RelativeLayout relativeLayout = (RelativeLayout) UploadDocs$getUploadLink$1.this.this$0._$_findCachedViewById(R.id.uploadingView);
                            Intrinsics.checkExpressionValueIsNotNull(relativeLayout, "uploadingView");
                            relativeLayout.setClickable(false);
                            RelativeLayout relativeLayout2 = (RelativeLayout) UploadDocs$getUploadLink$1.this.this$0._$_findCachedViewById(R.id.uploadingView);
                            Intrinsics.checkExpressionValueIsNotNull(relativeLayout2, "uploadingView");
                            relativeLayout2.setEnabled(false);
                            ((Button) UploadDocs$getUploadLink$1.this.this$0._$_findCachedViewById(R.id.uploadDoc)).setText("Next");
                            if (UploadDocs$getUploadLink$1.this.this$0.getSIGN_TYPE() != "suresign") {
                                UploadDocs$getUploadLink$1.this.this$0.showPreuploadedXml(UploadDocs$getUploadLink$1.this.this$0.getUrlForUploadingData());
                                return;
                            }
                            return;
                        }
                        ((Button) UploadDocs$getUploadLink$1.this.this$0._$_findCachedViewById(R.id.uploadDoc)).setText("Upload Now");
                        RelativeLayout relativeLayout3 = (RelativeLayout) UploadDocs$getUploadLink$1.this.this$0._$_findCachedViewById(R.id.uploadingView);
                        Intrinsics.checkExpressionValueIsNotNull(relativeLayout3, "uploadingView");
                        relativeLayout3.setEnabled(true);
                        RelativeLayout relativeLayout4 = (RelativeLayout) UploadDocs$getUploadLink$1.this.this$0._$_findCachedViewById(R.id.uploadingView);
                        Intrinsics.checkExpressionValueIsNotNull(relativeLayout4, "uploadingView");
                        relativeLayout4.setClickable(true);
                        JSONObject fieldsWithUploadingData = this.$dataFromOTP.getJSONObject("fields");
                        UploadDocs uploadDocs2 = UploadDocs$getUploadLink$1.this.this$0;
                        Object fromJson = new Gson().fromJson(fieldsWithUploadingData.toString(), (Class<Object>) HashMap.class);
                        Intrinsics.checkExpressionValueIsNotNull(fromJson, "Gson().fromJson(fieldsWi…g(), HashMap::class.java)");
                        uploadDocs2.setHashmapOfFields((HashMap) fromJson);
                    }
                });
            } catch (Exception e) {
                this.this$0.getDialog().closeDialog();
                e.printStackTrace();
                this.this$0.onCaughtFailureException(e);
            }
        } else {
            String tag = this.this$0.getTAG();
            Log.e(tag, "response " + response.message());
            this.this$0.getDialog().closeDialog();
            this.this$0.onCaughtWrongResponse(response.code());
        }
    }
}
