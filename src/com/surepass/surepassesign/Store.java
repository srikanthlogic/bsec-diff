package com.surepass.surepassesign;

import android.content.Context;
import android.content.SharedPreferences;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.common.internal.ImagesContract;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;
/* compiled from: Store.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u001a\u001a\u0004\u0018\u00010\u0006J\b\u0010\u001b\u001a\u0004\u0018\u00010\u0006J\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006J\b\u0010\u001d\u001a\u0004\u0018\u00010\u0006J\b\u0010\u001e\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0006J\u000e\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020$J\u000e\u0010%\u001a\u00020 2\u0006\u0010&\u001a\u00020$J\u0016\u0010'\u001a\u00020 2\u0006\u0010(\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u0006R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u0004R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006*"}, d2 = {"Lcom/surepass/surepassesign/Store;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "PREFS_NAME", "", "getPREFS_NAME", "()Ljava/lang/String;", "setPREFS_NAME", "(Ljava/lang/String;)V", "getContext", "()Landroid/content/Context;", "setContext", "editor", "Landroid/content/SharedPreferences$Editor;", "getEditor", "()Landroid/content/SharedPreferences$Editor;", "setEditor", "(Landroid/content/SharedPreferences$Editor;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "setSharedPreferences", "(Landroid/content/SharedPreferences;)V", "getBaseUrl", "getBrandDetails", "getConfig", "getEnv", "getToken", "setBaseUrl", "", ImagesContract.URL, "setBrandDetails", UriUtil.DATA_SCHEME, "Lorg/json/JSONObject;", "setConfig", "config", "storeToken", "token", "env", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class Store {
    private String PREFS_NAME = "credentials";
    public Context context;
    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;

    public Store(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.context = context;
        Context context2 = this.context;
        if (context2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("context");
        }
        SharedPreferences sharedPreferences = context2.getSharedPreferences(this.PREFS_NAME, 0);
        Intrinsics.checkExpressionValueIsNotNull(sharedPreferences, "this.context.getSharedPr…PREFS_NAME, MODE_PRIVATE)");
        this.sharedPreferences = sharedPreferences;
        SharedPreferences sharedPreferences2 = this.sharedPreferences;
        if (sharedPreferences2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        SharedPreferences.Editor edit = sharedPreferences2.edit();
        Intrinsics.checkExpressionValueIsNotNull(edit, "sharedPreferences.edit()");
        this.editor = edit;
    }

    public final Context getContext() {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("context");
        }
        return context;
    }

    public final void setContext(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "<set-?>");
        this.context = context;
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

    public final SharedPreferences.Editor getEditor() {
        SharedPreferences.Editor editor = this.editor;
        if (editor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        return editor;
    }

    public final void setEditor(SharedPreferences.Editor editor) {
        Intrinsics.checkParameterIsNotNull(editor, "<set-?>");
        this.editor = editor;
    }

    public final String getPREFS_NAME() {
        return this.PREFS_NAME;
    }

    public final void setPREFS_NAME(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.PREFS_NAME = str;
    }

    public final void storeToken(String token, String env) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        Intrinsics.checkParameterIsNotNull(env, "env");
        SharedPreferences.Editor editor = this.editor;
        if (editor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        editor.putString("token", token);
        SharedPreferences.Editor editor2 = this.editor;
        if (editor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        editor2.putString("env", env);
        SharedPreferences.Editor editor3 = this.editor;
        if (editor3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        editor3.commit();
    }

    public final void setBaseUrl(String url) {
        Intrinsics.checkParameterIsNotNull(url, ImagesContract.URL);
        SharedPreferences.Editor editor = this.editor;
        if (editor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        editor.putString("BASE_URL", url);
        SharedPreferences.Editor editor2 = this.editor;
        if (editor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        editor2.commit();
    }

    public final void setConfig(JSONObject config) {
        Intrinsics.checkParameterIsNotNull(config, "config");
        SharedPreferences.Editor editor = this.editor;
        if (editor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        editor.putString("config", config.toString());
        SharedPreferences.Editor editor2 = this.editor;
        if (editor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        editor2.commit();
    }

    public final void setBrandDetails(JSONObject data) {
        Intrinsics.checkParameterIsNotNull(data, UriUtil.DATA_SCHEME);
        SharedPreferences.Editor editor = this.editor;
        if (editor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        editor.putString("brand_details", data.toString());
        SharedPreferences.Editor editor2 = this.editor;
        if (editor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        }
        editor2.commit();
    }

    public final String getBaseUrl() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        return sharedPreferences.getString("BASE_URL", null);
    }

    public final String getBrandDetails() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        return sharedPreferences.getString("brand_details", null);
    }

    public final String getConfig() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        return sharedPreferences.getString("config", null);
    }

    public final String getToken() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        return sharedPreferences.getString("token", null);
    }

    public final String getEnv() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPreferences");
        }
        return sharedPreferences.getString("env", null);
    }
}
