package com.example.doldrum.tortoiseandrabbit.bean;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.main.MainActivity;
import com.example.doldrum.tortoiseandrabbit.module.register.RegisterActivity;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class IdDataBean extends BaseObservable {
    /**
     * row_number : 1
     * jine : 0.00
     * user_bh : 10000
     */
    private int row_number;
    private String jine;
    private int user_bh;

    public IdDataBean(int row_number, String jine, int user_bh) {
        this.row_number = row_number;
        this.jine = jine;
        this.user_bh = user_bh;
    }

    public IdDataBean() {

    }

    @Bindable
    public int getRow_number() {
        return row_number;
    }

    public void setRow_number(int row_number) {
        this.row_number = row_number;
    }

    @Bindable
    public String getJine() {
        return jine+"金币";
    }

    public void setJine(String jine) {
        this.jine = jine;
    }

    @Bindable
    public int getUser_bh() {
        return user_bh;
    }

    public void setUser_bh(int user_bh) {
        this.user_bh = user_bh;
    }


    public void toActivity(final View view ){
        final SweetAlertDialog pDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        dialogShow2(view.getContext(),pDialog);
        //initDialog(view.getContext(),pDialog);
    }


    /*
   初始化AlertDialog
    */
    public void initDialog(final Context context, final SweetAlertDialog pDialog)
    {
        //创建AlertDialog的构造器的对象
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        //设置构造器标题
        builder.setTitle("确认购买？");
        //构造器对应的图标
       // builder.setIcon(R.mipmap.ic_launcher);
        //构造器内容,为对话框设置文本项(之后还有列表项的例子)
       // builder.setMessage("你是否要狠心离我而去？");
        //为构造器设置确定按钮,第一个参数为按钮显示的文本信息，第二个参数为点击后的监听事件，用匿名内部类实现
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(final DialogInterface dialog, int which)
            {
                //第一个参数dialog是点击的确定按钮所属的Dialog对象,第二个参数which是按钮的标示值
                if (NetUtils.isNetworkConnected(context)) {
                    TARService service = App.getInstance().getService();
                    service.goumai_id(Constants.GOUMAI_ID,App.userData.getSessionToken(),App.userData.getUserid(),
                            getUser_bh(),getJine())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result>() {
                                @Override
                                public void onCompleted() {
                                    pDialog.dismiss();
                                }
                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(context,"购买失败",Toast.LENGTH_SHORT).show();

                                }
                                @Override
                                public void onNext(Result result) {
                                    if (result.isSuccess()){
                                        Toast.makeText(context,result.getMsg(),Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(context,result.getMsg(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(context,"请连接网络",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        //为构造器设置取消按钮,若点击按钮后不需要做任何操作则直接为第二个参数赋值null
        builder.setNegativeButton("取消",null);
        //为构造器设置一个比较中性的按钮，比如忽略、稍后提醒等
        //builder.setNeutralButton("稍后提醒",null);
        //利用构造器创建AlertDialog的对象,实现实例化
        AlertDialog  alertDialog=builder.create();
        alertDialog.show();
    }


    /**
     * 自定义布局
     * setView()只会覆盖AlertDialog的Title与Button之间的那部分，而setContentView()则会覆盖全部，
     * setContentView()必须放在show()的后面
     */
    private void dialogShow2(final Context mContext,final SweetAlertDialog pDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_manage_dialog, null);
        TextView content = (TextView) v.findViewById(R.id.dialog_content);
        Button btn_sure = (Button) v.findViewById(R.id.dialog_btn_sure);
        Button btn_cancel = (Button) v.findViewById(R.id.dialog_btn_cancel);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (NetUtils.isNetworkConnected(mContext)) {
                    TARService service = App.getInstance().getService();
                    service.goumai_id(Constants.GOUMAI_ID,App.userData.getSessionToken(),App.userData.getUserid(),
                            getUser_bh(),getJine())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result>() {
                                @Override
                                public void onCompleted() {
                                    pDialog.dismiss();
                                }
                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(mContext,"购买失败",Toast.LENGTH_SHORT).show();

                                }
                                @Override
                                public void onNext(Result result) {
                                    if (result.isSuccess()){
                                        Toast.makeText(mContext,result.getMsg(),Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(mContext,result.getMsg(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(mContext,"请连接网络",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }

}
