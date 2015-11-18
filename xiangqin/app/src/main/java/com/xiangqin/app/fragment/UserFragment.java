package com.xiangqin.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.xiangqin.app.R;
import com.xiangqin.app.XQApplication;
import com.xiangqin.app.activity.IntentGenerator;
import com.xiangqin.app.adapter.InfoAdapter;
import com.xiangqin.app.adapter.ItemDivider;
import com.xiangqin.app.adapter.OnRecyclerViewItemClickListener;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.model.User;
import com.xiangqin.app.utils.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by dandanba on 11/16/15.
 */
public class UserFragment extends BaseFragment implements OnRecyclerViewItemClickListener {
    private static final int REQUEST_IMAGE = 1;
    private static final String TAG = UserFragment.class.getSimpleName();
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    User mUser;

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    InfoAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUser = (User) XQApplication.getInstance().getMessager().get("user");
        mAdapter = new InfoAdapter(context, mUser, this);
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
                final File file = new File(path.get(0));
                byte[] bs = FileUtils.readFile(file);
                final AVFile avFile = new AVFile(file.getName(), bs);
                avFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            final String fileUrl = avFile.getUrl();
                            mUser.setIcon(fileUrl);
                            mUser.saveInBackground();
                            Log.i(TAG, fileUrl);
                        }
                    }
                });


            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onEvent(Object event) {
        super.onEvent(event);
        final ActionEvent actionEvent = (ActionEvent) event;
        if (actionEvent.mAction.equals("onIconClick")) {
            final Intent intent = IntentGenerator.showSelector(mBaseActivity, 1);
            startActivityForResult(intent, REQUEST_IMAGE);
        }
    }


}
