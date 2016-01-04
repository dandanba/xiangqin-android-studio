package com.xiangqin.app.fragment;

import android.app.Activity;
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
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.xiangqin.app.R;
import com.xiangqin.app.adapter.ItemDivider;
import com.xiangqin.app.adapter.MyAdapter;
import com.xiangqin.app.adapter.OnRecyclerViewItemClickListener;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.model.User;

import java.io.FileNotFoundException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by dandanba on 11/16/15.
 */
public class MyFragment extends BaseFragment implements OnRecyclerViewItemClickListener {
    private static final int REQUEST_IMAGE = 1000;
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
        mUser = User.getCurrentUser(User.class);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get the result list of select image paths
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // do your logic ....

                try {
                    final String filePath = path.get(0);
                    final String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
                    final AVFile file = AVFile.withAbsoluteLocalPath(filename, filePath);
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                mUser.setIcon(file.getUrl());
                                mUser.saveInBackground();
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }, new ProgressCallback() {
                        @Override
                        public void done(Integer percentDone) {
                            //打印进度
                            System.out.println("uploading: " + percentDone);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }


    @Override
    public void onEvent(Object event) {
        super.onEvent(event);

        if (event instanceof ActionEvent) {
            ActionEvent actionEvent = (ActionEvent) event;
            if ("header".equals(actionEvent.mAction)) {

                Intent intent = new Intent(mBaseActivity, MultiImageSelectorActivity.class);
                // whether show camera
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // max select image amount
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                startActivityForResult(intent, REQUEST_IMAGE);

            }
        }
    }
}
