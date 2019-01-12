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

public class CountDownTextView extends android.support.v7.widget.AppCompatTextView {

    /**
     * 提示文字
     */
    private String mHintText = "重新获取";

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


    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            }
        } else {
            //不可用
            if (isClickable()) {
                setClickable(usable);
                setTextColor(getResources().getColor(unusableColorId));
            }
           /* int minutes = (int) (mLastMillis / 60);
            int remainingSeconds = (int) (mLastMillis % 60);*/
            setText(mLastMillis / 1000 + "s");
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
}