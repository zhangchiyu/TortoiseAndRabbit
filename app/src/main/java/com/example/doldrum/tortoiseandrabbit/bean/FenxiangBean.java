package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

public class FenxiangBean extends Result{

    /**
     * biaoti : 龟兔游戏
     * miaoshu : 邀请玩游戏，我的邀请码98242
     * url : http://app.guituyule.com/mobile/user/myshare.aspx?user_bh=98242
     */

    private String biaoti;
    private String miaoshu;
    private String url;

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getMiaoshu() {
        return miaoshu;
    }

    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
