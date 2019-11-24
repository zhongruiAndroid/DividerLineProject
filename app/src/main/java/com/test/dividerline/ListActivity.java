package com.test.dividerline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.github.dividerline.BaseDividerGridItem;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    MyAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView=findViewById(R.id.rv);



        List<String> list=new ArrayList<>();
        adapter=new MyAdapter();
        for (int i = 0; i < 30; i++) {
            list.add("第"+i+"个item");
        }
        adapter.setList(list);


        int num = getIntent().getIntExtra("num", 1);
        int type = getIntent().getIntExtra("type", 1);
        boolean orientation = getIntent().getBooleanExtra("orientation", false);
        boolean reverse = getIntent().getBooleanExtra("reverse", false);
        boolean showTop = getIntent().getBooleanExtra("showTop", false);
        boolean showBottom = getIntent().getBooleanExtra("showBottom", false);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        switch (type){
            case 1:
                linearLayoutManager = new LinearLayoutManager(this,orientation?LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL,reverse);
            break;
            case 2:
                linearLayoutManager = new GridLayoutManager(this,num,orientation?LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL,reverse);
            break;
            case 3:
                linearLayoutManager = new StaggeredGridLayoutManager(num,orientation?LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL);
            break;
        }

        BaseDividerGridItem baseDividerGridItem = new BaseDividerGridItem(this, 30,R.color.colorAccent3);
        baseDividerGridItem.setShowFristLine(showTop);
        baseDividerGridItem.setShowLastLine(showBottom);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(baseDividerGridItem);
        recyclerView.setAdapter(adapter);

    }
}
