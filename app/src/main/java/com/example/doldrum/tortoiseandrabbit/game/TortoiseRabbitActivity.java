package com.example.doldrum.tortoiseandrabbit.game;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.CommonAdapter;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.Chip;
import com.example.doldrum.tortoiseandrabbit.bean.ColorPoolAmount;
import com.example.doldrum.tortoiseandrabbit.bean.GItem;
import com.example.doldrum.tortoiseandrabbit.bean.GameType;
import com.example.doldrum.tortoiseandrabbit.bean.GongGaoBean;
import com.example.doldrum.tortoiseandrabbit.bean.LotteryRecord;
import com.example.doldrum.tortoiseandrabbit.bean.LuShuBean;
import com.example.doldrum.tortoiseandrabbit.bean.LuShuItem;
import com.example.doldrum.tortoiseandrabbit.bean.PeiLv;
import com.example.doldrum.tortoiseandrabbit.bean.RecordBean;
import com.example.doldrum.tortoiseandrabbit.bean.Stake;
import com.example.doldrum.tortoiseandrabbit.bean.StakeDao;
import com.example.doldrum.tortoiseandrabbit.bean.TouzhuBena;
import com.example.doldrum.tortoiseandrabbit.bean.TouzhuJine;
import com.example.doldrum.tortoiseandrabbit.bean.UserInfo;
import com.example.doldrum.tortoiseandrabbit.bean.XiTongShijianBean;
import com.example.doldrum.tortoiseandrabbit.bean.ZhanjiBean;
import com.example.doldrum.tortoiseandrabbit.databinding.ActivityTortoiseRabbitBinding;
import com.example.doldrum.tortoiseandrabbit.me.WebViewActivity;
import com.example.doldrum.tortoiseandrabbit.receiver.GuiTuBean;
import com.example.doldrum.tortoiseandrabbit.utils.DensityUtil;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;
import com.example.doldrum.tortoiseandrabbit.utils.ToastImage;
import com.example.doldrum.tortoiseandrabbit.utils.ToastUtils;
import com.example.doldrum.tortoiseandrabbit.widget.ChipView;
import com.example.doldrum.tortoiseandrabbit.widget.ContainerViewGroup;
import com.example.doldrum.tortoiseandrabbit.widget.CountDownTime;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.QueryBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class TortoiseRabbitActivity extends AppCompatActivity implements View.OnClickListener ,CountDownTime.TextWatchListener{
    private ActivityTortoiseRabbitBinding binding;
    private CommonAdapter adapter;
    private List<RecordBean> datas = new ArrayList<>();
    private RecyclerView record_recyclerView;
    private FrameLayout fl_wugui, fl_tuizi, fl_long, fl_he, fl_hu;

    private ArrayList<FrameLayout>  frameLayouts = new ArrayList();

    private ImageView iv_qian, iv_wan, iv_tenwan, iv_ttwan, iv_fwan;
    private Button bt_sure, bt_cancle, bt_touzu, bt_lushu;
    private List<FrameLayout> guitulists = new ArrayList<>();
    private List<FrameLayout> lhhlists = new ArrayList<>();
    private TextView tv_num1,tv_num2,tv_num3,tv_long,tv_hu,tv_he,guiPeilv,tuPeilv;
    private RelativeLayout rl_caichi;
    private List<GItem> glist = new ArrayList<>();
    private TextView caichi;
    private CountDownTime mCountDownTextView;
    private TextView marquee,tvZhanji,tvJibbi;
    private ImageView [] images = new ImageView[5];
    Animation enlarge1,enlarge2,enlarge3,enlarge4,enlarge5,narrow;
    private ImageView muisc,introduce;
    FrameLayout mFlContainer,mFlCardBack,mFlCardFront;
    FrameLayout yFlContainer,yFlCardBack,yFlCardFront;
    FrameLayout rFlContainer,rFlCardBack,rFlCardFront;
    private ContainerViewGroup cvg;
    private  SoundPool soundPool;
    private int soundId;
    private void playSound(){
        int streamId= soundPool.play(soundId, 1,1,1,0,1);//播放，得到StreamId
    }
    StakeDao stakeDao = null;

    String dateNowStr;

    private int shuziKG;
    private int wg_width,wg_height,lhh_width,lhh_height;

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            wg_width = fl_wugui.getWidth() - 2 * DensityUtil.dp2px(this,20);
            wg_height = fl_wugui.getHeight() - 2 * DensityUtil.dp2px(this,10);
            lhh_width = fl_long.getWidth() - 2 * DensityUtil.dp2px(this,10);
            lhh_height = fl_long.getHeight() - 2 * DensityUtil.dp2px(this,10);
            if (wg_width <= 0){
                wg_width= DensityUtil.dp2px(this,120);
            }
            if (wg_height<=0){
                wg_height = DensityUtil.dp2px(this,120);
            }
            if (lhh_width<=0){
                lhh_width = DensityUtil.dp2px(this,60);
            }
            if (lhh_height <=0){
                lhh_height = DensityUtil.dp2px(this,60);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ("false".equals(SharedPreferencesUtil.getSpVal("isFlagChip"))){
            isFlagChip = false;
        }else{
            isFlagChip = true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateNowStr = sdf.format(new Date());

        stakeDao = App.getInstance().getDaoSession().getStakeDao();
        soundPool=new SoundPool(5, AudioManager.STREAM_MUSIC,0);//构建对象
        soundId=soundPool.load(this,R.raw.sound,1);//加载资源，得到soundId
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tortoise_rabbit);
        shuziKG = DensityUtil.dp2px(this,50);
        frameLayouts.add(binding.framelayout0);
        frameLayouts.add(binding.framelayout1);
        frameLayouts.add(binding.framelayout2);
        frameLayouts.add(binding.framelayout3);
        frameLayouts.add(binding.framelayout4);
        frameLayouts.add(binding.framelayout5);
        frameLayouts.add(binding.framelayout6);
        frameLayouts.add(binding.framelayout7);
        frameLayouts.add(binding.framelayout8);
        frameLayouts.add(binding.framelayout9);

        tvJibbi = binding.getRoot().findViewById(R.id.tv_jibbi);
        tvZhanji = binding.getRoot().findViewById(R.id.tv_zhanji);
        marquee = binding.getRoot().findViewById(R.id.marquee);
        mFlContainer =binding.getRoot().findViewById(R.id.main_fl_container);
        mFlCardBack = binding.getRoot().findViewById(R.id.main_fl_card_back);
        mFlCardFront = binding.getRoot().findViewById(R.id.main_fl_card_front);

        yFlContainer =binding.getRoot().findViewById(R.id.ymain_fl_container);
        yFlCardBack = binding.getRoot().findViewById(R.id.ymain_fl_card_back);
        yFlCardFront = binding.getRoot().findViewById(R.id.ymain_fl_card_front);

        rFlContainer =binding.getRoot().findViewById(R.id.rmain_fl_container);
        rFlCardBack = binding.getRoot().findViewById(R.id.rmain_fl_card_back);
        rFlCardFront = binding.getRoot().findViewById(R.id.rmain_fl_card_front);


        mCountDownTextView = binding.getRoot().findViewById(R.id.time);
        mCountDownTextView.setCountDownColor(R.color.Gray,R.color.colorPrimary);
        mCountDownTextView.setListener(this);
        cvg = binding.getRoot().findViewById(R.id.cvg);
        fl_wugui = binding.getRoot().findViewById(R.id.fl_wugui);
        fl_tuizi = binding.getRoot().findViewById(R.id.fl_tuizi);
        fl_long = binding.getRoot().findViewById(R.id.fl_long);
        fl_he = binding.getRoot().findViewById(R.id.fl_he);
        fl_hu = binding.getRoot().findViewById(R.id.fl_hu);

        tv_num1 = binding.getRoot().findViewById(R.id.tv_num1);
        tv_num2 = binding.getRoot().findViewById(R.id.tv_num2);
        tv_num3 = binding.getRoot().findViewById(R.id.tv_num3);

        tv_long = binding.getRoot().findViewById(R.id.tv_long);
        tv_hu = binding.getRoot().findViewById(R.id.tv_hu);
        tv_he = binding.getRoot().findViewById(R.id.tv_he);
        guiPeilv = binding.getRoot().findViewById(R.id.gui_peilv);
        tuPeilv = binding.getRoot().findViewById(R.id.tu_peilv);

        rl_caichi = binding.getRoot().findViewById(R.id.rl_caichi);
        rl_caichi.setOnClickListener(this);
        guitulists.add(fl_wugui);
        guitulists.add(fl_tuizi);
        lhhlists.add(fl_long);
        lhhlists.add(fl_he);
        lhhlists.add(fl_hu);

        fl_wugui.setOnClickListener(this);
        fl_tuizi.setOnClickListener(this);
        fl_long.setOnClickListener(this);
        fl_he.setOnClickListener(this);
        fl_hu.setOnClickListener(this);

        //iv_qian,iv_wan, iv_tenwan, iv_ttwan,  iv_fwan;
        iv_qian = binding.getRoot().findViewById(R.id.iv_qian);
        iv_wan = binding.getRoot().findViewById(R.id.iv_wan);
        iv_tenwan = binding.getRoot().findViewById(R.id.iv_tenwan);
        iv_ttwan = binding.getRoot().findViewById(R.id.iv_ttwan);
        iv_fwan = binding.getRoot().findViewById(R.id.iv_fwan);

        images[0] = iv_qian;
        images[1] = iv_wan;
        images[2] = iv_tenwan;
        images[3] = iv_ttwan;
        images[4] = iv_fwan;
        iv_qian.setOnClickListener(this);
        iv_wan.setOnClickListener(this);
        iv_tenwan.setOnClickListener(this);
        iv_ttwan.setOnClickListener(this);
        iv_fwan.setOnClickListener(this);

        //bt_sure  bt_cancle  bt_touzu  bt_lushu
        bt_sure = binding.getRoot().findViewById(R.id.bt_sure);
        bt_cancle = binding.getRoot().findViewById(R.id.bt_cancle);
        bt_touzu = binding.getRoot().findViewById(R.id.bt_touzu);
        bt_lushu = binding.getRoot().findViewById(R.id.bt_lushu);

        bt_sure.setOnClickListener(this);
        bt_cancle.setOnClickListener(this);
        bt_touzu.setOnClickListener(this);
        bt_lushu.setOnClickListener(this);

        lushuAdapter = new LushuAdapter(data,TortoiseRabbitActivity.this);
        touzhuAdapter = new TouzhuAdapter(TortoiseRabbitActivity.this,touzhu_data);


        caichi = binding.getRoot().findViewById(R.id.caichi_jine);
        muisc = binding.getRoot().findViewById(R.id.ivMuisc);

        muisc.setOnClickListener(this);
        introduce = binding.getRoot().findViewById(R.id.introduce);
        introduce.setOnClickListener(this);

        binding.title.setText("龟兔赛跑");

        record_recyclerView = binding.getRoot().findViewById(R.id.record_recyclerview);
        adapter = new CommonAdapter<RecordBean>(this, datas, R.layout.record_item);
        record_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        record_recyclerView.setAdapter(adapter);

        enlarge1= AnimationUtils.loadAnimation(this,R.anim.enlarge);
        enlarge2= AnimationUtils.loadAnimation(this,R.anim.enlarge);
        enlarge3= AnimationUtils.loadAnimation(this,R.anim.enlarge);
        enlarge4= AnimationUtils.loadAnimation(this,R.anim.enlarge);
        enlarge5= AnimationUtils.loadAnimation(this,R.anim.enlarge);
        narrow = AnimationUtils.loadAnimation(this,R.anim.narrow);


        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离
        typeface = Typeface.createFromAsset(getAssets(), "front/DS-DIGIB.TTF");
    }

    private Typeface typeface;
    private  AnimatorSet mRightOutSet,mLeftInSet,yRightOutSet,yLeftInSet,rRightOutSet,rLeftInSet;
    // 设置动画
    private void setAnimators() {
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);

        yRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);
        yLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);

        rRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);
        rLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);

        // 设置点击事件
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mFlContainer.setClickable(false);
            }

           /* @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                yFlContainer.performClick();
            }*/
        });
        mLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFlContainer.setClickable(true);
            }

            /*@Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                yFlContainer.performClick();
            }*/
        });

        // 设置点击事件
        yRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                yFlContainer.setClickable(false);
            }

            /*@Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                rFlContainer.performClick();
            }*/
        });
        yLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                yFlContainer.setClickable(true);
            }

           /* @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                rFlContainer.performClick();
            }*/
        });


        // 设置点击事件
        rRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                rFlContainer.setClickable(false);
            }
        });
        rLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rFlContainer.setClickable(true);
            }
        });
    }

    // 改变视角距离, 贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance(scale);
        mFlCardBack.setCameraDistance(scale);

        yFlCardFront.setCameraDistance(scale);
        yFlCardBack.setCameraDistance(scale);

        rFlCardFront.setCameraDistance(scale);
        rFlCardBack.setCameraDistance(scale);
    }

    private boolean mIsShowBack,ymIsShowBack,rmIsShowBack;
    // 翻转卡片
    public void flipCard(/*View view*/) {
        // 正面朝上
        if (mIsShowBack) {
            mRightOutSet.setTarget(mFlCardFront);
            mLeftInSet.setTarget(mFlCardBack);
            mLeftInSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = false;
        } else { // 背面朝上
            mRightOutSet.setTarget(mFlCardBack);
            mLeftInSet.setTarget(mFlCardFront);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = true;

        }
    }

    public void yflipCard(/*View view*/) {
        // 正面朝上
        if (ymIsShowBack) {
            yRightOutSet.setTarget(yFlCardFront);
            yLeftInSet.setTarget(yFlCardBack);
            yLeftInSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            yRightOutSet.start();
            yLeftInSet.start();
            ymIsShowBack = false;
        } else { // 背面朝上
            yRightOutSet.setTarget(yFlCardBack);
            yLeftInSet.setTarget(yFlCardFront);
            yRightOutSet.start();
            yLeftInSet.start();
            ymIsShowBack = true;
        }
    }

    public void rflipCard(/*View view*/) {
        // 正面朝上
        if (rmIsShowBack) {
            rRightOutSet.setTarget(rFlCardFront);
            rLeftInSet.setTarget(rFlCardBack);
            rLeftInSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
            rRightOutSet.start();
            rLeftInSet.start();
            rmIsShowBack = false;
        } else { // 背面朝上
            rRightOutSet.setTarget(rFlCardBack);
            rLeftInSet.setTarget(rFlCardFront);
            rRightOutSet.start();
            rLeftInSet.start();
            rmIsShowBack = true;
        }
    }


    private boolean isFlagChip = SharedPreferencesUtil.getSpVal("isFlagChip").equals("false");
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMuisc:
                //todo 关闭音乐
                if (isFlagChip){
                    //isFlagChip = !isFlagChip;
                    isFlagChip =  false;
                    muisc.setImageResource(R.mipmap.colosemuise);
                    SharedPreferencesUtil.changeSp("isFlagChip","false");
                }else {
                    //isFlagChip = !isFlagChip;
                    isFlagChip = true ;
                    muisc.setImageResource(R.mipmap.muisc);
                    SharedPreferencesUtil.changeSp("isFlagChip","true");
                }

                //finish();
                break;
            case R.id.introduce:
                startActivity(new Intent(this, WebViewActivity.class)
                        .putExtra("webUrl","")
                        .putExtra("title","游戏介绍"));
               /* Intent inten = new Intent(context , clazz);
                inten.putExtra("webUrl","http://122.14.213.233:1002/mobile/user/jieshao.aspx");
                inten.putExtra("title","游戏分享");*/
                break;
            case R.id.fl_wugui:
            case R.id.fl_tuizi:
            case R.id.fl_long:
            case R.id.fl_he:
            case R.id.fl_hu:
                if (mCountDownTextView.getText().equals("即将开奖")){
                    ToastUtils.centerShow(TortoiseRabbitActivity.this,"本期已封单");
                    return;
                }
                clearBackGround(view.getId());
                break;
            case R.id.framelayout0:
                buildStake(0,frameLayouts.get(0));
                break;
            case R.id.framelayout1:
                buildStake(1,frameLayouts.get(1));
                break;
            case R.id.framelayout2:
                buildStake(2,frameLayouts.get(2));
                break;
            case R.id.framelayout3:
                buildStake(3,frameLayouts.get(3));
                break;
            case R.id.framelayout4:
                buildStake(4,frameLayouts.get(4));
                break;
            case R.id.framelayout5:
                buildStake(5,frameLayouts.get(5));
                break;
            case R.id.framelayout6:
                buildStake(6,frameLayouts.get(6));
                break;
            case R.id.framelayout7:
                buildStake(7,frameLayouts.get(7));
                break;
            case R.id.framelayout8:
                buildStake(8,frameLayouts.get(8));
                break;
            case R.id.framelayout9:
                buildStake(9,frameLayouts.get(9));
                break;
            case R.id.iv_qian:
            case R.id.iv_wan:
            case R.id.iv_tenwan:
            case R.id.iv_ttwan:
            case R.id.iv_fwan:
                touzu(view.getId());
                startAnimation(view);
                break;
            case R.id.bt_sure:
                processListStake();
                break;
            case R.id.bt_cancle:
                if (mCountDownTextView.getText().equals("即将开奖")){
                    ToastUtils.centerShow(TortoiseRabbitActivity.this,"封单期间不可撤单");
                    return;
                }
                removeChip(fl_wugui,false);
                removeChip(fl_tuizi,false);
                removeChip(fl_long,false);
                removeChip(fl_he,false);
                removeChip(fl_hu,false);
                for (int i = 0; i <10 ;i++){
                    removeChip(frameLayouts.get(i),false);
                }
                listStake.clear();
                chedan() ;//游戏撤单 撤单成功
                break;
            case R.id.bt_touzu:
                //getTouzhuZhuangtai();
                touzhuPage = 1;
                list_wode_touzhu();
                break;
            case R.id.bt_lushu:
                getList_lushu();//获取路数
                break;
            case R.id.rl_caichi:
                if (!mCountDownTextView.getText().equals("即将开奖")){
                    caichi_touzhu();  //可以彩池用投注成功
                }
                break;
        }
    }

    private void startAnimation(View view) {
        for (ImageView imageView :images){
            Animation animation = imageView.getAnimation();
            if (animation!=null){
                animation.setFillAfter(false);
                view.startAnimation(animation);
            }
        }
        switch (view.getId()){
            case R.id.iv_qian:
                enlarge1.setFillAfter(true);
                view.startAnimation(enlarge1);
                break;
            case R.id.iv_wan:
                enlarge2.setFillAfter(true);
                view.startAnimation(enlarge2);
                break;
            case R.id.iv_tenwan:
                enlarge3.setFillAfter(true);
                view.startAnimation(enlarge3);
                break;
            case R.id.iv_ttwan:
                enlarge4.setFillAfter(true);
                view.startAnimation(enlarge4);
                break;
            case R.id.iv_fwan:
                enlarge5.setFillAfter(true);
                view.startAnimation(enlarge5);
                break;
        }


    }

    private void processListStake() {


        if (mCountDownTextView.getText().equals("即将开奖")){
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"封单期间不可投注");
            return;
        }
        if (listStake.size() == 0){
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"未投注");
            return;
        }

        String s = new Gson().toJson(listStake);
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.san_touzhu(Constants.SAN_TOUZHU, App.userData.getSessionToken(), new Gson().toJson(listStake))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                            listStake.clear();
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
                                ToastImage.centerShow(TortoiseRabbitActivity.this,result.getMsg(),true);
                                //TODO 插入数据库
                                App.getInstance().getDaoSession().getStakeDao().insertInTx(listStake);
                                zhanji(); //更新战绩
                                getUserInfo(); //更新金币
                            } else {
                                ToastImage.centerShow(TortoiseRabbitActivity.this,result.getMsg(),false);
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }


    private void touzu(int id) {
        switch (id){
            case R.id.iv_qian:
                chip = Chip.THOUSAND;
                break;
            case R.id.iv_wan:
                chip = Chip.TENTHOUSAND;
                break;
            case R.id.iv_tenwan:
                chip = Chip.TENFINISH;
                break;
            case R.id.iv_ttwan:
                chip = Chip.ONEMILLION;
                break;
            case R.id.iv_fwan:
                chip = Chip.FIVEMILLION;
                break;
        }
    }

    private List<Stake> listStake = new ArrayList<>();

    private GameType gameType;
    private Chip chip;
    //private List<GameTypeChip> gameTypeChips = new ArrayList<>();
    //private HashMap<GameType,GameTypeChip> gameTypeChipMaps = new HashMap();

    private void clearBackGround(int id) {
        if (chip == null){
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请先选择筹码");
            return;
        }
        if (isFlagChip){
            playSound();
        }
        for (FrameLayout frameLayout : guitulists) {
            frameLayout.setBackgroundResource(R.drawable.un_selector);
        }
        for (FrameLayout frameLayout : lhhlists) {
            frameLayout.setBackgroundResource(R.drawable.un_selector_longandhu);
        }
        switch (id) {
            case R.id.fl_wugui:
                fl_wugui.setBackgroundResource(R.drawable.guitu_bg);
                gameType =GameType.GUI;
                ChipView wugui = new ChipView(TortoiseRabbitActivity.this, chip.getResimg(), wg_width, wg_height);
                switch (chip){
                    case THOUSAND:
                        wugui.number = 1000;
                        break;
                    case TENTHOUSAND:
                        wugui.number = 10000;
                        break;
                    case TENFINISH:
                        wugui.number = 100000;
                        break;
                    case ONEMILLION:
                        wugui.number = 1000000;
                        break;
                    case FIVEMILLION:
                        wugui.number = 5000000;
                        break;
                }
                amount -= wugui.number;
                fl_wugui.addView(wugui);
                //点击乌龟
                break;
            case R.id.fl_tuizi:
                fl_tuizi.setBackgroundResource(R.drawable.guitu_bg);
                gameType =GameType.TU;
                ChipView tuizi = new ChipView(TortoiseRabbitActivity.this, chip.getResimg(), wg_width, wg_height);
                switch (chip){
                    case THOUSAND:
                        tuizi.number = 1000;
                        break;
                    case TENTHOUSAND:
                        tuizi.number = 10000;
                        break;
                    case TENFINISH:
                        tuizi.number = 100000;
                        break;
                    case ONEMILLION:
                        tuizi.number = 1000000;
                        break;
                    case FIVEMILLION:
                        tuizi.number = 5000000;
                        break;
                }
                amount -= tuizi.number;
                fl_tuizi.addView(tuizi);
                //点击兔子
                break;
            case R.id.fl_long:
                fl_long.setBackgroundResource(R.drawable.selector_longandhu);
                gameType =GameType.LONG;
                ChipView fll_long = new ChipView(TortoiseRabbitActivity.this, chip.getResimg(), lhh_width, lhh_height);
                switch (chip){
                    case THOUSAND:
                        fll_long.number = 1000;
                        break;
                    case TENTHOUSAND:
                        fll_long.number = 10000;
                        break;
                    case TENFINISH:
                        fll_long.number = 100000;
                        break;
                    case ONEMILLION:
                        fll_long.number = 1000000;
                        break;
                    case FIVEMILLION:
                        fll_long.number = 5000000;
                        break;
                }
                amount -= fll_long.number;
                fl_long.addView(fll_long);
                //龙
                break;
            case R.id.fl_he:
                fl_he.setBackgroundResource(R.drawable.selector_longandhu);
                gameType =GameType.HE;
                ChipView fll_he =new ChipView(TortoiseRabbitActivity.this, chip.getResimg(), lhh_width, lhh_height);
                switch (chip){
                    case THOUSAND:
                        fll_he.number = 1000;
                        break;
                    case TENTHOUSAND:
                        fll_he.number = 10000;
                        break;
                    case TENFINISH:
                        fll_he.number = 100000;
                        break;
                    case ONEMILLION:
                        fll_he.number = 1000000;
                        break;
                    case FIVEMILLION:
                        fll_he.number = 5000000;
                        break;
                }
                amount -= fll_he.number;
                fl_he.addView(fll_he);
                //和
                break;
            case R.id.fl_hu:
                fl_hu.setBackgroundResource(R.drawable.selector_longandhu);
                gameType =GameType.HU;
                ChipView fll_hu =new ChipView(TortoiseRabbitActivity.this, chip.getResimg(), lhh_width, lhh_height);
                switch (chip){
                    case THOUSAND:
                        fll_hu.number = 1000;
                        break;
                    case TENTHOUSAND:
                        fll_hu.number = 10000;
                        break;
                    case TENFINISH:
                        fll_hu.number = 100000;
                        break;
                    case ONEMILLION:
                        fll_hu.number = 1000000;
                        break;
                    case FIVEMILLION:
                        fll_hu.number = 5000000;
                        break;
                }
                amount -= fll_hu.number;
                fl_hu.addView(fll_hu);
                //虎
                break;
        }
        tvJibbi.setText("金币："+amount);
        Stake stake = Stake.build(gameType,chip);
        stake.setQishu(qishu);
        stake.setDate(dateNowStr);
        stake.setIsOther(false);
        listStake.add(stake);
    }




    @Override
    protected void onResume() {
        /**
         * 点击选择1W筹码点击押注区域板块在点第二下板块等于下注2W
         * 这个时候选择10W筹码在点击板块则是投注12W金币
         * 确定以后需要点击 确认投注按钮  不想投注需要点击一键撤单
         */
        super.onResume();
        if (App.audioService.isPlaying()){
            App.audioService.stop();
        }
        getRecord();  //获取开奖记录
        if (isFlagChip){
            muisc.setImageResource(R.mipmap.muisc);
        }else{
            muisc.setImageResource(R.mipmap.colosemuise);
        }
        //getList_gongGao();
        getCaichiJine();//获取彩池金额
        getRule();  //获取游戏规则
        zhanji();
        getUserInfo();
        youxi_peilv();
     }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情
            super.handleMessage(msg);
        }
    };

    private void getUserInfo() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.getUserInfor(Constants.GETUSERINFOR,App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserInfo>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }
                        @Override
                        public void onNext(UserInfo result) {
                            if (result.isSuccess()){
                                amount = Integer.parseInt(result.getData().getAmount());
                                tvJibbi.setText("金币："+amount);
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }
    private int  amount;
    public static String rvZeroAndDot(String s){
        if (s.isEmpty()) {
            return null;
        }
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
    private void zhanji() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.zhanji(Constants.ZHANJI,App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ZhanjiBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(ZhanjiBean result) {
                            if (result.isSuccess()){
                                tvZhanji.setText("今日战绩："+rvZeroAndDot(String.valueOf(result.getZhanji())));
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }


    private void getList_gongGao() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.list_gonggao(Constants.LIST_GONGGAO)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GongGaoBean>() {
                        @Override
                        public void onCompleted() {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(GongGaoBean result) {
                            if (result.isSuccess()){
                                String title = result.getTitle();
                                marquee.setText(title+title);
                                marquee.setSelected(true); //设置跑马灯滚动起来
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }

    private void  getTouzhuZhuangtai(){
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.touzhu_zhuangtai(Constants.TOUZHU_ZHUANGTAI, App.userData.getSessionToken(), App.userData.getUserid())
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
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                        }
                        @Override
                        public void onNext(Result result) {
                            if (result.isSuccess()) {
                                showTouzhuZhuangtai();
                            } else {
                                ToastImage.centerShow(TortoiseRabbitActivity.this,result.getMsg(),false);
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }
    int touzhuPage = 1;
    private void  list_wode_touzhu(){
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.list_wode_touzhu("list_wode_touzhu", App.userData.getSessionToken(), App.userData.getUserid(),20,touzhuPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<TouzhuBena>() {
                        @Override
                        public void onCompleted() {
                             touzhuAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                        }
                        @Override
                        public void onNext(TouzhuBena result) {
                            if (result.isSuccess()) {
                                showTouzhuZhuangtai();
                                if (touzhuPage  ==1){
                                    touzhuAdapter.setData(result.getData());
                                }else {
                                    touzhuAdapter.addData(result.getData());
                                }
                            } else {
                                ToastImage.centerShow(TortoiseRabbitActivity.this,result.getMsg(),false);
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }


    Dialog touzhuDialog;
    RecyclerView touzhu_recyclerview;
    TouzhuAdapter touzhuAdapter;
    List<TouzhuBena.DataBean> touzhu_data = new ArrayList<>();
    private void showTouzhuZhuangtai() {
        if (touzhuDialog == null) {
            View view = View.inflate(TortoiseRabbitActivity.this, R.layout.show_touzhu, null);
            touzhu_recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
            RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
           refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    touzhuPage = 1;
                    list_wode_touzhu();
                    refreshlayout.finishRefresh(2000);//传入false表示刷新失败
                }
            });
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshlayout) {
                    touzhuPage++;
                    list_wode_touzhu();
                    refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                }
            });
            view.findViewById(R.id.iv_dclose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (touzhuDialog.isShowing()){
                        touzhuDialog.dismiss();

                    }
                }
            });
            touzhuDialog = new Dialog(TortoiseRabbitActivity.this, R.style.transparentFrameWindowStyle);
            touzhuDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            touzhu_recyclerview.setLayoutManager(new LinearLayoutManager(this));
            touzhu_recyclerview.setAdapter(touzhuAdapter);
        }
        Window window = touzhuDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 100 ;
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        touzhuDialog.onWindowAttributesChanged(wl);
        touzhuDialog.setCanceledOnTouchOutside(true);
        touzhuDialog.show();
    }

    private String h1,h2,h3;
    private void getRecord() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.list_kaijiang(Constants.LIST_KAIJIANG)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LotteryRecord>() {
                        @Override
                        public void onCompleted() {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(LotteryRecord result) {
                            if (result.isSuccess()) {
                                List<RecordBean> data = result.getData();
                                RecordBean recordBean = data.get(0);
                                qishu = recordBean.getQishu();
                                String haoma = recordBean.getHaoma();
                                String[] split = haoma.split("  ");
                                h1= split[0];
                                h2 = split[1];
                                h3 = split[2];
                                tv_num1.setText(h1);
                                tv_num2.setText(h2);
                                tv_num3.setText(h3);
                                int i  = 0 ;
                                if (qishu <25){
                                     i = (qishu + 1) * 5 * 60 * 1000;
                                }else if (qishu >= 25 && qishu  <96){
                                     i = (qishu - 23) * 10 * 60 * 1000 + 36000000;
                                }else {
                                     i = (qishu  - 95) * 5 * 60 * 1000 + 79200000;
                                }
                                getxitongshijian(i);
                                adapter.setData(data);
                            } else {
                                ToastImage.centerShow(TortoiseRabbitActivity.this,result.getMsg(),false);
                            }
                        }


                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }

    /**
     * 类型
     * 0龟兔游戏，1数字游戏，2龙虎游戏
     * 投注
     * 0表示龟，1表示兔，
     * 0，1，2，3，4，5，6，7，8，9
     * 1龙2虎3和
     *
     * 金额
     * 1000,10000,100000，100000,500000
     */

    private List<Stake>  randChipView(){
        Random rand = new Random();
        int[] money = new int[]{5000, 1000, 100, 10,1};
        List<Stake> list = new ArrayList<Stake>();
        int j = rand.nextInt(20);   //生成的筹码数量
        for (int i=0; i <j;i++){
            int numIndex = rand.nextInt(5); //筹码
            int leixing = rand.nextInt(3);  //游戏类型
            int touzhu= 0;
            switch (leixing){
                case 0:
                    touzhu = rand.nextInt(2);
                    break;
                case 1:
                    touzhu = rand.nextInt(10);
                    break;
                case 2:
                    touzhu = rand.nextInt(4) -1;
                    break;
            }
            Stake stake = new Stake();
            stake.setLeixing(leixing);
            stake.touzhu = touzhu;
            stake.jine = money[numIndex] * 1000;
            stake.isOther = true;
            list.add(stake);
        }
        return list;
    }



    //恢复本期投注筹码
    private void resumeView(List<Stake> list) {
        int resImg = 0;
        if (!list.isEmpty()){
            for (Stake stake:list){
                int jine = stake.getJine();
                switch (jine){
                    case 1000:
                        resImg =  R.mipmap.qian;
                        break;
                    case 10000:
                        resImg = R.mipmap.wan;
                        break;
                    case 100000:
                        resImg = R.mipmap.tenwan;
                        break;
                    case 1000000:
                        resImg = R.mipmap.ttwan;
                        break;
                    case 5000000:
                        resImg =R.mipmap.fwan;
                        break;
                }
                switch ( stake.getleixingtouzhu()){
                    case "00": //龟
                        ChipView wugui = new ChipView(TortoiseRabbitActivity.this, resImg, wg_width, wg_height);
                        if (stake.isOther){ //
                            wugui.isOther = true;
                        }
                        wugui.number = jine;
                        wugui.measure(0,0);
                        fl_wugui.addView(wugui);
                        break;
                    case "01": //兔
                        ChipView tuizhi = new ChipView(TortoiseRabbitActivity.this,resImg, wg_width, wg_height);
                        if (stake.isOther){
                            tuizhi.isOther = true;
                        }
                        tuizhi.number = jine;
                        tuizhi.measure(0,0);
                        Log.e("兔", tuizhi.bitmapX+":"+tuizhi.bitmapY);
                        fl_tuizi.addView(tuizhi);
                        break;
                    case "10": //0
                    case "11": //1
                    case "12": //2
                    case "13": //3
                    case "14": //4
                    case "15": //5
                    case "16": //6
                    case "17": //7
                    case "18": //8
                    case "19": //9
                        ChipView chipView = new ChipView(TortoiseRabbitActivity.this, resImg, shuziKG, shuziKG, 0.25f);
                        if (stake.isOther){
                            chipView.isOther = true;
                        }
                        chipView.number = jine;
                        chipView.measure(0,0);
                        frameLayouts.get(stake.getTouzhu()).addView(chipView);
                        break;
                    case "21": //龙
                        ChipView fll_long = new ChipView(TortoiseRabbitActivity.this, resImg, lhh_width, lhh_height);
                        if (stake.isOther){
                            fll_long.isOther = true;
                        }
                        fll_long.number = jine;
                        fll_long.measure(0,0);
                        fl_long.addView(fll_long);
                        break;
                    case "22": //虎
                        ChipView fll_hu = new ChipView(TortoiseRabbitActivity.this, resImg, lhh_width, lhh_height);
                        if (stake.isOther){
                            fll_hu.isOther = true;
                        }
                        fll_hu.number = jine;
                        fll_hu.measure(0,0);
                        fl_hu.addView(fll_hu);
                        break;
                    case "23": //和
                        ChipView fll_he = new ChipView(TortoiseRabbitActivity.this, resImg, lhh_width, lhh_height);
                        if (stake.isOther){
                            fll_he.isOther = true;
                        }
                        fll_he.number = jine;
                        fll_he.measure(0,0);
                        fl_he.addView(fll_he);
                        break;
                }
            }
        }
    }
    public void getRule() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.youxi_jieshao(Constants.YOUXI_JIESHAO)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(Result result) {
                            if (result.isSuccess()) {

                            } else {
                                Snackbar.make(getWindow().getDecorView(), result.getMsg(), LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }

    public void getCaichiJine() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.caichi_jine(Constants.CAICHI_JINE, App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ColorPoolAmount>() {
                        @Override
                        public void onCompleted() {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(ColorPoolAmount result) {
                            if (result.isSuccess()) {
                                double jine = result.getData().getJine();
                                setCaichi(caichi,String.valueOf(jine));
                            } else {
                                Snackbar.make(getWindow().getDecorView(), result.getMsg(), LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }

    }

    int page = 1;

    public void getList_lushu() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();

            service.list_lushu(Constants.LIST_LUSHU, App.userData.getSessionToken(), App.userData.getUserid(), page, 30)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LuShuBean>() {
                        @Override
                        public void onCompleted() {
                            lushuAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(LuShuBean result) {
                            if (result.isSuccess()) {
                                List<LuShuItem> data = result.getData();
                                Collections.reverse(data);
                                if (page >1){
                                    lushuAdapter.addData(data);
                                }else{
                                    lushuAdapter.setData(data);
                                }
                                showLuShu();
                            } else {
                                Snackbar.make(getWindow().getDecorView(), result.getMsg(), LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }

    Dialog pictureDialog;
    RecyclerView lushu_recyclerview;
    LushuAdapter lushuAdapter;
    List<LuShuItem> data = new ArrayList<>();
    private void showLuShu() {
        if (pictureDialog == null) {
            View view = View.inflate(TortoiseRabbitActivity.this, R.layout.show_lushu, null);
            ImageView iv_close = view.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pictureDialog.dismiss();
                    pictureDialog = null;
                }
            });
            lushu_recyclerview = (RecyclerView) view.findViewById(R.id.lushu_recyclerview);
            pictureDialog = new Dialog(TortoiseRabbitActivity.this, R.style.transparentFrameWindowStyle);
            pictureDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TortoiseRabbitActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            lushu_recyclerview.setLayoutManager(linearLayoutManager);
            lushu_recyclerview.setAdapter(lushuAdapter);
            ((LinearLayoutManager)lushu_recyclerview.getLayoutManager()).scrollToPositionWithOffset(29,0);
        }


        Window window = pictureDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        //wl.y = (int) (TortoiseRabbitActivity.this.getWindowManager().getDefaultDisplay().getHeight() * 0.5);
        wl.y = 100;
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        pictureDialog.onWindowAttributesChanged(wl);
        pictureDialog.setCanceledOnTouchOutside(false);
        pictureDialog.show();
    }




    public void caichi_touzhu() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();

            service.caichi_touzhu(Constants.CAICHI_TOUZHU, App.userData.getSessionToken(), App.userData.getUserid())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(Result result) {
                            if (result.isSuccess()) {
                                //List<LuShuItem> data = result.getData();
                                ToastUtils.centerShow(TortoiseRabbitActivity.this,"彩池投注成功");
                            } else {
                                ToastUtils.centerShow(TortoiseRabbitActivity.this,result.getMsg());
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }

    /**
     * 游戏赔率
     */
    public void youxi_peilv() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();

            service.youxi_peilv(Constants.YOUXI_PEILV)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<PeiLv>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }
                        @Override
                        public void onNext(PeiLv result) {
                            if (result.isSuccess()) {
                                guiPeilv.setText("1 赔 " +result.getGuitu());
                                tuPeilv.setText("1 赔 " +result.getGuitu());
                                tv_long.setText("1 赔 " + result.getLonghu());
                                tv_he.setText("1 赔 " + result.getLonghu_he());
                                tv_hu.setText("1 赔 " + result.getLonghu());
                                if (glist.size() !=0){
                                    glist.clear();
                                }
                                for (int i = 0; i < 10; i++) {
                                    GItem gItem = new GItem(i,"1 赔 "+result.getShuzi_1());
                                    glist.add(i, gItem);
                                }
                                binding.tvPeilv0.setText("1 赔 "+result.getShuzi_1());
                                binding.tvPeilv1.setText("1 赔 "+result.getShuzi_1());
                                binding.tvPeilv2.setText("1 赔 "+result.getShuzi_1());
                                binding.tvPeilv3.setText("1 赔 "+result.getShuzi_1());
                                binding.tvPeilv4.setText("1 赔 "+result.getShuzi_1());
                                binding.tvPeilv5.setText("1 赔 "+result.getShuzi_1());
                                binding.tvPeilv6.setText("1 赔 "+result.getShuzi_1());
                                binding.tvPeilv7.setText("1 赔 "+result.getShuzi_1());
                                binding.tvPeilv8.setText("1 赔 "+result.getShuzi_1());
                                binding.tvPeilv9.setText("1 赔 "+result.getShuzi_1());

                                QueryBuilder<Stake> qb = stakeDao.queryBuilder();
                                List<Stake> list = qb.where(StakeDao.Properties.Qishu.eq(qishu)).list();
                                final ArrayList<Stake> filterList = new ArrayList<>();
                                for (Stake stake:list){
                                    if (stake.getDate().equals(dateNowStr)){
                                        filterList.add(stake);
                                    }
                                }
                                filterList.addAll(randChipView());
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        resumeView(filterList);
                                    }
                                },100);

                            } else {
                                Snackbar.make(getWindow().getDecorView(), result.getMsg(), LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }

    private String buildPeilv(double peilv){
        return "1 赔 " +peilv;
    }


    /**
     * @param frameLayout
     * @param flag  false撤单  true下一期
     */
    private void removeChip(FrameLayout frameLayout,boolean flag){
        int childCount = frameLayout.getChildCount();
        Log.e("childCount",childCount+"");
        for (int i = childCount ; i>0; i--) {
            View childAt = frameLayout.getChildAt(i);
            if (childAt instanceof ChipView) {
                if (flag){
                    frameLayout.removeViewAt(i);
                }else{
                    if (!((ChipView) childAt).isOther){
                        frameLayout.removeViewAt(i);
                    }
                }
            }
        }
    }

    /**
     * 游戏撤单
     */
    public void chedan() {
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.chedan(Constants.CHEDAN,App.userData.getSessionToken(),App.userData.getUserid())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                            zhanji(); //更新战绩
                            getUserInfo(); //更新金币
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"撤单失败",false);
                            e.printStackTrace();
                        }
                        @Override
                        public void onNext(Result result) {
                            if (result.isSuccess()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //stakeDao.deleteAll();
                                        QueryBuilder<Stake> qb = stakeDao.queryBuilder();
                                        List<Stake> list = qb.where(StakeDao.Properties.Qishu.eq(qishu)).list();
                                        ArrayList<Stake> filterList = new ArrayList<>();
                                        for (Stake stake:list){
                                            if (stake.getDate().equals(dateNowStr) && stake.isOther == false){
                                                filterList.add(stake);
                                            }
                                        }
                                        stakeDao.deleteInTx(filterList);
                                        /*removeChip(fl_wugui,false);
                                        removeChip(fl_tuizi,false);
                                        removeChip(fl_long,false);
                                        removeChip(fl_he,false);
                                        removeChip(fl_hu,false);
                                        listStake.clear();
                                        for (int i = 0; i <10 ;i++){
                                            removeChip(frameLayouts.get(i),false);
                                        }*/
                                    }
                                });
                                ToastImage.centerShow(TortoiseRabbitActivity.this,"撤单成功",true);
                            } else {
                                ToastUtils.centerShow(TortoiseRabbitActivity.this,result.getMsg());
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请连接网络");
        }
    }

    public void buildStake(int num, final FrameLayout frameLayout) {
        if (mCountDownTextView.getText().equals("即将开奖")){
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"本期已封单");
            return;
        }
        if (chip == null){
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"请先选择筹码");
            return;
        }
        final ChipView chipView = new ChipView(TortoiseRabbitActivity.this, chip.getResimg(),
                shuziKG, shuziKG, 0.25f);
        switch (chip){
            case THOUSAND:
                chipView.number = 1000;
                break;
            case TENTHOUSAND:
                chipView.number = 10000;
                break;
            case TENFINISH:
                chipView.number = 100000;
                break;
            case ONEMILLION:
                chipView.number = 1000000;
                break;
            case FIVEMILLION:
                chipView.number = 5000000;
                break;
        }
        amount -= chipView.number;
        tvJibbi.setText("金币："+amount);
        chipView.measure(0,0);
        if (isFlagChip){
            playSound();
        }
        frameLayout.addView(chipView);
        Stake stake = Stake.build(chip, num);
        stake.setQishu(qishu);
        stake.setDate(dateNowStr);
        stake.setIsOther(false);
        listStake.add(stake);
    }


    private int qishu;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GuiTuBean event) {
        if (!TextUtils.isEmpty(event.caichi_jine)){
            setCaichi(caichi,event.caichi_jine);
            zhanji(); //更新战绩
            getUserInfo(); //更新金币
        }else {
            if (event.qishu > qishu) {
                qishu = event.qishu;
                getxitongshijian(event.time);
                if (flag) {
                    final String s1 = event.haoma.split(",")[0];
                    final String s2 = event.haoma.split(",")[1];
                    final String s3 = event.haoma.split(",")[2];
                    zhanji(); //更新战绩
                    getUserInfo(); //更新金币
                    getRecord();  //获取开奖记录

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stakeDao.deleteAll();
                            removeChip(fl_wugui,true);
                            removeChip(fl_tuizi,true);
                            removeChip(fl_long,true);
                            removeChip(fl_he,true);
                            removeChip(fl_hu,true);
                            listStake.clear();
                            for (int i = 0; i <10 ;i++){
                                removeChip(frameLayouts.get(i),true);
                            }
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flipCard();
                            tv_num1.setText(s1);
                        }
                    }, 0);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            yflipCard();
                            tv_num2.setText(s2);
                        }
                    }, 2000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rflipCard();
                            tv_num3.setText(s3);
                        }
                    }, 4000);
                    flag = !flag;
                }
            }
        }
        if (event.touzhu_jine!=null){
            Log.e("Touzhu",event.touzhu_jine.toString());
            processTouzhuJine(event.touzhu_jine);
        }
    }

    private void setCaichi(TextView caichi, String caichi_jine) {
        int i = 0;
        if (caichi_jine.contains(".")){
            String substring = caichi_jine.substring(0, caichi_jine.indexOf("."));
            i = Integer.parseInt(substring);
        }else {
           i =  Integer.parseInt(caichi_jine);
        }
        if (i %10000 ==0){
            caichi.setText((i/10000)+"万");
        }else{
            caichi.setText(String.valueOf(i));
        }
    }


    int [] res = new int []{R.mipmap.fwan,R.mipmap.ttwan,R.mipmap.tenwan,R.mipmap.wan,R.mipmap.qian};

    private void buildchip(int n,FrameLayout view,int leixing ,int touzhu,boolean flag){
        int[] money = new int[]{5000, 1000, 100, 10,1};    //先对硬币按面值从大到小排序
        int num[] = new int[money.length];
        for (int i = 0; i < money.length; i++) {
            num[i] = n / money[i];
            n = n % money[i];
        }
        for (int i = 0; i < num.length; i++) {
            for (int j = 0 ; j< num[i];j++){
                ChipView wugui;
                if (flag){
                    //数字
                    wugui = new ChipView(TortoiseRabbitActivity.this, res[i], shuziKG, shuziKG,0.25f);
                }else {
                    //wugui = new ChipView(TortoiseRabbitActivity.this, res[i], wg_width, wg_height);
                    wugui = new ChipView(TortoiseRabbitActivity.this, res[i], lhh_width, lhh_height);
                }
                if (isFlagChip){
                    playSound();
                }
                wugui.isOther = true;
                wugui.measure(0,0);
                view.addView(wugui);

                Stake stake = Stake.build(leixing,money[i]*1000,touzhu);
                stake.setQishu(qishu);
                stake.setDate(dateNowStr);
                stake.setIsOther(true);
                stakeDao.insert(stake);
            }
        }
    }
    private int  cpuTotal(FrameLayout fl_wugui) {
         int childCount = fl_wugui.getChildCount();
            int total = 0;
            for (int i = 0 ; i< childCount ;i++){
                if (fl_wugui.getChildAt(i) instanceof ChipView){
                    total+=((ChipView) fl_wugui.getChildAt(i)).number;
                }
            }
            return  total;
    }

    private void parseView(int num,FrameLayout frameLayout,int leixing ,int touzhu){
        if (num > 0){
            buildchip((num -cpuTotal(frameLayout)) /1000,frameLayout,leixing,touzhu,false);
        }
    }

    private void processTouzhuJine(TouzhuJine touzhu_jine) {
        parseView(touzhu_jine.getGui(),fl_wugui,0,0);
        parseView(touzhu_jine.getTu(),fl_tuizi,0,1);
        parseView(touzhu_jine.getLongX(),fl_long,2,1);
        parseView(touzhu_jine.getHe(),fl_he,2,3);
        parseView(touzhu_jine.getHu(),fl_hu,2,2);
        int shu0 = touzhu_jine.getShu0();
        if (shu0 > 0) {
            buildchip((shu0 - cpuTotal(frameLayouts.get(0))) / 1000, frameLayouts.get(0),1,0,true);
        }
        int shu1 = touzhu_jine.getShu1();
        if (shu1 > 0){
            buildchip((shu1 -cpuTotal(frameLayouts.get(1)))/1000,frameLayouts.get(1),1,1,true);
        }

        int shu2 = touzhu_jine.getShu2();
        if (shu2 > 0){
            buildchip((shu2 - cpuTotal(frameLayouts.get(2))) / 1000, frameLayouts.get(2),1,2,true);
        }
        int shu3 = touzhu_jine.getShu3();
        if (shu3 > 0){
            buildchip((shu3 - cpuTotal(frameLayouts.get(3))) / 1000, frameLayouts.get(3),1,3,true);
        }
        int shu4 = touzhu_jine.getShu4();
        if (shu4 > 0){
            buildchip((shu4 - cpuTotal(frameLayouts.get(4))) / 1000, frameLayouts.get(4),1,4,true);
        }
        int shu5 = touzhu_jine.getShu5();
        if (shu5 > 0){
            buildchip((shu5 - cpuTotal(frameLayouts.get(5))) / 1000, frameLayouts.get(5),1,5,true);
        }
        int shu6 = touzhu_jine.getShu6();
        if (shu6 > 0){
            buildchip((shu6 - cpuTotal(frameLayouts.get(6))) / 1000, frameLayouts.get(6),1,6,true);
        }
        int shu7 = touzhu_jine.getShu7();
        if (shu7 > 0){
            buildchip((shu7 - cpuTotal(frameLayouts.get(7))) / 1000, frameLayouts.get(7),1,7,true);
        }
        int shu8 = touzhu_jine.getShu8();
        if (shu8 > 0){
            buildchip((shu8 - cpuTotal(frameLayouts.get(8))) / 1000, frameLayouts.get(8),1,8,true);
        }
        int shu9 = touzhu_jine.getShu9();
        if (shu9 > 0){
            buildchip((shu9 - cpuTotal(frameLayouts.get(9))) / 1000, frameLayouts.get(9),1,9,true);
        }
    }
    private boolean flag;
    @Override
    public void isChanged() {
        flag = true;
        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        flipCard(); //显示？号
                }
            }, 0);

        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        yflipCard();
                }
            }, 2000);
        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        rflipCard();
                }
            }, 4000);
        ToastUtils.centerShow(TortoiseRabbitActivity.this,"本期已封单,请等待开奖");
    }


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferencesUtil.changeSp("isPlaying","false");
        SharedPreferencesUtil.changeSp("gameActivity","true");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isFlagChip){
            SharedPreferencesUtil.changeSp("isPlaying","true");
        }
        SharedPreferencesUtil.changeSp("gameActivity","false");
        EventBus.getDefault().unregister(this);
    }

    /**
     * 10点到凌晨2点 5分种一次
     * 9点到晚上10点10分钟一次
     * @return
     */
    private int isEffectiveDate(){
        int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//22
        int resault = 0;
        if ( i >=9 && i < 22){
            //10分钟一次
            resault = 10* 60 * 1000;
        }
        if (i>= 22 && 1<=2){
            resault = 5 * 60 * 1000;
        }
        return resault;
    }

    boolean isFendDan;
    private void getxitongshijian(final long time){
        if (NetUtils.isNetworkConnected(TortoiseRabbitActivity.this)) {
            TARService service = App.getInstance().getService();
            service.xitongshijian("xitong_shijian")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<XiTongShijianBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastImage.centerShow(TortoiseRabbitActivity.this,"发生错误",false);
                            e.printStackTrace();
                        }
                        @Override
                        public void onNext(XiTongShijianBean result) {
                            if (result.isSuccess()) {
                                String xitong_shijian = result.getXitong_shijian(); //2018/10/18 1:05:26
                                String hms = xitong_shijian.split(" ")[1];
                                String[] split = hms.split(":");
                                String h = split[0];
                                String m = split[1];
                                String s = split[2];
                                int i = Integer.parseInt(h) * 3600 + Integer.parseInt(m) * 60 + Integer.parseInt(s);
                                isFendDan = time -60 * 1000 - i * 1000 > 0;
                                mCountDownTextView.removMsg();
                                mCountDownTextView.setCountDownMillis(time -60 * 1000 - i * 1000);
                                mCountDownTextView.setTypeface(typeface);
                                mCountDownTextView.start();
                            } else {
                                ToastUtils.centerShow(TortoiseRabbitActivity.this,result.getMsg());
                            }
                        }
                    });
        } else {
            ToastUtils.centerShow(TortoiseRabbitActivity.this,"本期已封单,请等待开奖");
        }
    }
}
