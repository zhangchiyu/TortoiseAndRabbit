package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

public class CongZhi extends Result {

    /**
     * code : 0
     * message :
     * biaoti : 龟兔科技充值
     * ifkai : 1
     * url : http://app.guituyule.com/mobile/user/chongzhi.aspx
     */

    private String biaoti;
    private String ifkai;
    private String url;

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getIfkai() {
        return ifkai;
    }

    public void setIfkai(String ifkai) {
        this.ifkai = ifkai;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
