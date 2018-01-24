package com.example.chenlei.recyclerview_itemtop;

import android.view.View;
import android.widget.TextView;

/**
 * Created by ChenLei on 2017/6/21.
 */

public class ItemViewHolder extends BaseViewHolder {

    private TextView textView;
    public ItemViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.item_textview1);
    }

    @Override
    public void BindData(Object data) {
        TestModel m = (TestModel)data;
        textView.setText(m.getTitle());
    }
}
