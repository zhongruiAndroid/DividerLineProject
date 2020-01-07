package com.test.dividerline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/***
 *   created by zhongrui on 2019/11/24
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private List<String>list;
    public void setList(List<String> list) {
        this.list = list;
    }
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        if(needRandomHeight){
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height=dp2px(viewGroup.getContext(),50,120);
        }
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }
    private int dp2px(Context context,int fromValue,int toValue){
        int from = (int) (context.getResources().getDisplayMetrics().density * fromValue);
        int to = (int) (context.getResources().getDisplayMetrics().density * toValue);
        if(from>to){
            from=from-to;
            to=to+from;
            from=to-from;
        }
        return new Random().nextInt(to-from)+from;
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.tvTitle.setText(list.get(i));
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
        }
    }

    private boolean needRandomHeight;
    public void needRandomHeight(){
        needRandomHeight=true;
    }
//    private int crossPosition=1;
//
//    public int getCrossPosition() {
//        return crossPosition;
//    }
//
//    public void setCrossPosition(int crossPosition) {
//        this.crossPosition = crossPosition;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if(position==crossPosition){
//            return 1;
//        }else{
//            return 0;
//        }
//    }
}
