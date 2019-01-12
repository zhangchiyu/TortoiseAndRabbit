package com.example.doldrum.tortoiseandrabbit.bean;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.me.personandteam.TeamReportActivity;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class TeamDataDetailBean extends BaseObservable {
    /**
     * userid : 55802
     * jia : 7.00
     * jian : -8.00
     *
     *
     * "user_bh": "10003",
     "user_name": "",
     "avatar": "http://122.14.213.233:1002/templates/main/images/user-avatar.png",
     "xiaoliang": "18180.00",
     "ifyoumingxi": "0"
     *"yingkui": 0,
     * "zonge": 0
     *
     */



    private String user_bh;
    private String user_name;
    private String avatar;
    private String xiaoliang;
    private String ifyoumingxi;
    private String yingkui;
    private String zonge;
    @Bindable
    public String getYingkui() {
        return yingkui;
    }

    public void setYingkui(String yingkui) {
        this.yingkui = yingkui;
    }
    @Bindable
    public String getZonge() {
        return zonge;
    }
    public void setZonge(String zonge) {
        this.zonge = zonge;
    }

    @Bindable
    public String getUser_bh() {
        return user_bh;
    }

    public void setUser_bh(String user_bh) {
        this.user_bh = user_bh;
    }

    @Bindable
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Bindable
    public String getXiaoliang() {
        return xiaoliang;
    }

    public void setXiaoliang(String xiaoliang) {
        this.xiaoliang = xiaoliang;
    }

    @Bindable
    public String getIfyoumingxi() {
        return ifyoumingxi;
    }

    public void setIfyoumingxi(String ifyoumingxi) {
        this.ifyoumingxi = ifyoumingxi;
    }


    public void toActivity(View view ){
        getNetWorkData(view.getContext());
    }

    private void getNetWorkData(final Context context) {
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String etime = simpleDateFormat.format(instance.getTime());
        instance.add(Calendar.DATE,-30);
        String stime = simpleDateFormat.format(instance.getTime());
        if (NetUtils.isNetworkConnected(context)) {
            TARService service = App.getInstance().getService();
            service.list_tuandui_jinbi(Constants.LIST_TUANDUI_JINBI,App.userData.getSessionToken(),user_bh,stime,etime,1,20)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<TeamRecordBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastUtils.centerShow(context,"发生错误");
                            e.printStackTrace();
                        }
                        @Override
                        public void onNext(TeamRecordBean result) {
                            if(result.isSuccess()){
                                if (result.getData().getDataDetail()!=null && result.getData().getDataDetail().size()>0 && !result.getData().getDataDetail().get(1).getUser_bh().equals(user_bh)){
                                    context.startActivity(new Intent(context, TeamReportActivity.class).putExtra("user_bh",getUser_bh()));
                                }
                            }else {
                                ToastUtils.centerShow(context,result.getMsg());
                            }
                        }
                    });
        }else{
            ToastUtils.centerShow(context,"请连接网络");
        }
    }

}
