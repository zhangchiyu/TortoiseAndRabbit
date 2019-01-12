package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class PurchaseHistoryBean extends Result {

    /**
     * data : {"amount":"900000.00","recordCount":4,"dataDetail":[{"value":"-8.00","remark":"消费","add_time":"2018-07-03"},{"value":"7.00","remark":"充值","add_time":"2018-07-03"},{"value":"6.00","remark":"充值","add_time":"2018-07-03"},{"value":"5.00","remark":"充值","add_time":"2018-07-03"}]}
     */

    private PurchaseBean data;

    public PurchaseBean getData() {
        return data;
    }

    public void setData(PurchaseBean data) {
        this.data = data;
    }

}
