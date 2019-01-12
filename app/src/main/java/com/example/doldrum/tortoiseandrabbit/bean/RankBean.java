package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class RankBean extends Result {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * user_bh : 50225
         * avatar : http://app.guituyule.com/templates/main/images/user-avatar.png
         * amount : 6.75675675675E11
         */

        private String user_bh;
        private String avatar;
        private double amount;

        public String getUser_bh() {
            return user_bh;
        }

        public void setUser_bh(String user_bh) {
            this.user_bh = user_bh;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
}
