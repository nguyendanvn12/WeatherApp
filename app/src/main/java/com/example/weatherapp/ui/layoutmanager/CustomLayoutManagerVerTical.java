package com.example.weatherapp.ui.layoutmanager;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManagerVerTical extends LinearLayoutManager {
    private int activeBackgroundResource;
    private int nonActiveBackgroundResource;
    private final int BACKGROUND_CHANGE_CODE=0;
    private int activeLevel;

    public void setActiveBackgroundResource(int activeBackgroundResource) {
        this.activeBackgroundResource = activeBackgroundResource;
    }

    public void setNonActiveBackgroundResource(int nonActiveBackgroundResource) {
        this.nonActiveBackgroundResource = nonActiveBackgroundResource;
    }


    private final float mShrinkAmount = 0.5f;
    // The cards will be at 50% when they are 75% of the way between the
    // center and the edge.
    private final float mShrinkDistance = 0.75f;

    @Override
    public int getPaddingTop() {
        return 90;
    }

    @Override
    public int getPaddingBottom() {
        return getPaddingTop();
    }

    public CustomLayoutManagerVerTical(Context context) {
        super(context);
    }

    public CustomLayoutManagerVerTical(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLayoutManagerVerTical(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scrollVerticallyBy(0, recycler, state);
    }



    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        int scroller = super.scrollVerticallyBy(dy, recycler, state);
        float midpoint = getHeight() / 2.f;
        float d0 = 0.f;
        float d1 = mShrinkDistance * midpoint;
        float s0 = 1.f;
        float s1 = 1.f - mShrinkAmount;
        float max = -100;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float childMidpoint = (getDecoratedTop(child) + getDecoratedBottom(child)) / 2.f;
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
            child.setScaleX(scale);
            child.setScaleY(scale);
    }return scroller;



}}



