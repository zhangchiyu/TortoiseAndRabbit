package com.example.doldrum.tortoiseandrabbit.module.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.bean.UserData;
import com.example.doldrum.tortoiseandrabbit.databinding.ActivityLoginBinding;
import com.example.doldrum.tortoiseandrabbit.main.MainActivity;
import com.example.doldrum.tortoiseandrabbit.module.register.RegisterActivity;
import com.example.doldrum.tortoiseandrabbit.wxapi.WxShareAndLoginUtils;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.forgetPassword.setOnClickListener(this);
        binding.register.setOnClickListener(this);
        binding.loginAccount.setOnClickListener(this);
        binding.loginWeixin.setOnClickListener(this);
        List<UserData> userData = App.getInstance().getDaoSession().loadAll(UserData.class);
        if (userData.size() > 0)
        {
            //TODO 退出登录时要删除用户
            App.userData = userData.get(0);
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login_account:
                startActivity(new Intent(this, AccountLoginActivity.class));
                break;
            case R.id.login_weixin:
                WxShareAndLoginUtils.WxLogin(this);
                break;
            case R.id.forget_password:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
                default:
                    break;
        }
    }
}
