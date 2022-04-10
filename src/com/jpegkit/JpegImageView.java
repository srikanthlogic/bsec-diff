package com.jpegkit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
/* loaded from: classes3.dex */
public class JpegImageView extends ImageView {
    private Bitmap mBitmap;
    private int mInSampleSize = 1;
    private Jpeg mJpeg;

    public JpegImageView(Context context) {
        super(context);
    }

    public JpegImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JpegImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JpegImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        adjustSize(w, h);
    }

    private void adjustSize(int w, int h) {
        Jpeg jpeg;
        if (w > 0 && h > 0 && (jpeg = this.mJpeg) != null) {
            int jpegWidth = jpeg.getWidth();
            int jpegHeight = this.mJpeg.getHeight();
            if (((float) (jpegWidth * jpegHeight)) > ((float) (w * h)) * 1.5f) {
                float widthRatio = ((float) w) / ((float) jpegWidth);
                float heightRatio = ((float) h) / ((float) jpegHeight);
                BitmapFactory.Options options = new BitmapFactory.Options();
                if (widthRatio <= heightRatio) {
                    options.inSampleSize = (int) (1.0f / widthRatio);
                } else {
                    options.inSampleSize = (int) (1.0f / heightRatio);
                }
                if (options.inSampleSize != this.mInSampleSize || this.mBitmap == null) {
                    this.mInSampleSize = options.inSampleSize;
                    byte[] jpegBytes = this.mJpeg.getJpegBytes();
                    this.mBitmap = BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.length, options);
                    setImageBitmap(this.mBitmap);
                }
            } else if (this.mBitmap == null) {
                byte[] jpegBytes2 = this.mJpeg.getJpegBytes();
                this.mBitmap = BitmapFactory.decodeByteArray(jpegBytes2, 0, jpegBytes2.length);
                setImageBitmap(this.mBitmap);
            }
        }
    }

    public void setJpeg(Jpeg jpeg) {
        setImageBitmap(null);
        this.mJpeg = jpeg;
        this.mInSampleSize = 1;
        this.mBitmap = null;
        adjustSize(getWidth(), getHeight());
    }
}
