package com.example.weatherapp.ui.layoutmanager;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManager extends LinearLayoutManager {
    private int activeBackgroundResource;
    private int nonActiveBackgroundResource;
    private final int BACKGROUND_CHANGE_CODE=0;
    private int activeLevel;
    private int mParentWidth = 250;
    private int mItemWidth = 200;


    public void setActiveBackgroundResource(int activeBackgroundResource) {
        this.activeBackgroundResource = activeBackgroundResource;
    }

    public void setNonActiveBackgroundResource(int nonActiveBackgroundResource) {
        this.nonActiveBackgroundResource = nonActiveBackgroundResource;
    }
    public float getPivotY() {
        return pivotY;
    }

    public float getPivotX() {
        return pivotX;
    }


    private float pivotY = 50;

    public void setPivotY(float pivotY) {
        this.pivotY = pivotY;
    }

    public void setPivotX(float pivotX) {
        this.pivotX = pivotX;
    }

    private float pivotX = 50;

    private final float mShrinkAmount = 0.5f;
    // The cards will be at 50% when they are 75% of the way between the
    // center and the edge.
    private final float mShrinkDistance = 0.75f;


    public CustomLayoutManager(Context context) {
        super(context);
    }

    public CustomLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public int getPaddingLeft() {
        return Math.round(250);
    }


    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }



    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scroller = super.scrollHorizontallyBy(dx, recycler, state);
        float midpoint = getWidth() / 2.f;
        float d0 = 0.f;
        float d1 = mShrinkDistance * midpoint;
        float s0 = 1.f;
        float s1 = 1.f - mShrinkAmount;
        float max = -100;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
            if (scale > 0.9) {
                if(BACKGROUND_CHANGE_CODE!=activeBackgroundResource){
                    child.setBackgroundResource(activeBackgroundResource);
                }

            } else {
                if(BACKGROUND_CHANGE_CODE!=nonActiveBackgroundResource){
                    child.setBackgroundResource(nonActiveBackgroundResource);
                }else {
                    child.setBackground(null);
                }
            }
            child.setPivotY(pivotY);
            child.setPivotX(pivotX);
            child.setScaleX(scale);
            child.setScaleY(scale);

        }



        return scroller;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scrollHorizontallyBy(0, recycler, state);
    }
}



