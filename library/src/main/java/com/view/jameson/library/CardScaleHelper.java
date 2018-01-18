package com.view.jameson.library;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


/**
 * Created by jameson on 8/30/16.
 */
public class CardScaleHelper {


    private int mCardWidth; // 卡片宽度
    private int mOnePageWidth; // 滑动一页的距离
    private int centerOffset = 0;

    private LinearSnapHelper mLinearSnapHelper = new LinearSnapHelper();
    private RecyclerView mView;

    public void attachToRecyclerView(final RecyclerView mRecyclerView, int offset) {
        // 开启log会影响滑动体验, 调试时才开启
        mLinearSnapHelper.attachToRecyclerView(mRecyclerView);
        mView = mRecyclerView;
        centerOffset = offset;
    }

    public static int getCurrentItemPos(RecyclerView view, int space) {
        int index = -1;
        if (view.getChildCount() > 0) {
            View childAt = view.getChildAt(0);
            index = view.getLayoutManager().getPosition(childAt);
            if (childAt.getLeft() >= (view.getLeft() - space)
                    && (childAt.getLeft() + childAt.getWidth()) <= (view.getRight() + space)) {
                return index;
            } else {
                return index + 1;
            }
        }
        return index;
    }

    public void scrollToPos(int index) {
        ((LinearLayoutManager)mView.getLayoutManager()).scrollToPositionWithOffset(index, centerOffset);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



}
