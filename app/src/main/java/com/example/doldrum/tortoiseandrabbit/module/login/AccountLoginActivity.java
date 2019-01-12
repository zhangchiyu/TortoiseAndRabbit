package com.example.doldrum.tortoiseandrabbit.module.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.UserBean;
import com.example.doldrum.tortoiseandrabbit.bean.UserData;
import com.example.doldrum.tortoiseandrabbit.bean.UserDataDao;
import com.example.doldrum.tortoiseandrabbit.databinding.ActivityAccountLoginBinding;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.main.MainActivity;
import com.example.doldrum.tortoiseandrabbit.utils.Md5Utils;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;
import com.example.doldrum.tortoiseandrabbit.wxapi.WxShareAndLoginUtils;

import java.io.UnsupportedEncodingException;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static com.example.doldrum.tortoiseandrabbit.app.App.*;

public class AccountLoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityAccountLoginBinding binding;
    private EditText et_password;
    private EditText et_phonenumber;
    private SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account_login);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_login);
        binding.login.setOnClickListener(this);
        binding.loginWeixin.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("正在登陆");
        pDialog.setCancelable(false);
        String phoneNumber = SharedPreferencesUtil.getSpVal("phoneNumber");
        String passWord = SharedPreferencesUtil.getSpVal("passWord");
        et_password = binding.getRoot().findViewById(R.id.et_password);
        et_phonenumber = binding.getRoot().findViewById(R.id.et_phonenumber);
        if (!TextUtils.isEmpty(phoneNumber)){
            et_phonenumber.setText(phoneNumber);
        }
        if (!TextUtils.isEmpty(passWord)){
            et_password.setText(passWord);
        }
    }

    @Override
    public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.back:
                    this.finish();
                    break;
                case R.id.login_weixin:
                    WxShareAndLoginUtils.WxLogin(this);
                    break;
                case R.id.login:
                    final String s = et_phonenumber.getText().toString();
                    final String s1 = et_password.getText().toString();
                    String md5String = null;
                    if (TextUtils.isEmpty(s)) {
                        Snackbar.make(et_phonenumber,"请填写账号",LENGTH_LONG).show();
                        return;
                    }
                    if (TextUtils.isEmpty(s1)) {
                        Snackbar.make(et_phonenumber,"请填写密码",LENGTH_LONG).show();
                        return;
                    }
                    try {
                         md5String = Md5Utils.getMd5String(s1);
                         //md5String ="e10adc3949ba59abbe56e057f20f883e";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    TARService service = getInstance().getService();
                    if (NetUtils.isNetworkConnected(AccountLoginActivity.this)){
                        if (pDialog == null){
                            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("正在登陆");
                            pDialog.setCancelable(false);
                        }
                        pDialog.show();
                        service.longin(Constants.LOGIN,s,md5String)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<UserBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }
                                    @Override
                                    public void onError(Throwable e) {
                                        if (pDialog !=null && pDialog.isShowing()){
                                            pDialog.dismissWithAnimation();
                                            pDialog = null;

                                        }
                                        Toasty.error(AccountLoginActivity.this,e.getMessage(), Toast.LENGTH_LONG,true).show();
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onNext(UserBean userBean) {
                                        if (userBean.isSuccess()){
                                            UserData userData = userBean.data;
                                            App.userData = userData;
                                            long insert = getInstance().getDaoSession().insert(userData);
                                            pDialog.dismissWithAnimation();
                                            SharedPreferencesUtil.changeSp("phoneNumber",s);
                                            SharedPreferencesUtil.changeSp("passWord",s1);
                                            toMain();
                                        }else {
                                            if (pDialog !=null && pDialog.isShowing()){
                                                pDialog.dismissWithAnimation();
                                                pDialog = null;
                                            }
                                            Toasty.error(AccountLoginActivity.this,userBean.getMsg(), Toast.LENGTH_SHORT,true).show();
                                        }

                                    }
                                });
                    }else {
                        Snackbar.make(et_phonenumber,R.string.network,LENGTH_LONG).show();
                        return;
                    }
                    break;
                default:
                    break;
            }
        }
    private void toMain(){
        startActivity(new Intent(this,MainActivity.class));
    }

}
