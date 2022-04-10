package com.surepass.surepassesign;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: WarningDialog.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0006\u0010\u000e\u001a\u00020\u0006R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u000f"}, d2 = {"Lcom/surepass/surepassesign/WarningDialog;", "", "context", "Landroid/content/Context;", "positiveCallback", "Lkotlin/Function0;", "", "(Landroid/content/Context;Lkotlin/jvm/functions/Function0;)V", "dialog", "Landroid/app/AlertDialog$Builder;", "getDialog", "()Landroid/app/AlertDialog$Builder;", "setDialog", "(Landroid/app/AlertDialog$Builder;)V", "showDialog", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class WarningDialog {
    private AlertDialog.Builder dialog;

    public WarningDialog(Context context, final Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(function0, "positiveCallback");
        this.dialog = new AlertDialog.Builder(context);
        this.dialog.setTitle("Cancel");
        this.dialog.setMessage("Do you want to cancel ?");
        this.dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.surepass.surepassesign.WarningDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialog, int which) {
                Function0.this.invoke();
            }
        });
        this.dialog.setNegativeButton("Cancel", AnonymousClass2.INSTANCE);
        this.dialog.create();
        this.dialog.setCancelable(false);
    }

    public final AlertDialog.Builder getDialog() {
        return this.dialog;
    }

    public final void setDialog(AlertDialog.Builder builder) {
        Intrinsics.checkParameterIsNotNull(builder, "<set-?>");
        this.dialog = builder;
    }

    public final void showDialog() {
        this.dialog.show();
    }
}
