package com.example.doldrum.tortoiseandrabbit.service;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.bean.UserData;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;
import java.util.List;
import static com.example.doldrum.tortoiseandrabbit.app.App.getInstance;
import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * 为了可以使得在后台播放音乐，我们需要Service
 * Service就是用来在后台完成一些不需要和用户交互的动作
 * @author Administrator
 *
 */
public class AudioService extends Service implements MediaPlayer.OnCompletionListener/*,AudioManager.OnAudioFocusChangeListener*/ {
    public static String TAG = "AudioService";
    MediaPlayer player;
    PowerManager pm ;
    BroadcastReceiver mBatInfoReceiver;
    private final IBinder binder = new AudioBinder();
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return binder;
    }
    /**
     * 当Audio播放完的时候触发该动作
     */
    @Override
    public void onCompletion(MediaPlayer player) {
        // TODO Auto-generated method stub
        //stopSelf();//结束了，则结束Service
    }

    //在这里我们需要实例化MediaPlayer对象
    public void onCreate(){
        super.onCreate();
        pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
       // boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        packageName = "com.example.doldrum.tortoiseandrabbit";
        //我们从raw文件夹中获取一个应用自带的mp3文件
       /* AudioManager audioManager  = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // could not get audio focus.
        }*/
        player = MediaPlayer.create(this, R.raw.bgmuisc);
        player.setLooping(true);
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
         mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d(TAG, "screen on");
                   /* if (isAppOnForeground(AudioService.this)){
                        restart();
                    }*/
                    if (!App.getInstance().isRunInBackground && App.getInstance().activity.getLocalClassName().equals("game.TortoiseRabbitActivity")){
                        restart();
                    }
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d(TAG, "screen off");
                    stop();
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {

                    Log.d(TAG, "screen unlock");
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    Log.i(TAG, " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
                }
            }
        };
        Log.d(TAG, "registerReceiver");
        registerReceiver(mBatInfoReceiver, filter);
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    *//**
                     * 1.后台
                     * 2.屏幕亮
                     * 3.登陆
                     * 4.龟兔页面
                     * 5.
                     *//*
                    if (pm.isScreenOn()){
                        //屏幕亮的情况下
                        if (*//*isApplicationInBackground(AudioService.this) *//* !isAppOnForeground(AudioService.this)){
                            if (player.isPlaying()){
                                player.pause();
                            }
                        }else {
                            if (getInstance().getDaoSession().loadAll(UserData.class).size() == 0){ //未登录情况下
                                if (player.isPlaying()){
                                    player.pause();
                                }
                            }else {
                                String gameActivity = SharedPreferencesUtil.getSpVal("gameActivity");
                                if (gameActivity.equals("true") ){
                                    if (player.isPlaying()){
                                        player.pause();
                                    }
                                }else{
                                    if (SharedPreferencesUtil.getSpVal("isFlagChip").equals("true"))
                                        if (!player.isPlaying()){
                                            player.start();
                                    }
                                    if (SharedPreferencesUtil.getSpVal("home").equals("true")){
                                        if (!player.isPlaying()){
                                            player.start();
                                        }
                                    }else{
                                        if (player.isPlaying()){
                                            player.pause();   
                                        }
                                    }
                                }
                            }
                        }

                    }else {
                        //屏幕暗的情况下
                        if (player.isPlaying()){
                            player.pause();
                        }
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
    }

    private void getTopApp(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                //获取60秒之内的应用数据
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
                Log.i(TAG, "Running app number in last 60 seconds : " + stats.size());
                String topActivity = "";
                //取得最近运行的一个app，即当前运行的app
                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                    }
                    topActivity = stats.get(j).getPackageName();
                }
                Log.i(TAG, "top running app is : "+topActivity);
            }
        }
    }

    private ActivityManager activityManager;
    private String packageName;

    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    private boolean appOnForeground() {
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAppOnForeground(Context context)
    {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RecentTaskInfo> appTask = activityManager.getRecentTasks(Integer.MAX_VALUE, 1);

        if (appTask == null)
        {
            return false;
        }

        if (appTask.get(0).baseIntent.toString().contains(packageName))
        {
            return true;
        }
        return false;
    }


    public boolean isPlaying(){
        if (player!=null){
            return  player.isPlaying();
        }else {
            return false;
        }
    }


    /**
     * 该方法在SDK2.0才开始有的，替代原来的onStart方法
     */
    public int onStartCommand(Intent intent, int flags, int startId){
        if(!player.isPlaying()){
            player.start();
        }
        return START_STICKY;
    }

    public void onDestroy(){
        //super.onDestroy();
        if(player.isPlaying()){
            player.stop();
        }
        player.release();
        unregisterReceiver(mBatInfoReceiver);
    }

  //  @Override
    public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //暂时失去AudioFocus，可以很快再次获取AudioFocus，可以不释放播放资源,只需暂停播放
                if (player!=null&&player.isPlaying()){
                    player.pause();
                }
          /*     if (AudioPlayerService.status == Status.PLAY || AudioPlayerService.status == Status.LOADING) {

                    PlayerController.pausePlay();
                }*/

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //获取了AudioFocus，如果当前处于播放暂停状态，并且这个暂停状态不是用户手动点击的暂停，才会继续播放
                if (player!=null&& !player.isPlaying()){
                    player.start();
                }
              /*  if (AudioPlayerService.status == Status.PAUSE && !Constant.isUserPauseAudio) {
                    PlayerController.continuePlay();
                }*/
            }

            /*case AudioManager.AUDIOFOCUS_GAIN:
                Log.d("AudioService:","获取音频焦点");
                if (player == null) {
                    player = MediaPlayer.create(this, R.raw.bgmuisc);
                    player.setLooping(true);
                }
                else if (!player.isPlaying()) player.start();
                player.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.d("AudioService:","失去音频焦点");
                if (player.isPlaying()) player.stop();
                player.release();
                player = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.d("AudioService:","AUDIOFOCUS_LOSS_TRANSIENT");
                restart();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                Log.d("AudioService:","AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                break;*/
    }

    //为了和Activity交互，我们需要定义一个Binder对象
    public class AudioBinder extends Binder{

        //返回Service对象
        public AudioService getService(){
            return AudioService.this;
        }
    }

    public void stop(){
        if (player!=null){
            if (player.isPlaying()){
                player.pause();
            }
        }
    }
    public void restart(){
        if (player!=null) {
            if (!player.isPlaying()) {
                player.start();
            }
        }
    }

}
