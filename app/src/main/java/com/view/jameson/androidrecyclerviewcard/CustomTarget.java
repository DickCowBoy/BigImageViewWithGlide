package com.view.jameson.androidrecyclerviewcard;

import android.graphics.Bitmap;

import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class CustomTarget extends ViewTarget<SubsamplingScaleImageView, Bitmap>{
    public CustomTarget(SubsamplingScaleImageView view) {
        super(view);
    }

    @Override
    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
        this.view.setFullImageBitmap(bitmap);
    }
}
