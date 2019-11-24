package com.github.dividerline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2016/11/2.
 */
public class BaseDividerListItem extends RecyclerView.ItemDecoration  {
    private final String TAG=this.getClass().getSimpleName();
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    @IntDef({HORIZONTAL_LIST,VERTICAL_LIST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ParamType{};

    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerWidth;
//    private int mOrientation;

    public BaseDividerListItem(Context context) {
        this(context,-1);
    }
    public BaseDividerListItem(Context context, int dividerHeight) {
        this(context,dividerHeight,null);
    }
    public BaseDividerListItem(Context context, int dividerHeight, @DrawableRes int drawableId) {
        this(context,dividerHeight,ContextCompat.getDrawable(context, drawableId));
    }
    public BaseDividerListItem(Context context, int dividerHeight, Drawable drawable) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        if(drawable==null){
            mDivider = a.getDrawable(0);
        }else{
            mDivider=drawable;
        }
        if(dividerHeight<0){
            mDividerHeight=mDivider.getIntrinsicHeight();
            mDividerWidth=mDivider.getIntrinsicWidth();
        }else{
            mDividerHeight=dividerHeight;
            mDividerWidth=dividerHeight;
        }
        a.recycle();
    }

    private int getDividerHeight(){
        return mDividerHeight;
    }
    private int getDividerWidth(){
        return mDividerWidth;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int orientation=layoutManager.getOrientation();
        final boolean reverseLayout = layoutManager.getReverseLayout();
        if (orientation == VERTICAL_LIST) {
            if(reverseLayout){
                drawHorizontalLineForReverse(c, parent);
            }else{
                drawHorizontalLine(c, parent);
            }
        } else {
            if(reverseLayout){
                drawVerticalLineForReverse(c, parent);
            }else{
                drawVerticalLine(c, parent);
            }
        }
    }

    public void drawHorizontalFirstLineForReverse(Canvas c, RecyclerView parent, RecyclerView.LayoutParams params) {
        final View child = parent.getChildAt(0);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int top = child.getBottom() + params.bottomMargin;
        final int bottom = top + getDividerHeight();
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(c);
    }
    public void drawHorizontalFirstLine(Canvas c, RecyclerView parent, RecyclerView.LayoutParams params) {
        final View child = parent.getChildAt(0);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int bottom = child.getTop() + params.topMargin;
        final int top =bottom  ;
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(c);
    }
    public void drawHorizontalLineForReverse(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            //画第一条线
            if(i == 0 ){
                drawHorizontalFirstLineForReverse(c, parent,params);
            }
            final int top = child.getTop() - params.topMargin-getDividerHeight();
            final int bottom = child.getTop() - params.topMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
    public void drawHorizontalLine(Canvas c, RecyclerView parent) {

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            //画第一条线
            if(i == 0 ){
                drawHorizontalFirstLine(c, parent,params);
            }

            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + getDividerHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVerticalLineForReverse(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin-getDividerHeight();
            final int right = child.getLeft() - params.leftMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
    public void drawVerticalLine(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + getDividerHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int orientation=layoutManager.getOrientation();
        boolean reverseLayout = layoutManager.getReverseLayout();

        int itemPosition= parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        //最后一个item不设置偏移
        //&&isMyAdapter(adapter)&&((LoadMoreAdapter) adapter).onLoadMoreListener!=null
        if(itemPosition==parent.getAdapter().getItemCount()-1) {
            outRect.set(0, 0, 0, 0);
        } else{
            //垂直列表
            if (orientation == VERTICAL_LIST) {
                //列表是否反向
                if(reverseLayout){
                    outRect.set(0, getDividerHeight(), 0, 0);
                }else{
                    outRect.set(0,0,0, getDividerHeight());
                }
            } else {//水平列表
                //列表是否反向
                if(reverseLayout){
                    outRect.set(getDividerWidth(), 0,0, 0);
                }else{
                    outRect.set(0, 0,getDividerWidth(), 0);
                }
            }
        }
    }

}
