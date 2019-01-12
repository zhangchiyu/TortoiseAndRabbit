package com.example.doldrum.tortoiseandrabbit.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Stake {

    @Id
    private Long id;
    private int qishu;
    private String date;
    public int leixing;
    public int jine;
    public int touzhu;
    public boolean isOther;

    public Stake(int leixing, int jine, int touzhu) {
        this.leixing = leixing;
        this.jine = jine;
        this.touzhu = touzhu;
    }

    public Stake() {

    }

    @Generated(hash = 936463649)
    public Stake(Long id, int qishu, String date, int leixing, int jine, int touzhu,
            boolean isOther) {
        this.id = id;
        this.qishu = qishu;
        this.date = date;
        this.leixing = leixing;
        this.jine = jine;
        this.touzhu = touzhu;
        this.isOther = isOther;
    }

    public static Stake build(int leixing, int jine, int touzhu) {
        Stake stake = new Stake(leixing, jine, touzhu);
        return stake;
    }

    public static Stake build(Chip chip, int num) {
        Stake stake = new Stake();
        stake.leixing = 1;
        stake.touzhu = num;
        switch (chip.name()) {
            case "THOUSAND"://千
                stake.jine = 1000;
                break;
            case "TENTHOUSAND"://万
                stake.jine = 10000;
                break;
            case "TENFINISH"://十万
                stake.jine = 100000;
                break;
            case "ONEMILLION"://百万
                stake.jine = 1000000;
                break;
            case "FIVEMILLION"://五百万
                stake.jine = 5000000;
                break;

        }
        return stake;
    }


    /**
     * 龙和虎  龟兔 生成投注信息
     *
     * @param gameType
     * @param chip
     * @return
     */
    public static Stake build(GameType gameType, Chip chip) {
        Stake stake = new Stake();
        switch (gameType.name()) {
            case "GUI":   //类型 0
                stake.leixing = 0;
                stake.touzhu = 0;
                break;
            case "TU":    //类型 0
                stake.leixing = 0;
                stake.touzhu = 1;
                break;
            case "LONG":   //类型 2
                stake.leixing = 2;
                stake.touzhu = 1;
                break;
            case "HE":    //类型 2
                stake.leixing = 2;
                stake.touzhu = 3;
                break;
            case "HU":      //类型 2
                stake.leixing = 2;
                stake.touzhu = 2;
                break;
        }
        switch (chip.name()) {
            case "THOUSAND"://千
                stake.jine = 1000;
                break;
            case "TENTHOUSAND"://万
                stake.jine = 10000;
                break;
            case "TENFINISH"://十万
                stake.jine = 100000;
                break;
            case "ONEMILLION"://百万
                stake.jine = 1000000;
                break;
            case "FIVEMILLION"://五百万
                stake.jine = 5000000;
                break;

        }
        return stake;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQishu() {
        return this.qishu;
    }

    public void setQishu(int qishu) {
        this.qishu = qishu;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLeixing() {
        return this.leixing;
    }

    public void setLeixing(int leixing) {
        this.leixing = leixing;
    }

    public int getJine() {
        return this.jine;
    }

    public void setJine(int jine) {
        this.jine = jine;
    }

    public int getTouzhu() {
        return this.touzhu;
    }

    public void setTouzhu(int touzhu) {
        this.touzhu = touzhu;
    }


    public String getleixingtouzhu(){
        return String.valueOf(leixing)+String.valueOf(touzhu);
    }

    public boolean getIsOther() {
        return this.isOther;
    }

    public void setIsOther(boolean isOther) {
        this.isOther = isOther;
    }
}
