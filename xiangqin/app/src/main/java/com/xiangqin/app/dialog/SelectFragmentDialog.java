package com.xiangqin.app.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiangqin.app.R;
import com.xiangqin.app.widget.WheelView;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectFragmentDialog extends AbSampleDialogFragment {
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.select)
    WheelView mWheel;

    private OnSelectChangedListener mListener;

    public interface OnSelectChangedListener {
        public void onSelectChanged(DialogFragment dialog, String text);
    }

    public static SelectFragmentDialog instance(String title, CharSequence[] sa) {
        final SelectFragmentDialog dialog = new SelectFragmentDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putCharSequenceArray("sa", sa);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStyle = DialogFragment.STYLE_NORMAL;
        mTheme = android.R.style.Theme_NoTitleBar;
        mGravity = Gravity.CENTER;
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_select, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String title = getArguments().getString("title");
        mTitle.setText(title);
        final String[] sa = getArguments().getStringArray("sa");
        mWheel.setItems(Arrays.asList(sa));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.confirm_button)
    public void onConfirmButtonClick(View view) {
        dismiss();

        if (mListener != null) {
            mListener.onSelectChanged(this, mWheel.getSeletedItem());
        }
    }

    @OnClick(R.id.cancel_button)
    public void onCancelButtonClick(View view) {
        dismiss();
    }


    public void setOnSelectChangedListener(OnSelectChangedListener listener) {
        mListener = listener;
    }
}