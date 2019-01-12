package com.example.doldrum.tortoiseandrabbit.me;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.Result;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.BaoXianXiangBean;
import com.example.doldrum.tortoiseandrabbit.bean.CongZhi;
import com.example.doldrum.tortoiseandrabbit.bean.Gong;
import com.example.doldrum.tortoiseandrabbit.bean.SaveImageBena;
import com.example.doldrum.tortoiseandrabbit.bean.UserInfo;
import com.example.doldrum.tortoiseandrabbit.databinding.FragmentMeBinding;
import com.example.doldrum.tortoiseandrabbit.module.login.LoginActivity;
import com.example.doldrum.tortoiseandrabbit.utils.ImagesUtil;
import com.example.doldrum.tortoiseandrabbit.utils.Md5Utils;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;
import com.example.doldrum.tortoiseandrabbit.utils.ToastImage;
import com.example.doldrum.tortoiseandrabbit.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment implements View.OnClickListener ,OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SweetAlertDialog pDialog;
    private UserInfo.DataBean data;

    CircleImageView profile_image;
    Dialog pictureDialog;
    Button btTakePhoto;
    Button btGallety;
    Button btCancel;
    String imagePath = "";
    String RESULTTYPE;


    public MeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private FragmentMeBinding binding;
    private List<MeItem> datas = new ArrayList<>();
    private int [] icons =  {
        R.mipmap.transfer_accounts,
        R.mipmap.account_settings,
        R.mipmap.userreport,
        R.mipmap.teamreport,
        R.mipmap.safebox,
        R.mipmap.gonggao,
        R.mipmap.about
    };
    private SelectAdapter adapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_me,null,false);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),3));
        binding.recyclerview.setAdapter(adapter);
        binding.profileImage.setOnClickListener(this);
        binding.tvName.setOnClickListener(this);
        binding.congzhi.setOnClickListener(this);
        String imagePath = SharedPreferencesUtil.getSpVal("imagePath");
        if (imagePath !=null&&!imagePath.isEmpty()) {
            Picasso.with(getActivity()).load(imagePath).into(binding.profileImage);
        }
        return binding.getRoot();
    }



    private void initdata() {
        Resources resources = getResources();
        String [] content = resources.getStringArray(R.array.content);
        String [] classname = resources.getStringArray(R.array.classname);
        for (int i = 0; i < content.length; i++) {
            datas.add(new MeItem(icons[i],content[i],classname[i],getActivity()));
        }
        //adapter = new CommonAdapter(getActivity(),datas,R.layout.item_griad);
        adapter = new SelectAdapter(getActivity(),datas);
        adapter.setListener(this);
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_image:
                List<PermissionItem> permissionItems = new ArrayList<>();
                permissionItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "内存读写", R.drawable.permission_ic_storage));
                permissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE));
                permissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "打开相机", R.drawable.permission_ic_camera));
                HiPermission.create(getActivity())
                        .permissions(permissionItems)
                        .animStyle(R.style.PermissionAnimFade)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {

                            }

                            @Override
                            public void onFinish() {
                                chickPhoto();
                            }

                            @Override
                            public void onDeny(String permission, int position) {

                            }

                            @Override
                            public void onGuarantee(String permission, int position) {
                            }
                        });

                break;
            case R.id.tv_name:
                startActivity(new Intent(getActivity(),ReNameActivity.class));
                break;
            case R.id.congzhi:
                if (NetUtils.isNetworkConnected(getActivity())) {
                    TARService service = App.getInstance().getService();
                    service.chongzhi_yemian("chongzhi_yemian")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<CongZhi>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    ToastImage.centerShow(getActivity(),"发生错误",false);
                                    e.printStackTrace();
                                }


                                @Override
                                public void onNext(CongZhi result) {
                                    if (result.isSuccess()){
                                        String biaoti = result.getBiaoti();
                                        String url = result.getUrl();
                                        String ifkai = result.getIfkai();

                                        Intent inten = new Intent(getActivity() , WebViewActivity.class);
                                        inten.putExtra("webUrl",url);
                                        inten.putExtra("title",biaoti);
                                        inten.putExtra("ifkai",ifkai);
                                        startActivity(inten);
                                    }
                                }
                            });
                } else {
                    ToastUtils.centerShow(getActivity(),"请连接网络");
                }

                break;
        }
    }




    private void chickPhoto() {
        if (pictureDialog == null) {
            View view = View.inflate(getContext(), R.layout.dialog_choose_pic, null);
            btTakePhoto = (Button) view.findViewById(R.id.bt_takephoto);
            btGallety = (Button) view.findViewById(R.id.bt_gallery);
            btCancel = (Button) view.findViewById(R.id.bt_cancel);
            pictureDialog = new Dialog(getContext(), R.style.transparentFrameWindowStyle);
            pictureDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            btTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String SDState = Environment.getExternalStorageState();
                    if (SDState.equals(Environment.MEDIA_MOUNTED)) {
                        //构建隐式Intent
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //调用系统相机
                        startActivityForResult(intent, ImagesUtil.PHOTO_REQUEST_CAMERA);

                    } else {
                        Snackbar.make(getActivity().getWindow().getDecorView(),"内存卡不存在",LENGTH_LONG).show();
                    }
                    pictureDialog.dismiss();
                    pictureDialog = null;
                }
            });
            btGallety.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent getImage = new Intent();
                            getImage.setType("image/*");
                            getImage.setAction(Intent.ACTION_PICK);//ACTION_GET_CONTENT
                            startActivityForResult(getImage, ImagesUtil.PHOTO_REQUEST_GALLERY);
                            pictureDialog.dismiss();
                            pictureDialog = null;
                        }
            });

            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pictureDialog.dismiss();
                    pictureDialog = null;
                }
            });
        }


        btTakePhoto.setText("打开照相机");
        btGallety.setText("从相册上传");
        Window window = pictureDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        pictureDialog.onWindowAttributesChanged(wl);
        pictureDialog.setCanceledOnTouchOutside(true);
        pictureDialog.show();


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        initdata();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetUtils.isNetworkConnected(getActivity())) {
            TARService service = App.getInstance().getService();
            //pDialog.show();
            service.getUserInfor(Constants.GETUSERINFOR,App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserInfo>() {
                        @Override
                        public void onCompleted() {
                            //pDialog.dismissWithAnimation();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Snackbar.make(getActivity().getWindow().getDecorView(),"出现错误",LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(UserInfo result) {
                            if(result.isSuccess()){
                                data = result.getData();
                                binding.tvName.setText(data.getUserName());
                                binding.tvId.setText("龟兔ID:"+data.getUserbh());
                                binding.tvJinbi.setText(data.getAmount());
                                if (result.getData().getAvatar().contains("user-avatar.png")){
                                    binding.profileImage.setImageResource(R.mipmap.ic_launcher_tortoise);
                                }else {
                                    Picasso.with(getActivity()).load(result.getData().getAvatar())
                                            .error(R.mipmap.ic_launcher_tortoise)
                                            .into(binding.profileImage);
                                }

                            }else {
                                if (result.getCode() == -10){
                                    startActivity(new Intent(getActivity(),LoginActivity.class));
                                }
                                Snackbar.make(getActivity().getWindow().getDecorView(),result.getMsg(),LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Snackbar.make(getActivity().getWindow().getDecorView(),"请连接网络",LENGTH_LONG).show();
        }
        gonggaolan_id = SharedPreferencesUtil.getIntSpVal("gonggaolan_id");

       /* Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Gong gong = new Gong();
                gong.gonggaolan_id =1 ;
                newgonggaolan_id =1;
                EventBus.getDefault().post(gong);
            }
        };
        timer.scheduleAtFixedRate(task,5000L,1000L);*/
        Log.d("gonggaolan_id", "gonggaolan_id: "+gonggaolan_id);
        Log.d("gonggaolan_id", "newgonggaolan_id: "+newgonggaolan_id);
        if (gonggaolan_id == newgonggaolan_id){
            datas.get(5).setIcon(R.mipmap.gonggao);
            datas.get(5).gonggaolan_id = newgonggaolan_id;
            adapter.setData(datas);
        }
    }


    int gonggaolan_id = 0;
    int newgonggaolan_id =0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Gong event) {
        newgonggaolan_id = event.gonggaolan_id;
        if (newgonggaolan_id >gonggaolan_id){
            datas.get(5).setIcon(R.mipmap.gong_red);
            datas.get(5).gonggaolan_id = newgonggaolan_id;
            adapter.setData(datas);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagesUtil.PHOTO_REQUEST_CAMERA) {
            if (data == null) {
                return;
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    //获得拍的照片
                    Bitmap bm = extras.getParcelable("data");
                    //将Bitmap转化为uri
                    Uri uri = saveBitmap(bm, "temp");
                    imagePath = ImagesUtil.getRealPathFromURI(uri);
                    if (imagePath == null && uri != null) {
                        imagePath = uri.getPath();
                    }

                }
            }
        } else if (requestCode == ImagesUtil.PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                Uri contentUri = data.getData();
                if (contentUri == null) return;
                imagePath = ImagesUtil.getRealPathFromURI(contentUri);//拿到相册图片路径
                if (imagePath == null && contentUri != null) {
                    imagePath = contentUri.getPath();
                }
            }

        }

        if (imagePath != null && new File(imagePath).exists()) {
            uploadImg();
        }
    }

    /**
     * 上传头像
     */
    private void uploadImg() {
            if (NetUtils.isNetworkConnected(getActivity())) {
                File file = new File(imagePath);
                RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), App.userData.getSessionToken());
                RequestBody actionBody = RequestBody.create(MediaType.parse("text/plain"), Constants.SAVEIMG);

                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("Filedata", file.getName(), imageBody);


                TARService service = App.getInstance().getService();
                service.saveImg(actionBody,tokenBody,imageBodyPart)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<SaveImageBena>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }



                            @Override
                            public void onNext(SaveImageBena result) {
                                if (result.isSuccess()){
                                    SharedPreferencesUtil.changeSp("imagePath",result.data.domian +result.data.url);
                                    Picasso.with(getActivity()).load(result.data.domian +result.data.url)
                                            .error(R.mipmap.ic_launcher_tortoise)
                                            .into(binding.profileImage);
                                    updateInfo(/*result.data.domian +*/result.data.url);
                                }
                            }
                        });
            }else{
                //Snackbar.make(getWindow().getDecorView(),"请连接网络",LENGTH_LONG).show();
            }

    }


    private void updateInfo(final String avatar){
        if (NetUtils.isNetworkConnected(getActivity())) {
        TARService service = App.getInstance().getService();
        service.updateAvatar("updateUserInfor", App.userData.getSessionToken(), avatar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (pDialog !=null && pDialog.isShowing()){
                            pDialog.dismissWithAnimation();
                            pDialog = null;
                        }
                       // Snackbar.make(getWindow().getDecorView(), "修改昵称失败", 2).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result result) {
                        if (result.isSuccess()){
                            //TODO 修改个人信息
                            App.userData.setAvatar(avatar);
                            App.getInstance().getDaoSession().getUserDataDao().update(App.userData);
                        }
                    }
                });
    }
    }



    /**
     * 将Bitmap写入SD卡中的一个文件中,并返回写入文件的Uri
     *
     * @param bm
     * @param dirPath
     * @return
     */
    private Uri saveBitmap(Bitmap bm, String dirPath) {
        //新建文件夹用于存放裁剪后的图片
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/" + dirPath);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }

        //新建文件存储裁剪后的图片
        File img = new File(tmpDir.getAbsolutePath() + "/avator.png");
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(Bitmap.CompressFormat.PNG, 50, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onItemClick(View view) {
        showSafe();
    }
    Dialog touzhuDialog;
    private void showSafe() {
        if (touzhuDialog == null) {
            View view = View.inflate(getActivity(), R.layout.shwo_safe, null);
            view.findViewById(R.id.iv_dclose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (touzhuDialog.isShowing()){
                        touzhuDialog.dismiss();
                    }
                }
            });
            final EditText mEditText = view.findViewById(R.id.editText);
            view.findViewById(R.id.button17).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = mEditText.getText().toString();
                    if (TextUtils.isEmpty(s)){
                        Toasty.info(getActivity(),"请输入保险箱密码",Toast.LENGTH_SHORT,true).show();
                    }
                    if (s.equals(SharedPreferencesUtil.getSpVal("baoxian"))){
                        getwode_yue(Md5Utils.getMd5String(s));
                        if (touzhuDialog.isShowing()){
                            touzhuDialog.dismiss();
                        }
                    }else{
                        Toasty.info(getActivity(),"保险箱密码错误",Toast.LENGTH_SHORT,true).show();
                    }
                }
            });
            view.findViewById(R.id.button16).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(),UpdatePasswordActivity.class));
                }
            });
            view.findViewById(R.id.button18).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (touzhuDialog.isShowing()){
                        touzhuDialog.dismiss();
                    }
                }
            });
            touzhuDialog = new Dialog(getActivity(), R.style.transparentFrameWindowStyle);
            touzhuDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        Window window = touzhuDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 100 ;
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        touzhuDialog.onWindowAttributesChanged(wl);
        touzhuDialog.setCanceledOnTouchOutside(true);
        touzhuDialog.show();
    }

    private void getwode_yue(String pwd) {
        if (NetUtils.isNetworkConnected(getActivity())) {
            TARService service = App.getInstance().getService();
            service.wode_baoxian_yue("wode_baoxian_yue",App.userData.getSessionToken(),pwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaoXianXiangBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toasty.error(getActivity(),"发生错误",0).show();
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(BaoXianXiangBean result) {
                            if(result.isSuccess()){
                                BaoXianXiangBean.DataBean data = result.getData();
                                showTouzhuZhuangtai(data.getAmount(),data.getAmount_mibao());
                            }else {
                                Toasty.info(getActivity(),result.getMsg(),0).show();
                            }
                        }
                    });
        }else{
           Toasty.info(getActivity(),"请连接网络",0).show();
        }
    }


    Dialog touzhuDialog1;

    private void showTouzhuZhuangtai(String amount ,String mibao) {
        if (touzhuDialog1 == null) {
            View view = View.inflate(getActivity(), R.layout.baoxianxiang, null);
            TextView textVIew = view.findViewById(R.id.textView21);
            textVIew.setText(amount);
            TextView baoxian = view.findViewById(R.id.textView24);
            baoxian.setText(mibao);
            Button submit = view.findViewById(R.id.button11);
            ImageView imageView2 = view.findViewById(R.id.imageView2);
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (touzhuDialog1.isShowing()){
                        touzhuDialog1.dismiss();
                    }
                }
            });
            final TextInputEditText mTextInputEditText = view.findViewById(R.id.baoxian);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = mTextInputEditText.getText().toString();
                    if (TextUtils.isEmpty(s)){
                        Toasty.info(getActivity(),"请输入存取金额");
                    }else {
                        chu(s);
                    }
                }
            });
            Button quchu = view.findViewById(R.id.button12);
            quchu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = mTextInputEditText.getText().toString();
                    if (TextUtils.isEmpty(s)){
                        Toasty.info(getActivity(),"请输入存取金额");
                    }else {
                        qu(s);
                    }
                }
            });
            touzhuDialog1 = new Dialog(getActivity(), R.style.transparentFrameWindowStyle);
            touzhuDialog1.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        Window window = touzhuDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 100 ;
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        touzhuDialog1.onWindowAttributesChanged(wl);
        touzhuDialog1.setCanceledOnTouchOutside(true);
        touzhuDialog1.show();
    }

    private void chu(String s) {
        if (NetUtils.isNetworkConnected(getActivity())) {
            TARService service = App.getInstance().getService();
            service.cunbaoxian("cunbaoxian",App.userData.getSessionToken(),s)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaoXianXiangBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toasty.error(getActivity(),"发生错误",0).show();
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(BaoXianXiangBean result) {
                            if(result.isSuccess()){
                                touzhuDialog.dismiss();
                                touzhuDialog = null;
                            }else {
                                Toasty.info(getActivity(),result.getMsg(),0).show();
                            }
                        }
                    });
        }else{
            Toasty.info(getActivity(),"请连接网络",0).show();
        }
    }

    private void qu(String s) {
        if (NetUtils.isNetworkConnected(getActivity())) {
            TARService service = App.getInstance().getService();
            service.qubaoxian("qubaoxian",App.userData.getSessionToken(),s)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaoXianXiangBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toasty.error(getActivity(),"发生错误",0).show();
                            e.printStackTrace();
                        }



                        @Override
                        public void onNext(BaoXianXiangBean result) {
                            if(result.isSuccess()){
                                touzhuDialog.dismiss();
                                touzhuDialog = null;
                            }else {
                                Toasty.info(getActivity(),result.getMsg(),0).show();
                            }
                        }
                    });
        }else{
            Toasty.info(getActivity(),"请连接网络",0).show();
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
