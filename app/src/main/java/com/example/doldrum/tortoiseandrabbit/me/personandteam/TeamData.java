package com.example.doldrum.tortoiseandrabbit.me.personandteam;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.doldrum.tortoiseandrabbit.BR;

public class TeamData extends BaseObservable {
    private  int id ;
    private  String name;
    private  int total;
    private  String url;

    public TeamData(int id, String name, int total, String url) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.url = url;
    }

    public TeamData() {
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        notifyPropertyChanged(BR.total);
    }
    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }



}

