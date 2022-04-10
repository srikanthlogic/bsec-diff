package com.camerakit;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.camerakit.CameraPreview;
import com.camerakit.type.CameraFacing;
import com.camerakit.type.CameraFlash;
import com.camerakit.type.CameraSize;
import com.jpegkit.Jpeg;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class CameraKitView extends GestureLayout {
    public static final int PERMISSION_CAMERA = 1;
    public static final int PERMISSION_LOCATION = 8;
    public static final int PERMISSION_MICROPHONE = 2;
    private static final int PERMISSION_REQUEST_CODE = 99107;
    public static final int PERMISSION_STORAGE = 4;
    private static CameraFacing cameraFacing;
    private static CameraFlash cameraFlash;
    private boolean mAdjustViewBounds;
    private float mAspectRatio;
    private CameraListener mCameraListener;
    private CameraPreview mCameraPreview;
    private ErrorListener mErrorListener;
    private int mFacing;
    private int mFlash;
    private int mFocus;
    private GestureListener mGestureListener;
    private int mImageJpegQuality;
    private float mImageMegaPixels;
    private int mPermissions;
    private PermissionsListener mPermissionsListener;
    private int mPreviewEffect;
    private PreviewListener mPreviewListener;
    private int mSensorPreset;
    private float mZoomFactor;

    /* loaded from: classes.dex */
    public interface CameraListener {
        void onClosed();

        void onOpened();
    }

    /* loaded from: classes.dex */
    public interface ErrorListener {
        void onError(CameraKitView cameraKitView, CameraException cameraException);
    }

    /* loaded from: classes.dex */
    public interface FrameCallback {
        void onFrame(CameraKitView cameraKitView, byte[] bArr);
    }

    /* loaded from: classes.dex */
    public interface GestureListener {
        void onDoubleTap(CameraKitView cameraKitView, float f, float f2);

        void onLongTap(CameraKitView cameraKitView, float f, float f2);

        void onPinch(CameraKitView cameraKitView, float f, float f2, float f3);

        void onTap(CameraKitView cameraKitView, float f, float f2);
    }

    /* loaded from: classes.dex */
    public interface ImageCallback {
        void onImage(CameraKitView cameraKitView, byte[] bArr);
    }

    /* loaded from: classes.dex */
    public interface JpegCallback {
        void onJpeg(Jpeg jpeg);
    }

    /* loaded from: classes.dex */
    public interface PermissionsListener {
        void onPermissionsFailure();

        void onPermissionsSuccess();
    }

    /* loaded from: classes.dex */
    public interface PreviewListener {
        void onStart();

        void onStop();
    }

    /* loaded from: classes.dex */
    public interface VideoCallback {
        void onVideo(CameraKitView cameraKitView, Object obj);
    }

    public CameraKitView(Context context) {
        super(context);
        obtainAttributes(context, null);
    }

    public CameraKitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(context, attrs);
    }

    public CameraKitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CameraKitView);
        this.mAdjustViewBounds = a.getBoolean(R.styleable.CameraKitView_android_adjustViewBounds, false);
        this.mAspectRatio = a.getFloat(R.styleable.CameraKitView_camera_aspectRatio, -1.0f);
        this.mFacing = a.getInteger(R.styleable.CameraKitView_camera_facing, 0);
        if (cameraFacing == CameraFacing.FRONT) {
            this.mFacing = 1;
        }
        this.mFlash = a.getInteger(R.styleable.CameraKitView_camera_flash, 0);
        if (cameraFlash == CameraFlash.ON) {
            this.mFlash = 1;
        }
        this.mFocus = a.getInteger(R.styleable.CameraKitView_camera_focus, 1);
        this.mZoomFactor = a.getFloat(R.styleable.CameraKitView_camera_zoomFactor, 1.0f);
        this.mPermissions = a.getInteger(R.styleable.CameraKitView_camera_permissions, 1);
        this.mImageMegaPixels = a.getFloat(R.styleable.CameraKitView_camera_imageMegaPixels, 2.0f);
        this.mImageJpegQuality = a.getInteger(R.styleable.CameraKitView_camera_imageJpegQuality, 100);
        a.recycle();
        this.mCameraPreview = new CameraPreview(getContext());
        addView(this.mCameraPreview);
        this.mCameraPreview.setListener(new CameraPreview.Listener() { // from class: com.camerakit.CameraKitView.1
            @Override // com.camerakit.CameraPreview.Listener
            public void onCameraOpened() {
                if (CameraKitView.this.mCameraListener != null) {
                    CameraKitView.this.post(new Runnable() { // from class: com.camerakit.CameraKitView.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraKitView.this.mCameraListener.onOpened();
                        }
                    });
                }
            }

            @Override // com.camerakit.CameraPreview.Listener
            public void onCameraClosed() {
                if (CameraKitView.this.mCameraListener != null) {
                    CameraKitView.this.post(new Runnable() { // from class: com.camerakit.CameraKitView.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraKitView.this.mCameraListener.onClosed();
                        }
                    });
                }
            }

            @Override // com.camerakit.CameraPreview.Listener
            public void onPreviewStarted() {
                if (CameraKitView.this.mPreviewListener != null) {
                    CameraKitView.this.post(new Runnable() { // from class: com.camerakit.CameraKitView.1.3
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraKitView.this.mPreviewListener.onStart();
                        }
                    });
                }
            }

            @Override // com.camerakit.CameraPreview.Listener
            public void onPreviewStopped() {
                if (CameraKitView.this.mPreviewListener != null) {
                    CameraKitView.this.post(new Runnable() { // from class: com.camerakit.CameraKitView.1.4
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraKitView.this.mPreviewListener.onStop();
                        }
                    });
                }
            }
        });
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mAdjustViewBounds) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams.width == -2 && layoutParams.height == -2) {
                throw new CameraException("android:adjustViewBounds=true while both layout_width and layout_height are setView to wrap_content - only 1 is allowed.");
            } else if (layoutParams.width == -2) {
                int height = View.MeasureSpec.getSize(heightMeasureSpec);
                float f = this.mAspectRatio;
                if (f > 0.0f) {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (((float) height) * f), 1073741824);
                } else {
                    CameraPreview cameraPreview = this.mCameraPreview;
                    if (cameraPreview != null && cameraPreview.getSurfaceSize().area() > 0) {
                        CameraSize previewSize = this.mCameraPreview.getSurfaceSize();
                        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) ((((float) height) / ((float) previewSize.getHeight())) * ((float) previewSize.getWidth())), 1073741824);
                    }
                }
            } else if (layoutParams.height == -2) {
                int width = View.MeasureSpec.getSize(widthMeasureSpec);
                float f2 = this.mAspectRatio;
                if (f2 > 0.0f) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (((float) width) * f2), 1073741824);
                } else {
                    CameraPreview cameraPreview2 = this.mCameraPreview;
                    if (cameraPreview2 != null && cameraPreview2.getSurfaceSize().area() > 0) {
                        CameraSize previewSize2 = this.mCameraPreview.getSurfaceSize();
                        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) ((((float) width) / ((float) previewSize2.getWidth())) * ((float) previewSize2.getHeight())), 1073741824);
                    }
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // com.camerakit.GestureLayout
    protected void onTap(float x, float y) {
        GestureListener gestureListener = this.mGestureListener;
        if (gestureListener != null) {
            gestureListener.onTap(this, x, y);
        }
    }

    @Override // com.camerakit.GestureLayout
    protected void onLongTap(float x, float y) {
        GestureListener gestureListener = this.mGestureListener;
        if (gestureListener != null) {
            gestureListener.onLongTap(this, x, y);
        }
    }

    @Override // com.camerakit.GestureLayout
    protected void onDoubleTap(float x, float y) {
        GestureListener gestureListener = this.mGestureListener;
        if (gestureListener != null) {
            gestureListener.onDoubleTap(this, x, y);
        }
    }

    @Override // com.camerakit.GestureLayout
    protected void onPinch(float ds, float dsx, float dsy) {
        GestureListener gestureListener = this.mGestureListener;
        if (gestureListener != null) {
            gestureListener.onPinch(this, ds, dsx, dsy);
        }
    }

    public void onStart() {
        PermissionsListener permissionsListener;
        if (!isInEditMode()) {
            List<String> missingPermissions = getMissingPermissions();
            if (Build.VERSION.SDK_INT < 23 || missingPermissions.size() <= 0) {
                PermissionsListener permissionsListener2 = this.mPermissionsListener;
                if (permissionsListener2 != null) {
                    permissionsListener2.onPermissionsSuccess();
                }
                setFlash(this.mFlash);
                setImageMegaPixels(this.mImageMegaPixels);
                cameraFacing = getFacing() == 0 ? CameraFacing.BACK : CameraFacing.FRONT;
                this.mCameraPreview.start(cameraFacing);
                return;
            }
            Activity activity = null;
            for (Context context = getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
                if (context instanceof Activity) {
                    activity = (Activity) context;
                }
            }
            if (activity != null) {
                List<String> requestPermissions = new ArrayList<>();
                List<String> rationalePermissions = new ArrayList<>();
                for (String permission : missingPermissions) {
                    if (!activity.shouldShowRequestPermissionRationale(permission)) {
                        requestPermissions.add(permission);
                    } else {
                        rationalePermissions.add(permission);
                    }
                }
                if (requestPermissions.size() > 0) {
                    activity.requestPermissions((String[]) requestPermissions.toArray(new String[requestPermissions.size()]), PERMISSION_REQUEST_CODE);
                }
                if (rationalePermissions.size() > 0 && (permissionsListener = this.mPermissionsListener) != null) {
                    permissionsListener.onPermissionsFailure();
                }
            }
        }
    }

    public void onStop() {
        if (!isInEditMode()) {
            this.mCameraPreview.stop();
        }
    }

    public void onResume() {
        if (!isInEditMode()) {
            this.mCameraPreview.resume();
        }
    }

    public void onPause() {
        if (!isInEditMode()) {
            this.mCameraPreview.pause();
        }
    }

    public void captureImage(final ImageCallback callback) {
        this.mCameraPreview.capturePhoto(new CameraPreview.PhotoCallback() { // from class: com.camerakit.CameraKitView.2
            @Override // com.camerakit.CameraPreview.PhotoCallback
            public void onCapture(final byte[] jpeg) {
                CameraKitView.this.post(new Runnable() { // from class: com.camerakit.CameraKitView.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onImage(CameraKitView.this, jpeg);
                    }
                });
            }
        });
    }

    public void startVideo() {
    }

    public void stopVideo() {
    }

    public void captureVideo(VideoCallback callback) {
    }

    public void captureFrame(FrameCallback callback) {
    }

    public void setFrameCallback(FrameCallback callback) {
    }

    private List<String> getMissingPermissions() {
        List<String> manifestPermissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT < 23) {
            return manifestPermissions;
        }
        int i = this.mPermissions;
        if ((i | 1) == i && getContext().checkSelfPermission("android.permission.CAMERA") == -1) {
            manifestPermissions.add("android.permission.CAMERA");
        }
        int i2 = this.mPermissions;
        if ((i2 | 2) == i2 && getContext().checkSelfPermission("android.permission.RECORD_AUDIO") == -1) {
            manifestPermissions.add("android.permission.RECORD_AUDIO");
        }
        int i3 = this.mPermissions;
        if ((i3 | 4) == i3 && getContext().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == -1) {
            manifestPermissions.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        int i4 = this.mPermissions;
        if ((i4 | 8) == i4 && getContext().checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == -1) {
            manifestPermissions.add("android.permission.ACCESS_FINE_LOCATION");
        }
        return manifestPermissions;
    }

    public void setPermissionsListener(PermissionsListener permissionsListener) {
        this.mPermissionsListener = permissionsListener;
    }

    public void requestPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> manifestPermissions = getMissingPermissions();
            if (manifestPermissions.size() > 0) {
                activity.requestPermissions((String[]) manifestPermissions.toArray(new String[manifestPermissions.size()]), PERMISSION_REQUEST_CODE);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            int approvedPermissions = 0;
            int deniedPermissions = 0;
            for (int i = 0; i < permissions.length; i++) {
                int flag = 0;
                String str = permissions[i];
                char c = 65535;
                int hashCode = str.hashCode();
                if (hashCode != 463403621) {
                    if (hashCode != 1365911975) {
                        if (hashCode == 1831139720 && str.equals("android.permission.RECORD_AUDIO")) {
                            c = 1;
                        }
                    } else if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                        c = 2;
                    }
                } else if (str.equals("android.permission.CAMERA")) {
                    c = 0;
                }
                if (c == 0) {
                    flag = 1;
                } else if (c == 1) {
                    flag = 2;
                } else if (c == 2) {
                    flag = 4;
                }
                if (grantResults[i] == 0) {
                    approvedPermissions |= flag;
                } else {
                    deniedPermissions |= flag;
                }
            }
            onStart();
        }
    }

    public void setAdjustViewBounds(boolean adjustViewBounds) {
        this.mAdjustViewBounds = adjustViewBounds;
    }

    public boolean getAdjustViewBounds() {
        return this.mAdjustViewBounds;
    }

    public void setAspectRatio(float aspectRatio) {
        this.mAspectRatio = aspectRatio;
    }

    public float getAspectRatio() {
        return this.mAspectRatio;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.camerakit.CameraKitView$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$camerakit$CameraPreview$LifecycleState = new int[CameraPreview.LifecycleState.values().length];

        static {
            try {
                $SwitchMap$com$camerakit$CameraPreview$LifecycleState[CameraPreview.LifecycleState.PAUSED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$camerakit$CameraPreview$LifecycleState[CameraPreview.LifecycleState.STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$camerakit$CameraPreview$LifecycleState[CameraPreview.LifecycleState.RESUMED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public void setFacing(int facing) {
        this.mFacing = facing;
        int i = AnonymousClass3.$SwitchMap$com$camerakit$CameraPreview$LifecycleState[this.mCameraPreview.getLifecycleState().ordinal()];
        if (i == 1 || i == 2) {
            onStop();
            onStart();
        } else if (i == 3) {
            onStop();
            onStart();
            onResume();
        }
    }

    public int getFacing() {
        return this.mFacing;
    }

    public void toggleFacing() {
        if (getFacing() == 0) {
            setFacing(1);
        } else {
            setFacing(0);
        }
    }

    public void setFlash(int flash) {
        this.mFlash = flash;
        try {
            if (flash == 0) {
                cameraFlash = CameraFlash.OFF;
            } else if (flash == 1) {
                cameraFlash = CameraFlash.ON;
            } else if (flash == 2) {
                throw new CameraException("FLASH_AUTO is not supported in this version of CameraKit.");
            } else if (flash == 3) {
                throw new CameraException("FLASH_TORCH is not supported in this version of CameraKit.");
            }
            this.mCameraPreview.setFlash(cameraFlash);
        } catch (CameraException exception) {
            Log.e("CameraException: Flash", exception.getMessage());
        }
    }

    public int getFlash() {
        return this.mFlash;
    }

    public void setFocus(int focus) {
        this.mFocus = focus;
    }

    public int getFocus() {
        return this.mFocus;
    }

    public void setZoomFactor(float zoomFactor) {
        this.mZoomFactor = zoomFactor;
    }

    public float getZoomFactor() {
        return this.mZoomFactor;
    }

    public void setSensorPreset(int sensorPreset) {
        this.mSensorPreset = sensorPreset;
    }

    public int getSensorPreset() {
        return this.mSensorPreset;
    }

    public void setPreviewEffect(int previewEffect) {
        this.mPreviewEffect = previewEffect;
    }

    public int getPreviewEffect() {
        return this.mPreviewEffect;
    }

    public void setPermissions(int permissions) {
        this.mPermissions = permissions;
    }

    public int getPermissions() {
        return this.mPermissions;
    }

    public void setImageMegaPixels(float imageMegaPixels) {
        this.mImageMegaPixels = imageMegaPixels;
        this.mCameraPreview.setImageMegaPixels(this.mImageMegaPixels);
    }

    public float getImageMegaPixels() {
        return this.mImageMegaPixels;
    }

    public void setGestureListener(GestureListener gestureListener) {
        this.mGestureListener = gestureListener;
    }

    public GestureListener getGestureListener() {
        return this.mGestureListener;
    }

    /* loaded from: classes.dex */
    public static class GestureListenerAdapter implements GestureListener {
        @Override // com.camerakit.CameraKitView.GestureListener
        public void onTap(CameraKitView view, float x, float y) {
        }

        @Override // com.camerakit.CameraKitView.GestureListener
        public void onLongTap(CameraKitView view, float x, float y) {
        }

        @Override // com.camerakit.CameraKitView.GestureListener
        public void onDoubleTap(CameraKitView view, float x, float y) {
        }

        @Override // com.camerakit.CameraKitView.GestureListener
        public void onPinch(CameraKitView view, float ds, float dsx, float dsy) {
        }
    }

    public void setCameraListener(CameraListener cameraListener) {
        this.mCameraListener = cameraListener;
    }

    public CameraListener getCameraListener() {
        return this.mCameraListener;
    }

    public void removeCameraListener() {
        this.mCameraListener = null;
    }

    public void setPreviewListener(PreviewListener previewListener) {
        this.mPreviewListener = previewListener;
    }

    public PreviewListener getPreviewListener() {
        return this.mPreviewListener;
    }

    public void removePreviewListener() {
        this.mPreviewListener = null;
    }

    public void setErrorListener(ErrorListener errorListener) {
        this.mErrorListener = errorListener;
    }

    public ErrorListener getErrorListener() {
        return this.mErrorListener;
    }

    public void removeErrorListener() {
        this.mErrorListener = null;
    }

    public CameraSize getPreviewResolution() {
        if (this.mCameraPreview.getPreviewSize().area() == 0) {
            return null;
        }
        return this.mCameraPreview.getPreviewSize();
    }

    public CameraSize getPhotoResolution() {
        if (this.mCameraPreview.getPhotoSize().area() == 0) {
            return null;
        }
        return this.mCameraPreview.getPhotoSize();
    }

    /* loaded from: classes.dex */
    public static class Size implements Comparable<Size> {
        private final int mHeight;
        private final int mWidth;

        public Size(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        @Override // java.lang.Object
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (this == o) {
                return true;
            }
            if (!(o instanceof Size)) {
                return false;
            }
            Size size = (Size) o;
            if (this.mWidth == size.mWidth && this.mHeight == size.mHeight) {
                return true;
            }
            return false;
        }

        @Override // java.lang.Object
        public String toString() {
            return this.mWidth + "x" + this.mHeight;
        }

        @Override // java.lang.Object
        public int hashCode() {
            int i = this.mHeight;
            int i2 = this.mWidth;
            return i ^ ((i2 >>> 16) | (i2 << 16));
        }

        public int compareTo(Size another) {
            return (this.mWidth * this.mHeight) - (another.mWidth * another.mHeight);
        }
    }

    /* loaded from: classes.dex */
    public static class CameraException extends RuntimeException {
        public CameraException() {
        }

        public CameraException(String message) {
            super(message);
        }

        public CameraException(String message, Throwable cause) {
            super(message, cause);
        }

        public boolean isFatal() {
            return false;
        }
    }
}
