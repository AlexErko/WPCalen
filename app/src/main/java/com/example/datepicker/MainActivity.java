package com.example.datepicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements WPDatePicker.WpOnDateChanged {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((WPDate) getWindow().getDecorView().getRootView().findViewById(R.id.Sada)).setOnDateChanged(this);


    }

    @Override
    public void dateChanged(WPDatePicker datePicker, Calendar date, String dateString) {
        Log.d("###", dateString);
    }
}