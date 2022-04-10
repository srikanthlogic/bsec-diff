package com.mantra.mfs100;
/* loaded from: classes3.dex */
public class FingerData {
    byte[] _FingerImage = null;
    int _Quality = 0;
    int _Nfiq = 0;
    byte[] _RawData = null;
    byte[] _ISOTemplate = null;
    double _InWidth = 0.0d;
    double _InHeight = 0.0d;
    double _InArea = 0.0d;
    double _Resolution = 0.0d;
    int _GrayScale = 0;
    int _Bpp = 0;
    double _WSQCompressRatio = 0.0d;
    String _WSQInfo = "";

    public byte[] FingerImage() {
        return this._FingerImage;
    }

    public int Quality() {
        return this._Quality;
    }

    public int Nfiq() {
        return this._Nfiq;
    }

    public byte[] RawData() {
        return this._RawData;
    }

    public byte[] ISOTemplate() {
        return this._ISOTemplate;
    }

    public double InWidth() {
        return this._InWidth;
    }

    public double InHeight() {
        return this._InHeight;
    }

    public double InArea() {
        return this._InArea;
    }

    public double Resolution() {
        return this._Resolution;
    }

    public int GrayScale() {
        return this._GrayScale;
    }

    public int Bpp() {
        return this._Bpp;
    }

    public double WSQCompressRatio() {
        return this._WSQCompressRatio;
    }

    public String WSQInfo() {
        return this._WSQInfo;
    }
}
