package com.example.doldrum.tortoiseandrabbit.me;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.BaoXianXiangBean;
import com.example.doldrum.tortoiseandrabbit.bean.TeamRecordBean;
import com.example.doldrum.tortoiseandrabbit.me.personandteam.TeamReportActivity;
import com.example.doldrum.tortoiseandrabbit.utils.Md5Utils;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;

import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class SafeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button open,update;
    private TextInputEditText textpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_safe);
        open = findViewById(R.id.button9);
        update = findViewById(R.id.button10);
        textpassword = findViewById(R.id.textpassword);
        findViewById(R.id.back).setOnClickListener(this);
        open.setOnClickListener(this);
        update.setOnClickListener(this);
        View viewById = findViewById(R.id.textView17);
        String baoxian = SharedPreferencesUtil.getSpVal("baoxian");
        String setbaoxian = SharedPreferencesUtil.getSpVal("setbaoxian");
        if (TextUtils.isEmpty(setbaoxian)){
            viewById.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button9:
                String s = textpassword.getText().toString();
                if (TextUtils.isEmpty(s)){
                    Toasty.info(SafeActivity.this,"请输入保险箱密码",Toast.LENGTH_SHORT,true).show();
                }
                if (s.equals(SharedPreferencesUtil.getSpVal("baoxian"))){
                    getwode_yue(Md5Utils.getMd5String(s));
                }else{
                    Toasty.info(SafeActivity.this,"保险箱密码错误",Toast.LENGTH_SHORT,true).show();
                }
                break;
            case R.id.button10:
                startActivity(new Intent(this,UpdatePasswordActivity.class));
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void getwode_yue(String pwd) {
        if (NetUtils.isNetworkConnected(SafeActivity.this)) {
            TARService service = App.getInstance().getService();
            service.wode_baoxian_yue("wode_baoxian_yue",App.userData.getSessionToken(),pwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaoXianXiangBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Snackbar.make(getWindow().getDecorView(),"发生错误",LENGTH_LONG).show();
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(BaoXianXiangBean result) {
                            if(result.isSuccess()){
                                BaoXianXiangBean.DataBean data = result.getData();
                                showTouzhuZhuangtai(data.getAmount(),data.getAmount_mibao());
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Snackbar.make(getWindow().getDecorView(),"请连接网络",LENGTH_LONG).show();
        }
    }


    Dialog touzhuDialog;

    private void showTouzhuZhuangtai(String amount ,String mibao) {
        if (touzhuDialog == null) {
            View view = View.inflate(SafeActivity.this, R.layout.baoxianxiang, null);
            TextView textVIew = view.findViewById(R.id.textView21);
            textVIew.setText(amount);
            TextView baoxian = view.findViewById(R.id.textView24);
            baoxian.setText(mibao);
            Button submit = view.findViewById(R.id.button11);
            ImageView imageView2 = view.findViewById(R.id.imageView2);
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (touzhuDialog.isShowing()){
                        touzhuDialog.dismiss();
                    }
                }
            });
            final TextInputEditText mTextInputEditText = view.findViewById(R.id.baoxian);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = mTextInputEditText.getText().toString();
                    if (TextUtils.isEmpty(s)){
                        Toasty.info(SafeActivity.this,"请输入存取金额");
                    }else {
                        chu(s);
                    }
                }
            });
            Button quchu = view.findViewById(R.id.button12);
            quchu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = mTextInputEditText.getText().toString();
                    if (TextUtils.isEmpty(s)){
                        Toasty.info(SafeActivity.this,"请输入存取金额");
                    }else {
                        qu(s);
                    }
                }
            });
            touzhuDialog = new Dialog(SafeActivity.this, R.style.transparentFrameWindowStyle);
            touzhuDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        Window window = touzhuDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 100 ;
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        touzhuDialog.onWindowAttributesChanged(wl);
        touzhuDialog.setCanceledOnTouchOutside(true);
        touzhuDialog.show();
    }

    private void chu(String s) {
        if (NetUtils.isNetworkConnected(SafeActivity.this)) {
            TARService service = App.getInstance().getService();
            service.cunbaoxian("cunbaoxian",App.userData.getSessionToken(),s)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaoXianXiangBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Snackbar.make(getWindow().getDecorView(),"发生错误",LENGTH_LONG).show();
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(BaoXianXiangBean result) {
                            if(result.isSuccess()){
                                touzhuDialog.dismiss();
                                touzhuDialog = null;
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Snackbar.make(getWindow().getDecorView(),"请连接网络",LENGTH_LONG).show();
        }
    }

    private void qu(String s) {
        if (NetUtils.isNetworkConnected(SafeActivity.this)) {
            TARService service = App.getInstance().getService();
            service.qubaoxian("qubaoxian",App.userData.getSessionToken(),s)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaoXianXiangBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Snackbar.make(getWindow().getDecorView(),"发生错误",LENGTH_LONG).show();
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(BaoXianXiangBean result) {
                            if(result.isSuccess()){
                                touzhuDialog.dismiss();
                                touzhuDialog = null;
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Snackbar.make(getWindow().getDecorView(),"请连接网络",LENGTH_LONG).show();
        }
    }

}
