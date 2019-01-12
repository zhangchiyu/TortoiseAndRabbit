package com.example.doldrum.tortoiseandrabbit.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class LotteryRecord extends Result {


    private List<RecordBean> data;

    public List<RecordBean> getData() {
        return data;
    }

    public void setData(List<RecordBean> data) {
        this.data = data;
    }


}
