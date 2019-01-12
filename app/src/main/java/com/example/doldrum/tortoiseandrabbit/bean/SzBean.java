package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class SzBean extends Result {

    public List<DataBean> data;



    public static class DataBean {
        /**
         * row : 1
         * jine : 0
         * user_name : 大耳
         * avatar : http://app.guituyule.com/upload/20181205060549.jpg
         * user_bh : 98242
         */

        private int row;
        private int jine;
        private String user_name;
        private String avatar;
        private String user_bh;

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getJine() {
            return jine;
        }

        public void setJine(int jine) {
            this.jine = jine;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUser_bh() {
            return user_bh;
        }

        public void setUser_bh(String user_bh) {
            this.user_bh = user_bh;
        }
    }
}
