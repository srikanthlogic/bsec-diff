package com.camerakit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;
/* loaded from: classes.dex */
public abstract class GestureLayout extends FrameLayout {
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.camerakit.GestureLayout.1
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent e) {
            GestureLayout.this.performTap(e.getX() / ((float) GestureLayout.this.getWidth()), e.getY() / ((float) GestureLayout.this.getHeight()));
            return super.onSingleTapConfirmed(e);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent e) {
            GestureLayout.this.performDoubleTap(e.getX() / ((float) GestureLayout.this.getWidth()), e.getY() / ((float) GestureLayout.this.getHeight()));
            return super.onDoubleTap(e);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent e) {
            GestureLayout.this.performLongTap(e.getX() / ((float) GestureLayout.this.getWidth()), e.getY() / ((float) GestureLayout.this.getHeight()));
        }
    };
    private ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.camerakit.GestureLayout.2
        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector detector) {
            GestureLayout.this.performPinch(detector.getCurrentSpanX() - detector.getPreviousSpanX(), detector.getCurrentSpanY() - detector.getPreviousSpanY());
            return true;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    };

    protected abstract void onDoubleTap(float f, float f2);

    protected abstract void onLongTap(float f, float f2);

    protected abstract void onPinch(float f, float f2, float f3);

    protected abstract void onTap(float f, float f2);

    public GestureLayout(Context context) {
        super(context);
        initialize();
    }

    public GestureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public GestureLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        this.mScaleGestureDetector = new ScaleGestureDetector(getContext(), this.mScaleGestureListener);
        this.mGestureDetector = new GestureDetector(getContext(), this.mGestureListener);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        this.mGestureDetector.onTouchEvent(event);
        this.mScaleGestureDetector.onTouchEvent(event);
        return true;
    }

    public void performTap(float x, float y) {
        onTap(x, y);
    }

    public void performLongTap(float x, float y) {
        onLongTap(x, y);
    }

    public void performDoubleTap(float x, float y) {
        onDoubleTap(x, y);
    }

    public void performPinch(float dsx, float dsy) {
        onPinch((float) Math.sqrt((double) ((dsx * dsx) + (dsy * dsy))), dsx, dsy);
    }
}
