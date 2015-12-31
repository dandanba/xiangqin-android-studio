package com.xiangqin.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiangqin.app.R;
import com.xiangqin.app.adapter.ItemDivider;
import com.xiangqin.app.adapter.MyAdapter;
import com.xiangqin.app.adapter.OnRecyclerViewItemClickListener;
import com.xiangqin.app.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dandanba on 11/16/15.
 */
public class MyFragment extends BaseFragment implements OnRecyclerViewItemClickListener {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    MyAdapter mAdapter;
    User mUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUser = User.getUser(mBaseActivity);
        mAdapter = new MyAdapter(context, mUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        //创建布局管理器
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mBaseActivity);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new ItemDivider(mBaseActivity, R.drawable.item_divider));
        return view;
    }

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
