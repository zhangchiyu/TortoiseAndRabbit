package com.example.doldrum.tortoiseandrabbit.home;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.doldrum.tortoiseandrabbit.R;
import com.example.doldrum.tortoiseandrabbit.bean.Fuhao;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FuhaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Fuhao> datas;
    private Context context;
    private LayoutInflater inflater;


    public FuhaoAdapter(Context context ,List<Fuhao> datas) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public FuhaoAdapter addData(List<Fuhao> datas){
        if (this.datas==null || this.datas.size()==0){
            this.datas.addAll(datas);
        }
        return this;
    }

    public FuhaoAdapter setData(List<Fuhao> datas){
        this.datas = datas;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder =null;
        if (viewType ==1 ){
            viewHolder =  new IVHolder(inflater.inflate(R.layout.fuhao_list_item,null,false));
        }else if (viewType ==2){
            viewHolder =  new TVHolder(inflater.inflate(R.layout.fuhao_list_item_tv,null,false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Fuhao fuhao = datas.get(position);
        String portrait = fuhao.getPortrait();
        if (holder.getItemViewType() == 1){
            ((IVHolder)holder).tv_amount.setText(fuhao.getAmount());
            ((IVHolder)holder).tv_id.setText("ID:"+fuhao.getUserId());
            ((IVHolder)holder).tv_name.setText(fuhao.getName());
            if (portrait.contains("user-avatar.png")){
               /* Picasso.with(context)
                        .load(fuhao.getPortrait()+".....")
                        .error(R.mipmap.ic_launcher_tortoise)
                        .into(((IVHolder)holder).iv_icon);*/
                ((IVHolder)holder).iv_icon.setImageResource(R.mipmap.ic_launcher_tortoise);
            }else{
                Picasso.with(context)
                        .load(fuhao.getPortrait())
                        .error(R.mipmap.ic_launcher_tortoise)
                        .into(((IVHolder)holder).iv_icon);
            }
            if (fuhao.getRanking() == 1){
                ((IVHolder)holder).iv_rank.setImageResource(R.mipmap.one);
            }else if (fuhao.getRanking() ==2){
                ((IVHolder)holder).iv_rank.setImageResource(R.mipmap.two);
            }else if (fuhao.getRanking() ==3){
                ((IVHolder)holder).iv_rank.setImageResource(R.mipmap.three);
            }
        }else {
            ((TVHolder)holder).tv_rank.setText(String.valueOf(fuhao.getRanking()));
            if (portrait.contains("user-avatar.png")){
                /*Picasso.with(context)
                        .load(fuhao.getPortrait()+".....")
                        .error(R.mipmap.ic_launcher_tortoise)
                        .into(((TVHolder)holder).iv_icon);*/
                ((TVHolder)holder).iv_icon.setImageResource(R.mipmap.ic_launcher_tortoise);
            }else {
                Picasso.with(context)
                        .load(fuhao.getPortrait())
                        .error(R.mipmap.ic_launcher_tortoise)
                        .into(((TVHolder)holder).iv_icon);

            }
            ((TVHolder)holder).tv_amount.setText(fuhao.getAmount());
            ((TVHolder)holder).tv_id.setText("ID:"+fuhao.getUserId());
            ((TVHolder)holder).tv_name.setText(fuhao.getName());
    }
    }

    @Override
    public int getItemViewType(int position) {
        Fuhao fuhao = datas.get(position);
        if (fuhao.getRanking() == 1 || fuhao.getRanking() ==2 || fuhao.getRanking() ==3 ){
            return  1;
        }else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class IVHolder extends RecyclerView.ViewHolder{
        ImageView iv_icon,iv_rank;
        TextView tv_id,tv_name,tv_amount;
        public IVHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            iv_rank = itemView.findViewById(R.id.iv_rank);
        }
    }

    class TVHolder extends RecyclerView.ViewHolder{
        ImageView iv_icon;
        TextView tv_id,tv_name,tv_amount,tv_rank;
        public TVHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_rank = itemView.findViewById(R.id.ranktv);
        }
    }
}
