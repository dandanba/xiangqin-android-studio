package com.xiangqin.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.xiangqin.app.R;
import com.xiangqin.app.XQApplication;
import com.xiangqin.app.activity.InfoActivity;
import com.xiangqin.app.activity.IntentGenerator;
import com.xiangqin.app.adapter.GridSpacingItemDecoration;
import com.xiangqin.app.adapter.ItemDivider;
import com.xiangqin.app.adapter.OnRecyclerViewItemClickListener;
import com.xiangqin.app.adapter.UserAdapter;
import com.xiangqin.app.adapter.UserDataHolder;
import com.xiangqin.app.model.User;
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
    AVQuery<User> mUserQuery;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    UserAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new UserAdapter(context, this);
        mUserQuery = AVQuery.getQuery(User.class);
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
        mUserQuery.whereEqualTo("sex", 2);
        mUserQuery.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e != null) {
                    ToastUtils.showToast(mBaseActivity, e.toString());
                    return;
                }

                final List<UserDataHolder> datas = new ArrayList<UserDataHolder>();
                final int size = list.size();
                UserDataHolder data;
                for (int i = 0; i < size; i++) {
                    data = new UserDataHolder(0);
                    data.setUser(list.get(i));
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
        final UserDataHolder data = mAdapter.mDatas.get(position);

        final User user = data.getUser();
        user.setIcon("http://ac-svu6vore.clouddn.com/sboEnFELsrDdSUMAJyZiRZPmV69J8Q1nv708rTID.png");
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                ToastUtils.showToast(mBaseActivity, e == null ? "okkk" : e.toString());
            }
        });
//        XQApplication.getInstance().getMessager().put("user", data.getUser());
        //startActivity(IntentGenerator.genSimpleActivityIntent(mBaseActivity, InfoActivity.class));
    }
}
