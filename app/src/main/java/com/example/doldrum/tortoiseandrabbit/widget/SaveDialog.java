package com.example.doldrum.tortoiseandrabbit.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.widget.Toast.LENGTH_LONG;

public class SaveDialog implements View.OnClickListener {
    private Context mContext;
    private Dialog mDialog;
    private View mDialogContentView;
    EditText password;
    Button open ,setpassword,cancle;
    public SaveDialog(Context mContext){
        this.mContext = mContext;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.dialog);
        mDialogContentView= LayoutInflater.from(mContext).inflate(R.layout.open_dialog,null);
         password = mDialogContentView.findViewById(R.id.textView9);

         open = mDialogContentView.findViewById(R.id.button2);
         setpassword = mDialogContentView.findViewById(R.id.button5);
         cancle = mDialogContentView.findViewById(R.id.button6);

        open.setOnClickListener(this);
        setpassword.setOnClickListener(this);
        cancle.setOnClickListener(this);

        Window window = mDialog.getWindow();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);
        window.setWindowAnimations(R.style.AnimationPreview);
//        mDialogContentView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.bl_top_in));
//        mLoadingView= (CircleProgress) mDialogContentView.findViewById(R.id.bl_layout_pregress);
//        bl_layout_text = (TextView)mDialogContentView.findViewById(R.id.bl_layout_text);
//        mLoadingView.startAnim();
        mDialog.setContentView(mDialogContentView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                String s = password.getText().toString();
                break;
            case R.id.button5:
                wode_yue();
                break;
            case R.id.button6:
                if (mDialog!=null && mDialog.isShowing()){
                    mDialog.dismiss();
                }

                break;
        }
    }


    public void show(){
        mDialog.show();

    }
    /**
     * 影藏
     */
    public void dismiss(){
        mDialog.dismiss();
    }

    public Dialog getDialog(){
        return  mDialog;
    }


    public void wode_yue() {
        if (NetUtils.isNetworkConnected(mContext)) {
            TARService service = App.getInstance().getService();

            service.wode_yue(Constants.WODE_YUE, App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Snackbar.make(mDialog.getWindow().getDecorView(), "发生错误", LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(Result result) {
                            if (result.isSuccess()) {

                            } else {
                                Snackbar.make(mDialog.getWindow().getDecorView(), result.getMsg(), LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Snackbar.make(mDialog.getWindow().getDecorView(), "请连接网络", LENGTH_LONG).show();
        }
    }
}
