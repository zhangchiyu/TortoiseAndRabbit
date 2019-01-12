package com.example.doldrum.tortoiseandrabbit.bean;

import java.util.List;

public class TeamRecord {
    /**
     * recordCount : 1
     * dataDetail : [{"userid":"55802","jia":"7.00","jian":"-8.00"}]
     */

    private int recordCount;
    private List<TeamDataDetailBean> dataDetail;

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public List<TeamDataDetailBean> getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(List<TeamDataDetailBean> dataDetail) {
        this.dataDetail = dataDetail;
    }


}
