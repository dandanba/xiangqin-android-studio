package com.xiangqin.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.relex.circleindicator.CircleIndicator;
import com.xiangqin.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rharter on 11/11/13.
 */
public class ImagePagerFragment extends Fragment {
    ViewPager mPager;
    ImagePagerAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new ImagePagerAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_imagepager, container, false);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager.setAdapter(mAdapter);

        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
    }

    public static class ImagePagerAdapter extends PagerAdapter {
        private static class PageInfo {
            int resId;

            PageInfo(int resId) {
                this.resId = resId;
            }
        }

        static final List<PageInfo> PAGES = new ArrayList<PageInfo>();

        static {
            PAGES.add(new PageInfo(R.mipmap.splash_1));
            PAGES.add(new PageInfo(R.mipmap.splash_2));
            PAGES.add(new PageInfo(R.mipmap.splash_3));
            PAGES.add(new PageInfo(R.mipmap.splash_4));
        }

        Context mContext;
        LayoutInflater mLayoutInflater;

        public ImagePagerAdapter(Context context) {
            super();
            mContext = context;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return PAGES.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = mLayoutInflater.inflate(R.layout.page, container, false);
            final PageInfo info = PAGES.get(position);
            final View page = v.findViewById(R.id.container);
            final ImageView image = (ImageView) page.findViewById(R.id.image);
            image.setImageResource(info.resId);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

}
