package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

public class PeiLv extends Result{

    /**
     * guitu : 1.95
     * shuzi_1 : 3.25
     * shuzi_2 : 6.5
     * shuzi_3 : 9.75
     * longhu : 1.2
     * longhu_he : 9
     *
     "guitu": 1.95,
     "shuzi_1": 3.25,
     "shuzi_2": 6.5,
     "shuzi_3": 9.75,
     "longhu": 1.2,
     "longhu_he": 9
     */

    /**
     * {
     * 	"guitu": 1.97,
     * 	"shuzi_1": 3.25,
     * 	"shuzi_2": 6.5,
     * 	"shuzi_3": 9.75,
     * 	"longhu": 2.15,
     * 	"longhu_he": 9.75
     * }
     */

    private double guitu;
    private double shuzi_1;
    private double shuzi_2;
    private double shuzi_3;
    private double longhu;
    private double longhu_he;

    public double getGuitu() {
        return guitu;
    }

    public void setGuitu(double guitu) {
        this.guitu = guitu;
    }

    public double getShuzi_1() {
        return shuzi_1;
    }

    public void setShuzi_1(double shuzi_1) {
        this.shuzi_1 = shuzi_1;
    }

    public double getShuzi_2() {
        return shuzi_2;
    }

    public void setShuzi_2(double shuzi_2) {
        this.shuzi_2 = shuzi_2;
    }

    public double getShuzi_3() {
        return shuzi_3;
    }

    public void setShuzi_3(double shuzi_3) {
        this.shuzi_3 = shuzi_3;
    }

    public double getLonghu() {
        return longhu;
    }

    public void setLonghu(double longhu) {
        this.longhu = longhu;
    }

    public double getLonghu_he() {
        return longhu_he;
    }

    public void setLonghu_he(int longhu_he) {
        this.longhu_he = longhu_he;
    }
}
