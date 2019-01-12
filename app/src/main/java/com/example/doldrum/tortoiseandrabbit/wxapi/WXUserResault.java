package com.example.doldrum.tortoiseandrabbit.wxapi;

import com.example.doldrum.tortoiseandrabbit.app.Result;

public class WXUserResault extends Result {


    /**
     * data : {"sessionToken":"458c38347f0e459384524b557acee2a0","userbh":"50514","userName":"Doldrums","recommandUserbh":null,"recommandCode":"40649","token":null,"avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/DIzCuicXIibibE726QzIwYrVE1e3ITIBsxNVLyTibeYKewsfj4p8A97Py0YN4miaibFs1MMIAaDIdKd42FkKHqm136dw/132"}
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
         * sessionToken : 458c38347f0e459384524b557acee2a0
         * userbh : 50514
         * userName : Doldrums
         * recommandUserbh : null
         * recommandCode : 40649
         * token : null
         * avatar : https://thirdwx.qlogo.cn/mmopen/vi_32/DIzCuicXIibibE726QzIwYrVE1e3ITIBsxNVLyTibeYKewsfj4p8A97Py0YN4miaibFs1MMIAaDIdKd42FkKHqm136dw/132
         */

        private String sessionToken;
        private String userbh;
        private String userName;
        private String recommandUserbh;
        private String recommandCode;
        private String token;
        private String avatar;

        public String getSessionToken() {
            return sessionToken;
        }

        public void setSessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
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

        public String getRecommandUserbh() {
            return recommandUserbh;
        }

        public void setRecommandUserbh(String recommandUserbh) {
            this.recommandUserbh = recommandUserbh;
        }

        public String getRecommandCode() {
            return recommandCode;
        }

        public void setRecommandCode(String recommandCode) {
            this.recommandCode = recommandCode;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
