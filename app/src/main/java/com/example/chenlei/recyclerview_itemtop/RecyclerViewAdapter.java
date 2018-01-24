package com.example.chenlei.recyclerview_itemtop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ItemTop.ChildTopHelp;

/**
 * Created by ChenLei on 2017/6/21.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener{

    private Context context;
    private ChildTopHelp help;

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null){
            int position = (int)v.getTag();
            onItemClickListener.itemClick(help.getChildType(position),help.getDataAtIndex(position));
        }
    }

    public interface OnItemClickListener{
        public void itemClick(int itemType,Object data);
    }
    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerViewAdapter(Context context, ChildTopHelp help) {
        this.context = context;
        this.help = help;
    }

    @Override
    public int getItemViewType(int position) {
        return help.getChildType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ChildTopHelp.CHILD_TYPE_TOP){
            View itemTopView = LayoutInflater.from(context).inflate(R.layout.recyclerview_top_item,parent,false);
            return new ItemTopViewHolder(itemTopView);
        }
        View item = LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false);
        return new ItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
        if (help.getChildType(position) == ChildTopHelp.CHILD_TYPE_ITEM){
            holder.BindData(help.getDataAtIndex(position));
        }
        else if(help.getChildType(position) == ChildTopHelp.CHILD_TYPE_TOP){
            holder.BindData(help.getDataAtIndex(position));
        }
    }

    @Override
    public int getItemCount() {
        return help.getCount();
    }
}
