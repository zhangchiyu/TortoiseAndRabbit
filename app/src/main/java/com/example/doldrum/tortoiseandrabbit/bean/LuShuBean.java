package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class LuShuBean extends Result{


    private List<LuShuItem> data;

    public List<LuShuItem> getData() {
        return data;
    }

    public void setData(List<LuShuItem> data) {
        this.data = data;
    }

}
