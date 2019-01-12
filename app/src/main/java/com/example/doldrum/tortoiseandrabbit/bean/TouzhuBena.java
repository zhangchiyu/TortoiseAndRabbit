package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class TouzhuBena extends Result{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * row_number : 1
         * shijian : 2018-10-10 00:00:00
         * qishu : 97
         * youxi : 龟兔
         * haoma : null
         * peilv : 0.0
         * jiangli : 0.0
         * touzhu : 龟
         * jine : 1000.0
         * zhuangtai : ""
         */

        private int row_number;
        private String shijian;
        private int qishu;
        private String youxi;
        private String haoma;
        private double peilv;
        private double jiangli;
        private String touzhu;
        private double jine;
        private String zhuangtai;

        public String getZhuangtai() {
            return zhuangtai;
        }

        public void setZhuangtai(String zhuangtai) {
            this.zhuangtai = zhuangtai;
        }

        public int getRow_number() {
            return row_number;
        }

        public void setRow_number(int row_number) {
            this.row_number = row_number;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public int getQishu() {
            return qishu;
        }

        public void setQishu(int qishu) {
            this.qishu = qishu;
        }

        public String getYouxi() {
            return youxi;
        }

        public void setYouxi(String youxi) {
            this.youxi = youxi;
        }

        public String getHaoma() {
            return haoma;
        }

        public void setHaoma(String haoma) {
            this.haoma = haoma;
        }

        public double getPeilv() {
            return peilv;
        }

        public void setPeilv(double peilv) {
            this.peilv = peilv;
        }

        public double getJiangli() {
            return jiangli;
        }

        public void setJiangli(double jiangli) {
            this.jiangli = jiangli;
        }

        public String getTouzhu() {
            return touzhu;
        }

        public void setTouzhu(String touzhu) {
            this.touzhu = touzhu;
        }

        public double getJine() {
            return jine;
        }

        public void setJine(double jine) {
            this.jine = jine;
        }
    }
}
