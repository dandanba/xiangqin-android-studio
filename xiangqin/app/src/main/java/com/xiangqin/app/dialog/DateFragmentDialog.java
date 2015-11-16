package com.xiangqin.app.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.xiangqin.app.R;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DateFragmentDialog extends AbSampleDialogFragment implements DatePicker.OnDateChangedListener {
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.date_picker)
    DatePicker mDatePicker;

    private OnDateChangedListener mListener;

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // mListener.onDateChanged(this, String.format("%1$d年%2$d月%3$d日", year + 1, monthOfYear + 1, dayOfMonth + 1));
    }

    public interface OnDateChangedListener {
        public void onDateChanged(DialogFragment dialog, String year_month_day);
    }

    public static DateFragmentDialog instance(String title) {
        final DateFragmentDialog dialog = new DateFragmentDialog();
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
        final View view = inflater.inflate(R.layout.dialog_wheel, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String title = getArguments().getString("title");
        mTitle.setText(title);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth, this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.confirm_button)
    public void onConfirmButtonClick(View view) {
        dismiss();
        int year = mDatePicker.getYear();
        int monthOfYear = mDatePicker.getMonth();
        int dayOfMonth = mDatePicker.getDayOfMonth();
        if (mListener != null) {
            mListener.onDateChanged(this, String.format("%1$d年%2$d月%3$d日", year, monthOfYear + 1, dayOfMonth));
        }

    }

    @OnClick(R.id.cancel_button)
    public void onCancelButtonClick(View view) {
        dismiss();
    }


    public void setOnDateChangedListener(OnDateChangedListener listener) {
        mListener = listener;
    }
}