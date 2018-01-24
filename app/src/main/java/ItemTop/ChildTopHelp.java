package ItemTop;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.chenlei.recyclerview_itemtop.R;

import java.util.List;

/**
 * Created by ChenLei on 2017/6/21.
 */

public class ChildTopHelp {

    public static int CHILD_TYPE_TOP = 1;
    public static int CHILD_TYPE_ITEM = 2;

    private List<? extends BaseChild> data;
    private RecyclerView recyclerView;

    private LinearLayout recyclerTopView;
    private View recyclerTopView_top;
    private View recyclerTopView_center;
    private View recyclerTopView_bottom;

    private int currentChildTopIndex = -1;
    private int currentChildTopHeight;

    private View currentChildTopView;
    private View beforeChildTopView;
    private View nextChildTopView;

    public interface ChildTopViewListener{
        public void TopViewBindData(View topView,Object data);
    }

    public ChildTopViewListener listener;

    public ChildTopHelp(final Context context, List<? extends BaseChild> data, final RecyclerView recyclerView, final LinearLayout recyclerTopView, ChildTopViewListener listener){
        this.data = data;
        this.recyclerView = recyclerView;
        this.recyclerTopView = recyclerTopView;
        this.listener = listener;

        recyclerTopView_top = LayoutInflater.from(context).inflate(R.layout.recyclerview_top_item,null,false);
        recyclerTopView_top.setVisibility(View.GONE);
        recyclerTopView_center = LayoutInflater.from(context).inflate(R.layout.recyclerview_top_item,null,false);
        recyclerTopView_center.setVisibility(View.GONE);
        recyclerTopView_bottom = LayoutInflater.from(context).inflate(R.layout.recyclerview_top_item,null,false);
        recyclerTopView_bottom.setVisibility(View.GONE);

        this.recyclerTopView.addView(recyclerTopView_top);
        this.recyclerTopView.addView(recyclerTopView_center);
        this.recyclerTopView.addView(recyclerTopView_bottom);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    OnScroll();
                }
            });
        }else{
            recyclerView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    OnScroll();
                }
            });
        }


    }

    private void OnScroll(){
        //判断是当前layoutManager是否为LinearLayoutManager
        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //获取最后一个可见view的位置
//                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
            //获取第一个可见view的位置
            int firstItemPosition = linearManager.findFirstVisibleItemPosition();
            if (getChildType(firstItemPosition) == CHILD_TYPE_TOP){

                currentChildTopIndex = firstItemPosition;
                beforeChildTopView = currentChildTopView;
                currentChildTopView = linearManager.findViewByPosition(firstItemPosition);
                currentChildTopHeight = currentChildTopView.getHeight();

                recyclerTopView.setTranslationY(0);
                showTopView(false,true,false,0,firstItemPosition,0);

//                        int beforeChildTopIndex = getBeforeChildTopIndex(firstItemPosition);
//                        if (beforeChildTopIndex == firstItemPosition){
//                            recyclerTopView.setTranslationY(0);
//                            showTopView(false,true,false,0,firstItemPosition,0);
//                        }else{
//                            View beforeChildTopView = linearManager.findViewByPosition(beforeChildTopIndex);
//                            recyclerTopView.setTranslationY(-beforeChildTopView.getHeight());
//                            showTopView(true,true,false,beforeChildTopIndex,firstItemPosition,0);
//                        }


//                        Log.e("b", currentChildTopHeight + " " + currentChildTopIndex);
            }else if (getChildType(firstItemPosition) == CHILD_TYPE_ITEM){
                int nextChildTopIndex = getNextChildTopIndex(firstItemPosition);
//                        Log.e("a",firstItemPosition + " " + currentChildTopIndex + " " + currentChildTopView + " " + beforeChildTopView);
                int beforeChildTopIndex = getBeforeChildTopIndex(firstItemPosition);
//                        View currentChildTopView = recyclerView.getChildAt(currentChildTopIndex);
//                        View beforeChildTopView = linearManager.findViewByPosition(beforeChildTopIndex);
                nextChildTopView = linearManager.findViewByPosition(nextChildTopIndex);

                if (beforeChildTopIndex == firstItemPosition){
                    showTopView(false,false,false,0,0,0);
                    return;
                }
                if (firstItemPosition > currentChildTopIndex){
                    if (currentChildTopView != null && nextChildTopView != null){
//                                Log.e("a",nextChildTopView.getTop() + " " + currentChildTopHeight + " " + currentChildTopIndex + " " + nextChildTopIndex);
                        if (nextChildTopView.getTop() >= 0 && nextChildTopView.getTop() <= currentChildTopHeight && nextChildTopIndex != firstItemPosition){
                            showTopView(false,true,true,0,currentChildTopIndex,nextChildTopIndex);
//                                    Log.e("c",(currentChildTopHeight - nextChildTopView.getTop()) + "");
                            recyclerTopView.setTranslationY(nextChildTopView.getTop() - currentChildTopHeight);
                            return;
                        }else{
                            recyclerTopView.setTranslationY(0);
                            showTopView(false,true,false,0,beforeChildTopIndex,0);
                        }
                    }
                }else if (firstItemPosition < currentChildTopIndex){
                    if (currentChildTopView != null && beforeChildTopView != null){
                        if (currentChildTopView.getTop() >= 0 && currentChildTopView.getTop() <= beforeChildTopView.getHeight() && beforeChildTopIndex != firstItemPosition){
                            showTopView(true,true,false,beforeChildTopIndex,currentChildTopIndex,0);
//                                    Log.e("a",currentChildTopView.getTop() + " " + beforeChildTopView.getHeight() + " " + (currentChildTopView.getTop()-beforeChildTopView.getHeight()));
                            recyclerTopView.setTranslationY(currentChildTopView.getTop() - beforeChildTopView.getHeight());
                        }else{
                            recyclerTopView.setTranslationY(0);
                            showTopView(false,true,false,0,beforeChildTopIndex,0);
                        }
                    }
                }

            }
        }
    }

    private void showTopView(boolean top,boolean center,boolean bottom,int top_index,int center_index,int bottom_index){
        recyclerTopView.removeAllViews();
        if (top){
            recyclerTopView_top.setVisibility(View.VISIBLE);
            if (listener != null)
                listener.TopViewBindData(recyclerTopView_top,getDataAtIndex(top_index));
        }
        else
            recyclerTopView_top.setVisibility(View.GONE);
        if (center){
            recyclerTopView_center.setVisibility(View.VISIBLE);
            if (listener != null)
                listener.TopViewBindData(recyclerTopView_center,getDataAtIndex(center_index));
        }
        else
            recyclerTopView_center.setVisibility(View.GONE);
        if (bottom){
            recyclerTopView_bottom.setVisibility(View.VISIBLE);
            if (listener != null)
                listener.TopViewBindData(recyclerTopView_bottom,getDataAtIndex(bottom_index));
        }
        else
            recyclerTopView_bottom.setVisibility(View.GONE);

        recyclerTopView.addView(recyclerTopView_top);
        recyclerTopView.addView(recyclerTopView_center);
        recyclerTopView.addView(recyclerTopView_bottom);
    }

    public int getCount(){
        int count = data.size();
        for (BaseChild child : data) {
            if(child.isHasChildTop()){
                count += 1;
            }
        }
        return count;
    }

    public int getChildType(int index){
        int c_index = -1;
        for (BaseChild child : data) {
            if (child.isHasChildTop())
                c_index += 1;
            if (index == c_index)
                return CHILD_TYPE_TOP;
            c_index += 1;
            if (index == c_index)
                return CHILD_TYPE_ITEM;
        }
        return CHILD_TYPE_ITEM;
    }

    public Object getDataAtIndex(int index){
        int c_index = -1;
        for (BaseChild child : data) {
            if (child.isHasChildTop())
                c_index += 1;
            if (index == c_index)
                return child.getChildTop();
            c_index += 1;
            if (index == c_index)
                return child;
        }
        return null;
    }

    private int getNextChildTopIndex(int index){
        int c_index = index;
        if (getChildType(c_index) == CHILD_TYPE_ITEM){
            c_index += 1;
            while (c_index < getCount()){
                if (getChildType(c_index) == CHILD_TYPE_TOP)
                    return c_index;
                c_index += 1;
            }
        }
        return index;
    }

    private int getBeforeChildTopIndex(int index){
        int c_index = index;
        if (getChildType(c_index) == CHILD_TYPE_ITEM){
            c_index -= 1;
            while (c_index >= 0){
                if (getChildType(c_index) == CHILD_TYPE_TOP)
                    return c_index;
                c_index -= 1;
            }
        }
        return index;
    }
}

