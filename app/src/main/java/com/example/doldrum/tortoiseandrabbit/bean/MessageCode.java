package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

public class MessageCode extends Result{

    public MessageCode(int code, String msg) {
        super(code, msg);
    }

    public Data data;

    public class Data{
        public String sessionId;

    }

}
