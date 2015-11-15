package com.xiangqin.app.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.xiangqin.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditFragmentDialog extends AbSampleDialogFragment {
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.edit)
    EditText mEdit;

    private OnEditChangedListener mListener;

    public interface OnEditChangedListener {
        public void onEditChanged(DialogFragment dialog, String text);
    }

    public static EditFragmentDialog instance(String title) {
        final EditFragmentDialog dialog = new EditFragmentDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
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
        final View view = inflater.inflate(R.layout.dialog_edit, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String title = getArguments().getString("title");
        mTitle.setText(title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.confirm_button)
    public void onConfirmButtonClick(View view) {
        final String text = mEdit.getText().toString();
        if (mListener != null) {
            mListener.onEditChanged(this, text);
        }
        dismiss();
    }

    @OnClick(R.id.cancel_button)
    public void onCancelButtonClick(View view) {
        dismiss();
    }


    public void setOnEditChangedListener(OnEditChangedListener listener) {
        mListener = listener;
    }
}