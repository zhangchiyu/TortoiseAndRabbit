package com.example.doldrum.tortoiseandrabbit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.doldrum.tortoiseandrabbit.app.App;


/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class SharedPreferencesUtil {

    public final static String ADDRESS = "address";
    public final static String BDADDRESS = "BDADDRESS";
    public final static String CITY = "CITY";
    public final static String ACCOUNT = "account";
    public final static String  PROID = "proid";
    public final static String  ORDERID = "orderid";
    public final static String  ORDER_TOKEN = "ordertoken";
    public final static String  REFERNECE="refernece";
    public final static String  PASSWORD="password";
    public final static String  LAST_REPORT_LOCATION="last_report_loction";
    public final static String  LAST_REPORT_LAT="last_report_lat";
    public final static String  LAST_REPORT_LON="last_report_lon";

    public static void changeSp(String name, String value) {
        SharedPreferences sp = App.getInstance().getSharedPreferences("sp", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static void changeIntSp(String name, int value) {
        SharedPreferences sp = App.getInstance().getSharedPreferences("sp", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    public static String getSpVal(String name) {
        SharedPreferences sp = App.getInstance().getSharedPreferences("sp", Context.MODE_MULTI_PROCESS);
        String value = sp.getString(name, null);
        return value;
    }

    public static int getIntSpVal(String name) {
        SharedPreferences sp = App.getInstance().getSharedPreferences("sp", Context.MODE_MULTI_PROCESS);
        int value = sp.getInt(name, 0);
        return value;
    }


}
