package com.example.doldrum.tortoiseandrabbit.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.UserData;
import com.example.doldrum.tortoiseandrabbit.main.MainActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.doldrum.tortoiseandrabbit.app.App.getInstance;

/**
 * https://blog.csdn.net/u013144287/article/details/79274020
 * https://blog.csdn.net/mingchashui/article/details/79481566
 * 可参见此两篇博客
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public  int WX_LOGIN = 1;

    private IWXAPI iwxapi;

    private SendAuth.Resp resp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        iwxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
    }



    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {
        System.out.println("------------------------" + baseResp.getType());
        //微信登录为getType为1，分享为0
        if (baseResp.getType() == WX_LOGIN){
            //登录回调
//            System.out.println("------------登陆回调------------");
            resp = (SendAuth.Resp) baseResp;
//            System.out.println("------------登陆回调的结果------------：" +  new Gson().toJson(resp));
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = String.valueOf(resp.code);
                    //获取用户信息
                    //getAccessToken(code)
                    TARService service = App.getInstance().getService();
                    service.wx_login(Constants.WXLOGIN,code).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<WXUserResault>() {
                                @Override
                                public void onCompleted() {
                                    startActivity(new Intent(WXEntryActivity.this,MainActivity.class));
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toasty.error(WXEntryActivity.this,e.getMessage(),Toast.LENGTH_LONG,true).show();
                                }
                                @Override
                                public void onNext(WXUserResault result) {
                                    UserData userData = new UserData();
                                    userData.setAvatar(result.getData().getAvatar());
                                    userData.setRecommandCode(result.getData().getRecommandCode());
                                    userData.setRecommandUserbh(result.getData().getRecommandUserbh());
                                    userData.setUserbh(result.getData().getUserbh());
                                    userData.setSessionToken(result.getData().getSessionToken());
                                    userData.setToken(result.getData().getToken());
                                    App.userData = userData;
                                    List<UserData> listUserData = getInstance().getDaoSession().loadAll(UserData.class);
                                    if (null!= listUserData && listUserData.size()>0){
                                        getInstance().getDaoSession().update(userData);
                                    }else {
                                        long insert = getInstance().getDaoSession().insert(userData);
                                    }
                                }
                            });

                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    Toast.makeText(WXEntryActivity.this, "用户拒绝授权", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    Toast.makeText(WXEntryActivity.this, "用户取消登录", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }else{
            //分享成功回调
            System.out.println("------------分享回调------------");
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //分享成功
                    Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //分享取消
                    Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //分享拒绝
                    Toast.makeText(WXEntryActivity.this, "分享拒绝", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        finish();
    }


}
