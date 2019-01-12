package com.example.doldrum.tortoiseandrabbit.me;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;

import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class ReNameActivity extends AppCompatActivity {
    private SweetAlertDialog pDialog;
    private TextInputEditText rename;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        setContentView(R.layout.activity_re_name);
        rename = findViewById(R.id.rename);
        submit = findViewById(R.id.bt_submit);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = rename.getText().toString();
                if (!TextUtils.isEmpty(sName)){
                    if (NetUtils.isNetworkConnected(ReNameActivity.this)) {
                        TARService service = App.getInstance().getService();
                        if (pDialog == null){
                            pDialog = new SweetAlertDialog(ReNameActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Loading");
                            pDialog.setCancelable(false);
                        }
                        pDialog.show();
                        service.updateUserInfor("updateUserInfor", App.userData.getSessionToken(), rename.getText().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Result>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if (pDialog !=null && pDialog.isShowing()){
                                            pDialog.dismissWithAnimation();
                                            pDialog = null;
                                        }
                                        Snackbar.make(getWindow().getDecorView(), "修改昵称失败", 2).show();
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onNext(Result result) {
                                            if (result.isSuccess()){
                                                //TODO 修改个人信息
                                                App.userData.setUserName(rename.getText().toString());
                                                App.getInstance().getDaoSession().getUserDataDao().update(App.userData);
                                                finish();
                                            }else{
                                                Snackbar.make(getWindow().getDecorView(), result.getMsg(), LENGTH_LONG).show();
                                                if (pDialog !=null && pDialog.isShowing()){
                                                    pDialog.dismissWithAnimation();
                                                    pDialog = null;
                                                }
                                            }
                                    }
                                });
                    } else {
                        Snackbar.make(getWindow().getDecorView(), "请连接网络", LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}
