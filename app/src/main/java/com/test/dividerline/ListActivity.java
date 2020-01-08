package com.test.dividerline;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.github.dividerline.BaseItemDivider;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    MyAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.rv);

        int count = getIntent().getIntExtra("count", 1);
        int hGap = getIntent().getIntExtra("hGap", 0);
        int vGap = getIntent().getIntExtra("vGap", 0);


        List<String> list = new ArrayList<>();
        adapter = new MyAdapter();
        for (int i = 0; i < count; i++) {
            list.add("第" + i + "个item");
        }
        adapter.setList(list);


        int num = getIntent().getIntExtra("num", 1);
        int type = getIntent().getIntExtra("type", 1);
        boolean orientation = getIntent().getBooleanExtra("orientation", false);
        boolean reverse = getIntent().getBooleanExtra("reverse", false);
        boolean showTop = getIntent().getBooleanExtra("showTop", false);
        boolean showBottom = getIntent().getBooleanExtra("showBottom", false);
        adapter.setVertical(orientation);
        switch (type) {
            case 1:
                adapter.setGrid(false);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, orientation ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL, reverse);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;
            case 2:
                adapter.setGrid(true);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, num, orientation ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL, reverse);
                recyclerView.setLayoutManager(gridLayoutManager);
                break;
            case 3:
                adapter.needRandomHeight();
                adapter.setGrid(true);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(num, orientation ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
                staggeredGridLayoutManager.setReverseLayout(reverse);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
        }

        BaseItemDivider baseDividerGridItem = new BaseItemDivider(this, 30,ContextCompat.getColor(this,R.color.colorAccent2));
        baseDividerGridItem.setHGap(hGap);
        baseDividerGridItem.setVGap(vGap);
        baseDividerGridItem.setShowFirstLine(showTop);
        baseDividerGridItem.setShowLastLine(showBottom);

        recyclerView.addItemDecoration(baseDividerGridItem);
        recyclerView.setAdapter(adapter);

    }
}
