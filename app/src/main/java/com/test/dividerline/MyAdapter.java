package com.test.dividerline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
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
}
