package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.io.Serializable;

public class AccountBean extends Result {


    /**
     * data : {"userid":5555,"username":"","avatar":"http://122.14.213.233:1002/templates/main/images/user-avatar.png"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public  class DataBean implements Serializable {
        /**
         * userid : 5555
         * username :
         * avatar : http://122.14.213.233:1002/templates/main/images/user-avatar.png
         */

        private int userid;
        private String username;
        private String avatar;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
