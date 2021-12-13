package com.calen;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class WPRange extends androidx.appcompat.widget.AppCompatTextView implements WPRangePicker.WpOnRangeChanged, DialogInterface.OnDismissListener {

    AlertDialog dialog;
    WPRangePicker wpRangePicker;

    private WPRangePicker.WpOnRangeChanged onDateChanged;

    private boolean setTextOn = false;
    private boolean autoClose = true;

    public WPRange(Context context) {
        super(context);
        init();


    }

    public WPRange(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        init();
    }
    public WPRangePicker getWpRangePicker(){return wpRangePicker;}
    void init() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        wpRangePicker = new WPRangePicker(getContext());
        wpRangePicker.setOnDateChanged(this);
        builder.setView(wpRangePicker);

        dialog = builder.create();

        dialog.setOnDismissListener(this);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });

        setTextOn = true;
        setText(wpRangePicker.getFullDate());
    }
    public String getStartDate() {
        return wpRangePicker.getStringStartDate();
    }
    public String getEndDate() {
        return wpRangePicker.getStringEndDate();
    }
    public String getLocalizeDate() {
        return wpRangePicker.getFullDate();
    }
    public void setAutoClose(boolean b) {
        autoClose = b;
    }


    public void setStartDate(String date) {
        wpRangePicker.setStartDate(date);
        setTextOn = true;
        setText(wpRangePicker.getFullDate());

    }

    public void setStartDate(Calendar date) {
        wpRangePicker.setStartDate(date);
        setTextOn = true;
        setText(wpRangePicker.getFullDate());
    }
    public void setStartDate(Integer day, Integer month, Integer year) {
        wpRangePicker.setStartDate(day, month, year);
        setTextOn = true;
        setText(wpRangePicker.getFullDate());
    }
    public void setEndDate(String date) {
        wpRangePicker.setEndDate(date);
        setTextOn = true;
        setText(wpRangePicker.getFullDate());

    }
    public AlertDialog getDialog() {
        return dialog;
    }
    public void setEndDate(Integer day, Integer month, Integer year) {
        wpRangePicker.setEndDate(day, month, year);
        setTextOn = true;
        setText(wpRangePicker.getFullDate());
    }
    public void setEndDate(Calendar date) {
        wpRangePicker.setEndDate(date);
        setTextOn = true;
        setText(wpRangePicker.getFullDate());
    }
    public void setOnDateChanged(WPRangePicker.WpOnRangeChanged onDateChanged) {
        this.onDateChanged = onDateChanged;
    }

    @Override
    public void datesChanged(WPRangePicker rangePicker, String startDate, String endDate, String fullDate) {
        if (this.onDateChanged != null) {
            this.onDateChanged.datesChanged(rangePicker, startDate, endDate, fullDate);
        }

        setTextOn = true;
        setText(fullDate);
        if (autoClose) {
            dialog.dismiss();

        }
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if (setTextOn) {
            setTextOn = false;
            super.setText(text, type);
        }


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (wpRangePicker != null) {
            setTextOn = true;
            setText(wpRangePicker.getFullDate());

        }

    }
}
