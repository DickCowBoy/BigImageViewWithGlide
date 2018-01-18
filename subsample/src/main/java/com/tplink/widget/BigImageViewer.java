package com.tplink.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BigImageViewer extends ImageView {

    private Drawable mThumbDrawable;



    public BigImageViewer(Context context) {
        this(context, null);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public BigImageViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BigImageViewer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private boolean needDrawaThumb() {
        return true;
    }
}
