package com.surepass.surepassesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.chaos.view.PinView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
/* compiled from: OTP.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 @2\u00020\u00012\u00020\u0002:\u0001@B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u000104H\u0016J&\u00105\u001a\u0004\u0018\u0001062\u0006\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010:2\b\u00103\u001a\u0004\u0018\u000104H\u0016J\u0006\u0010;\u001a\u000202J\u0006\u0010<\u001a\u000202J\u0006\u0010=\u001a\u000202J\u0010\u0010>\u001a\u0002022\b\u0010?\u001a\u0004\u0018\u00010\u0005R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\u00020\u000eX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020,X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100¨\u0006A"}, d2 = {"Lcom/surepass/surepassesign/OTP;", "Landroidx/fragment/app/Fragment;", "Ljava/io/Serializable;", "()V", "BRAND_LOGO_URL", "", "getBRAND_LOGO_URL", "()Ljava/lang/String;", "setBRAND_LOGO_URL", "(Ljava/lang/String;)V", "BRAND_NAME", "getBRAND_NAME", "setBRAND_NAME", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "setClient", "(Lokhttp3/OkHttpClient;)V", "dialog", "Lcom/surepass/surepassesign/Dialog;", "getDialog", "()Lcom/surepass/surepassesign/Dialog;", "setDialog", "(Lcom/surepass/surepassesign/Dialog;)V", "imageView", "Landroid/widget/ImageView;", "getImageView", "()Landroid/widget/ImageView;", "setImageView", "(Landroid/widget/ImageView;)V", "request", "Lcom/surepass/surepassesign/OkHttpRequest;", "getRequest", "()Lcom/surepass/surepassesign/OkHttpRequest;", "setRequest", "(Lcom/surepass/surepassesign/OkHttpRequest;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "setSharedPreferences", "(Landroid/content/SharedPreferences;)V", "store", "Lcom/surepass/surepassesign/Store;", "getStore", "()Lcom/surepass/surepassesign/Store;", "setStore", "(Lcom/surepass/surepassesign/Store;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "setAppLogo", "setAppName", "setDefaultLogo", "verifyOTP", "otp", "Companion", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class OTP extends Fragment implements Serializable {
    public static final Companion Companion = new Companion(null);
    private String BRAND_LOGO_URL;
    private String BRAND_NAME;
    private HashMap _$_findViewCache;
    public OkHttpClient client;
    public Dialog dialog;
    public ImageView imageView;
    public OkHttpRequest request;
    public SharedPreferences sharedPreferences;
    public Store store;

    @JvmStatic
    public static final OTP newInstance() {
        return Companion.newInstance();
    }

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View view2 = getView();
        if (view2 == null) {
            return null;
        }
        View findViewById = view2.findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    @Override // androidx.fragment.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    public final OkHttpClient getClient() {
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        return okHttpClient;
    }

    public final void setClient(OkHttpClient okHttpClient) {
        Intrinsics.checkParameterIsNotNull(okHttpClient, "<set-?>");
        this.client = okHttpClient;
    }

    public final OkHttpRequest getRequest() {
        OkHttpRequest okHttpRequest = this.request;
        if (okHttpRequest == null) {
            Intrinsics.throwUninitializedPropertyAccessException("request");
        }
        return okHttpRequest;
    }

    public final void setRequest(OkHttpRequest okHttpRequest) {
        Intrinsics.checkParameterIsNotNull(okHttpRequest, "<set-?>");
        this.request = okHttpRequest;
    }

    public final Dialog getDialog() {
        Dialog dialog = this.dialog;
        if (dialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
        }
        return dialog;
    }

    public final void setDialog(Dialog dialog) {
        Intrinsics.checkParameterIsNotNull(dialog, "<set-?>");
        this.dialog = dialog;
    }

    public final String getBRAND_LOGO_URL() {
        return this.BRAND_LOGO_URL;
    }

    public final void setBRAND_LOGO_URL(String str) {
        this.BRAND_LOGO_URL = str;
    }

    public final String getBRAND_NAME() {
        return this.BRAND_NAME;
    }

    public final void setBRAND_NAME(String str) {
        this.BRAND_NAME = str;
    }

    public final Store getStore() {
        Store store = this.store;
        if (store == null) {
            Intrinsics.throwUninitializedPropertyAccessException("store");
        }
        return store;
    }

    public final void setStore(Store store) {
        Intrinsics.checkParameterIsNotNull(store, "<set-?>");
        this.store = store;
    }

    public final SharedPreferences getSharedPreferences() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        return sharedPreferences;
    }

    public final void setSharedPreferences(SharedPreferences sharedPreferences) {
        Intrinsics.checkParameterIsNotNull(sharedPreferences, "<set-?>");
        this.sharedPreferences = sharedPreferences;
    }

    public final ImageView getImageView() {
        ImageView imageView = this.imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageView");
        }
        return imageView;
    }

    public final void setImageView(ImageView imageView) {
        Intrinsics.checkParameterIsNotNull(imageView, "<set-?>");
        this.imageView = imageView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.client = new OkHttpClient();
        OkHttpClient okHttpClient = this.client;
        if (okHttpClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("client");
        }
        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.request = new OkHttpRequest(okHttpClient, (PhoneNumber) activity);
            FragmentActivity activity2 = getActivity();
            if (activity2 != null) {
                this.dialog = new Dialog((PhoneNumber) activity2);
                FragmentActivity activity3 = getActivity();
                if (activity3 != null) {
                    this.store = new Store((PhoneNumber) activity3);
                    try {
                        Store store = this.store;
                        if (store == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("store");
                        }
                        String brandDetails = store.getBrandDetails();
                        this.BRAND_LOGO_URL = new JSONObject(brandDetails).getString("brand_image_url");
                        this.BRAND_NAME = new JSONObject(brandDetails).getString("brand_name");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type com.surepass.surepassesign.PhoneNumber");
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type com.surepass.surepassesign.PhoneNumber");
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type com.surepass.surepassesign.PhoneNumber");
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        FragmentActivity activity = getActivity();
        if (activity != null) {
            WindowManager windowManager = ((PhoneNumber) activity).getWindowManager();
            Intrinsics.checkExpressionValueIsNotNull(windowManager, "(activity as PhoneNumber).windowManager");
            ResponsiveDimensions responseDimensions = new ResponsiveDimensions(windowManager);
            boolean z = false;
            View View = inflater.inflate(R.layout.fragment_ot, container, false);
            Bundle arguments = getArguments();
            if (arguments == null) {
                Intrinsics.throwNpe();
            }
            String phoneNumber = arguments.getString("phone");
            Button button = (Button) View.findViewById(R.id.getOTPbutton);
            View findViewById = View.findViewById(R.id.otpLogoView);
            Intrinsics.checkExpressionValueIsNotNull(findViewById, "View.findViewById<ImageView>(R.id.otpLogoView)");
            this.imageView = (ImageView) findViewById;
            PinView otpView = (PinView) View.findViewById(R.id.otpView);
            TextView warningText = (TextView) View.findViewById(R.id.description);
            TextView sliderTitle = (TextView) View.findViewById(R.id.sliderTitle);
            Intrinsics.checkExpressionValueIsNotNull(sliderTitle, "sliderTitle");
            sliderTitle.setTextSize(responseDimensions.getResponsiveSize(20));
            Intrinsics.checkExpressionValueIsNotNull(warningText, "warningText");
            warningText.setTextSize(responseDimensions.getResponsiveSize(12));
            String str = this.BRAND_LOGO_URL;
            if (!(str == null || str.length() == 0)) {
                setAppLogo();
            } else {
                String str2 = this.BRAND_NAME;
                if (str2 == null || str2.length() == 0) {
                    z = true;
                }
                if (!z) {
                    setAppName();
                } else {
                    setDefaultLogo();
                }
            }
            warningText.setText("Code sent to " + phoneNumber);
            button.setOnClickListener(new View.OnClickListener(otpView) { // from class: com.surepass.surepassesign.OTP$onCreateView$1
                final /* synthetic */ PinView $otpView;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.$otpView = r2;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View it) {
                    PinView pinView = this.$otpView;
                    Intrinsics.checkExpressionValueIsNotNull(pinView, "otpView");
                    OTP.this.verifyOTP(String.valueOf(pinView.getText()));
                }
            });
            return View;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.surepass.surepassesign.PhoneNumber");
    }

    public final void verifyOTP(String otp) {
        HashMap map = MapsKt.hashMapOf(TuplesKt.to("otp", otp), TuplesKt.to("consent", "OK"), TuplesKt.to("consent_text", "OK"));
        FragmentActivity activity = getActivity();
        if (activity != null) {
            String url = Intrinsics.stringPlus(((PhoneNumber) activity).getBASEURL(), "verify-otp");
            Dialog dialog = this.dialog;
            if (dialog == null) {
                Intrinsics.throwUninitializedPropertyAccessException("dialog");
            }
            dialog.showDialog();
            OkHttpRequest okHttpRequest = this.request;
            if (okHttpRequest == null) {
                Intrinsics.throwUninitializedPropertyAccessException("request");
            }
            okHttpRequest.POST(url, map, new Callback() { // from class: com.surepass.surepassesign.OTP$verifyOTP$1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException e) {
                    Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                    Intrinsics.checkParameterIsNotNull(e, "e");
                    new Intent();
                    e.printStackTrace();
                    OTP.this.getDialog().closeDialog();
                    FragmentActivity activity2 = OTP.this.getActivity();
                    if (activity2 != null) {
                        ((PhoneNumber) activity2).onCaughtFailureException(e);
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type com.surepass.surepassesign.PhoneNumber");
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) {
                    Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
                    Intrinsics.checkParameterIsNotNull(response, "response");
                    ResponseBody body = response.body();
                    if (body != null) {
                        body.string();
                    }
                    if (response.isSuccessful()) {
                        try {
                            OTP.this.getDialog().closeDialog();
                            FragmentActivity activity2 = OTP.this.getActivity();
                            if (activity2 != null) {
                                ((PhoneNumber) activity2).openDocUploadActivity();
                                return;
                            }
                            throw new TypeCastException("null cannot be cast to non-null type com.surepass.surepassesign.PhoneNumber");
                        } catch (JSONException e) {
                            OTP.this.getDialog().closeDialog();
                            e.printStackTrace();
                            FragmentActivity activity3 = OTP.this.getActivity();
                            if (activity3 != null) {
                                ((PhoneNumber) activity3).onCaughtFailureException(e);
                                return;
                            }
                            throw new TypeCastException("null cannot be cast to non-null type com.surepass.surepassesign.PhoneNumber");
                        }
                    } else {
                        OTP.this.getDialog().closeDialog();
                        FragmentActivity activity4 = OTP.this.getActivity();
                        if (activity4 != null) {
                            ((PhoneNumber) activity4).onCaughtWrongResponse(response.code());
                            return;
                        }
                        throw new TypeCastException("null cannot be cast to non-null type com.surepass.surepassesign.PhoneNumber");
                    }
                }
            });
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.surepass.surepassesign.PhoneNumber");
    }

    public final void setAppLogo() {
        RequestCreator load = Picasso.get().load(this.BRAND_LOGO_URL);
        ImageView imageView = this.imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageView");
        }
        load.into(imageView);
    }

    public final void setDefaultLogo() {
        RequestCreator load = Picasso.get().load(R.drawable.surepass);
        ImageView imageView = this.imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageView");
        }
        load.into(imageView);
    }

    public final void setAppName() {
        ImageView imageView = this.imageView;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imageView");
        }
        imageView.setVisibility(0);
    }

    /* compiled from: OTP.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, d2 = {"Lcom/surepass/surepassesign/OTP$Companion;", "", "()V", "newInstance", "Lcom/surepass/surepassesign/OTP;", "app_release"}, k = 1, mv = {1, 1, 15})
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @JvmStatic
        public final OTP newInstance() {
            return new OTP();
        }
    }
}
