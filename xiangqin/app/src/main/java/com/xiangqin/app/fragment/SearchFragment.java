package com.xiangqin.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.xiangqin.app.R;
import com.xiangqin.app.activity.IntentGenerator;
import com.xiangqin.app.activity.WebActivity;
import com.xiangqin.app.adapter.EventAdapter;
import com.xiangqin.app.adapter.EventDataHolder;
import com.xiangqin.app.adapter.ItemDivider;
import com.xiangqin.app.adapter.OnRecyclerViewItemClickListener;
import com.xiangqin.app.model.Event;
import com.xiangqin.app.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dandanba on 11/16/15.
 */
public class SearchFragment extends BaseFragment implements OnRecyclerViewItemClickListener {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    AVQuery<Event> mEventQuery;
    private String mTag = "广州";

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    EventAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new EventAdapter(context, this);
        mEventQuery = AVQuery.getQuery(Event.class);
        mEventQuery.whereEqualTo("areaTag", mTag);
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
        mEventQuery.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, AVException e) {
                if (e != null) {
                    ToastUtils.showToast(mBaseActivity, e.toString());
                    return;
                }

                final List<EventDataHolder> datas = new ArrayList<EventDataHolder>();
                final int size = list.size();
                EventDataHolder data;
                for (int i = 0; i < size; i++) {
                    data = new EventDataHolder(0);
                    data.setEvent(list.get(i));
                    datas.add(data);
                }
                mAdapter.addAll(datas);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        final EventDataHolder data = mAdapter.mDatas.get(position);
        final Event user = data.getEvent();
        final Intent intent = IntentGenerator.genSimpleActivityIntent(mBaseActivity, WebActivity.class);
        intent.putExtra("url", user.getUrl());
        startActivity(intent);
    }
}
