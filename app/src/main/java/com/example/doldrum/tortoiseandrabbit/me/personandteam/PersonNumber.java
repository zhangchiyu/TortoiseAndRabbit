package com.example.doldrum.tortoiseandrabbit.me.personandteam;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.BR;

public class PersonNumber extends BaseObservable {
    private int number;
    private int price;
    public PersonNumber(int number, int price) {
        this.number = number;
        this.price = price;
    }

    public PersonNumber() {
    }

    @Bindable
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        notifyPropertyChanged(BR.number);
    }

    @Bindable
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }


    public void toActivity(View view ){
        Toast.makeText(view.getContext(),this.toString(),Toast.LENGTH_SHORT).show();
    }

}
