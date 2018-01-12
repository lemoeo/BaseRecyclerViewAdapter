package com.ikarr.databinding_example.base;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sunjays on 2018/1/10.
 */

public abstract class BaseRecyclerViewAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter {
    private ObservableList<M> items;
    private ListChangedCallback callback;

    private ItemClickListener itemClickListener;
    private OnItemClickListener onItemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private ItemChildClickListener itemChildClickListener;
    private OnItemChildClickListener onItemChildClickListener;


    public BaseRecyclerViewAdapter() {
        items = new ObservableArrayList<>();
        callback = new ListChangedCallback();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutResId(viewType), parent, false);
        RecyclerView.ViewHolder holder = new BaseRecyBindingViewHolder(binding);

        bindItemViewClickListener(holder);
        addChildClickListener(holder);
        Log.e("viewHolder", "onCreateViewHolder: viewHolder"+holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindItem(((BaseRecyBindingViewHolder)holder).getBinding(), getItem(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        items.addOnListChangedCallback(callback);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        items.removeOnListChangedCallback(callback);
    }

    private void bindItemViewClickListener(RecyclerView.ViewHolder holder) {
        if (itemClickListener == null) {
            itemClickListener = new ItemClickListener();
        }
        if (itemLongClickListener == null) {
            itemLongClickListener = new ItemLongClickListener();
        }
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(itemClickListener);
        holder.itemView.setOnLongClickListener(itemLongClickListener);
    }

    protected void setChildClickListener(RecyclerView.ViewHolder holder, @IdRes int id) {
        if (itemChildClickListener == null) {
            itemChildClickListener = new ItemChildClickListener();
        }
        final View v = holder.itemView.findViewById(id);
        v.setTag(holder);
        v.setOnClickListener(itemChildClickListener);
    }

    protected abstract void addChildClickListener(RecyclerView.ViewHolder holder);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 设置新的数据源 并监听新数据源的变化
     * @param newItems
     */
    public void setItems(ObservableList<M> newItems) {
        items.removeOnListChangedCallback(callback);
        this.items = newItems;
        items.addOnListChangedCallback(callback);
        notifyDataSetChanged();
    }

    /**
     * 获取Adapter的数据源
     * @return
     */
    public ObservableList<M> getItems() {
        return items;
    }

    public M getItem(@IntRange(from = 0) int position) {
        return position < items.size() ? items.get(position) : null;
    }

    /**
     * 设置item布局
     * @param viewType
     * @return
     */
    protected abstract @LayoutRes int getLayoutResId(int viewType);

    /**
     * item绑定数据
     * @param binding
     * @param item
     */
    protected abstract void onBindItem(B binding, M item);


    private class BaseRecyBindingViewHolder extends RecyclerView.ViewHolder {
        private B binding;

        public BaseRecyBindingViewHolder(B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected B getBinding() {
            return binding;
        }
    }

    /**
     * 数据源变化监听器
     */
    private class ListChangedCallback extends ObservableList.OnListChangedCallback<ObservableList<M>> {

        @Override
        public void onChanged(ObservableList<M> ms) {
            BaseRecyclerViewAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList<M> ms, int i, int i1) {
            BaseRecyclerViewAdapter.this.notifyItemRangeChanged(i, i1);
        }

        @Override
        public void onItemRangeInserted(ObservableList<M> ms, int i, int i1) {
            BaseRecyclerViewAdapter.this.notifyItemRangeInserted(i, i1);
        }

        @Override
        public void onItemRangeMoved(ObservableList<M> ms, int i, int i1, int i2) {
            if (i2 == 1) {
                BaseRecyclerViewAdapter.this.notifyItemMoved(i, i1);
            } else {
                BaseRecyclerViewAdapter.this.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList<M> ms, int i, int i1) {
            BaseRecyclerViewAdapter.this.notifyItemRangeRemoved(i, i1);
        }
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(View view, int position);
    }

    private class ItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, ((RecyclerView.ViewHolder) view.getTag()).getLayoutPosition());
            }
        }
    }

    private class ItemLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClick(view, ((RecyclerView.ViewHolder) view.getTag()).getLayoutPosition());
            }
            return false;
        }
    }


    private class ItemChildClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (onItemChildClickListener != null) {
                Log.e("viewHolder", "ItemChildClickListener: viewHolder"+view.getTag());
                onItemChildClickListener.onItemChildClick(view, ((RecyclerView.ViewHolder)view.getTag()).getLayoutPosition());
            }
        }
    }
}
