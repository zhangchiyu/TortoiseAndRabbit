package com.example.doldrum.tortoiseandrabbit.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.R;

public class ToastImage {

    private static String oldMsg;
    private static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static TextView tvWarningMessage;
    private static ImageView ivStatus;

    //自定义Toast  flag true 成功 false 失败
    public static void centerShow(Context context, String text,boolean flag) {
        View warningView = LayoutInflater.from(context).inflate(R.layout.gamedialog, null);
        ivStatus = warningView.findViewById(R.id.iv_status);
        if (flag){
            ivStatus.setImageResource(R.mipmap.sucss);
        }else {
            ivStatus.setImageResource(R.mipmap.wrong);
        }
        tvWarningMessage = (TextView) warningView.findViewById(R.id.tvWarningMessage);
        tvWarningMessage.setText(text);
        toast = new Toast(context);
        toast.setView(warningView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
        /*if (toast == null) {
            View warningView = LayoutInflater.from(context).inflate(R.layout.gamedialog, null);
            ivStatus = warningView.findViewById(R.id.iv_status);
            if (flag){
                ivStatus.setImageResource(R.mipmap.sucss);
            }else {
                ivStatus.setImageResource(R.mipmap.wrong);
            }
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
    }*/
}
