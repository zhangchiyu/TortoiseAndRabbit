package com.example.doldrum.tortoiseandrabbit.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.app.CommonAdapter;

import java.util.List;


public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.MyHolder>  {
    private Context mContex;
    private LayoutInflater inflater;
    private List<MeItem> list;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SelectAdapter(Context context, List<MeItem> list){
        this.mContex = context;
        inflater = LayoutInflater.from(mContex);
        this.list = list;
    }


    public void setData(List<MeItem> datas){
        this.list = datas;
        notifyDataSetChanged();
    }

    @Override
    public SelectAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_griad, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectAdapter.MyHolder holder, final int position) {
        final MeItem meItem = list.get(position);
        holder.icon.setImageResource(meItem.getIcon());
        holder.textView.setText(meItem.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 4){
                    listener.onItemClick(view);
                }else {
                    meItem.toActivity(view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size() >0){
            return  list.size();
        }else {
            return 0;
        }
    }


    class MyHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
