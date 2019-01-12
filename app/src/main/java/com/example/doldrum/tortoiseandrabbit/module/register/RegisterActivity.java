package com.example.doldrum.tortoiseandrabbit.module.register;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.MessageCode;
import com.example.doldrum.tortoiseandrabbit.databinding.ActivityRegisterBinding;
import com.example.doldrum.tortoiseandrabbit.module.login.AccountLoginActivity;
import com.example.doldrum.tortoiseandrabbit.module.login.ForgetPasswordActivity;
import com.example.doldrum.tortoiseandrabbit.utils.Md5Utils;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;
import com.example.doldrum.tortoiseandrabbit.utils.StringUtils;
import com.example.doldrum.tortoiseandrabbit.widget.CountDownTextView;
import java.io.UnsupportedEncodingException;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // private UserBean userBean;
    ActivityRegisterBinding binding;
    public String sessionId;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        // userBean = new UserBean();
        binding.register.setOnClickListener(this);
        binding.back.setOnClickListener(this);
        CountDownTextView mCountDownTextView = binding.getRoot().findViewById(R.id.send_code);
        mCountDownTextView.setCountDownMillis(60000);
        mCountDownTextView.setCountDownColor(R.color.Gray,R.color.colorPrimary);
        mCountDownTextView.setOnClickListener(this);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_code:
                String phone = binding.etPhone.getText().toString();
                boolean phoneNumberValid = StringUtils.isPhoneNumberValid(phone);
                if (phoneNumberValid ) {
                    if (NetUtils.isNetworkConnected(RegisterActivity.this)) {
                        TARService service = App.getInstance().getService();
                        service.getverifCode(phone, Constants.SENDCODE)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new Subscriber<MessageCode>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(MessageCode messageCode) {
                                        System.out.println(messageCode.toString());
                                        sessionId = messageCode.data.sessionId;
                                    }
                                });
                    }else{
                        Snackbar.make(getWindow().getDecorView(),"请连接网络",LENGTH_LONG).show();
                    }
                }else {
                    binding.etPhone.setText("");
                    binding.etPhone.setHint("输入正确的手机号");
                    binding.etPhone.setHintTextColor(Color.RED);
                }
                break;
            case R.id.register:
                final String phone1 = binding.etPhone.getText().toString();
                String name = binding.etName.getText().toString();
                String code =  binding.etVerifcode.getText().toString();
                final String password =binding.etPassword.getText().toString();
                if (TextUtils.isEmpty(code)){
                    Snackbar.make(binding.etPhone,"请填写短信验证码",LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Snackbar.make(binding.etPhone,"密码不能为空",LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    Snackbar.make(binding.etPhone,"请填写昵称",LENGTH_LONG).show();
                    return;
                }
                String md5String = null;
                try {
                    md5String = Md5Utils.getMd5String(password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean phoneNumberValid1 = StringUtils.isPhoneNumberValid(phone1);
                if (phoneNumberValid1) {
                    if (NetUtils.isNetworkConnected(RegisterActivity.this)) {
                    TARService service = App.getInstance().getService();
                    pDialog.show();
                    service.register(Constants.REGISTER,phone1,name,sessionId,code,md5String)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toasty.error(RegisterActivity.this,
                                            "注册失败", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(Result result) {
                                    if(result.isSuccess()){
                                        SharedPreferencesUtil.changeSp("phoneNumber",phone1);
                                        SharedPreferencesUtil.changeSp("passWord",password);
                                        pDialog.dismissWithAnimation();
                                        toMain();
                                    }else {
                                        Toasty.error(RegisterActivity.this,
                                                result.getMsg(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    }else{
                        Snackbar.make(getWindow().getDecorView(),"请连接网络",LENGTH_LONG).show();
                    }

                }else {
                    binding.etPhone.setText("");
                    binding.etPhone.setHint("输入正确的手机号");
                    binding.etPhone.setHintTextColor(Color.RED);
                }
                break;
            case R.id.back:
                this.finish();
                break;
            default:
                break;
        }
    }

     private void toMain(){
         startActivity(new Intent(this,AccountLoginActivity.class));
     }

}
