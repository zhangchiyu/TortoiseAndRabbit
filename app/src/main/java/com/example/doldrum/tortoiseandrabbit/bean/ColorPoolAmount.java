package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

public class ColorPoolAmount extends Result {

    /**
     * data : {"jine":900}
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
         * jine : 900.0
         */

        private double jine;

        public double getJine() {
            return jine;
        }

        public void setJine(double jine) {
            this.jine = jine;
        }
    }
}
