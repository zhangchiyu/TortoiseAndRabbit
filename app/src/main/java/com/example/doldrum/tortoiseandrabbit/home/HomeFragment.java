package com.example.doldrum.tortoiseandrabbit.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.App;
import com.example.doldrum.tortoiseandrabbit.app.CommonAdapter;
import com.example.doldrum.tortoiseandrabbit.app.Constants;
import com.example.doldrum.tortoiseandrabbit.app.TARService;
import com.example.doldrum.tortoiseandrabbit.bean.FenxiangBean;
import com.example.doldrum.tortoiseandrabbit.bean.Fuhao;
import com.example.doldrum.tortoiseandrabbit.bean.FuhaobangBean;
import com.example.doldrum.tortoiseandrabbit.bean.GongGaoBean;
import com.example.doldrum.tortoiseandrabbit.bean.GonggaolanBean;
import com.example.doldrum.tortoiseandrabbit.bean.Owner;
import com.example.doldrum.tortoiseandrabbit.game.SixteenBarActivity;
import com.example.doldrum.tortoiseandrabbit.game.TortoiseRabbitActivity;
import com.example.doldrum.tortoiseandrabbit.me.WebViewActivity;
import com.example.doldrum.tortoiseandrabbit.me.personandteam.AnnouncementActivity;
import com.example.doldrum.tortoiseandrabbit.me.personandteam.PersonListActivity;
import com.example.doldrum.tortoiseandrabbit.utils.NetUtils;
import com.example.doldrum.tortoiseandrabbit.utils.RecycleViewDivider;
import com.example.doldrum.tortoiseandrabbit.utils.SharedPreferencesUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.design.widget.BaseTransientBottomBar.LENGTH_SHORT;
import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    ImageView ivMuisc,iv_icon;
    TextView rank,tv_id,tv_name,tv_amount;
    RecyclerView recyclerview;

    private FuhaoAdapter adapter;
    private List<Fuhao> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ivMuisc = view.findViewById(R.id.ivMuisc);
        iv_icon = view.findViewById(R.id.iv_icon);
        rank = view.findViewById(R.id.rank);
        tv_id = view.findViewById(R.id.tv_id);
        tv_name = view.findViewById(R.id.tv_name);
        tv_amount = view.findViewById(R.id.tv_amount);
        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FuhaoAdapter(getActivity(), data);
        recyclerview.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, 10,
                getResources().getColor(R.color.divide_gray_color)));
        recyclerview.setAdapter(adapter);

   /*     if (SharedPreferencesUtil.getSpVal("isFlagChip").equals("false")){
            ivMuisc.setImageResource(R.mipmap.colosemuise);
        }*/



        ivMuisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (App.audioService.isPlaying()){
                    ivMuisc.setImageResource(R.mipmap.colosemuise);
                    App.audioService.stop();
                    SharedPreferencesUtil.changeSp("isFlagChip","false");
                }else{
                    ivMuisc.setImageResource(R.mipmap.muisc);
                    App.audioService.restart();
                    SharedPreferencesUtil.changeSp("isFlagChip","true");
                }
                /*if (SharedPreferencesUtil.getSpVal("home").equals("false")){
                    ivMuisc.setImageResource(R.mipmap.muisc);
                    SharedPreferencesUtil.changeSp("home","true");
                }else {
                    ivMuisc.setImageResource(R.mipmap.colosemuise);
                    SharedPreferencesUtil.changeSp("home","false");
                }*/
            }
        });
        marquee = view.findViewById(R.id.marquee);
        ImageView mImageView = view.findViewById(R.id.id_hall);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),IDActivity.class));
            }
        });
        view.findViewById(R.id.gt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TortoiseRabbitActivity.class));
            }
        });
        view.findViewById(R.id.eb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SixteenBarActivity.class));
            }
        });
        view.findViewById(R.id.woyao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fenixiang();
            }
        });
        list_fuhaobang();
        return view;
    }


    private void fenixiang() {
        if (NetUtils.isNetworkConnected(getActivity())) {
            TARService service = App.getInstance().getService();
            service.fenxiang(Constants.FENXIANG,App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<FenxiangBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toasty.error(getActivity(),e.getMessage(),0,true).show();
                        }


                        @Override
                        public void onNext(FenxiangBean result) {
                            if (result.isSuccess()) {
                                WebViewActivity.instanceAct(getActivity(),result.getUrl(),result.getBiaoti());
                            }
                        }
                    });
        } else {
            Toasty.error(getActivity(),"请连接网络",0,true).show();
        }
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
    }


    @Override
    public void onResume() {
        super.onResume();
        if (App.audioService!=null){
            if (SharedPreferencesUtil.getSpVal("isFlagChip").equals("false")){
                ivMuisc.setImageResource(R.mipmap.colosemuise);
            }else{
                ivMuisc.setImageResource(R.mipmap.muisc);
                if (!App.audioService.isPlaying()){
                    App.audioService.restart();
                }
            }
        }
        getList_gongGao();

    }

    private void list_fuhaobang() {
        if (NetUtils.isNetworkConnected(getActivity())) {
            TARService service = App.getInstance().getService();
            service.list_fuhaobang("list_fuhaobang",App.userData.getSessionToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<FuhaobangBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toasty.error(getActivity(),"发生错误",LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(FuhaobangBean result) {
                            if (result.isSuccess()){
                                Owner owner = result.data.owner;
                                if (owner.getPortrait().contains("user-avatar.png")){
                                    iv_icon.setImageResource(R.mipmap.ic_launcher_tortoise);
                                }else{
                                    Picasso.with(getActivity())
                                            .load(owner.getPortrait())
                                            .error(R.mipmap.ic_launcher_tortoise)
                                            .into(iv_icon);
                                }

                                tv_id.setText("ID:"+owner.getUserId());
                                tv_amount.setText(owner.getAmount());
                                rank.setText("NO."+owner.getRanking());
                                tv_name.setText(owner.getName());
                                adapter.addData(result.data.list);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        } else {
            Toasty.info(getActivity(),"请连接网络",LENGTH_SHORT).show();
        }
    }

    TextView marquee;

    private void getList_gongGao() {
        if (NetUtils.isNetworkConnected(getActivity())) {
            TARService service = App.getInstance().getService();
            service.list_gonggao(Constants.LIST_GONGGAO)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GongGaoBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toasty.error(getActivity(),"发生错误",LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                        @Override
                        public void onNext(GongGaoBean result) {
                            if (result.isSuccess()){
                                String title = result.getTitle();
                                marquee.setText(title+title+title+title);
                                marquee.setSelected(true); //设置跑马灯滚动起来
                            }
                        }
                    });
        } else {
            Toasty.info(getActivity(),"请连接网络",LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
