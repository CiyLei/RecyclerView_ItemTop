package com.example.chenlei.recyclerview_itemtop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ItemTop.ChildTopHelp;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout RecyclerView_Top;
    private List<TestModel> datas = datas = new ArrayList<>();
    private ChildTopHelp help;

    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView_Top = (LinearLayout) findViewById(R.id.recyclerView_top_linear);

        for (int i = 1; i < 41; i++) {
            TestModel t = new TestModel(i + "");
            if (i % 5 == 0){
                t.setIsHasChildTop(true);
                t.setChildTop("五的倍数" + (int)i / 5);
            }
            datas.add(t);
        }

        help = new ChildTopHelp(this, datas, recyclerView, RecyclerView_Top, new ChildTopHelp.ChildTopViewListener() {
            @Override
            public void TopViewBindData(View topView, Object data) {
                TextView textView = (TextView) topView.findViewById(R.id.recycler_item_top_textview);
                textView.setText((String)data);
            }
        });
        adapter = new RecyclerViewAdapter(this,help);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int itemType, Object data) {
                if (itemType == ChildTopHelp.CHILD_TYPE_ITEM){
                    TestModel tm = (TestModel)data;
                    Toast.makeText(MainActivity.this,tm.getTitle(),Toast.LENGTH_SHORT).show();
                }
                else if(itemType == ChildTopHelp.CHILD_TYPE_TOP){
                    Toast.makeText(MainActivity.this,(String)data,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
