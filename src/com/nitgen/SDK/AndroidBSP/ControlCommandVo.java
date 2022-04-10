package com.nitgen.SDK.AndroidBSP;
/* loaded from: classes4.dex */
class ControlCommandVo {
    private byte[] buffer;
    private byte[] bufferBlk;
    private int index;
    private int length;
    private int request;
    private int requestType;
    private int value;
    private boolean isCtlSuccess = false;
    private boolean isBlkSuccess = false;

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCtlSuccess() {
        return this.isCtlSuccess;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setCtlSuccess(boolean isCtlSuccess) {
        this.isCtlSuccess = isCtlSuccess;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isBlkSuccess() {
        return this.isBlkSuccess;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBlkSuccess(boolean isBlkSuccess) {
        this.isBlkSuccess = isBlkSuccess;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRequestType() {
        return this.requestType;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getRequest() {
        return this.request;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setRequest(int request) {
        this.request = request;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getValue() {
        return this.value;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setValue(int value) {
        this.value = value;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getIndex() {
        return this.index;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setIndex(int index) {
        this.index = index;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public byte[] getBuffer() {
        return this.buffer;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLength() {
        return this.length;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setLength(int length) {
        this.length = length;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public byte[] getBufferBlk() {
        return this.bufferBlk;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBufferBlk(byte[] bufferBlk) {
        this.bufferBlk = bufferBlk;
    }
}
