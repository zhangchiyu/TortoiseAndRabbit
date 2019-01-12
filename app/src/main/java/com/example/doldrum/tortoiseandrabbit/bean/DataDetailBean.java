package com.example.doldrum.tortoiseandrabbit.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class DataDetailBean extends BaseObservable {
    /**
     * value : -8.00
     * remark : 消费
     * add_time : 2018-07-03
     */

    private String value;
    private String remark;
    private String add_time;
    private String djtype;
    private String qihao;
    private String yujine;

    public String getQihao() {
        return qihao;
    }

    public void setQihao(String qihao) {
        this.qihao = qihao;
    }

    public String getYujine() {
        return yujine;
    }

    public void setYujine(String yujine) {
        this.yujine = yujine;
    }

    @Bindable
    public String getDjtype() {
        return djtype;
    }

    public void setDjtype(String djtype) {
        this.djtype = djtype;
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Bindable
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Bindable
    public String getAdd_time() {
        return add_time.replace(" ","\n");
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }


    public String getDesc(){
        if (getRemark().contains("购买")){
            return getRemark();
        }
        //item.remark.contains(item.djtype)?item.remark:item.djtype.concat(item.remark)
        if (getRemark().contains(getDjtype())){
            return getRemark();
        }else {
            return getDjtype().concat(getRemark());
        }

    }
}
