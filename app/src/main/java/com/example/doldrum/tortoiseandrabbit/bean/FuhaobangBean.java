package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class FuhaobangBean extends Result {
    public DataBean data;
    public class DataBean{
        public Owner owner;
        public List<Fuhao> list;
    }
}
