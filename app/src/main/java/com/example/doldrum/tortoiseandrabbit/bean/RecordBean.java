package com.example.doldrum.tortoiseandrabbit.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class RecordBean extends BaseObservable {
    /**
     * id : 2
     * shijian : /Date(1530547200000)/
     * qishu : 2
     * piao : 32315
     * haoma : 315
     */

    private int id;
    private String shijian;
    private int qishu;
    private String piao;
    private String haoma;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    @Bindable
    public int getQishu() {
        return qishu;
    }

    public void setQishu(int qishu) {
        this.qishu = qishu;
    }

    public String getPiao() {
        return piao;
    }

    public void setPiao(String piao) {
        this.piao = piao;
    }

    @Bindable
    public String getHaoma() {
      /*  String[] chars = haoma.split(",");
        StringBuffer sb = new StringBuffer();
        for (String c:chars){
            sb.append(c).append("  ");
        }
        return sb.toString().trim();*/
        String replace = haoma.replace(",", "  ");
        return replace;
    /*    char[] chars = haoma.replace(","," ").toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c :chars){
            sb.append(c).append(" ");
        }
        return sb.toString().trim();*/
    }




    public void setHaoma(String haoma) {
        this.haoma = haoma;
    }
}
