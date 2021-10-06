package com.calen;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.Calendar;
import java.util.Locale;

public class WPDateTime extends androidx.appcompat.widget.AppCompatTextView implements WPDateTimePicker.WpOnDateTimeChanged, DialogInterface.OnDismissListener {

    AlertDialog dialog;
    WPDateTimePicker wpDateTimePicker;
    WPDateTimePicker.WpOnDateTimeChanged onDateChanged;
    private boolean setTextOn = false;
    private boolean autoClose = true;

    public WPDateTime(Context context) {
        super(context);
        init();


    }

    public WPDateTime(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    void init() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        wpDateTimePicker = new WPDateTimePicker(getContext());
        builder.setView(wpDateTimePicker);
        wpDateTimePicker.setOnDateChanged(this);
        dialog = builder.create();
        dialog.setOnDismissListener(this);
        setTextOn = true;
        setText(wpDateTimePicker.getFullDate());

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });

    }
    public String getStringDate() {
        return wpDateTimePicker.getStringDate();
    }
    public String getLocalizeDate() {
        return wpDateTimePicker.getFullDate();
    }
    public void setAutoClose(boolean b) {
        autoClose = b;
    }
    public AlertDialog getDialog() {
        return dialog;
    }
    public void setDate(String date) {
        wpDateTimePicker.setCurrentDate(date);
        setTextOn = true;
        setText(wpDateTimePicker.getFullDate());

    }
    public void setTime(Integer hour, Integer minutes) {
        wpDateTimePicker.setCurrentTime(hour, minutes);
        setTextOn = true;
        setText(wpDateTimePicker.getFullDate());
    }
    public void setDate(Calendar date) {
        wpDateTimePicker.setCurrentDate(date);
        setTextOn = true;
        setText(wpDateTimePicker.getFullDate());
    }

    public void setDate(Integer day, Integer month, Integer year) {
        wpDateTimePicker.setCurrentDate(day, month, year);
        setTextOn = true;
        setText(wpDateTimePicker.getFullDate());
    }
    public void setOnDateChanged(WPDateTimePicker.WpOnDateTimeChanged onDateChanged) {
        this.onDateChanged = onDateChanged;
    }

    public String getTime() {
        return wpDateTimePicker.getStringTime();
    }


    public void setLocale(Locale locale) {
        wpDateTimePicker.setLocale(locale);
    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        if (setTextOn) {
            setTextOn = false;
            super.setText(text, type);
        }


    }

    @Override
    public void dateTimeChanged(WPDateTimePicker datePicker, String date, String time) {
        setTextOn = true;
        setText(datePicker.getFullDate());
        if (autoClose) {
            dialog.dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (wpDateTimePicker != null) {
            setTextOn = true;
            setText(wpDateTimePicker.getFullDate());

        }

    }
}
