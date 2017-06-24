package com.example.administrator.eddievideoplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.eddievideoplay.bean.LocalVideoBean;
import com.example.administrator.eddievideoplay.view.MyRecycleItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eddie on 2017/6/23.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private Context mContext;
    private List<LocalVideoBean> mList  = new ArrayList<LocalVideoBean>();
    private OnItemClickListen mOnItemClickListen = null;


    public MyAdapter(Context context,List<LocalVideoBean> mList){
        this.mList = mList;
        mContext = context;

    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new MyRecycleItemView(mContext));
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
        holder.mMyRecycleView.bindView(mList.get(position));
        holder.mMyRecycleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListen !=null){
                    mOnItemClickListen.OnItemClick(holder.mMyRecycleView,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public interface OnItemClickListen {
        void OnItemClick(View v,int position);
    }

    public void setOnItemClickListen(OnItemClickListen listen){
        mOnItemClickListen = listen;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private MyRecycleItemView mMyRecycleView;

        public ViewHolder(MyRecycleItemView itemView) {
            super(itemView);
            mMyRecycleView = itemView;
        }
    }

}
