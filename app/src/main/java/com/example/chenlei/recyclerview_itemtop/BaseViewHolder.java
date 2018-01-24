package com.example.chenlei.recyclerview_itemtop;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ChenLei on 2017/6/21.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void BindData(Object data);

}
