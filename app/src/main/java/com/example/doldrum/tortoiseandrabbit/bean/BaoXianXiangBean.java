package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

public class BaoXianXiangBean extends Result{
    /**
     * data : {"amount":9.9991601E7,"amount_mibao":399}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * amount : 9.9991601E7
         * amount_mibao : 399.0
         */

        private String amount;
        private String amount_mibao;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmount_mibao() {
            return amount_mibao;
        }

        public void setAmount_mibao(String amount_mibao) {
            this.amount_mibao = amount_mibao;
        }
    }

}
