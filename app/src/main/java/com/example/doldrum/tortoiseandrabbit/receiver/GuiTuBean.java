package com.example.doldrum.tortoiseandrabbit.receiver;

import com.example.doldrum.tortoiseandrabbit.bean.TouzhuJine;

public class GuiTuBean {
    public String haoma;
    public int qishu;
    public long time;
    public String caichi_jine;
    public TouzhuJine touzhu_jine;


    public GuiTuBean(long time, String haoma,int qishu) {
        this.time = time;
        this.haoma = haoma;
        this.qishu = qishu;
    }

    public GuiTuBean(String caichi_jine) {
        this.caichi_jine = caichi_jine;
    }

    public GuiTuBean(TouzhuJine touzhu_jine) {
        this.touzhu_jine = touzhu_jine;
    }
}
