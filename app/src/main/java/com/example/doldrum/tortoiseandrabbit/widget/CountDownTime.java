package com.example.doldrum.tortoiseandrabbit.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 倒计时TextView
 * Created by weijing on 2017-08-21 14:43.
 */

public class CountDownTime extends android.support.v7.widget.AppCompatTextView {

    /**
     * 提示文字
     */
    private String mHintText = "即将开奖";

    /**
     * 倒计时时间
     */
    private long mCountDownMillis = 60_000;

    /**
     * 剩余倒计时时间
     */
    private long mLastMillis;
    /**
     * 间隔时间差(两次发送handler)
     */
    private long mIntervalMillis = 1_000;
    /**
     * 开始倒计时code
     */
    private final int MSG_WHAT_START = 10_010;
    /**
     * 可用状态下字体颜色Id
     */
    private int usableColorId = android.R.color.holo_blue_light;
    /**
     * 不可用状态下字体颜色Id
     */
    private int unusableColorId = android.R.color.darker_gray;


    public CountDownTime(Context context) {
        super(context);
    }

    public CountDownTime(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTime(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_WHAT_START:
//                    Log.e("l", mLastMillis + "");
                    if (mLastMillis > 0) {
                        setUsable(false);
                        mLastMillis -= mIntervalMillis;
                        mHandler.sendEmptyMessageDelayed(MSG_WHAT_START, mIntervalMillis);
                    } else {
                        setUsable(true);
                    }
                    break;
            }
        }
    };


    /**
     * 设置是否可用
     *
     * @param usable
     */
    public void setUsable(boolean usable) {


        if (usable) {
            //可用
            if (!isClickable()) {
                setClickable(usable);
                setTextColor(getResources().getColor(usableColorId));
                setText(mHintText);
                listener.isChanged();
            }
        } else {
            //不可用
            if (isClickable()) {
                setClickable(usable);
                setTextColor(getResources().getColor(unusableColorId));
            }
            int minutes = (int) (mLastMillis / 60 /1000);
            int remainingSeconds = (int) (mLastMillis/1000 % 60);
            StringBuffer sb = new StringBuffer();
            if (minutes == 0){
                sb.append("00:");
            }else {
                sb.append("0"+minutes+":");
            }
            if (remainingSeconds <10){
                sb.append("0"+remainingSeconds);
            }else {
                sb.append(remainingSeconds);
            }
            setText(sb.toString());
            /*if (minutes == 0){
                setText("00："+remainingSeconds);
            }else{
                setText("0"+minutes+"："+remainingSeconds);
            }*/
        }
    }

    /**
     * 设置倒计时颜色
     *
     * @param usableColorId   可用状态下的颜色
     * @param unusableColorId 不可用状态下的颜色
     */
    public void setCountDownColor(@ColorRes int usableColorId, @ColorRes int unusableColorId) {
        this.usableColorId = usableColorId;
        this.unusableColorId = unusableColorId;
    }

    /**
     * 设置倒计时时间
     *
     * @param millis 毫秒值
     */
    public void setCountDownMillis(long millis) {
        mCountDownMillis = millis;
    }

    /**
     * 开始倒计时
     */
    public void start() {
        mLastMillis = mCountDownMillis;
        mHandler.sendEmptyMessage(MSG_WHAT_START);
    }

    /**
     * 重置倒计时
     */
    public void reset() {
        mLastMillis = 0;
        mHandler.sendEmptyMessage(MSG_WHAT_START);
    }

    @Override
    public void setOnClickListener(@Nullable final OnClickListener onClickListener) {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(MSG_WHAT_START);
                start();
                onClickListener.onClick(v);
            }
        });

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(MSG_WHAT_START);
    }

    public void removMsg(){
        mHandler.removeMessages(MSG_WHAT_START);
    }

    public interface TextWatchListener{
        void isChanged();
    }
    private TextWatchListener listener;

    public TextWatchListener getListener() {
        return listener;
    }

    public void setListener(TextWatchListener listener) {
        this.listener = listener;
    }
}