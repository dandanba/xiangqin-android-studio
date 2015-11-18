package com.xiangqin.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangqin.app.R;
import com.xiangqin.app.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dandanba on 11/16/15.
 */
public abstract class BaseAdapter<D extends BaseDataHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    public Context mContext;
    public final List<D> mDatas = new ArrayList<D>();
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public BaseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getType();
    }

    public void add(D data) {
        mDatas.add(data);
        notifyDataSetChanged();
    }

    public void addAll(List<D> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }


}
