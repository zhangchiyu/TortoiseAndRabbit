package com.example.doldrum.tortoiseandrabbit.app;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.doldrum.tortoiseandrabbit.BR;
import java.util.List;


public class  CommonAdapter<T> extends  RecyclerView.Adapter<BaseBindingViewHolder>{
    private List<T> datas;
    private Context context;
    private LayoutInflater inflater;
    private int layoutRes;




    public CommonAdapter(Context context ,List<T> datas,int LayoutRes) {
        this.datas = datas;
        this.context = context;
        this.layoutRes = LayoutRes;
        inflater = LayoutInflater.from(context);
    }



    public CommonAdapter<T> addData(List<T> datas){
        this.datas.addAll(datas);
        return this;
    }

    public CommonAdapter<T> setData(List<T> datas){
        this.datas = datas;
        return this;
    }


    @Override
    public BaseBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseBindingViewHolder(DataBindingUtil.inflate(inflater,
                layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseBindingViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.item,datas.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return datas.size() == 0 ? 0 : datas.size();
    }




}