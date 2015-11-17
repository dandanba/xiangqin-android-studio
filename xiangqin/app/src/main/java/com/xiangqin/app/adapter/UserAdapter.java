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
public class UserAdapter extends RecyclerView.Adapter<BaseViewHolder> implements OnRecyclerViewItemClickListener {

    private Context mContext;
    private final List<UserDataHolder> mDatas = new ArrayList<UserDataHolder>();
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public UserAdapter(Context context, OnRecyclerViewItemClickListener onItemClickListener) {
        mContext = context;
        mOnItemClickListener = this;

        final User user = new User();
        user.setUsername("哈哈哈");
        user.setIcon("https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=3954132626,953163769&fm=96&s=F9930BD55C72519C79D5AC5A03001032");
        user.setEarning("2000-5000");
        user.setBirthday("23");
        user.setHeight("165");
        user.setArea("北京");

        UserDataHolder
                item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);

        item = new UserDataHolder(0);
        item.setUser(user);
        mDatas.add(item);


    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                v = View.inflate(mContext, R.layout.layout_user_item, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                viewHolder = new UserViewHolder(v, this);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final UserDataHolder datHolder = mDatas.get(position);
        final int itemViewType = datHolder.getType();
        switch (itemViewType) {
            case 0:
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.bind(mContext, datHolder, position);
                break;
            default:
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getType();
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    /**
     * Created by dandanba on 11/16/15.
     */
    public class UserDataHolder extends BaseDataHolder {
        public UserDataHolder(int type) {
            super(type);
        }

        private User user;


        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }


    public class UserViewHolder extends BaseViewHolder {
        @Bind(R.id.icon)
        SimpleDraweeView mIcon;
        @Bind(R.id.title)
        TextView mTitleText;
        @Bind(R.id.text)
        TextView mTextText;
        @Bind(R.id.info)
        TextView mInfoText;

        public UserViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context mContext, UserDataHolder datHolder, int position) {
            final User user = datHolder.getUser();
            mIcon.setImageURI(Uri.parse(user.getIcon()));
            mTitleText.setText(user.getUsername());
            mTextText.setText(String.format("%1$s %2$s", user.getBirthday(), user.getArea()));
            mInfoText.setText(String.format("%1$scm %2$s", user.getHeight(), user.getEarning()));
        }
    }
}
