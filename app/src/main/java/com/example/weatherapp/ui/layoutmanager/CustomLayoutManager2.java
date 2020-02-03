package com.example.weatherapp.ui.layoutmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

public class CustomLayoutManager2 extends LinearLayoutManager {
    private final float mShrinkAmount = 0.5f;
    // The cards will be at 50% when they are 75% of the way between the
    // center and the edge.
    private final float mShrinkDistance = 0.75f;
    public CustomLayoutManager2(Context context) {
        super(context);
    }

    public CustomLayoutManager2(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLayoutManager2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
