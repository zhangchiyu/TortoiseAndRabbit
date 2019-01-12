package com.example.doldrum.tortoiseandrabbit.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class GItem extends BaseObservable {
    private int num;
    private String peilv;
    private boolean ifFlag;

    public boolean isIfFlag() {
        return ifFlag;
    }

    public void setIfFlag(boolean ifFlag) {
        this.ifFlag = ifFlag;
    }

    public GItem(int num, String peilv) {
        this.num = num;
        this.peilv = peilv;
    }


    @Bindable
    public String getPeilv() {
        return peilv;
    }

    public void setPeilv(String peilv) {
        this.peilv = peilv;
    }

    @Bindable
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
