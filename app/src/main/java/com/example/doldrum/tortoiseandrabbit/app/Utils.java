package com.example.doldrum.tortoiseandrabbit.app;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doldrum.tortoiseandrabbit.R;
import com.squareup.picasso.Picasso;


public class Utils {
    @BindingAdapter("bind:srcint")
    public static void img_load(ImageView view , int res){
        view.setImageResource(res);
    }


    @BindingAdapter("bind:str")
    public static void imag_load(ImageView view, String string){
        Picasso.with(view.getContext())
                .load(string)
                .error(R.mipmap.ic_launcher_tortoise)
                .into(view);
    }


    @BindingAdapter("total")
    public static void total(TextView view , String string){
        String[] split = string.split("  ");
        Integer total = 0 ;
        for (String str:split) {
            int i = Integer.parseInt(str);
            total += i;
        }
        view.setText(total.toString());
    }


}
