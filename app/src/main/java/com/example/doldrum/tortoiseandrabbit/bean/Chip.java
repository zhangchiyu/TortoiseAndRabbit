package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.R;

public enum Chip {
    THOUSAND(R.mipmap.qian), //千
    TENTHOUSAND(R.mipmap.wan),//万
    TENFINISH(R.mipmap.tenwan), //十万
    ONEMILLION(R.mipmap.ttwan), //百万
    FIVEMILLION(R.mipmap.fwan) //五百万
    ;

    private int resimg;

    Chip(int resimg) {
        this.resimg =resimg;
    }

    public int getResimg() {
        return resimg;
    }

    public void setResimg(int resimg) {
        this.resimg = resimg;
    }
}
