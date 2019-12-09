package com.github.dividerline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


/**
 * @author zhongrui
 */
public class BaseDividerGridItem extends RecyclerView.ItemDecoration {
    private String TAG = this.getClass().getSimpleName();
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable dividerDrawable;
    /*水平item间距*/
    private int hGap;
    /*垂直item间距*/
    private int vGap;
    private boolean showFirstLine;
    private boolean showLastLine;

    public BaseDividerGridItem(Context context) {
        this(context, -1);
    }

    public BaseDividerGridItem(Context context, int hGap) {
        this(context, hGap, null);
    }

    public BaseDividerGridItem(Context context, int hGap, @ColorRes int colorId) {
        this(context, hGap, new ColorDrawable(ContextCompat.getColor(context, colorId)));
    }

    public BaseDividerGridItem(Context context, int hGap, Drawable drawable) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        if (drawable == null) {
            dividerDrawable = a.getDrawable(0);
        } else {
            dividerDrawable = drawable;
        }
        if (hGap < 0) {
            this.vGap = dividerDrawable.getIntrinsicHeight();
            this.hGap = dividerDrawable.getIntrinsicWidth();
        } else {
            this.hGap = hGap;
            vGap = hGap;
        }
        a.recycle();
    }

    public boolean isShowFirstLine() {
        return showFirstLine;
    }

    public void setShowFirstLine(boolean showFirstLine) {
        this.showFirstLine = showFirstLine;
    }

    public boolean isShowLastLine() {
        return showLastLine;
    }

    public void setShowLastLine(boolean showLastLine) {
        this.showLastLine = showLastLine;
    }

    public void setShowBoth() {
        this.showFirstLine = true;
        this.showLastLine = true;
    }

    public int getHGap() {
        /*绘制垂直线的时候用水平间距*/
        return hGap;
    }

    public void setHGap(int hGap) {
        this.hGap = hGap;
    }

    public int getVGap() {
        /*绘制水平线的时候用垂直间距*/
        return vGap;
    }

    public void setVGap(int vGap) {
        this.vGap = vGap;
    }

    private int getAdapterBottomViewCount(RecyclerView parent) {
        /*RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter instanceof MyLoadMoreAdapter && ((MyLoadMoreAdapter) adapter).onLoadMoreListener != null) {
            return ((MyLoadMoreAdapter) adapter).getLoadMoreViewCount();
        }*/
        return 0;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        //LinearLayoutManager
        if (isLinearLayoutManager(parent)) {
            drawLinearLayoutHorizontalDividerLine(c, parent);
            return;
        }
        //GridLayoutManager
        if (isGridLayoutManager(parent)) {
//            drawGridLayoutHorizontalDividerLine(c, parent);
            return;
        }
        //StaggeredGridLayoutManager
        if (isStaggeredGridLayoutManager(parent)) {
            return;
        }
    }


    private void drawGridLayoutHorizontalDividerLine(Canvas c, RecyclerView parent) {
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        drawHorizontal(c, parent, spanCount);
        drawVertical(c, parent, spanCount);
    }

    public void drawHorizontal(Canvas c, RecyclerView parent, int spanCount) {
        int childCount = parent.getChildCount();
        int hGap = 0;
        View child;
        RecyclerView.LayoutParams params;
        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int tempLeftGap = 0;
            if (isGridLeftItem(i, spanCount)) {
                //顺向列表左边部分的item
                hGap = getGridFirstGap(spanCount);
            } else if (isGridRightItem(i, spanCount)) {
                //顺向列表右边部分的item
                hGap = getGridFirstGap(spanCount);
                tempLeftGap = hGap;
            } else {
                //顺向列表中间部分的item
                hGap = getGridSecondRightGap(spanCount);
                tempLeftGap = getGridSecondLeftGap(spanCount);
            }
            final int left = child.getLeft() - params.leftMargin - tempLeftGap;
            final int right = child.getRight() + params.rightMargin + hGap;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + getVGap();
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent, int spanCount) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + getHGap();

            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }
    }

    private void drawLinearLayoutHorizontalDividerLine(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            //是否绘制第一个item顶部
            if (i == 0 && isShowFirstLine()) {
                drawHorizontalFirstLine(c, parent, params);
            }
            if (i < childCount - 1) {
                drawHorizontalBottomLine(c, parent, child, params);
            } else {
                if (isShowLastLine()) {
                    //是否绘制最后一个item的底部
                    drawHorizontalBottomLine(c, parent, child, params);
                }
            }
        }
    }

    private void drawHorizontalBottomLine(Canvas c, RecyclerView parent, View child, RecyclerView.LayoutParams params) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int top;
        final int bottom;
        if (isReverseLayout(parent)) {
            bottom = child.getTop() - params.topMargin;
            top = bottom - getVGap();
        } else {
            top = child.getBottom() + params.bottomMargin;
            bottom = top + getVGap();
        }
        dividerDrawable.setBounds(left, top, right, bottom);
        dividerDrawable.draw(c);
    }

    public void drawHorizontalFirstLine(Canvas c, RecyclerView parent, RecyclerView.LayoutParams params) {
        final View child;
        final int left;
        final int right;
        final int bottom;
        final int top;
        if (isReverseLayout(parent)) {
            child = parent.getChildAt(0);
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            top = child.getBottom() + params.bottomMargin;
            bottom = top + getVGap();
        } else {
            child = parent.getChildAt(0);
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            bottom = child.getTop() - params.topMargin;
            top = bottom - getVGap();
        }
        dividerDrawable.setBounds(left, top, right, bottom);
        dividerDrawable.draw(c);
    }

    private boolean isReverseLayout(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) (parent.getLayoutManager())).getReverseLayout();
        } else {
            return ((LinearLayoutManager) (parent.getLayoutManager())).getReverseLayout();
        }
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = 1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontalForReverse(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + getHGap();
            final int top = child.getTop() - params.topMargin - getVGap();
            final int bottom = child.getTop() - params.topMargin;
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }
    }


    public void drawVertical2(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + getHGap();

            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }


    //偏移量包着item的margin包着item的padding
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        boolean reverseLayout = isReverseLayout(parent);
        if (isLinearLayoutManager(parent)) {
            //list
            if (reverseLayout) {
                setListReverseOffsets(outRect, view, parent);
            } else {
                setListOffsets(outRect, view, parent);
            }
            return;
        }
        if (isGridLayoutManager(parent)) {
            //grid
            setGridOffsets(outRect, view, parent);
            return;
        }
        if (isStaggeredGridLayoutManager(parent)) {
            //grid
            setGridOffsets(outRect, view, parent);
            return;
        }
    }

    private void setGridOffsets(Rect outRect, View view, RecyclerView parent) {
        int itemPosition = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        //最后一行
        if (isGridLastRaw(itemPosition, spanCount, childCount)) {
            if (isGridLeftItem(itemPosition, spanCount)) {
                //最左边item
                outRect.set(0, 0, getGridFirstGap(spanCount), 0);
            } else if (isGridRightItem(itemPosition, spanCount)) {
                //最右边item
                outRect.set(getGridFirstGap(spanCount), 0, 0, 0);
            } else {
                //中间item
                outRect.set(getGridSecondLeftGap(spanCount), 0, getGridSecondRightGap(spanCount), 0);
            }
        } else {
            if (isGridLeftItem(itemPosition, spanCount)) {
                //最左边item
                outRect.set(0, 0, getGridFirstGap(spanCount), getVGap());
            } else if (isGridRightItem(itemPosition, spanCount)) {
                //最右边item
                outRect.set(getGridFirstGap(spanCount), 0, 0, getVGap());
            } else {
                //中间item
                outRect.set(getGridSecondLeftGap(spanCount), 0, getGridSecondRightGap(spanCount), getVGap());
            }
        }
    }

    private boolean isGridLeftItem(int position, int spanCount) {
        return position % spanCount == 0;
    }

    private boolean isGridRightItem(int position, int spanCount) {
        return (position + 1) % spanCount == 0;
    }

    private boolean isGridCenterItem(int position, int spanCount) {
        return (isGridLeftItem(position, spanCount) == false) && (isGridRightItem(position, spanCount) == false);
    }

    private boolean isGridLastRaw(int position, int spanCount, int childCount) {
        int ranger = childCount % spanCount;
        if (ranger == 0) {
            ranger = spanCount;
        }
        return position >= childCount - ranger;
    }

    /**
     * 列表两边的item所拥有的间隔
     *
     * @param spanCount
     * @return
     */
    private int getGridFirstGap(int spanCount) {
        return getHGap() * (spanCount - 1) / spanCount;
    }

    /**
     * 列表中间部分item左边所拥有的间隔
     *
     * @param spanCount
     * @return
     */
    private int getGridSecondLeftGap(int spanCount) {
        return getHGap() / spanCount;
    }

    /**
     * 列表中间部分item右边所拥有的间隔
     *
     * @param spanCount
     * @return
     */
    private int getGridSecondRightGap(int spanCount) {
        return getHGap() * (spanCount - 2) / spanCount;
    }

    private void setListReverseOffsets(Rect outRect, View view, RecyclerView parent) {
        //反向以绘制item的top部位
        int itemPosition = parent.getChildAdapterPosition(view);
        int childCount = parent.getAdapter().getItemCount();
        //第一个item
        if (itemPosition == 0) {
            if (isShowFirstLine()) {
                //是否显示第一个item的底部分割线(反向的原因)
                outRect.set(0, getVGap(), 0, getVGap());
            } else {
                outRect.set(0, getVGap(), 0, 0);
            }
        } else if (itemPosition == childCount - 1) {
            //最后一个item
            if (isShowLastLine()) {
                //是否显示最后一个item的上面分割线(反向的原因)
                outRect.set(0, getVGap(), 0, 0);
            }
        } else {
            //其他item正常显示上面部分割线(反向的原因)
            outRect.set(0, getVGap(), 0, 0);
        }
    }

    private void setListOffsets(Rect outRect, View view, RecyclerView parent) {
        //顺向以绘制item的bottom部位
        int itemPosition = parent.getChildAdapterPosition(view);
        int childCount = parent.getAdapter().getItemCount();
        //第一个item
        if (itemPosition == 0) {
            if (isShowFirstLine()) {
                //是否显示第一个item的顶部分割线
                outRect.set(0, getVGap(), 0, getVGap());
            } else {
                outRect.set(0, 0, 0, getVGap());
            }
        } else if (itemPosition == childCount - 1) {
            //最后一个item
            if (isShowLastLine()) {
                //是否显示最后一个item的底部分割线
                outRect.set(0, 0, 0, getVGap());
            }
        } else {
            //其他item正常显示底部分割线
            outRect.set(0, 0, 0, getVGap());
        }
    }

    private boolean isLinearLayoutManager(RecyclerView parent) {
        return parent.getLayoutManager() instanceof LinearLayoutManager && (parent.getLayoutManager() instanceof GridLayoutManager) == false;
    }

    private boolean isGridLayoutManager(RecyclerView parent) {
        return parent.getLayoutManager() instanceof GridLayoutManager;
    }

    private boolean isStaggeredGridLayoutManager(RecyclerView parent) {
        return parent.getLayoutManager() instanceof StaggeredGridLayoutManager;
    }
}