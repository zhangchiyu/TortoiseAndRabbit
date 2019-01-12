package com.example.doldrum.tortoiseandrabbit.bean;

public class Owner {

    /**
     * userId : 10021
     * portrait : https://thirdwx.qlogo.cn/mmopen/vi_32/DIzCuicXIibibE726QzIwYrVE1e3ITIBsxNVLyTibeYKewsfj4p8A97Py0YN4miaibFs1MMIAaDIdKd42FkKHqm136dw/132
     * name :
     * amount : 531.5万
     * ranking : 12
     */

    private String userId;
    private String portrait;
    private String name;
    private String amount;
    private int ranking;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
