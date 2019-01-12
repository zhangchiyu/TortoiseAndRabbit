package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.text.DecimalFormat;

public class UserInfo extends Result {

    /**
     * data : {"userid":5555,"userbh":"55801","userName":"","mobile":"13072778562","recommandCode":"17805","avatar":"122.14.213.233:1002/templates/main/images/user-avatar.png","amount":0,"gorupName":"普通会员","openid":null,"tuijianren_bh":null,"amount_mibao":0}
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
         * userid : 5555
         * userbh : 55801
         * userName :
         * mobile : 13072778562
         * recommandCode : 17805
         * avatar : 122.14.213.233:1002/templates/main/images/user-avatar.png
         * amount : 0.0
         * gorupName : 普通会员
         * openid : null
         * tuijianren_bh : null
         * amount_mibao : 0.0
         */

        private int userid;
        private String userbh;
        private String userName;
        private String mobile;
        private String recommandCode;
        private String avatar;
        private String amount;
        private String gorupName;
        private Object openid;
        private Object tuijianren_bh;
        private double amount_mibao;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUserbh() {
            return userbh;
        }

        public void setUserbh(String userbh) {
            this.userbh = userbh;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRecommandCode() {
            return recommandCode;
        }

        public void setRecommandCode(String recommandCode) {
            this.recommandCode = recommandCode;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAmount() {
            return rvZeroAndDot(amount);
        }


        public static String rvZeroAndDot(String s){
            if (s.isEmpty()) {
                return null;
            }
            if(s.indexOf(".") > 0){
                s = s.replaceAll("0+?$", "");//去掉多余的0
                s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
            }
            return s;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getGorupName() {
            return gorupName;
        }

        public void setGorupName(String gorupName) {
            this.gorupName = gorupName;
        }

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
            this.openid = openid;
        }

        public Object getTuijianren_bh() {
            return tuijianren_bh;
        }

        public void setTuijianren_bh(Object tuijianren_bh) {
            this.tuijianren_bh = tuijianren_bh;
        }

        public double getAmount_mibao() {
            return amount_mibao;
        }

        public void setAmount_mibao(double amount_mibao) {
            this.amount_mibao = amount_mibao;
        }
    }
}
