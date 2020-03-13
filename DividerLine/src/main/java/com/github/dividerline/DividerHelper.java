package com.github.dividerline;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

class DividerHelper {
    /*如果是垂直列表，改变对应位置的间隔*/
    protected static void changeRectForVerticalAndReverse(Rect outRect, boolean isVerticalList, boolean isReverse) {
        if (isVerticalList && isReverse) {
            //垂直反向的话 底 上交换位置
            int top = outRect.top;
            int bottom = outRect.bottom;
            outRect.top = bottom;
            outRect.bottom = top;
        }
    }

    /*如果是横向列表，改变对应位置的间隔*/
    protected static void changeRectForHorizontalAndReverse(Rect outRect, boolean isGridList, boolean isVerticalList, boolean isReverse) {
        if (isVerticalList) {
            return;
        }
        if (isGridList) {
            //grid
            if (isReverse) {
                //如果是横向列表
                /*(反向)左边间距变成上边间距*/
                /*(反向)上边间距变成左边间距*/
                /*(反向)右边间距变成下边间距*/
                int left = outRect.left;
                int bottom = outRect.bottom;
                int right = outRect.right;
                outRect.set(bottom, left, 0, right);
            } else {
                //如果是横向列表
                /*(顺向)左边间距变成下边间距*/
                /*(顺向)下边间距变成右边间距*/
                /*(顺向)右边间距变成上边间距*/
                int left = outRect.left;
                int bottom = outRect.bottom;
                int right = outRect.right;
                outRect.set(0, right, bottom, left);
            }
            return;
        }

        //list
        //如果是横向列表
        /*(顺向)间距位置：上变左，下变右*/
        /*(反向)间距位置：上变右，下变左*/
        int top = outRect.top;
        int bottom = outRect.bottom;
        if (isReverse) {
            outRect.set(bottom, 0, top, 0);
        } else {
            outRect.set(top, 0, bottom, 0);
        }
    }




    protected static boolean isReverseLayout(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) (parent.getLayoutManager())).getReverseLayout();
        } else {
            return ((LinearLayoutManager) (parent.getLayoutManager())).getReverseLayout();
        }
    }

    protected static boolean isVerticalList(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL;
        }
        return true;
    }

    protected static int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = 1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    protected static boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {
                // 如果是最后一列，则不需要绘制右边
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) {
                    // 如果是最后一列，则不需要绘制右边
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



    protected static boolean isGridLeftItem(int position, int spanCount) {
        return position % spanCount == 0;
    }

    protected static boolean isGridRightItem(int position, int spanCount) {
        return (position + 1) % spanCount == 0;
    }

    protected static boolean isGridLastRaw(int position, int spanCount, int childCount) {
        int ranger = childCount % spanCount;
        if (ranger == 0) {
            ranger = spanCount;
        }
        return position >= childCount - ranger;
    }


    protected static boolean isLinearLayoutManager(RecyclerView parent) {
        return parent.getLayoutManager() instanceof LinearLayoutManager && (parent.getLayoutManager() instanceof GridLayoutManager) == false;
    }

    protected static boolean isGridLayoutManager(RecyclerView parent) {
        return parent.getLayoutManager() instanceof GridLayoutManager;
    }

    protected static boolean isStaggeredGridLayoutManager(RecyclerView parent) {
        return parent.getLayoutManager() instanceof StaggeredGridLayoutManager;
    }
}
