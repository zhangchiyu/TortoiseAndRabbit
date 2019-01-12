package com.example.doldrum.tortoiseandrabbit.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.doldrum.tortoiseandrabbit.bean.MessageCode;

import java.util.Random;


public class ContainerViewGroup extends ViewGroup {

    public ContainerViewGroup(Context context) {
        this(context,null);
    }

    public ContainerViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ContainerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int width = getWidth();
        int height = getHeight();
        for (int i = 0 ; i <childCount ;i++){
            View childAt =  getChildAt(i);
            int childleft = (int)(Math.random() * width);
            int childtop = (int)(Math.random() * height);
            childAt.layout(childleft,childtop,childCount+childAt.getWidth(),childtop+childAt.getHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode= MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth= MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int width = getWidth();
        int height = getHeight();
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY ? sizeWidth : width), (heightMode == MeasureSpec.EXACTLY ? sizeHeight : height));
    }



}
