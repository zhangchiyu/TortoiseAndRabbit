package com.example.doldrum.tortoiseandrabbit.game;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.bean.TouzhuBena;

import java.util.List;

public class TouzhuAdapter extends RecyclerView.Adapter<TouzhuAdapter.MyHolder> {
    private Context mContex;
    private LayoutInflater inflater;
    private List<TouzhuBena.DataBean> list;

    public TouzhuAdapter(Context context,List<TouzhuBena.DataBean> list){
        this.mContex = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_touzhu, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        TouzhuBena.DataBean dataBean = list.get(position);
        holder.time.setText(dataBean.getTouzhu());
        holder.play.setText(dataBean.getJine() /10000 + "万");
        holder.qishu.setText(String.valueOf(dataBean.getQishu()));

        holder.haoma.setText(dataBean.getShijian() );
        holder.jine.setText(dataBean.getZhuangtai());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<TouzhuBena.DataBean> data) {
        this.list.addAll(data);
    }

    public void setData(List<TouzhuBena.DataBean> data) {
        this.list = data;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        private TextView time,play,qishu,haoma,neirong,jine;
        public MyHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.touzhushijian);
            play = itemView.findViewById(R.id.touzhuplay);
            qishu = itemView.findViewById(R.id.touzhuqishu);
            haoma = itemView.findViewById(R.id.touzhuhaoma);
            jine = itemView.findViewById(R.id.touzhujine);
        }
    }
}
