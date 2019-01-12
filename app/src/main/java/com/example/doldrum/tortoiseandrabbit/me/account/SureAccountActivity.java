package com.example.doldrum.tortoiseandrabbit.me.account;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.AccountBean;
import com.example.doldrum.tortoiseandrabbit.bean.LotteryRecord;
import com.example.doldrum.tortoiseandrabbit.game.TortoiseRabbitActivity;
import com.example.doldrum.tortoiseandrabbit.main.MainActivity;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.BaseTransientBottomBar.LENGTH_SHORT;
import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class SureAccountActivity extends AppCompatActivity {
    private ImageView image;
    private TextView name;
    private TextView id;
    private EditText edit_text;
    private Button button;
    private AccountBean.DataBean mDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_sure_account);
        mDataBean = (AccountBean.DataBean) getIntent().getSerializableExtra("data");
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        Picasso.with(SureAccountActivity.this).load(mDataBean.getAvatar()).into(image);
        name.setText(mDataBean.getUsername());
        id.setText(String.valueOf(mDataBean.getUserid()));


        edit_text = findViewById(R.id.edit_text);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edit_text.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    zhuanzhan(s);
                } else {
                    Snackbar.make(getWindow().getDecorView(), "请输入转出金额", LENGTH_LONG).show();
                }

            }
        });

    }


    private void zhuanzhan(String account) {
        if (NetUtils.isNetworkConnected(SureAccountActivity.this)) {
            TARService service = App.getInstance().getService();
            service.zhuanzhan(Constants.ZHUANZHANG, App.userData.getSessionToken(), App.userData.getUserid()
                    , mDataBean.getUserid(), mDataBean.getUsername(), Double.parseDouble(account))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
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
                        public void onNext(Result result) {
                            if (result.isSuccess()) {
                                startActivity(new Intent(SureAccountActivity.this,MainActivity.class).putExtra("skip",1));

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
