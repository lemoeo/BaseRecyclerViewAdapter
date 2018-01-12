package com.ikarr.baserecyclerviewadapter.adapter;

import android.view.View;

import com.ikarr.baserecyclerviewadapter.R;
import com.ikarr.library.BaseRecyclerViewAdapter;
import com.ikarr.library.BaseViewHolder;

/**
 * Created by sunjays on 2018/1/12.
 */

public class MainAdapter extends BaseRecyclerViewAdapter<String, BaseViewHolder> {
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_recy_main;
    }

    @Override
    protected BaseViewHolder createViewHolder(View itemView) {
        return new BaseViewHolder(itemView);
    }

    @Override
    protected void onBindItem(BaseViewHolder holder, String item) {
        holder.setText(R.id.textView, item);
    }

    @Override
    protected void bindClickListener(BaseViewHolder holder) {
        super.bindClickListener(holder);
    }
}
