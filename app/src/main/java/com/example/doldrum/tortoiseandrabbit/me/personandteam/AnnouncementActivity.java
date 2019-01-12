package com.example.doldrum.tortoiseandrabbit.me.personandteam;

import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.CommonAdapter;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.GonggaolanBean;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class AnnouncementActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageView back;
    private TextView title,tv_select;
    private CommonAdapter adapter;
    private List<GonggaolanBean.DataBean> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_announcement);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        tv_select= findViewById(R.id.tv_select);
        tv_select.setVisibility(View.GONE);
        title.setText("公告栏");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnnouncementActivity.this.finish();
            }
        });
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<GonggaolanBean.DataBean>(AnnouncementActivity.this, data, R.layout.announcement_list_item);
        mRecyclerView.setAdapter(adapter);
        getNetWorkData();
    }

    private void getNetWorkData() {
        if (NetUtils.isNetworkConnected(AnnouncementActivity.this)) {
            TARService service = App.getInstance().getService();
            service.list_gonggaolan("list_gonggaolan")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GonggaolanBean>() {
                        @Override
                        public void onCompleted() {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Snackbar.make(getWindow().getDecorView(),"发生错误",LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(GonggaolanBean result) {
                            adapter.addData(result.getData());
                        }
                    });
        }else{
            Snackbar.make(getWindow().getDecorView(),"请连接网络",LENGTH_LONG).show();
        }
    }
}
