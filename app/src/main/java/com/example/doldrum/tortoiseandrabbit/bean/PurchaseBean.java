package com.example.doldrum.tortoiseandrabbit.bean;

import java.util.List;

public class PurchaseBean {

    /**
     * amount : 900000.00
     * recordCount : 4
     * dataDetail : [{"value":"-8.00","remark":"消费","add_time":"2018-07-03"},{"value":"7.00","remark":"充值","add_time":"2018-07-03"},{"value":"6.00","remark":"充值","add_time":"2018-07-03"},{"value":"5.00","remark":"充值","add_time":"2018-07-03"}]
     */

    private String amount;
    private int recordCount;
    private List<DataDetailBean> dataDetail;

    public String getAmount() {
        return amount;
    }

    public List<DataDetailBean> getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(List<DataDetailBean> dataDetail) {
        this.dataDetail = dataDetail;
    }

    public void setAmount(String amount) {

        this.amount = amount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }




}
