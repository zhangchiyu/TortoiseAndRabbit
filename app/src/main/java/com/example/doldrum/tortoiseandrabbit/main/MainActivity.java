package com.example.doldrum.tortoiseandrabbit.main;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.UpdateBean;
import com.example.doldrum.tortoiseandrabbit.bean.UserData;
import com.example.doldrum.tortoiseandrabbit.game.GameFragment;
import com.example.doldrum.tortoiseandrabbit.home.HomeFragment;
import com.example.doldrum.tortoiseandrabbit.me.MeFragment;
import com.example.doldrum.tortoiseandrabbit.service.AudioService;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;
import java.util.ArrayList;
import java.util.List;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import util.UpdateAppUtils;

import static android.support.design.widget.BaseTransientBottomBar.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        GameFragment.OnFragmentInteractionListener,MeFragment.OnFragmentInteractionListener{
    private List<Fragment> fragmentList = new ArrayList<>();
    private BottomNavigationView navigation;
    private ViewPager viewPager;
    private MenuItem menuItem;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                /*case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;*/
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    public MainActivity() {
    }

    //检测用户是否对本app开启了“Apps with usage access”权限
    private boolean hasPermission() {
        AppOpsManager appOps = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            appOps = (AppOpsManager)
                    getSystemService(Context.APP_OPS_SERVICE);
        }
        int mode = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101;
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS) {
            if (!hasPermission()) {
                //若用户未开启权限，则引导用户开启“Apps with usage access”权限
                startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtil.changeSp("isFlagChip","true");
        intent = new Intent(this, AudioService.class);

        bindService(intent,conn, Context.BIND_AUTO_CREATE);
        startService(intent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        List<UserData> userData = App.getInstance().getDaoSession().loadAll(UserData.class);
        if (userData.size() > 0)
        {
            //TODO 退出登录时要删除用户
            App.userData = userData.get(0);
        }
        setContentView(R.layout.activity_main);
        navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        fragmentList.add(HomeFragment.newInstance("home","home"));
       /* fragmentList.add(GameFragment.newInstance("game","game"));*/
        fragmentList.add(MeFragment.newInstance("me","me"));

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem == null){
                    menuItem = navigation.getMenu().getItem(position);
                }else{
                    menuItem.setChecked(false);
                }
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        SharedPreferencesUtil.changeSp("isPlaying","true");

        SharedPreferencesUtil.changeSp("gameActivity","false");
        SharedPreferencesUtil.changeSp("home","true");
        if (!"true".equals(SharedPreferencesUtil.getSpVal("setbaoxian"))){
            SharedPreferencesUtil.changeSp("baoxian","888888");
        }
        lastVersion();

        int skip = getIntent().getIntExtra("skip",0);
        if (skip == 1){
            viewPager.setCurrentItem(1);
        }
    }

    private void lastVersion() {
        if (NetUtils.isNetworkConnected(MainActivity.this)) {
            TARService service = App.getInstance().getService();
            service.lastVersion(Constants.LASTVERSION,"android")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UpdateBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toasty.error(MainActivity.this,"发生错误",LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(UpdateBean result) {
                            if (result.isSuccess()){
                                UpdateAppUtils.from(MainActivity.this)
                                        .serverVersionCode(result.getData().getVersionCode())
                                        .serverVersionName(result.getData().getVersionName())
                                        .apkPath(result.getData().getUrl())
                                        .updateInfo(result.getData().getTitle())
                                        .isForce(!result.getData().getIfupdate().equals("0"))
                                        .update();
                            }
                        }
                    });
        } else {
            Toasty.info(MainActivity.this,"请连接网络",LENGTH_SHORT).show();
        }
    }


    private Intent intent;
    private AudioService audioService;

    //使用ServiceConnection来监听Service状态的变化
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            audioService = null;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //这里我们实例化audioService,通过binder来实现
            audioService = ((AudioService.AudioBinder)binder).getService();
            App.audioService = audioService;
        }
    };
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    private long time;
    /**
     * 双击返回桌面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 2000)) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
