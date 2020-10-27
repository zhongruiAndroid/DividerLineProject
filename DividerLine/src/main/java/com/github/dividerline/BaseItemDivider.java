package com.github.dividerline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhongrui
 */
public class BaseItemDivider extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable dividerDrawable;
    /*水平item间距*/
    private int hGap;
    /*垂直item间距*/
    private int vGap;
    private int hGapHalf;
    private boolean showFirstLine;
    private boolean showLastLine;
    private boolean isTransparentDrawable;

    /*不需要画线的position*/
    private List<Integer> skipPosition;
    private int skipStartCount;
    private int skipEndCount;

    private int marginLeft;
    private int marginTop;
    private int marginRight;
    private int marginBottom;

    public BaseItemDivider(Context context) {
        this(context, 0, null);
    }

    public BaseItemDivider(Context context, int hGap) {
        this(context, hGap, Color.TRANSPARENT);
    }

    public BaseItemDivider(Context context, int hGap, @ColorInt int colorId) {
        this(context, hGap, new ColorDrawable(colorId));
        isTransparentDrawable = (colorId == Color.TRANSPARENT);
    }

    public BaseItemDivider(Context context, int hGap, Drawable drawable) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        if (drawable == null) {
            dividerDrawable = a.getDrawable(0);
        } else {
            dividerDrawable = drawable;
        }
        if (hGap < 0) {
            this.vGap = dividerDrawable.getIntrinsicHeight();
            this.hGap = dividerDrawable.getIntrinsicWidth();
            //可能等于-1
            this.vGap = Math.max(this.vGap, 0);
            this.hGap = Math.max(this.hGap, 0);
        } else {
            this.hGap = hGap;
            this.vGap = hGap;
        }
        if (this.hGap > 0) {
            this.hGapHalf = Math.max(1, this.hGap / 2);
        } else {
            this.hGapHalf = 0;
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
        if (this.hGap > 0) {
            this.hGapHalf = Math.max(1, this.hGap / 2);
        } else {
            this.hGapHalf = 0;
        }
    }

    public int getVGap() {
        /*绘制水平线的时候用垂直间距*/
        return vGap;
    }

    public void setVGap(int vGap) {
        this.vGap = vGap;
    }

    public int getHGapHalf() {
        return hGapHalf;
    }

    private void initSkipList() {
        if (skipPosition == null) {
            skipPosition = new ArrayList<>();
        }
    }

    public List<Integer> getSkipPosition() {
        initSkipList();
        return skipPosition;
    }

    public void setSkipPosition(List<Integer> skipPosition) {
        if (skipPosition == null) {
            return;
        }
        this.skipPosition = skipPosition;
    }

    public void addSkipPosition(List<Integer> skipPosition) {
        if (skipPosition == null) {
            return;
        }
        initSkipList();
        this.skipPosition.addAll(skipPosition);
    }

    public void clearSkipPosition() {
        if (skipPosition != null) {
            skipPosition.clear();
        }
        setSkipStartCount(0);
        setSkipEndCount(0);
    }

    public void addSkipPosition(int position) {
        initSkipList();
        if (!skipPosition.contains(position)) {
            skipPosition.add(position);
        }
    }

    public void setSkipStartCount(int skipStartCount) {
        for (int i = 0; i < skipStartCount; i++) {
            addSkipPosition(i);
        }
        this.skipStartCount = skipStartCount;
    }

    public int getSkipStartCount() {
        return skipStartCount;
    }

    public void setSkipEndCount(int skipEndCount) {
        this.skipEndCount = skipEndCount;
    }

    public int getSkipEndCount() {
        return skipEndCount;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    private boolean needSkipPosition(int itemPosition, int childCount) {
        if (skipPosition != null && skipPosition.contains(itemPosition)) {
            /*如果存在需要忽略的item,不计算偏移量*/
            return true;
        }
        if (itemPosition >= (childCount - getSkipEndCount())) {
            /*如果存在需要忽略的item,不计算偏移量*/
            return true;
        }
        return false;
    }

    /*是否忽略头部局*/
    private boolean isSkipStart(int itemPosition, int childCount) {
        if (itemPosition -getSkipStartCount()<0) {
            /*如果存在需要忽略的item,不计算偏移量*/
            return true;
        }
        return false;
    }
    /*是否忽略尾部局*/
    private boolean isSkipEnd(int itemPosition, int childCount) {
        if (itemPosition >= (childCount - getSkipEndCount())) {
            /*如果存在需要忽略的item,不计算偏移量*/
            return true;
        }
        return false;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        if (isTransparentDrawable) {
            return;
        }
        if (DividerHelper.isStaggeredGridLayoutManager(parent)) {
            return;
        }
        if (parent.getLayoutManager() == null) {
            return;
        }
        //LinearLayoutManager
        if (DividerHelper.isLinearLayoutManager(parent)) {
            drawLinearLayoutHorizontalDividerLine(c, parent);
            return;
        }
        //GridLayoutManager
        if (DividerHelper.isGridLayoutManager(parent)) {
            drawGridLayoutHorizontalDividerLine(c, parent);
            return;
        }
    }

    //偏移量包着item的margin包着item的padding
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        boolean reverseLayout = DividerHelper.isReverseLayout(parent);

        int itemPosition = parent.getChildAdapterPosition(view);
        int childCount = parent.getAdapter().getItemCount();

        int spanCount = DividerHelper.getSpanCount(parent);
        boolean isVerticalList = DividerHelper.isVerticalList(parent);

        /********************************list*********************************/
        if (DividerHelper.isLinearLayoutManager(parent)) {
            if (needSkipPosition(itemPosition, childCount)) {
                return;
            }
            //list
            //默认垂直方向列表
            setListOffsets(outRect, itemPosition, childCount);
            /*如果是垂直反向需要改变间隔位置*/
            DividerHelper.changeRectForVerticalAndReverse(outRect, isVerticalList, reverseLayout);
            /*如果是横向列表，也需要改变间隔位置*/
            DividerHelper.changeRectForHorizontalAndReverse(outRect, false, isVerticalList, reverseLayout);
            return;
        }

        /********************************grid*********************************/
        if (DividerHelper.isGridLayoutManager(parent)) {
            //真实position减去需要忽略分割线的item(头布局)数量
            int tempPosition = itemPosition - getSkipStartCount();
            if (isSkipStart(itemPosition, childCount)||isSkipEnd(itemPosition, childCount)) {
                //头布局和尾部局的左右两侧需要分割空间，和列表左边、右边item类似
                outRect.set(getHGapHalf(), 0, getHGapHalf(), 0);
                return;
            }
            //真实position减去需要忽略分割线的item(头布局)数量
            itemPosition = tempPosition;
            //grid
            setGridOffsets(outRect, itemPosition, childCount, spanCount);
            /*如果是垂直反向需要改变间隔位置*/
            DividerHelper.changeRectForVerticalAndReverse(outRect, isVerticalList, reverseLayout);
            /*如果是横向列表，也需要改变间隔位置*/
            DividerHelper.changeRectForHorizontalAndReverse(outRect, true, isVerticalList, reverseLayout);
            return;
        }

        /********************************StaggeredGrid*********************************/
        if (DividerHelper.isStaggeredGridLayoutManager(parent)) {
            //真实position减去需要忽略分割线的item(头布局)数量
            int tempPosition = itemPosition - getSkipStartCount();
            if (isSkipStart(itemPosition, childCount)||isSkipEnd(itemPosition, childCount)) {
                //头布局和尾部局的左右两侧需要分割空间，和列表左边、右边item类似
                outRect.set(getHGapHalf(), 0, getHGapHalf(), 0);
                return;
            }
            //真实position减去需要忽略分割线的item(头布局)数量
            itemPosition = tempPosition;
            //grid
            setGridOffsets(outRect, itemPosition, childCount, spanCount);
            /*如果是垂直反向需要改变间隔位置*/
            DividerHelper.changeRectForVerticalAndReverse(outRect, isVerticalList, reverseLayout);
            /*如果是横向列表，也需要改变间隔位置*/
            DividerHelper.changeRectForHorizontalAndReverse(outRect, true, isVerticalList, reverseLayout);
            return;
        }
    }

    private void setListOffsets(Rect outRect, int itemPosition, int childCount) {
        //顺向以绘制item的bottom部位
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

    private void setGridOffsets(Rect outRect, int itemPosition, int childCount, int spanCount) {
        //最后一行
        if (DividerHelper.isGridLastRaw(itemPosition, spanCount, childCount, getSkipStartCount() + getSkipEndCount())) {
            if (DividerHelper.isGridLeftItem(itemPosition, spanCount)) {
                //最左边item
                outRect.set(getHGapHalf(), 0, getHGapHalf(), 0);
            } else if (DividerHelper.isGridRightItem(itemPosition, spanCount)) {
                //最右边item
                outRect.set(getHGapHalf(), 0, getHGapHalf(), 0);
            } else {
                //中间item
                outRect.set(getHGapHalf(), 0, getHGapHalf(), 0);
            }
        } else {
            if (DividerHelper.isGridLeftItem(itemPosition, spanCount)) {
                //最左边item
                outRect.set(getHGapHalf(), 0, getHGapHalf(), getVGap());
            } else if (DividerHelper.isGridRightItem(itemPosition, spanCount)) {
                //最右边item
                outRect.set(getHGapHalf(), 0, getHGapHalf(), getVGap());
            } else {
                //中间item
                outRect.set(getHGapHalf(), 0, getHGapHalf(), getVGap());
            }
        }
    }

    private void drawVerticalLine(int adapterPosition, int position, Canvas c, View child, int spanCount, int itemCount, boolean isReverseLayout, boolean isVerticalList) {
        position = adapterPosition - getSkipStartCount();
        /*为了保证每个item占有的宽度一致，则每个item左右分别画半个间距竖线，所以需要分别画两跟竖线*/
        int vGap = getHGapHalf();
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        int top;
        int bottom;
        int left;
        int right;
        if (isVerticalList) {
            top = child.getTop() - params.topMargin;
            bottom = child.getBottom() + params.bottomMargin;

            //最后一个但又不是最右边的一个item
            if (position == itemCount - 1 - getSkipEndCount() - getSkipStartCount() && !DividerHelper.isGridRightItem(position, spanCount)) {
                if (!DividerHelper.isGridLeftItem(position, spanCount)) {
                    //如果不是最左边的一个item
                    //左边部分
                    right = child.getLeft() - params.leftMargin;
                    left = right - vGap;


                    //绘制item左边的竖线
                    dividerDrawable.setBounds(left, top, right, bottom);
                    dividerDrawable.draw(c);
                }

                //右边部分
                left = child.getRight() + params.rightMargin;
                right = left + getHGap();
            } else if (DividerHelper.isGridLeftItem(position, spanCount)) {
                //顺向列表左边部分的item
                left = child.getRight() + params.rightMargin;
                right = left + vGap;
            } else if (DividerHelper.isGridRightItem(position, spanCount)) {
                //顺向列表右边部分的item
                right = child.getLeft() - params.leftMargin;
                left = right - vGap;
            } else {
                //顺向列表中间部分的item
                //左边部分
                right = child.getLeft() - params.leftMargin;
                left = right - vGap;

                //绘制item左边的竖线
                dividerDrawable.setBounds(left, top, right, bottom);
                dividerDrawable.draw(c);

                //右边部分
                left = child.getRight() + params.rightMargin;
                right = left + vGap;
            }

            //绘制item右边的竖线
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
            return;
        }

        /*横向列表，之前竖向列表的左边item变成顶部item,之前的右边item变成底部item,下面的注释没改还是之前的*/
        left = child.getLeft() - params.leftMargin;
        right = child.getRight() + params.rightMargin;

        if (position == itemCount - 1- getSkipEndCount() - getSkipStartCount() && !DividerHelper.isGridRightItem(position, spanCount)) {
            //最后一个但又不是最右边的一个item
            if (!DividerHelper.isGridLeftItem(position, spanCount)) {
                //如果不是最左边的一个item
                //左边部分
                bottom = child.getTop() - params.topMargin;
                top = bottom - vGap;

                //绘制item左边的竖线
                dividerDrawable.setBounds(left, top, right, bottom);
                dividerDrawable.draw(c);
            }

            //右边部分
            top = child.getBottom() + params.bottomMargin;
            bottom = top + getHGap();
        } else if (DividerHelper.isGridLeftItem(position, spanCount)) {
            //顺向列表左边部分的item
            top = child.getBottom() + params.bottomMargin;
            bottom = top + vGap;
        } else if (DividerHelper.isGridRightItem(position, spanCount)) {
            //顺向列表右边部分的item
            bottom = child.getTop() - params.topMargin;
            top = bottom - vGap;
        } else {
            //顺向列表中间部分的item
            bottom = child.getTop() - params.topMargin;
            top = bottom - vGap;

            //绘制item左边的竖线
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);

            //右边部分
            top = child.getBottom() + params.bottomMargin;
            bottom = top + vGap;
        }

        //绘制item右边的竖线
        dividerDrawable.setBounds(left, top, right, bottom);
        dividerDrawable.draw(c);
    }

    private void drawLinearLayoutHorizontalDividerLine(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int itemCount = parent.getAdapter().getItemCount();
        boolean isReverseLayout = DividerHelper.isReverseLayout(parent);
        boolean verticalList = DividerHelper.isVerticalList(parent);
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (needSkipPosition(parent.getChildAdapterPosition(child), itemCount)) {
                continue;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            //是否绘制第一个item顶部(反向就是底部)
            if (i == 0 && isShowFirstLine()) {
                drawHorizontalFirstLine(c, parent, child, params, isReverseLayout, verticalList);
            }
            if (i < itemCount - 1) {
                drawHorizontalBottomLine(c, parent, child, params, isReverseLayout, verticalList);
            } else {
                if (isShowLastLine()) {
                    //是否绘制最后一个item的底部
                    drawHorizontalBottomLine(c, parent, child, params, isReverseLayout, verticalList);
                }
            }
        }
    }

    private void drawHorizontalBottomLine(Canvas c, RecyclerView parent, View child, RecyclerView.LayoutParams params, boolean isReverseLayout, boolean verticalList) {
        int left;
        int right;
        int top;
        int bottom;

        if (verticalList) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            if (isReverseLayout) {
                bottom = child.getTop() - params.topMargin;
                top = bottom - getVGap();
            } else {
                top = child.getBottom() + params.bottomMargin;
                bottom = top + getVGap();
            }
            dividerDrawable.setBounds(left + getMarginLeft(), top, right - getMarginRight(), bottom);
            dividerDrawable.draw(c);

            return;
        }

        top = parent.getPaddingTop();
        bottom = parent.getHeight() - parent.getPaddingBottom();

        if (isReverseLayout) {
            right = child.getLeft() - params.leftMargin;
            left = right - getVGap();
        } else {
            left = child.getRight() + params.rightMargin;
            right = left + getVGap();
        }
        dividerDrawable.setBounds(left, top + getMarginTop(), right, bottom - getMarginBottom());
        dividerDrawable.draw(c);

    }

    private void drawHorizontalFirstLine(Canvas c, RecyclerView parent, View child, RecyclerView.LayoutParams params, boolean isReverseLayout, boolean verticalList) {
        int left;
        int right;
        int bottom;
        int top;

        if (verticalList) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();

            if (isReverseLayout) {
                top = child.getBottom() + params.bottomMargin;
                bottom = top + getVGap();
            } else {
                bottom = child.getTop() - params.topMargin;
                top = bottom - getVGap();
            }
            dividerDrawable.setBounds(left + getMarginLeft(), top, right - getMarginRight(), bottom);
            dividerDrawable.draw(c);

            return;
        }

        top = parent.getPaddingTop();
        bottom = parent.getHeight() - parent.getPaddingBottom();

        if (isReverseLayout) {
            left = child.getRight() + params.rightMargin;
            right = left + getVGap();
        } else {
            right = child.getLeft() - params.leftMargin;
            left = right - getVGap();
        }
        dividerDrawable.setBounds(left, top + getMarginTop(), right, bottom - getMarginBottom());
        dividerDrawable.draw(c);
    }

    private void drawGridLayoutHorizontalDividerLine(Canvas c, RecyclerView parent) {
        int spanCount = 0;
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount();
        }
        if (spanCount == 0) {
            return;
        }
        int childCount = parent.getChildCount();
        int itemCount = parent.getAdapter().getItemCount();
        boolean isReverseLayout = DividerHelper.isReverseLayout(parent);
        boolean verticalList = DividerHelper.isVerticalList(parent);
        View child;
        for (int i = 0; i < childCount; i++) {
            child = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(child);
            if (needSkipPosition(adapterPosition, itemCount)) {
                continue;
            }
            if (spanCount > 1) {
                //画竖线用水平间距hGap(如果是横向列表，就是画横线)
                drawVerticalLine(adapterPosition, i, c, child, spanCount, itemCount, isReverseLayout, verticalList);
            }
            //最后一行不画横线
            if (DividerHelper.isGridLastRaw(adapterPosition - getSkipStartCount(), spanCount, itemCount, getSkipStartCount() + getSkipEndCount())) {
                continue;
            }
            //画横线用垂直间距vGap(如果是横向列表，就是画竖线)
            drawHorizontalLine(adapterPosition, i, c, child, spanCount, isReverseLayout, verticalList);
        }
    }

    private void drawHorizontalLine(int adapterPosition, int position, Canvas c, View child, int spanCount, boolean isReverseLayout, boolean isVerticalList) {
        position = adapterPosition - getSkipStartCount();
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        if (isVerticalList) {
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + getVGap();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin;

            if (isReverseLayout) {
                bottom = child.getTop() - params.topMargin;
                top = bottom - getVGap();
            }

            if (DividerHelper.isGridLeftItem(position, spanCount)) {
                //顺向列表左边部分的item
                right = right + getHGapHalf();
            } else if (DividerHelper.isGridRightItem(position, spanCount)) {
                //顺向列表右边部分的item
                left = left - getHGapHalf();
            } else {
                //顺向列表中间部分的item
                left = left - getHGapHalf();
                right = right + getHGapHalf();
            }
            if (spanCount <= 1) {
                right = right - getHGapHalf();
            }
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
            return;
        }


        /*如果是横向列表，就是画竖线*/
        int top = child.getTop() - params.topMargin;
        int bottom = child.getBottom() + params.bottomMargin;

        int left = child.getRight() + params.rightMargin;
        int right = left + getVGap();

        if (isReverseLayout) {
            right = child.getLeft() - params.leftMargin;
            left = right - getVGap();
        }
        if (DividerHelper.isGridLeftItem(position, spanCount)) {
            //顺向列表左边部分的item(横向列表，就是顶上一排)
            bottom = bottom + getHGapHalf();
        } else if (DividerHelper.isGridRightItem(position, spanCount)) {
            //顺向列表右边部分的item(横向列表，就是底下一排)
            top = top - getHGapHalf();
        } else {
            //顺向列表中间部分的item
            top = top - getHGapHalf();
            bottom = bottom + getHGapHalf();
        }
        if (spanCount <= 1) {
            bottom = bottom - getHGapHalf();
        }
        dividerDrawable.setBounds(left, top, right, bottom);
        dividerDrawable.draw(c);

    }
}