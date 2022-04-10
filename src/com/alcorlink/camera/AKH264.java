package com.alcorlink.camera;

import a.a.a;
import com.alcorlink.camera.extension.H264BitRateLayers;
import com.alcorlink.camera.extension.H264PictureTypeControl;
import com.alcorlink.camera.extension.H264QpStepLayers;
import com.alcorlink.camera.extension.H264RateControlMode;
import com.alcorlink.camera.extension.H264Version;
/* loaded from: classes5.dex */
public class AKH264 {
    public static final short H264_DEFAULT_LAYER_RESERVED = 0;

    /* renamed from: a  reason: collision with root package name */
    private a f5a = a.a();

    public int h264GetBitrate(H264BitRateLayers h264BitRateLayers) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264GetBitrate(0, h264BitRateLayers);
    }

    public int h264GetFormat(byte[] bArr) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264GetFormat(bArr);
    }

    public int h264GetIFramePeriod(short[] sArr) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264GetIFramePeriod(sArr);
    }

    public int h264GetQpSteps(H264QpStepLayers h264QpStepLayers) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264GetQpSteps(h264QpStepLayers);
    }

    public int h264GetRateControl(H264RateControlMode h264RateControlMode) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264GetRateCtrlMode(h264RateControlMode);
    }

    public int h264GetVersion(H264Version h264Version) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264GetVersion(h264Version);
    }

    public int h264ResetEncoder(short s) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264Reset(s);
    }

    public int h264SetBitrate(H264BitRateLayers h264BitRateLayers) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264SetBitrate(0, h264BitRateLayers);
    }

    public int h264SetFormat(byte b) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264SetFormat(b);
    }

    public int h264SetIFramePeriod(short s) {
        short[] sArr = {s};
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264SetIFramePeriod(sArr);
    }

    public int h264SetNextFrameType(H264PictureTypeControl h264PictureTypeControl) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264SetNextFrameType(h264PictureTypeControl);
    }

    public int h264SetQpSteps(H264QpStepLayers h264QpStepLayers) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264SetQpSteps(h264QpStepLayers);
    }

    public int h264setRateControl(H264RateControlMode h264RateControlMode) {
        AlCamHAL alCamHAL = null;
        return alCamHAL.H264SetRateCtrlMode(h264RateControlMode);
    }
}
