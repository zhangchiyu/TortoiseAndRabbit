package com.example.doldrum.tortoiseandrabbit.me;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.FenxiangBean;
import com.example.doldrum.tortoiseandrabbit.bean.GongGaoBean;
import com.example.doldrum.tortoiseandrabbit.bean.GuanyuYouxiBean;
import com.example.doldrum.tortoiseandrabbit.game.TortoiseRabbitActivity;
import com.example.doldrum.tortoiseandrabbit.utils.ImagesUtil;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.widget.ProgressWebView;
import com.example.doldrum.tortoiseandrabbit.wxapi.WxShareAndLoginUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by Administrator on 2017/10/16.
 */

public class WebViewActivity extends Activity implements WebPageFinishCallBack ,View.OnClickListener{
    public static final String WEBURL = "web_url";
    ProgressWebView webView;
    Button btLeaveMessage;
    ImageView rightBtn,iv_back;
    String shareUrl;
    TextView tvTitle;  //显示标题
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.web_view_layout);
        initUI();
    }

    public static void instanceAct(Context ctx, String webUrl, String title) {
        Intent intent = new Intent(ctx, WebViewActivity.class);
        intent.putExtra("webUrl", webUrl);
        intent.putExtra("title", title);
        ctx.startActivity(intent);
    }


    private void initUI() {
        webView = findViewById(R.id.webView);
        btLeaveMessage = findViewById(R.id.bt_leave_message);
        rightBtn = findViewById(R.id.img_right_btn);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tvTitle = findViewById(R.id.tvTitle_base);

        webView.setWebPageFinishCallBack(this);
        String url = getIntent().getStringExtra("webUrl");
        Log.i("jason", url);
        if (TextUtils.isEmpty(url)){
            guanyuyouxi();
            setCustomTitle("游戏分享");
     /*       rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setImageResource(R.mipmap.shareicon);
            rightBtn.setOnClickListener(this);*/
        }else{
            webView.loadUrl(url);
            title = getIntent().getStringExtra("title");
            setCustomTitle(title);
            rightBtn.setVisibility(View.VISIBLE);
            rightBtn.setImageResource(R.mipmap.shareicon);
            rightBtn.setOnClickListener(this);
            String ifkai = getIntent().getStringExtra("ifkai");
            if (!TextUtils.isEmpty(ifkai)){
                rightBtn.setVisibility(View.GONE);
               /* webView.loadUrl(url);
                setCustomTitle(title);*/
            }
        }


        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setJavaScriptEnabled(true);
        //有可能是DOM储存API没有打开
        webView.getSettings().setDomStorageEnabled(true);//有可能是DOM储存API没
        //同步请求图片
        webView.getSettings().setBlockNetworkImage(false);


    }

    private void guanyuyouxi() {
        if (NetUtils.isNetworkConnected(WebViewActivity.this)) {
            TARService service = App.getInstance().getService();
            service.gunayu_youxi(Constants.GUANYU_YOUXI_BANBEN)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GuanyuYouxiBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Snackbar.make(getWindow().getDecorView(), "发生错误", LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(GuanyuYouxiBean result) {
                            if (result.isSuccess()) {
                                shareUrl = result.getUrl();
                                webView.loadUrl(shareUrl);
                            }
                        }
                    });
        } else {
            Snackbar.make(getWindow().getDecorView(), "请连接网络", LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                if (webView.getUrl() != null && (webView.getUrl().contains("/accessoriesDetails") || webView.getUrl().contains("/machineDetails"))) {
                    btLeaveMessage.setVisibility(View.GONE);
                }
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
    }

    public void setCustomTitle(String title) {
        if (tvTitle != null)
            tvTitle.setText(title);
    }
    @Override
    public void onObtainWebTitle(String title) {
        setCustomTitle(TextUtils.isEmpty(this.title) ? title : this.title);
    }

    @Override
    public void onObtainWebUrl(String url) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        webView.reload();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_right_btn:
                fenixiang();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private String biaoti,description;
    private void fenixiang() {
        if (NetUtils.isNetworkConnected(WebViewActivity.this)) {
            TARService service = App.getInstance().getService();
            service.fenxiang(Constants.FENXIANG,App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<FenxiangBean>() {
                        @Override
                        public void onCompleted() {
                           share();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Snackbar.make(getWindow().getDecorView(), "发生错误", LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(FenxiangBean result) {
                            if (result.isSuccess()) {
                                shareUrl = result.getUrl();
                                biaoti = result.getBiaoti();
                                description = result.getMiaoshu();
                            }
                        }
                    });
        } else {
            Snackbar.make(getWindow().getDecorView(), "请连接网络", LENGTH_LONG).show();
        }
    }

    Dialog pictureDialog;
    Button btTakePhoto;
    Button btGallety;
    Button btCancel;

    private void share() {
        if (pictureDialog == null) {
            View view = View.inflate(WebViewActivity.this, R.layout.dialog_choose_pic, null);
            btTakePhoto = (Button) view.findViewById(R.id.bt_takephoto);
            btGallety = (Button) view.findViewById(R.id.bt_gallery);
            btCancel = (Button) view.findViewById(R.id.bt_cancel);
            pictureDialog = new Dialog(WebViewActivity.this, R.style.transparentFrameWindowStyle);
            pictureDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            btTakePhoto.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View view) {

                    WxShareAndLoginUtils.WxUrlShare(WebViewActivity.this
                            ,shareUrl,biaoti,description,"",WxShareAndLoginUtils.WECHAT_FRIEND);
                    pictureDialog.dismiss();
                    pictureDialog = null;
                }
            });
            btGallety.setOnClickListener(new View.OnClickListener()  {

                @Override
                public void onClick(View view) {
                    WxShareAndLoginUtils.WxUrlShare(WebViewActivity.this
                            ,shareUrl,biaoti,description,"",WxShareAndLoginUtils.WECHAT_MOMENT);
                    pictureDialog.dismiss();
                    pictureDialog = null;
                }
            });

            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pictureDialog.dismiss();
                    pictureDialog = null;
                }
            });

        }


        btTakePhoto.setText("分享到微信好友");
        btGallety.setText("分享到微信朋友圈");
        Window window = pictureDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = WebViewActivity.this.getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        pictureDialog.onWindowAttributesChanged(wl);
        pictureDialog.setCanceledOnTouchOutside(true);
        pictureDialog.show();


    }
}
