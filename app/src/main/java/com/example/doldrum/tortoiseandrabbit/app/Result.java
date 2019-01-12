package com.example.doldrum.tortoiseandrabbit.app;

public class Result {
    private int code;
    private String message;

    public Result(){

    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
    //添加对返回状态成功的判断
    public boolean isSuccess() {
        return code == Constants.SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

}
