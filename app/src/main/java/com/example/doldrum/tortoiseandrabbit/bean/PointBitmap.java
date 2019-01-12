package com.example.doldrum.tortoiseandrabbit.bean;

import android.graphics.Point;

public class PointBitmap {
    private int bitmaprseId;
    private int radius;
    private Point point;

    public int getBitmaprseId() {
        return bitmaprseId;
    }

    public void setBitmaprseId(int bitmaprseId) {
        this.bitmaprseId = bitmaprseId;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public PointBitmap(int bitmaprseId, Point point) {
        this.bitmaprseId = bitmaprseId;
        this.point = point;
    }

    public PointBitmap() {
    }
}
