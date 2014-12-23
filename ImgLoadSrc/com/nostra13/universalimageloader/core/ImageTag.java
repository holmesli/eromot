package com.nostra13.universalimageloader.core;

import android.widget.ImageView.ScaleType;

public final class ImageTag {
    final Object originalObj;
    final ScaleType scaleType;

    public ImageTag(Object originalObj, ScaleType scaleType) {
        this.originalObj = originalObj;
        this.scaleType = scaleType;
    }

}
