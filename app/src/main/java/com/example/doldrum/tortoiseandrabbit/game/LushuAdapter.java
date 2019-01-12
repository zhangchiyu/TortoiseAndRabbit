package com.example.doldrum.tortoiseandrabbit.game;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.bean.LuShuItem;

import java.util.List;

public class LushuAdapter extends RecyclerView.Adapter<LushuAdapter.MyHolder> {
    private Context mContex;
    private LayoutInflater inflater;
    private List<LuShuItem> list;

    public LushuAdapter(List<LuShuItem> list,Context context) {
        this.list = list;
        this.mContex = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lushu_list_item, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        LuShuItem luShuItem = list.get(position);
        String jieguo = luShuItem.getJieguo();
        switch (jieguo){
            case "龙":
                holder.long_rore.setImageResource(R.mipmap.dright);
                holder.he_rore.setImageResource(R.mipmap.derror);
                holder.hu_rore.setImageResource(R.mipmap.derror);
                break;
            case "和":
                holder.long_rore.setImageResource(R.mipmap.derror);
                holder.he_rore.setImageResource(R.mipmap.dright);
                holder.hu_rore.setImageResource(R.mipmap.derror);
                break;
            case "虎":
                holder.long_rore.setImageResource(R.mipmap.derror);
                holder.he_rore.setImageResource(R.mipmap.derror);
                holder.hu_rore.setImageResource(R.mipmap.dright);
                break;
                default:
                    break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<LuShuItem> data) {
        this.list.addAll(data);
    }

    public void setData(List<LuShuItem> data) {
        this.list = data;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView long_rore,he_rore,hu_rore;

        public MyHolder(View itemView) {
            super(itemView);
            long_rore = itemView.findViewById(R.id.long_rore);
            he_rore = itemView.findViewById(R.id.he_rore);
            hu_rore = itemView.findViewById(R.id.hu_rore);
        }
    }
}