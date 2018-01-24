package com.example.chenlei.recyclerview_itemtop;

import android.view.View;
import android.widget.TextView;

/**
 * Created by ChenLei on 2017/6/21.
 */

public class ItemTopViewHolder extends BaseViewHolder {

    private TextView textView;

    public ItemTopViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.recycler_item_top_textview);
    }

    @Override
    public void BindData(Object data) {
        textView.setText((String)data);
    }
}
