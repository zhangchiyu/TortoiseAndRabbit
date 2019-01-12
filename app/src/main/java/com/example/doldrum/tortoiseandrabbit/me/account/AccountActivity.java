package com.example.doldrum.tortoiseandrabbit.me.account;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.AccountBean;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.squareup.picasso.Picasso;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class AccountActivity extends AppCompatActivity {
    private TextView title;
    private EditText account;
    private TextInputLayout textinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_account);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title =  findViewById(R.id.title);
        title.setText("转账");
        AppCompatButton mAppCompatButton = findViewById(R.id.next);
        textinput = findViewById(R.id.textinput);
        account = findViewById(R.id.account);
        
        mAppCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 18-5-18 验证账号是否合法 根据结果跳转 

                String accountString = account.getText().toString();
                if (!TextUtils.isEmpty(accountString)){
                    getUserInfo(accountString);
                }else{

                }

            }
        });
    }


    //action=getuserinfo_userbh&sessionToken=
    private void getUserInfo(String account) {
        if (NetUtils.isNetworkConnected(AccountActivity.this)) {
            TARService service = App.getInstance().getService();
            service.getuserinfo_userbh(Constants.GETUSERINFO_USERBH,App.userData.getSessionToken(),account)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<AccountBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Snackbar.make(getWindow().getDecorView(), "发生错误", LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(AccountBean result) {
                            if (result.isSuccess()) {
                                Intent intent = new Intent(AccountActivity.this,SureAccountActivity.class);
                                intent.putExtra("data",result.getData());
                                startActivity(intent);

                            } else {
                                Snackbar.make(getWindow().getDecorView(), result.getMsg(), LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Snackbar.make(getWindow().getDecorView(), "请连接网络", LENGTH_LONG).show();
        }
    }
}
