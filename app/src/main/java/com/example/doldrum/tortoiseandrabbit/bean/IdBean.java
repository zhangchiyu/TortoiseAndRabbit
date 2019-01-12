package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class IdBean extends Result {

    private List<IdDataBean> data;

    public List<IdDataBean> getData() {
        return data;
    }

    public void setData(List<IdDataBean> data) {
        this.data = data;
    }

}
