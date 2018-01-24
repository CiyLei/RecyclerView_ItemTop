package com.example.chenlei.recyclerview_itemtop;

import ItemTop.BaseChild;

/**
 * Created by ChenLei on 2017/6/21.
 */

public class TestModel extends BaseChild {

    private String title;
    private boolean isHasChildTop;

    public TestModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsHasChildTop(boolean isHasChildTop){
        this.isHasChildTop = isHasChildTop;
    }

    @Override
    public boolean isHasChildTop() {
        return isHasChildTop;
    }
}
