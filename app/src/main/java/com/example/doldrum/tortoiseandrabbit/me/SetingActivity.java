package com.example.doldrum.tortoiseandrabbit.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.UserData;
import com.example.doldrum.tortoiseandrabbit.module.login.LoginActivity;
import com.example.doldrum.tortoiseandrabbit.utils.Md5Utils;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;
import com.example.doldrum.tortoiseandrabbit.utils.StringUtils;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class SetingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout til_phonenumber, til_oldpassword, til_newpassword, til_surepassword;
    private TextInputEditText tie_phonenumber, tie_oldpassword, tie_newpassword, tie_surepassword;
    private Button bt_submit, bt_loginout;
    private ImageView back;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_seting);
        String phoneNumber = SharedPreferencesUtil.getSpVal("phoneNumber");
        til_phonenumber = findViewById(R.id.til_phonenumber);
        til_oldpassword = findViewById(R.id.til_oldpassword);
        til_newpassword = findViewById(R.id.til_newpassword);
        til_surepassword = findViewById(R.id.til_surepassword);


        tie_phonenumber = findViewById(R.id.tie_phonenumber);
        if (!TextUtils.isEmpty(phoneNumber)) {
            tie_phonenumber.setText(phoneNumber);
        }
        tie_oldpassword = findViewById(R.id.tie_oldpassword);
        tie_newpassword = findViewById(R.id.tie_newpassword);
        tie_surepassword = findViewById(R.id.tie_surepassword);


        bt_submit = findViewById(R.id.bt_submit);
        bt_loginout = findViewById(R.id.bt_loginout);
        back = findViewById(R.id.back);

        bt_submit.setOnClickListener(this);
        bt_loginout.setOnClickListener(this);
        back.setOnClickListener(this);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.bt_loginout:

                SharedPreferencesUtil.changeSp("phoneNumber", "");
                SharedPreferencesUtil.changeSp("passWord", "");
                List<UserData> userData = App.getInstance().getDaoSession().loadAll(UserData.class);
                for (UserData userData1 :userData){
                    App.getInstance().getDaoSession().delete(userData1);
                }
                toLogin();
                break;
            case R.id.bt_submit:
                String phone = tie_phonenumber.getText().toString();
                String oldPassword = tie_oldpassword.getText().toString();
                final String newPassword = tie_newpassword.getText().toString();
                String surepassword = tie_surepassword.getText().toString();
                if (TextUtils.isEmpty(phone) && !StringUtils.isPhoneNumberValid(phone)) {
                    til_phonenumber.setError("请填写正确的号码");
                    return;
                }
                if (TextUtils.isEmpty(oldPassword) && !SharedPreferencesUtil.getSpVal("passWord").equals(oldPassword)) {
                    til_oldpassword.setError("输入密码与之前设置的密码不符");
                    return;
                }
                if (TextUtils.isEmpty(newPassword)) {
                    til_newpassword.setError("请输入6到10位");
                    return;
                }
                if (TextUtils.isEmpty(surepassword) && !surepassword.equals(newPassword)) {
                    til_newpassword.setError("两次输入的密码不对");
                    return;
                }
                String oldmd5String = null, newmd5String = null;
                try {
                    oldmd5String = Md5Utils.getMd5String(oldPassword);
                    newmd5String = Md5Utils.getMd5String(newPassword);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (NetUtils.isNetworkConnected(SetingActivity.this)) {
                    TARService service = App.getInstance().getService();
                    pDialog.show();
                    service.resetPassword(Constants.RESETPASSWORD, App.userData.getSessionToken(), oldmd5String, newmd5String)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result>() {
                                @Override
                                public void onCompleted() {
                                    pDialog.dismissWithAnimation();
                                    toLogin();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Snackbar.make(getWindow().getDecorView(), "修改密码失败", 2).show();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(Result result) {
                                    if (result.isSuccess()) {
                                          //  SharedPreferencesUtil.changeSp("phoneNumber",phone);
                                        if (result.isSuccess()){
                                            SharedPreferencesUtil.changeSp("passWord",newPassword);
                                        }else{
                                            Snackbar.make(getWindow().getDecorView(), result.getMsg(), LENGTH_LONG).show();
                                        }

                                    } else {
                                        Snackbar.make(getWindow().getDecorView(), result.getMsg(), LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Snackbar.make(getWindow().getDecorView(), "请连接网络", LENGTH_LONG).show();
                }
                break;
        }
    }
    private void toLogin(){
        App.audioService.stop();
        startActivity(new Intent(this,LoginActivity.class));
    }

}
