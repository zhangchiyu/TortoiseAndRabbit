package com.example.doldrum.tortoiseandrabbit.game;
import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.CommonAdapter;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.RankBean;
import com.example.doldrum.tortoiseandrabbit.bean.SzBean;
import com.example.doldrum.tortoiseandrabbit.bean.Tousaizi;
import com.example.doldrum.tortoiseandrabbit.databinding.ActivitySixteenBarBinding;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.ToastImage;
import com.example.doldrum.tortoiseandrabbit.utils.ToastUtils;
import com.example.doldrum.tortoiseandrabbit.widget.SaveDialog;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class SixteenBarActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivitySixteenBarBinding binding;
    private CommonAdapter adapter;
    private RecyclerView rlSzList;
    private List<SzBean.DataBean> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sixteen_bar);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sixteen_bar);
        binding.signOut.setOnClickListener(this);

        binding.fiveMillion.setOnClickListener(this);
        binding.million.setOnClickListener(this);
        binding.tenThousand.setOnClickListener(this);
        binding.thousand.setOnClickListener(this);
        binding.oneHundredThousand.setOnClickListener(this);
        binding.safebox.setOnClickListener(this);


        rlSzList = binding.getRoot().findViewById(R.id.rvSzList);
        adapter = new CommonAdapter<SzBean.DataBean>(this, datas, R.layout.sz_item);
        rlSzList.setLayoutManager(new LinearLayoutManager(this));
        rlSzList.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        erba_yemian(1);
        list_shangzhuang_dui();
        list_paihangbang();
    }

    @Override
    protected void onStop() {
        super.onStop();
        erba_yemian(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.sign_out:
                finish();
                break;
            case R.id.safebox:
                SaveDialog dialog  = new SaveDialog(this);
                dialog.show();
                break;
            case R.id.dice:
                break;
            case R.id.one_hundred_thousand://十万
                Bitmap bitmap = loadBitmapFromView(view,true);
                float x = view.getX();
                float y = view.getY();
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmap);
                imageView.setX(x);
                imageView.setY(y);
                imageView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                imageView.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(imageView, "translationX", 200);
                valueAnimator.setDuration(2000);
                valueAnimator.start();
                break;
            case R.id.ten_thousand://万
                break;
            case R.id.five_million://五百万
                break;
            case R.id.million://百万
                break;
            case R.id.thousand://千
                break;
            case R.id.button7://我要上庄
                shenqing_shangzhuang();
                break;
            case R.id.button8://路子
                list_28_luzi();
                break;
        }
    }


    public Bitmap convertViewToBitmap(View view){
        view.getDrawingRect(new Rect());
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout((int)view.getX(), (int)view.getY(), view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static Bitmap loadBitmapFromView(View v, boolean isParemt) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(screenshot);
        v.draw(c);
        return screenshot;
    }


    /**
     * 获取金币排行榜
     */
    public void list_paihangbang(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();

            service.list_paihangbang(Constants.LIST_PAIHANGBANG,App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RankBean>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }
                        @Override
                        public void onNext(RankBean result) {
                            if(result.isSuccess()){
                                List<RankBean.DataBean> data = result.data;

                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }

    /**
     * 申请上庄
     */
    public void shenqing_shangzhuang(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();

            service.shenqing_shangzhuang(Constants.SHENQING_SHANGZHUANG,App.userData.getSessionToken(),
                    App.userData.getUserid(),0.0
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                            // adapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }
                        @Override
                        public void onNext(Result result) {
                            if(result.isSuccess()){
                                //List<LuShuItem> data = result.getData();
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }

    /**
     *撤销上庄
     * chexiao_shangzhuang_dui
     */
    public void chexiao_shangzhuang_dui(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();

            service.chexiao_shangzhuang_dui(Constants.CHEXIAO_SHANGZHUANG_DUI)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                            // adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(Result result) {
                            if(result.isSuccess()){
                                //List<LuShuItem> data = result.getData();

                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }

    /**
     * 申请上庄列表
     * list_shangzhuang_dui
     */
    public void list_shangzhuang_dui(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();

            service.list_shangzhuang_dui(Constants.LIST_SHANGZHUANG_DUI)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<SzBean>() {
                        @Override
                        public void onCompleted() {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(SzBean result) {
                            if(result.isSuccess()){
                                adapter.setData(result.data);
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }


    /**
     * 庄家输赢情况
     * zhuangjia_shuying
     */
    public void zhuangjia_shuying(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();
            service.zhuangjia_shuying(Constants.ZHUANGJIA_SHUYING,App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                            // adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(Result result) {
                            if(result.isSuccess()){
                                //List<LuShuItem> data = result.getData();

                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }

    /**
     * 投骰子
     * tou_saizi
     */
    public void tou_saizi(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();
            service.tou_saizi(Constants.TOU_SAIZI,App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Tousaizi>() {
                        @Override
                        public void onCompleted() {
                            // adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(Tousaizi result) {
                            if(result.isSuccess()){
                                //List<LuShuItem> data = result.getData();

                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }

    /**
     * 下庄
     * xiazhuang
     */
    public void xiazhuang(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();

            service.xiaozhuang(Constants.XIAZHUANG,App.userData.getSessionToken(),App.userData.getUserid())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                            // adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(Result result) {
                            if(result.isSuccess()){
                                //List<LuShuItem> data = result.getData();

                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }

    /**
     * (1表示进入，0表示离开)
     */
    public void erba_yemian(int iferba){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();
            service.erba_yemian("erba_yemian",App.userData.getSessionToken(),iferba)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                        }
                        @Override
                        public void onNext(Result result) {
                            if(result.isSuccess()){
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else {
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }


    /**
     * 投注
     */
    public void tuibing_touzhu(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();
            service.tuibing_touzhu("tuibing_touzhu",App.userData.getSessionToken(),1,1,1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                        }
                        @Override
                        public void onNext(Result result) {
                            if(result.isSuccess()){
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else {
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }

    /**
     * 路子
     */
    public void list_28_luzi(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();
            service.list_28_luzi("list_28_luzi",App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                        }
                        @Override
                        public void onNext(Result result) {
                            if(result.isSuccess()){
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else {
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }


    /**
     * 开始
     */
    public void erbakaishi(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();
            service.erbakaishi("erbakaishi")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                        }
                        @Override
                        public void onNext(Result result) {
                            if(result.isSuccess()){
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else {
           ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }

    public void zhuangjiaqian(){
        if (NetUtils.isNetworkConnected(SixteenBarActivity.this)) {
            TARService service = App.getInstance().getService();
            service.zhuangjiaqian("zhuangjiaqian",App.userData.getUserid(),1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(SixteenBarActivity.this,"发生错误",false);
                        }
                        @Override
                        public void onNext(Result result) {
                            if(result.isSuccess()){
                            }else {
                                Snackbar.make(getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else {
            ToastUtils.centerShow(SixteenBarActivity.this,"请连接网络");
        }
    }

}
