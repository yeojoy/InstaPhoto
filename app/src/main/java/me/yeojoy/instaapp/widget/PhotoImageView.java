package me.yeojoy.instaapp.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by yeojoy on 2017. 1. 21..
 */

public class PhotoImageView extends ImageView {
    public PhotoImageView(Context context) {
        this(context, null);
    }

    public PhotoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Drawable drawable = getDrawable();
        if (drawable != null) {
            height = width * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
        }

        setMeasuredDimension(width, height);
    }
}
