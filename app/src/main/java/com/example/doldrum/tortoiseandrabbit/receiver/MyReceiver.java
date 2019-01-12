package com.example.doldrum.tortoiseandrabbit.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.doldrum.tortoiseandrabbit.bean.CaichiJine;
import com.example.doldrum.tortoiseandrabbit.bean.Gong;
import com.example.doldrum.tortoiseandrabbit.bean.TouzhuJine;
import com.example.doldrum.tortoiseandrabbit.utils.Logger;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

				processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

				//打开自定义的Activity
				/*Intent i = new Intent(context, TestActivity.class);
				i.putExtras(bundle);
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);*/

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	private void processCustomMessage(Context context, Bundle bundle) {
		String json = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		if (json.contains("caichi_jine")){
			CaichiJine caichiJine = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_MESSAGE), CaichiJine.class);
			EventBus.getDefault().post(new GuiTuBean(caichiJine.getCaichi_jine()));
		}else if (json.contains("caipiaojieguo")){
			TuiSongGuiTuBean tuiSongGuiTuBean = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_MESSAGE), TuiSongGuiTuBean.class);
			List<TuiSongGuiTuBean.CaipiaojieguoBean> caipiaojieguo = tuiSongGuiTuBean.getCaipiaojieguo();
			TuiSongGuiTuBean.CaipiaojieguoBean caipiaojieguoBean = caipiaojieguo.get(0);
			Logger.e(TAG, "haoma"+caipiaojieguoBean.getHaoma());
			Logger.e(TAG, "qishu"+caipiaojieguoBean.getQishu());
			if (caipiaojieguoBean.getQishu() <25){
				int i = (caipiaojieguoBean.getQishu() + 1) * 5 * 60 * 1000;
				//long l = overTime();
				GuiTuBean guiTuBean = new GuiTuBean(i,caipiaojieguoBean.getHaoma(),caipiaojieguoBean.getQishu());
				EventBus.getDefault().post(guiTuBean);
			}else if (caipiaojieguoBean.getQishu() >= 25 && caipiaojieguoBean.getQishu()  <96){
                int i = (caipiaojieguoBean.getQishu() - 23) * 10 * 60 * 1000 + 36000000;
				//long l = overTime();
				GuiTuBean guiTuBean = new GuiTuBean(i ,caipiaojieguoBean.getHaoma(),caipiaojieguoBean.getQishu());
				EventBus.getDefault().post(guiTuBean);
			}else {
				int i = (caipiaojieguoBean.getQishu()  - 95) * 5 * 60 * 1000 + 79200000;
				//long l = overTime();
				GuiTuBean guiTuBean = new GuiTuBean(i ,caipiaojieguoBean.getHaoma(),caipiaojieguoBean.getQishu());
				EventBus.getDefault().post(guiTuBean);
			}
		}else if(json.contains("gonggaolan")){
			//公告栏消息推送
			Gong gong = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_MESSAGE), Gong.class);
			EventBus.getDefault().post(gong);
		}else{
			TouzhuJine mTouzhuJine = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_MESSAGE), TouzhuJine.class);
			GuiTuBean guiTuBean = new GuiTuBean(mTouzhuJine);
			EventBus.getDefault().post(guiTuBean);
		}
    }


    @SuppressLint("SimpleDateFormat")
	private long overTime()  {
		long now = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return now - sdf.parse(sdf.format(now)).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}


    /**
     * 10点到凌晨2点 5分种一次
     * 9点到晚上10点10分钟一次
     * @return
     */
    private long isEffectiveDate(){
    	//10：00 -22：00 (72期) 22:00-02:00(48期)
		//00：00-02：00（0 -24期）(00:05,00:10,10:15,00:20)
		//10:00 -22:00 (25-96期）
		//22:00 -24:00 (97-120)


        int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//22
        int resault = 0;
        if ( i >=9 && i < 22){
            //10分钟一次
            resault = 10* 60 * 1000;
        }
        if (i>= 22 || i<=2){
            resault = 5 * 60 * 1000;
        }
        return resault;
    }

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Logger.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Logger.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.get(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
/*	private void processCustomMessage(Context context, Bundle bundle) {
		if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
		}
	}*/
}
