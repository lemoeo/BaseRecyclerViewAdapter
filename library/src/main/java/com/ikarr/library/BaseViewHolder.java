package com.ikarr.library;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by iKarr on 2018/1/11.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final SparseArrayCompat<View> cacheViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        cacheViews = new SparseArrayCompat<>();
    }

    public BaseViewHolder setText(@IdRes int viewId, CharSequence text) {
        TextView tv = findViewById(viewId);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setText(@IdRes int viewId, @StringRes int resId) {
        TextView tv = findViewById(viewId);
        tv.setText(resId);
        return this;
    }

    public BaseViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView iv = findViewById(viewId);
        iv.setImageResource(resId);
        return this;
    }

    public BaseViewHolder setImageBitmap(@IdRes int viewId, Bitmap bm) {
        ImageView iv = findViewById(viewId);
        iv.setImageBitmap(bm);
        return this;
    }

    public BaseViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView iv = findViewById(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setChecked(@IdRes int viewId, boolean checked) {
        View v = findViewById(viewId);
        if (v instanceof Checkable) {
            ((Checkable) v).setChecked(checked);
        }
        return this;
    }


    private <T extends View> T findViewById(@IdRes int viewId) {
        View v = cacheViews.get(viewId);
        if (v == null) {
            v = itemView.findViewById(viewId);
            cacheViews.put(viewId, v);
        }
        return (T) v;
    }

}
