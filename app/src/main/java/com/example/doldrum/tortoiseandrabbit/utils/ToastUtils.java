package com.example.doldrum.tortoiseandrabbit.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.R;


/**
 * ToastUtils 防止相同内容吐司多次弹出
 */
public class ToastUtils {
    private static String oldMsg;
    private static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static TextView tvWarningMessage;

    //自定义Toast
    public static void centerShow(Context context, String text) {
        if (toast == null) {
            View warningView = LayoutInflater.from(context).inflate(R.layout.layout_warning_message, null);
            tvWarningMessage = (TextView) warningView.findViewById(R.id.tvWarningMessage);
            tvWarningMessage.setText(text);
            toast = new Toast(context);
            toast.setView(warningView);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (text.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = text;
                tvWarningMessage.setText(text);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
}