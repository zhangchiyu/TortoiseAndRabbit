package com.example.doldrum.tortoiseandrabbit.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.bean.UserData;
import com.example.doldrum.tortoiseandrabbit.module.login.LoginActivity;

import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(1,1000*2);

    }





   private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what)
            {
                case 1:
                    List<UserData> userData = App.getInstance().getDaoSession().loadAll(UserData.class);
                    if (userData.size() > 0) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    SplashActivity.this.finish();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };

}
