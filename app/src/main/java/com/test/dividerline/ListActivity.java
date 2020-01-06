package com.test.dividerline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.github.dividerline.BaseDividerGridItem;
import com.github.dividerline.BaseDividerGridItem3;

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

        int count = getIntent().getIntExtra("count", 1);



        List<String> list=new ArrayList<>();
        adapter=new MyAdapter();
        for (int i = 0; i < count; i++) {
            list.add("第"+i+"个item");
        }
        adapter.setList(list);


        int num = getIntent().getIntExtra("num", 1);
        int type = getIntent().getIntExtra("type", 1);
        boolean orientation = getIntent().getBooleanExtra("orientation", false);
        boolean reverse = getIntent().getBooleanExtra("reverse", false);
        boolean showTop = getIntent().getBooleanExtra("showTop", false);
        boolean showBottom = getIntent().getBooleanExtra("showBottom", false);
        switch (type){
            case 1:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,orientation?LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL,reverse);
                recyclerView.setLayoutManager(linearLayoutManager);
            break;
            case 2:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this,num,orientation?LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL,reverse);
                recyclerView.setLayoutManager(gridLayoutManager);
            break;
            case 3:
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(num,orientation?LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
        }

        BaseDividerGridItem3 baseDividerGridItem = new BaseDividerGridItem3(this, 30,R.color.colorAccent3);
        baseDividerGridItem.setShowFirstLine(showTop);
        baseDividerGridItem.setShowLastLine(showBottom);

        recyclerView.addItemDecoration(baseDividerGridItem);
        recyclerView.setAdapter(adapter);

    }
}
