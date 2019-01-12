package com.example.doldrum.tortoiseandrabbit.me;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.view.View;

import com.example.doldrum.tortoiseandrabbit.BR;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;


public class MeItem extends BaseObservable {

    private int icon;
    private String text;
    private String className;
    private Context context;
    public int gonggaolan_id;

    public MeItem(Context context){
        this.context = context;
    }

    public MeItem(int icon, String text, String className, Context context) {
        this.icon = icon;
        this.text = text;
        this.className = className;
        this.context = context;
    }

    @Bindable
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    @Bindable
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
        notifyPropertyChanged(BR.className);
    }


    public void toActivity(View view ){
        if (className.equals("com.example.doldrum.tortoiseandrabbit.me.personandteam.AnnouncementActivity")){
            SharedPreferencesUtil.changeIntSp("gonggaolan_id",gonggaolan_id);
        }
        Class clazz = null;
        try {
            clazz  = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Intent inten = new Intent(context , clazz);
        inten.putExtra("webUrl","");
        inten.putExtra("title","游戏分享");
        context.startActivity(inten);
    }
}
