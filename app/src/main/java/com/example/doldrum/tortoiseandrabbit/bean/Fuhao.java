package com.example.doldrum.tortoiseandrabbit.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class Fuhao extends BaseObservable {

    /**
     * userId : 50225
     * portrait : http://app.guituyule.com/templates/main/images/user-avatar.png
     * amount : 67567567.6万
     * ranking : 1
     * name :
     */

    private String userId;
    private String portrait;
    private String amount;
    private int ranking;
    private String name;

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Bindable
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Bindable
    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
