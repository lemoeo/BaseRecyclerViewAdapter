package com.ikarr.library;

import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iKarr on 2018/1/10.
 */

public abstract class BaseRecyclerViewAdapter<M, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    private List<M> items;
    protected LayoutInflater layoutInflater;
    private ItemClickListener itemClickListener;
    private OnItemClickListener onItemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private ItemChildClickListener itemChildClickListener;
    private OnItemChildClickListener onItemChildClickListener;
    private ItemChildLongClickListener itemChildLongClickListener;
    private OnItemChildLongClickListener onItemChildLongClickListener;

    public BaseRecyclerViewAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        VH holder = createViewHolder(getItemView(getLayoutResId(viewType), parent));
        bindClickListener(holder);
        return holder;
    }

    protected abstract @LayoutRes int getLayoutResId(int viewType);

    protected abstract VH createViewHolder(View itemView);

    protected void bindClickListener(VH holder) {
        addItemClickListener(holder);
        addItemLongClickListener(holder);
    }

    protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return layoutInflater.inflate(layoutResId, parent, false);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindItem(holder, getItem(position));
    }

    protected abstract void onBindItem(VH holder, M item);

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    protected void addItemClickListener(VH holder) {
        if (itemClickListener == null) {
            itemClickListener = new ItemClickListener();
        }
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(itemClickListener);
    }

    protected void addItemLongClickListener(VH holder) {
        if (itemLongClickListener == null) {
            itemLongClickListener = new ItemLongClickListener();
        }
        holder.itemView.setTag(holder);
        holder.itemView.setOnLongClickListener(itemLongClickListener);
    }

    protected void addChildClickListener(VH holder, @IdRes int id) {
        if (itemChildClickListener == null) {
            itemChildClickListener = new ItemChildClickListener();
        }
        final View v = holder.itemView.findViewById(id);
        v.setTag(holder);
        v.setOnClickListener(itemChildClickListener);
    }

    protected void addChildLongClickListener(VH holder, @IdRes int id) {
        if (itemChildLongClickListener == null) {
            itemChildLongClickListener = new ItemChildLongClickListener();
        }
        final View v = holder.itemView.findViewById(id);
        v.setTag(holder);
        v.setOnLongClickListener(itemChildLongClickListener);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        this.onItemChildLongClickListener = onItemChildLongClickListener;
    }

    public void setData(List<M> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public void addData(List<M> newItems) {
        this.items.addAll(newItems);
        notifyItemRangeInserted(items.size()-newItems.size(), newItems.size());
    }

    public void addData(@IntRange(from = 0) int index, List<M> newItems) {
        this.items.addAll(index, newItems);
        notifyItemRangeInserted(index, newItems.size());
    }

    public List<M> getData() {
        return items;
    }

    public void addItem(M item) {
        items.add(item);
        notifyItemInserted(items.size());
    }

    public void addItem(@IntRange(from = 0) int index, M item) {
        items.add(index, item);
        notifyItemInserted(index);
    }

    public void setItem(@IntRange(from = 0) int index, M item) {
        items.set(index, item);
        notifyItemChanged(index);
    }

    public M getItem(@IntRange(from = 0) int position) {
        return position < items.size() ? items.get(position) : null;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(View view, int position);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(View view, int position);
    }

    private class ItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, ((VH) view.getTag()).getLayoutPosition());
            }
        }
    }

    private class ItemLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClick(view, ((VH) view.getTag()).getLayoutPosition());
            }
            return false;
        }
    }

    private class ItemChildClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (onItemChildClickListener != null) {
                onItemChildClickListener.onItemChildClick(view, ((VH)view.getTag()).getLayoutPosition());
            }
        }
    }

    private class ItemChildLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            if (onItemChildLongClickListener != null) {
                return onItemChildLongClickListener.onItemChildLongClick(view, ((VH)view.getTag()).getLayoutPosition());
            }
            return false;
        }
    }
}
