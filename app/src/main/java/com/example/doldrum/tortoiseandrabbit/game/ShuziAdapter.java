package com.example.doldrum.tortoiseandrabbit.game;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.bean.GItem;

import java.util.List;


public class ShuziAdapter extends RecyclerView.Adapter<ShuziAdapter.MyHolder> {
    private Context mContex;
    private LayoutInflater inflater;
    private List<GItem> list;
    private SuiziListener mSuiziListener;
    public void setSuiziListener(SuiziListener mSuiziListener){
        this.mSuiziListener = mSuiziListener;
    }

    public ShuziAdapter(Context context,List<GItem> list){
        this.mContex = context;
        inflater = LayoutInflater.from(mContex);
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.g_list_item, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final GItem gItem = list.get(position);
        holder.tv_num.setText(String.valueOf(gItem.getNum()));
        holder.tv_peilv.setText(gItem.getPeilv());
        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (null != mSuiziListener){
                    mSuiziListener.buildStake(gItem.getNum(),holder.frameLayout);
                }
            }
        });
     /*   if(gItem.isIfFlag()){
            holder.view.setSelected(true);
        }else{
            holder.view.setSelected(false);
        }*/
    }

    public void setList(List<GItem> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface SuiziListener {
        void buildStake(int num,FrameLayout frameLayout);
    }

    class MyHolder extends RecyclerView.ViewHolder{
        public TextView tv_num;
        public TextView tv_peilv;
        public FrameLayout frameLayout;
        public View view;

        public MyHolder(View itemView) {
            super(itemView);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_peilv = itemView.findViewById(R.id.tv_peilv);
            frameLayout = itemView.findViewById(R.id.framelayout);
            view = itemView;
        }
        public void autoClick(){
            frameLayout.performClick();
        }

    }

}
