package com.surepass.surepassesign;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Dialog.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\fR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000e"}, d2 = {"Lcom/surepass/surepassesign/Dialog;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dialog", "Landroid/app/AlertDialog;", "getDialog", "()Landroid/app/AlertDialog;", "setDialog", "(Landroid/app/AlertDialog;)V", "closeDialog", "", "showDialog", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class Dialog {
    private AlertDialog dialog;

    public Dialog(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(50, 50, 50, 50);
        linearLayout.setGravity(17);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.gravity = 17;
        linearLayout.setLayoutParams(params);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(-2, -2);
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 50, 50, 50);
        progressBar.setIndeterminateTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button)));
        progressBar.setLayoutParams(params2);
        params2.gravity = 17;
        TextView title = new TextView(context);
        title.setText("Loading...");
        title.setTextColor(Color.parseColor("#000000"));
        title.setPadding(0, 0, 0, 30);
        title.setTextSize((float) 20);
        title.setLayoutParams(params2);
        linearLayout.addView(progressBar);
        linearLayout.addView(title);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(linearLayout);
        AlertDialog create = builder.create();
        Intrinsics.checkExpressionValueIsNotNull(create, "builder.create()");
        this.dialog = create;
        this.dialog.setCanceledOnTouchOutside(false);
        if (this.dialog.getWindow() != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            Window window = this.dialog.getWindow();
            if (window == null) {
                Intrinsics.throwNpe();
            }
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = -2;
            layoutParams.height = -2;
            AlertDialog alertDialog = this.dialog;
            if (alertDialog == null) {
                Intrinsics.throwNpe();
            }
            Window window2 = alertDialog.getWindow();
            if (window2 == null) {
                Intrinsics.throwNpe();
            }
            window2.setAttributes(layoutParams);
        }
    }

    public final AlertDialog getDialog() {
        return this.dialog;
    }

    public final void setDialog(AlertDialog alertDialog) {
        Intrinsics.checkParameterIsNotNull(alertDialog, "<set-?>");
        this.dialog = alertDialog;
    }

    public final void showDialog() {
        this.dialog.show();
    }

    public final void closeDialog() {
        this.dialog.dismiss();
    }
}
