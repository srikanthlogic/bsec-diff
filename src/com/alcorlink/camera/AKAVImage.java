package com.alcorlink.camera;

import a.a.a;
import java.util.Iterator;
/* loaded from: classes5.dex */
public class AKAVImage {

    /* renamed from: a  reason: collision with root package name */
    private AlCamHAL f4a;
    private boolean d = false;
    private a c = a.a();
    private i b = new i();

    /* JADX INFO: Access modifiers changed from: protected */
    public final int a(AlCamHAL alCamHAL) {
        if (this.f4a != null) {
            return AlErrorCode.ERR_IN_USE;
        }
        this.f4a = alCamHAL;
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void a() {
        synchronized (this.f4a) {
            this.f4a = null;
        }
    }

    public Iterator<StreamConfig> getStreamConfigList() {
        AlCamHAL alCamHAL = this.f4a;
        if (alCamHAL != null) {
            return alCamHAL.b();
        }
        throw new RuntimeException("device is closed or not properly initialized in AKAVImage.()");
    }

    public int getVideo(AlFrame alFrame, int i) throws CameraException {
        AlCamHAL alCamHAL = this.f4a;
        if (alCamHAL != null) {
            synchronized (alCamHAL) {
                int a2 = this.f4a.a(alFrame, i);
                if (a2 == 0) {
                    this.b.b();
                    this.b.a();
                    return a2;
                } else if (a2 == 208) {
                    AlErrorCode alErrorCode = new AlErrorCode(a2);
                    CameraException cameraException = new CameraException("streaming is closed");
                    cameraException.a(alErrorCode);
                    throw cameraException;
                } else if (a2 == 255) {
                    AlErrorCode alErrorCode2 = new AlErrorCode(a2);
                    CameraException cameraException2 = new CameraException("Memory Error");
                    cameraException2.a(alErrorCode2);
                    throw cameraException2;
                } else if (a2 != 209) {
                    return a2;
                } else {
                    AlErrorCode alErrorCode3 = new AlErrorCode(a2);
                    CameraException cameraException3 = new CameraException("Bad Frame");
                    cameraException3.a(alErrorCode3);
                    throw cameraException3;
                }
            }
        } else {
            throw new RuntimeException("device is closed or not properly initialized in AKAVImage.getVideo()");
        }
    }

    public void videoStart(StreamConfig streamConfig) throws CameraException {
        if (this.f4a != null) {
            if (this.d && streamConfig.format == 16) {
                Iterator<StreamConfig> streamConfigList = getStreamConfigList();
                boolean z = false;
                boolean z2 = false;
                boolean z3 = false;
                while (streamConfigList.hasNext()) {
                    StreamConfig next = streamConfigList.next();
                    if (next.format == 6) {
                        z2 = true;
                    } else if (next.format == 16) {
                        z3 = true;
                    }
                }
                if (z2 && z3) {
                    z = true;
                }
                if (!z) {
                    throw new CameraException("Multi-Stream is not support");
                }
            }
            if (streamConfig.format == 6) {
                this.d = true;
            }
            if (streamConfig == null) {
                throw new NullPointerException("input data is null");
            } else if (this.f4a != null) {
                byte b = streamConfig.streamId;
                int i = streamConfig.width;
                int i2 = streamConfig.height;
                int i3 = streamConfig.format;
                StringBuilder sb = new StringBuilder("setResolution-");
                sb.append(i3);
                sb.append("-");
                sb.append((int) b);
                sb.append(" ");
                sb.append(i);
                sb.append("x");
                sb.append(i2);
                AlCamHAL alCamHAL = this.f4a;
                if (alCamHAL != null) {
                    int a2 = alCamHAL.a(b, i, i2, i3);
                    if (a2 == 0) {
                        int a3 = this.f4a.a(streamConfig.streamId);
                        if (a3 == 0) {
                            i iVar = this.b;
                            System.nanoTime();
                            return;
                        }
                        AlErrorCode alErrorCode = new AlErrorCode(a3);
                        CameraException cameraException = new CameraException("videoStart fai:0x" + Integer.toHexString(a3));
                        cameraException.a(alErrorCode);
                        throw cameraException;
                    }
                    AlErrorCode alErrorCode2 = new AlErrorCode(a2);
                    CameraException cameraException2 = new CameraException("SetStreamConfig fail");
                    cameraException2.a(alErrorCode2);
                    throw cameraException2;
                }
                throw new RuntimeException("device is closed or not properly initialized in AKAVImage.setResolution()");
            } else {
                throw new RuntimeException("device is closed or not properly initialized in AKAVImage.setStreamConfig()");
            }
        } else {
            AlErrorCode alErrorCode3 = new AlErrorCode(AlErrorCode.ERR_NOT_INIT);
            CameraException cameraException3 = new CameraException("AKAVImage is not initial");
            cameraException3.a(alErrorCode3);
            throw cameraException3;
        }
    }

    public void videoStop(StreamConfig streamConfig) throws CameraException {
        if (this.f4a != null) {
            if (streamConfig.format == 6) {
                this.d = false;
            }
            int b = this.f4a.b(streamConfig.streamId);
            if (b != 0) {
                AlErrorCode alErrorCode = new AlErrorCode(b);
                CameraException cameraException = new CameraException("videoStop fai:0x" + Integer.toHexString(b));
                cameraException.a(alErrorCode);
                throw cameraException;
            }
        }
    }
}
