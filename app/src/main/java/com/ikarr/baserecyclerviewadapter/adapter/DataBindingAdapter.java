package com.ikarr.baserecyclerviewadapter.adapter;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.ikarr.baserecyclerviewadapter.R;
import com.ikarr.baserecyclerviewadapter.databinding.ItemRecyBindingBinding;
import com.ikarr.library.BaseRecyclerViewAdapter;
import com.ikarr.library.BaseViewHolder;

/**
 * Created by sunjays on 2018/1/12.
 */

public class DataBindingAdapter extends BaseRecyclerViewAdapter<String, DataBindingAdapter.BaseBindingViewHolder> {

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_recy_binding;
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ItemRecyBindingBinding b = DataBindingUtil.inflate(layoutInflater, layoutResId, parent, false);
        View v = b.getRoot();
        v.setTag(com.ikarr.library.R.id.binding_support, b);
        return v;
    }

    @Override
    protected BaseBindingViewHolder createViewHolder(View itemView) {
        return new BaseBindingViewHolder(itemView);
    }

    @Override
    protected void onBindItem(BaseBindingViewHolder holder, String item) {
        holder.getBinding().setStr(item);
    }

    public class BaseBindingViewHolder extends BaseViewHolder {
        ItemRecyBindingBinding binding;

        public BaseBindingViewHolder(View itemView) {
            super(itemView);
            binding = (ItemRecyBindingBinding) itemView.getTag(com.ikarr.library.R.id.binding_support);
        }

        public ItemRecyBindingBinding getBinding() {
            return binding;
        }
    }
}
