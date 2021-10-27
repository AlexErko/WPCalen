package com.calen;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.Calendar;
import java.util.Locale;

public class WPDate extends androidx.appcompat.widget.AppCompatTextView implements WPDatePicker.WpOnDateChanged, DialogInterface.OnDismissListener {

    AlertDialog dialog;
    WPDatePicker wpDatePicker;
    private WPDatePicker.WpOnDateChanged onDateChanged;
    private boolean printFullDate = true;
    private boolean setTextOn = false;
    private boolean inClass = false;
    private boolean autoClose = true;

    public WPDate(Context context) {
        super(context);
        init();


    }

    public WPDate(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    void init() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        wpDatePicker = new WPDatePicker(getContext());

        wpDatePicker.setOnDateChanged(this);
        builder.setView(wpDatePicker);

        dialog = builder.create();
        dialog.setOnDismissListener(this);

        wpDatePicker.setCurrentDate(getText().toString());
        inClass = true;
        setTextOn = true;
        if (printFullDate) {
            setText(wpDatePicker.getFullDate());
        }else{
            setText(wpDatePicker.getStringDate());
        }
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });

    }

    @Override
    public void setText(CharSequence text, BufferType type) {


        if (inClass) {
            if (setTextOn && wpDatePicker == null) {
                setTextOn = false;
                inClass = false;
                return;
            }
            if (setTextOn) {
                wpDatePicker.setCurrentDate(text.toString());
                setTextOn = false;
                inClass = false;
                super.setText(text, type);
                return;
            }

        } else {
            if (wpDatePicker == null) {
                inClass = true;
                setTextOn = true;
                super.setText(text, type);
            } else {
                wpDatePicker.setCurrentDate(text.toString());
                inClass = true;
                setTextOn = true;
                if (printFullDate) {
                    setText(wpDatePicker.getFullDate());
                }else{
                    setText(wpDatePicker.getStringDate());
                }
            }


        }


    }
    public AlertDialog getDialog() {
        return dialog;
    }
    @Override
    public void dateChanged(WPDatePicker datePicker, Calendar date, String dateString) {


        if (onDateChanged != null) {
            onDateChanged.dateChanged(datePicker, date, dateString);
        }
        inClass = true;
        setTextOn = true;
        if (printFullDate) {
            setText(wpDatePicker.getFullDate());
        }else{
            setText(wpDatePicker.getStringDate());
        }
        if (autoClose) {
            dialog.dismiss();
        }
    }
    public void setDate(Integer day, Integer month, Integer year) {
        wpDatePicker.setCurrentDate(day, month, year);
        inClass = true;
        setTextOn = true;
        if (printFullDate) {
            setText(wpDatePicker.getFullDate());
        }else{
            setText(wpDatePicker.getStringDate());
        }
    }
    public String getStringDate() {
        return wpDatePicker.getStringDate();
    }
    public String getLocalizeDate() {
        return wpDatePicker.getFullDate();
    }
    public void setAutoClose(boolean b) {
        autoClose = b;
    }
    public void setDate(String date) {
        wpDatePicker.setCurrentDate(date);
        inClass = true;
        setTextOn = true;
        if (printFullDate) {
            setText(wpDatePicker.getFullDate());
        }else{
            setText(wpDatePicker.getStringDate());
        }
    }

    public void setDate(Calendar date) {
        wpDatePicker.setCurrentDate(date);
        inClass = true;
        setTextOn = true;
        if (printFullDate) {
            setText(wpDatePicker.getFullDate());
        }else{
            setText(wpDatePicker.getStringDate());
        }
    }

    public void setOnDateChanged(WPDatePicker.WpOnDateChanged onDateChanged) {
        this.onDateChanged = onDateChanged;
    }



    public void setLocale(Locale locale) {
        wpDatePicker.setLocale(locale);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (wpDatePicker != null) {
            inClass = true;
            setTextOn = true;
            if (printFullDate) {
                setText(wpDatePicker.getFullDate());
            }else{
                setText(wpDatePicker.getStringDate());
            }

        }


    }

    public void setPrintFullDate(boolean printFullDate) {
        this.printFullDate = printFullDate;
    }
}
