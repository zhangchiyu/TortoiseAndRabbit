package com.example.doldrum.tortoiseandrabbit.me.personandteam;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.CommonAdapter;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.DataDetailBean;
import com.example.doldrum.tortoiseandrabbit.bean.IdBean;
import com.example.doldrum.tortoiseandrabbit.bean.IdDataBean;
import com.example.doldrum.tortoiseandrabbit.bean.PurchaseHistoryBean;
import com.example.doldrum.tortoiseandrabbit.home.IDActivity;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class PersonListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<DataDetailBean> data = new ArrayList<>();
    private int page = 1;
    private int pageSize = 20;
    private SweetAlertDialog pDialog;
    private CommonAdapter adapter;
    private ImageView back;
    private TextView title;
    private String stime,etime;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_person_list);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonListActivity.this.finish();
            }
        });
        title = findViewById(R.id.title);
        title.setText("个人报表");
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<DataDetailBean>(PersonListActivity.this, data, R.layout.person_list_item);
        mRecyclerView.setAdapter(adapter);
        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getNetWorkData();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                getNetWorkData();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        etime = simpleDateFormat.format(instance.getTime());
        instance.add(Calendar.DATE,-30);
        stime = simpleDateFormat.format(instance.getTime());
        Log.d("okhttp", "s:"+stime+"e:"+etime);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getNetWorkData();
    }

    private void getNetWorkData() {

        if (NetUtils.isNetworkConnected(PersonListActivity.this)) {
            TARService service = App.getInstance().getService();
            service.list_wode_jinbi(Constants.LIST_WODE_JINBI,App.userData.getSessionToken(),App.userData.getUserid(),stime,etime,page,pageSize)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<PurchaseHistoryBean>() {
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
                        public void onNext(PurchaseHistoryBean result) {
                            if(result.isSuccess()){
                                if (page >1 ){
                                    adapter.addData(result.getData().getDataDetail());
                                }else {
                                    adapter.setData(result.getData().getDataDetail());
                                }

                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Snackbar.make(getWindow().getDecorView(),"请连接网络",LENGTH_LONG).show();
        }
    }
}
