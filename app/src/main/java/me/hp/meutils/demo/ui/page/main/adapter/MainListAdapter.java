package me.hp.meutils.demo.ui.page.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import me.hp.meutils.demo.databinding.RecyclerItemviewMainListBinding;

/**
 * @author: hepingdev
 * @created: 2022/7/13
 * @desc: recycler数据适配器
 */
public class MainListAdapter extends BaseQuickAdapter<String, MainListAdapter.MainListViewHolder> {

    public MainListAdapter(@Nullable List<String> data) {
        super(0, data);
    }

    @NonNull
    @Override
    protected MainListViewHolder onCreateDefViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemviewMainListBinding bind = RecyclerItemviewMainListBinding.inflate(LayoutInflater.from(getContext()), parent, false);
        return new MainListViewHolder(bind);
    }

    @Override
    protected void convert(@NonNull MainListViewHolder mainListViewHolder, String s) {
        mainListViewHolder.mBinding.text.setText(s);
    }

    public static class MainListViewHolder extends BaseViewHolder {
        private RecyclerItemviewMainListBinding mBinding;
        public MainListViewHolder(@NonNull RecyclerItemviewMainListBinding bind) {
            super(bind.getRoot());
            mBinding = bind;
        }
    }
}
