package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class TeamRecordBean extends Result {

    /**
     * data : {"recordCount":1,"dataDetail":[{"userid":"55802","jia":"7.00","jian":"-8.00"}]}
     */

    private TeamRecord data;

    public TeamRecord getData() {
        return data;
    }

    public void setData(TeamRecord data) {
        this.data = data;
    }





}
