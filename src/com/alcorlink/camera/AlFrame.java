package com.alcorlink.camera;

import java.nio.ByteBuffer;
/* loaded from: classes5.dex */
public class AlFrame {

    /* renamed from: a  reason: collision with root package name */
    private int f13a;
    private ByteBuffer b;
    public int serialId;
    public int timeFromLast;
    public int validBufferLength;

    public AlFrame(StreamConfig streamConfig) {
        int i = (streamConfig.height * streamConfig.width) << 1;
        ByteBuffer byteBuffer = this.b;
        if (byteBuffer != null) {
            byteBuffer.clear();
            this.b = null;
        }
        this.b = ByteBuffer.allocateDirect(i);
        this.validBufferLength = 0;
        this.f13a = streamConfig.streamId;
    }

    public ByteBuffer getFrameByteBuffer() {
        return this.b;
    }

    public int getStreamId() {
        return this.f13a;
    }
}
