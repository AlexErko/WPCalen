package com.calen;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class WPDateTimePicker extends LinearLayout implements NumberPicker.OnValueChangeListener {

    private int mainColor = Color.parseColor("#fb8c00");
    private Locale locale;
    private Calendar currentDate;
    private String currentHour;
    private String currentMinute;

    private Calendar showedDate;
    private WPDateTimePicker.WpOnDateTimeChanged listenerOnChange;
    private String[] hours = new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private String[] minutes = new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",  "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    private TextView dateTitle;
    private LinearLayout backPageBut;
    private TextView monthTitle;
    private LinearLayout nextPageBut;
    private LinearLayout doneButton;
    private TextView textViewDone;
    NumberPicker pickerHours;
    NumberPicker pickerMinutes;

    private ArrayList<TextView> weekDay = new ArrayList<>();
    private ArrayList<TextView> days = new ArrayList<>();

    public WPDateTimePicker(Context context) {
        super(context);

        init();
    }

    public WPDateTimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    void init() {
        Context context = getContext();
        locale = context.getResources().getConfiguration().locale;
        currentMinute = "00";
        currentHour = "00";
        currentDate = Calendar.getInstance(locale);
        showedDate = Calendar.getInstance(locale);


        setOrientation(VERTICAL);
        setBackgroundColor(Color.WHITE);
        LinearLayout header = new LinearLayout(context); // Top section with selected date
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxFromDp(context, 60)));
        header.setBackgroundColor(Color.WHITE);

        dateTitle = new TextView(context); // TextView with selected date
        dateTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dateTitle.setTypeface(dateTitle.getTypeface(), Typeface.BOLD);
        dateTitle.setTextSize(pxFromDp(context, 9));
        dateTitle.setGravity(Gravity.CENTER);
        dateTitle.setTextColor(Color.BLACK);
        header.addView(dateTitle);
        addView(header);

        LinearLayout line = new LinearLayout(context); // Line between top section and body
        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxFromDp(context, 1)));
        line.setBackgroundColor(Color.GRAY);
        addView(line);


        LinearLayout monthSection = new LinearLayout(context); // Months section with selected month and switch them  buttons
        LinearLayout.LayoutParams monthParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxFromDp(context, 40));
        monthParam.topMargin = pxFromDp(context, 5);
        monthParam.bottomMargin = pxFromDp(context, 5);
        monthSection.setLayoutParams(monthParam);
        monthSection.setBackgroundColor(Color.WHITE);
        monthSection.setOrientation(HORIZONTAL);
        addView(monthSection);

        backPageBut = new LinearLayout(context);
        LinearLayout.LayoutParams backParamBut = new LinearLayout.LayoutParams(pxFromDp(context, 40), ViewGroup.LayoutParams.MATCH_PARENT);
        backParamBut.setMargins(pxFromDp(context, 30), 0, 0, 0);
        backPageBut.setLayoutParams(backParamBut);


        ImageView backPageImage = new ImageView(context);
        LinearLayout.LayoutParams backParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        backParam.setMargins(pxFromDp(context, 5), pxFromDp(context, 5), pxFromDp(context, 5), pxFromDp(context, 5));
        backPageImage.setLayoutParams(backParam);
        backPageImage.setBackgroundResource(R.drawable.image_next_page);
        backPageImage.setRotation(180);
        backPageBut.addView(backPageImage);
        monthSection.addView(backPageBut);

        monthTitle = new TextView(context);
        monthTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        monthTitle.setTextSize(pxFromDp(context, 7));
        monthTitle.setGravity(Gravity.CENTER);
        monthTitle.setTextColor(Color.BLACK);

        monthSection.addView(monthTitle);

        nextPageBut = new LinearLayout(context);
        LinearLayout.LayoutParams nextParamBut = new LinearLayout.LayoutParams(pxFromDp(context, 40), ViewGroup.LayoutParams.MATCH_PARENT);
        nextParamBut.setMargins(0, 0, pxFromDp(context, 30), 0);
        nextPageBut.setLayoutParams(nextParamBut);



        ImageView nextPageImage = new ImageView(context);
        LinearLayout.LayoutParams nextParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        nextParam.setMargins(pxFromDp(context, 5), pxFromDp(context, 5), pxFromDp(context, 5), pxFromDp(context, 5));
        nextPageImage.setLayoutParams(nextParam);
        nextPageImage.setBackgroundResource(R.drawable.image_next_page);
        nextPageBut.addView(nextPageImage);
        monthSection.addView(nextPageBut);




        LinearLayout weekSection = new LinearLayout(context);
        weekSection.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams weekParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxFromDp(context, 30));
        weekParam.bottomMargin = pxFromDp(context, 10);
        weekParam.leftMargin = pxFromDp(context, 10);
        weekParam.rightMargin = pxFromDp(context, 10);
        weekSection.setLayoutParams(weekParam);
        weekSection.setBackgroundColor(Color.WHITE);
        addView(weekSection);

        weekDay.clear();
        for (int i = 0; i < 7; i++) {
            TextView weekDayTitle = new TextView(context);
            weekDayTitle.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            weekDayTitle.setTextSize(pxFromDp(context, 6));
            weekDayTitle.setGravity(Gravity.CENTER);
            weekDayTitle.setTextColor(Color.BLACK);

            weekDay.add(weekDayTitle);
            weekSection.addView(weekDayTitle);
        }

        LinearLayout daysLine = null;
        for (int i = 0; i < 42; i++) {
            if (i % 7 == 0) {
                daysLine = new LinearLayout(context);
                LinearLayout.LayoutParams par = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxFromDp(context, 35));
                par.rightMargin = pxFromDp(context, 10);
                par.leftMargin = pxFromDp(context, 10);
                daysLine.setLayoutParams(par);
                addView(daysLine);
            }


            TextView dayTitle = new TextView(context);

            days.add(dayTitle);
            LinearLayout.LayoutParams pa = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            pa.leftMargin = pxFromDp(context, 2);
            pa.rightMargin = pxFromDp(context, 2);

            dayTitle.setLayoutParams(pa);
            dayTitle.setTextSize(pxFromDp(context, 6));
            dayTitle.setGravity(Gravity.CENTER);
            dayTitle.setTextColor(Color.BLACK);

            dayTitle.setText(i + "");
            daysLine.addView(dayTitle);
        }

        weekDay.get(5).setTypeface(Typeface.DEFAULT_BOLD);
        weekDay.get(6).setTypeface(Typeface.DEFAULT_BOLD);




        LinearLayout timeLinear = new LinearLayout(context);
        LinearLayout.LayoutParams paras = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxFromDp(context, 50));
        paras.topMargin = pxFromDp(context, 10);
        timeLinear.setLayoutParams(paras);
        timeLinear.setOrientation(HORIZONTAL);
        addView(timeLinear);

        LinearLayout space = new LinearLayout(context);
        space.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        timeLinear.addView(space);

        LinearLayout backPickers = new LinearLayout(context);
        backPickers.setLayoutParams(new LayoutParams(pxFromDp(context, 140), ViewGroup.LayoutParams.MATCH_PARENT));
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(pxFromDp(getContext(), 10));
        shape.setColor(Color.parseColor("#ededed"));
        backPickers.setBackground(shape);
        timeLinear.addView(backPickers);

        pickerHours = new NumberPicker(context);
        LinearLayout.LayoutParams pars = new LinearLayout.LayoutParams(pxFromDp(context, 50), ViewGroup.LayoutParams.MATCH_PARENT);
        pars.leftMargin = pxFromDp(context, 10);
        pickerHours.setLayoutParams(pars);
        pickerHours.setOrientation(VERTICAL);
        pickerHours.setMaxValue(23);
        pickerHours.setMinValue(0);
        pickerHours.setScaleY(2.5f);
        pickerHours.setScaleX(2.5f);
        pickerHours.setOnValueChangedListener(this);

        pickerHours.setDisplayedValues(hours);

        backPickers.addView(pickerHours);

        TextView twoDots = new TextView(context);
        twoDots.setText(":");
        twoDots.setTextColor(Color.BLACK);
        twoDots.setTypeface(twoDots.getTypeface(), Typeface.BOLD);
        twoDots.setTextSize(pxFromDp(context, 13));
        twoDots.setLayoutParams(new LayoutParams(pxFromDp(context, 20), ViewGroup.LayoutParams.MATCH_PARENT));
        twoDots.setGravity(Gravity.CENTER);
        backPickers.addView(twoDots);

        pickerMinutes = new NumberPicker(context);
        LinearLayout.LayoutParams pars2 = new LinearLayout.LayoutParams(pxFromDp(context, 50), ViewGroup.LayoutParams.MATCH_PARENT);
        pars2.rightMargin = pxFromDp(context, 10);
        pickerMinutes.setLayoutParams(pars2);
        pickerMinutes.setOrientation(VERTICAL);
        pickerMinutes.setMaxValue(59);
        pickerMinutes.setMinValue(0);
        pickerMinutes.setScaleY(2.5f);
        pickerMinutes.setScaleX(2.5f);
        pickerMinutes.setOnValueChangedListener(this);
        pickerMinutes.setDisplayedValues(minutes);
        backPickers.addView(pickerMinutes);


        LinearLayout space1 = new LinearLayout(context);
        space1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        timeLinear.addView(space1);

        doneButton = new LinearLayout(context);
        LinearLayout.LayoutParams doneParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxFromDp(context, 60));
        doneParam.topMargin = pxFromDp(context, 10);
        doneButton.setLayoutParams(doneParam);
        doneButton.setBackgroundColor(mainColor);

        addView(doneButton);

        textViewDone = new TextView(context);
        textViewDone.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textViewDone.setTypeface(textViewDone.getTypeface(), Typeface.BOLD);
        textViewDone.setTextSize(pxFromDp(context, 9));
        textViewDone.setGravity(Gravity.CENTER);
        textViewDone.setTextColor(Color.WHITE);
        doneButton.addView(textViewDone);


        setLocale(locale);
        updateDates();
        configButtons();


    }

    void onDoneClicked() {
        if (listenerOnChange != null) {
            listenerOnChange.dateTimeChanged(this, getStringDate(), getStringTime());
        }

    }
    public String getStringTime() {
        return currentHour + ":" + currentMinute;
    }
    void configButtons() {

        doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoneClicked();
            }
        });
        backPageBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showedDate.add(Calendar.MONTH, -1);
                SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy", locale);
                monthTitle.setText(df.format(showedDate.getTime()));

                updateDates();
            }
        });

        nextPageBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showedDate.add(Calendar.MONTH, 1);

                SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy", locale);
                monthTitle.setText(df.format(showedDate.getTime()));

                updateDates();

            }
        });
    }

    public void setOnDateChanged(WPDateTimePicker.WpOnDateTimeChanged wp) {
        listenerOnChange = wp;
    }
    public String getStringMonth(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy", locale);

        return df.format(currentDate.getTime());
    }
    public String getStringDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", locale);

        return df.format(currentDate.getTime());
    }
    public String getFullDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", locale);

        return df.format(currentDate.getTime()) + " " + currentHour + ":" + currentMinute;
    }
    int getFirstDayOfFirstWeek(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int returnedInt = 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            returnedInt = 6;
        }else{
            returnedInt = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        }
        return returnedInt;
    }
    public void setLocale(Locale localeForSet) {
        locale = localeForSet;

        dateTitle.setText(getFullDate());
        monthTitle.setText(getStringMonth(showedDate));
        if (locale.getLanguage().equals("uk")) {
            textViewDone.setText("Готово");
        }else if (locale.getLanguage().equals("en")) {
            textViewDone.setText("Done");
        }else{
            textViewDone.setText("Готово");
        }

        configWeeksDay();
    }

    void configWeeksDay() {
        if (locale.getLanguage().equals("uk")) {
            weekDay.get(0).setText("Пн");
            weekDay.get(1).setText("Вт");
            weekDay.get(2).setText("Ср");
            weekDay.get(3).setText("Чт");
            weekDay.get(4).setText("Пт");
            weekDay.get(5).setText("Сб");
            weekDay.get(6).setText("Вс");

        }else if (locale.getLanguage().equals("en")) {
            weekDay.get(0).setText("Mon");
            weekDay.get(1).setText("Tue");
            weekDay.get(2).setText("Wed");
            weekDay.get(3).setText("Thu");
            weekDay.get(4).setText("Fri");
            weekDay.get(5).setText("Sat");
            weekDay.get(6).setText("Sun");

        }else{
            weekDay.get(0).setText("Пн");
            weekDay.get(1).setText("Вт");
            weekDay.get(2).setText("Ср");
            weekDay.get(3).setText("Чт");
            weekDay.get(4).setText("Пт");
            weekDay.get(5).setText("Сб");
            weekDay.get(6).setText("Вс");

        }
    }
    void dateClicked(String date) {
        currentDate.set(Calendar.DAY_OF_MONTH, new Integer(date.split("-")[2]));
        currentDate.set(Calendar.YEAR, new Integer(date.split("-")[0]));
        currentDate.set(Calendar.MONTH, new Integer(date.split("-")[1]) - 1);

        dateTitle.setText(getFullDate());
        updateDates();

    }

    ArrayList<String> getDatesInMonth(Calendar dateOfMonth) {
        ArrayList<String> returnedArray = new ArrayList();

        Calendar cal = dateOfMonth;
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", locale);
        for (int i = 1; i <= maxDay; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i);
            String dat = df.format(cal.getTime());
            returnedArray.add(dat);
        }




        return returnedArray;
    }
    void updateDates() {
        ArrayList<String> preMonth = new ArrayList();
        ArrayList<String> currentMonth = new ArrayList();
        ArrayList<String> nextMonth = new ArrayList();


        int firstDay = getFirstDayOfFirstWeek(showedDate);
        if (firstDay == 0 && showedDate.getActualMaximum(Calendar.DAY_OF_MONTH) == 28) {
            firstDay += 7;
        }
        showedDate.add(Calendar.MONTH, -1);
        preMonth = getDatesInMonth(showedDate);


        showedDate.add(Calendar.MONTH, 2);
        nextMonth = getDatesInMonth(showedDate);

        showedDate.add(Calendar.MONTH, -1);
        currentMonth = getDatesInMonth(showedDate);



        for (int i = 0; i < days.size(); i++) {
            days.get(i).setBackgroundColor(Color.WHITE);

            if (i >= firstDay && i < firstDay + currentMonth.size()) {
                String cDate = currentMonth.get(i - firstDay);

                if (getStringDate().equals(cDate)) {
                    GradientDrawable shape =  new GradientDrawable();
                    shape.setCornerRadius(pxFromDp(getContext(), 10));
                    shape.setColor(mainColor);
                    days.get(i).setBackground(shape);
                    days.get(i).setTextColor(Color.WHITE);

                }else{

                    days.get(i).setTextColor(Color.BLACK);

                }

                if (cDate.split("-")[2].charAt(0) == '0') {
                    String da = cDate.split("-")[2];
                    da = da.replace("0","");

                    days.get(i).setText(da);

                }else{
                    days.get(i).setText(currentMonth.get(i- firstDay).split("-")[2]);

                }
                days.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateClicked(cDate);
                    }
                });

            }else{

                if (i < firstDay) {
                    days.get(i).setText(preMonth.get(preMonth.size() - firstDay + i).split("-")[2]);

                }else{
                    days.get(i).setText(i - currentMonth.size() - firstDay + 1 + "");

                }
                days.get(i).setTextColor(Color.GRAY);
                days.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }

        }



    }
    public void setCurrentTime(Integer hour, Integer minutes) {
        if (hour >= 0 && hour <= 23) {
            if (hour < 10) {
                currentHour = "0" + hour;
            }else{
                currentHour = "" + hour;

            }
        }else{
            currentHour = "00";
        }

        if (minutes >= 0 && minutes <= 59) {
            if (minutes < 10) {
                currentMinute = "0" + minutes;
            }else{
                currentMinute = "" + minutes;

            }
        }else{
            currentMinute = "00";
        }
        configPickers();
    }
    private void configPickers() {
        for (int i = 0; i < minutes.length; i++) {
            if (minutes[i].equals(currentMinute)) {
                pickerMinutes.setValue(i);

            }
        }
        for (int i = 0; i < hours.length; i++) {
            if (hours[i].equals(currentHour)) {
                pickerHours.setValue(i);
            }
        }
    }
    public void setCurrentDate(Integer day, Integer month, Integer year) {
        currentDate.set(Calendar.DAY_OF_MONTH, day);
        currentDate.set(Calendar.YEAR, year);
        currentDate.set(Calendar.MONTH, month);
        showedDate.setTime(currentDate.getTime());
        updateDates();

    }
    public void setCurrentDate(Calendar date) {
        currentDate.setTime(date.getTime());
        showedDate.setTime(date.getTime());
        updateDates();

    }
    public void setCurrentDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            currentDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("yyyy MM dd");
        try {
            currentDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            currentDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("dd MM yyyy");
        try {
            currentDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void showCurrentMonth() {

        showedDate.setTime(currentDate.getTime());

        updateDates();
    }
    private void Logs(String s) {
        Log.d("###", s);
    }

    int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
        if (picker.equals(pickerHours)) {

            currentHour = hours[newVal];
        }else{
            currentMinute = minutes[newVal];

        }
        dateTitle.setText(getFullDate());

    }

    public interface WpOnDateTimeChanged{
        void dateTimeChanged(WPDateTimePicker datePicker, String date, String time);
    }


    public class NumberPicker extends android.widget.NumberPicker {
        public NumberPicker(Context context) {
            super(context);
        }
        public NumberPicker(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {

            Logs(l + " l");
            Logs(oldl + " oldl");
            Logs(t + " t");
            Logs(oldt + " oldt");

            super.onScrollChanged(l, t, oldl, oldt);
        }

        @Override
        public void addView(View child) {
            super.addView(child);
            updateView(child);
        }

        @Override
        public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
            super.addView(child, index, params);
            updateView(child);
        }

        @Override
        public void addView(View child, android.view.ViewGroup.LayoutParams params) {
            super.addView(child, params);
            updateView(child);
        }

        private void updateView(View view) {

            if(view instanceof EditText){
                EditText editText = ((EditText) view);
                editText.setFocusable(false);
                editText.setTypeface(Typeface.DEFAULT_BOLD);
                editText.setTextColor(Color.BLACK);
            }
        }
        int pxFromDp(final Context context, final float dp) {
            return (int) (dp * context.getResources().getDisplayMetrics().density);
        }
    }
}
