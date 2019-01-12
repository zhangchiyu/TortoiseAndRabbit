package com.example.doldrum.tortoiseandrabbit.module.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.MessageCode;
import com.example.doldrum.tortoiseandrabbit.databinding.ActivityForgetPasswordBinding;
import com.example.doldrum.tortoiseandrabbit.parameter.SendCode;
import com.example.doldrum.tortoiseandrabbit.utils.Md5Utils;
import com.example.doldrum.tortoiseandrabbit.utils.StringUtils;
import com.example.doldrum.tortoiseandrabbit.widget.CountDownTextView;

import java.io.UnsupportedEncodingException;

import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private String phone;
    private String code;
    private ActivityForgetPasswordBinding binding;
    private String sessionId;
    private TextInputEditText newPassword,surePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        binding.sumbit.setOnClickListener(this);
        newPassword = binding.getRoot().findViewById(R.id.new_password);
        surePassword = binding.getRoot().findViewById(R.id.sure_password);
        CountDownTextView mCountDownTextView = binding.getRoot().findViewById(R.id.send_code);
        mCountDownTextView.setCountDownMillis(60000);
        mCountDownTextView.setCountDownColor(R.color.Gray,R.color.colorPrimary);
        mCountDownTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                this.finish();
                break;
            case R.id.send_code:
                String phone = binding.phoneNumber.getText().toString();
                boolean phoneNumberValid = StringUtils.isPhoneNumberValid(phone);
                if (phoneNumberValid) {
                    TARService service = App.getInstance().getService();
                    service.getverifCode(phone,Constants.SENDCODE)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<MessageCode>() {
                                @Override
                                public void onCompleted() {
                                    System.out.println("onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    System.out.println(e.toString());
                                }

                                @Override
                                public void onNext(MessageCode messageCode) {
                                    System.out.println(messageCode.toString());
                                    sessionId = messageCode.data.sessionId;
                                }
                            });
                }else {
                    binding.phoneNumber.setText("");
                    binding.phoneNumber.setHint("请输入正确的手机号码");
                    binding.phoneNumber.setHintTextColor(Color.RED);
                }
                break;
            case R.id.sumbit:
                phone = binding.phoneNumber.getText().toString();
                if (!StringUtils.isPhoneNumberValid(phone)){
                    binding.textPhoneNumber.setErrorEnabled(true);
                    binding.textPhoneNumber.setError("号码不对！");
                }
                String sNewPassWord = newPassword.getText().toString();
                String sSurePassword = surePassword.getText().toString();
                String code = binding.editCode.getText().toString();
                if (TextUtils.isEmpty(code)){
                    Toasty.error(this,"验证码为空", Toast.LENGTH_LONG,true).show();
                    return;
                }
                if (!sNewPassWord.equals(sSurePassword)){
                    Toasty.error(this,"两次输入密码不对，请重新输入", Toast.LENGTH_LONG,true).show();
                }else {

                    String md5String = null;
                    try {
                        md5String = Md5Utils.getMd5String(sNewPassWord);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    TARService service = App.getInstance().getService();
                    service.findPassword(Constants.FINDPASSWORD,phone,sessionId,code,md5String)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result>() {
                                @Override
                                public void onCompleted() {
                                    startActivity(new Intent(ForgetPasswordActivity.this,AccountLoginActivity.class));
                                    ForgetPasswordActivity.this.finish();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toasty.error(ForgetPasswordActivity.this,
                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onNext(Result result) {
                                    if (result.isSuccess()){
                                        Toasty.info(ForgetPasswordActivity.this,
                                                result.getMsg(),Toast.LENGTH_LONG,true).show();
                                    }
                                }
                            });
                }
                break;
                default:
                    break;
        }
    }
}
