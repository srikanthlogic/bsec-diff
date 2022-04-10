package com.surepass.surepassesign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Circle.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!H\u0014R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u001a\u0010\u0015\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000f\"\u0004\b\u0017\u0010\u0011R\u001a\u0010\u0018\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u000f\"\u0004\b\u001a\u0010\u0011R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/surepass/surepassesign/Circle;", "Landroid/view/View;", "context", "Landroid/content/Context;", "enabled", "", "check", "(Landroid/content/Context;ZZ)V", "checker", "enabler", "mPath", "Landroid/graphics/Path;", "ovalBottom", "", "getOvalBottom", "()F", "setOvalBottom", "(F)V", "ovalLeft", "getOvalLeft", "setOvalLeft", "ovalRight", "getOvalRight", "setOvalRight", "ovalTop", "getOvalTop", "setOvalTop", "strokePaint", "Landroid/graphics/Paint;", "initPaints", "", "onDraw", "canvas", "Landroid/graphics/Canvas;", "app_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes3.dex */
public final class Circle extends View {
    private HashMap _$_findViewCache;
    private final boolean checker;
    private final boolean enabler;
    private final Path mPath = new Path();
    private float ovalBottom;
    private float ovalLeft;
    private float ovalRight;
    private float ovalTop;
    private Paint strokePaint;

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
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Circle(Context context, boolean enabled, boolean check) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.enabler = enabled;
        this.checker = check;
        initPaints();
    }

    public final float getOvalLeft() {
        return this.ovalLeft;
    }

    public final void setOvalLeft(float f) {
        this.ovalLeft = f;
    }

    public final float getOvalRight() {
        return this.ovalRight;
    }

    public final void setOvalRight(float f) {
        this.ovalRight = f;
    }

    public final float getOvalTop() {
        return this.ovalTop;
    }

    public final void setOvalTop(float f) {
        this.ovalTop = f;
    }

    public final float getOvalBottom() {
        return this.ovalBottom;
    }

    public final void setOvalBottom(float f) {
        this.ovalBottom = f;
    }

    private final void initPaints() {
        this.strokePaint = new Paint();
        Paint paint = this.strokePaint;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokePaint");
        }
        paint.setStyle(Paint.Style.STROKE);
        boolean z = this.checker;
        if (z) {
            Paint paint2 = this.strokePaint;
            if (paint2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("strokePaint");
            }
            paint2.setColor(Color.rgb(21, 2, 255));
            Paint paint3 = this.strokePaint;
            if (paint3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("strokePaint");
            }
            paint3.setStrokeWidth(8.0f);
        } else if (this.enabler || z) {
            DashPathEffect effects = new DashPathEffect(new float[]{30.0f, 30.0f, 30.0f, 30.0f}, 10.0f);
            Paint paint4 = this.strokePaint;
            if (paint4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("strokePaint");
            }
            paint4.setPathEffect(effects);
            Paint paint5 = this.strokePaint;
            if (paint5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("strokePaint");
            }
            paint5.setColor(-1);
            Paint paint6 = this.strokePaint;
            if (paint6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("strokePaint");
            }
            paint6.setStrokeWidth(15.0f);
        } else {
            Paint paint7 = this.strokePaint;
            if (paint7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("strokePaint");
            }
            paint7.setColor(-1);
            Paint paint8 = this.strokePaint;
            if (paint8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("strokePaint");
            }
            paint8.setStrokeWidth(10.0f);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        super.onDraw(canvas);
        this.mPath.reset();
        int width = getWidth() / 2;
        int height = getHeight() / 3;
        int dy = getHeight() / 5;
        this.ovalTop = (float) dy;
        this.ovalLeft = 150.0f;
        this.ovalRight = ((float) getWidth()) - 150.0f;
        this.ovalBottom = ((float) ((((double) (getHeight() / 2)) * 0.9d) + ((double) dy))) + ((float) 100);
        this.mPath.addOval(this.ovalLeft, this.ovalTop, this.ovalRight, this.ovalBottom, Path.Direction.CW);
        Path path = this.mPath;
        Paint paint = this.strokePaint;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("strokePaint");
        }
        canvas.drawPath(path, paint);
        this.mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        if (this.enabler) {
            canvas.clipPath(this.mPath);
        }
        canvas.drawColor(Color.parseColor("#80000000"));
    }
}
