package com.calen;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class WPRangePicker extends LinearLayout {

    private int mainColor = Color.parseColor("#fb8c00");
    private Locale locale;
    private Calendar startDate;
    private Calendar endDate;

    private Calendar showedDate;
    private WpOnRangeChanged listenerOnChange;

    private TextView dateTitle;
    private LinearLayout backPageBut;
    private TextView monthTitle;
    private LinearLayout nextPageBut;
    private LinearLayout doneButton;
    private TextView textViewDone;
    private ArrayList<TextView> weekDay = new ArrayList<>();
    private ArrayList<TextView> days = new ArrayList<>();


    private boolean secondClick = true;
    public WPRangePicker(Context context) {
        super(context);

        init();
    }

    public WPRangePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    void init() {
        Context context = getContext();
        locale = context.getResources().getConfiguration().locale;

        startDate = Calendar.getInstance(locale);
        endDate = Calendar.getInstance(locale);
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
            pa.topMargin = pxFromDp(context, 2);
            pa.bottomMargin = pxFromDp(context, 2);

            dayTitle.setLayoutParams(pa);
            dayTitle.setTextSize(pxFromDp(context, 6));
            dayTitle.setGravity(Gravity.CENTER);
            dayTitle.setTextColor(Color.BLACK);

            dayTitle.setText(i + "");
            daysLine.addView(dayTitle);
        }

        weekDay.get(5).setTypeface(Typeface.DEFAULT_BOLD);
        weekDay.get(6).setTypeface(Typeface.DEFAULT_BOLD);


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
            listenerOnChange.datesChanged(this, getStringStartDate(), getStringEndDate(), getFullDate());
        }

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

    public void setOnDateChanged(WpOnRangeChanged wp) {
        listenerOnChange = wp;
    }
    public String getStringMonth() {

        return "Сентябрь";
    }
    public String getStringStartDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", locale);

        return df.format(startDate.getTime());
    }
    public String getStringEndDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", locale);

        return df.format(endDate.getTime());
    }

    public String getFullDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", locale);

        if (df.format(startDate.getTime()).equals(df.format(endDate.getTime()))) {
            SimpleDateFormat df1 = new SimpleDateFormat("dd MMMM yyyy", locale);

            return df1.format(startDate.getTime());
        }else if (df.format(startDate.getTime()).split("-")[0].equals(df.format(endDate.getTime()).split("-")[0]) && df.format(startDate.getTime()).split("-")[1].equals(df.format(endDate.getTime()).split("-")[1])) {
            SimpleDateFormat df1 = new SimpleDateFormat("dd", locale);
            SimpleDateFormat df2 = new SimpleDateFormat("MMM yyyy", locale);
            String s1 = df1.format(startDate.getTime());
            String s2 = df1.format(endDate.getTime());
            String s3 = df2.format(endDate.getTime());
            return s1 + " - " + s2 + " " + s3;

        }else if (df.format(startDate.getTime()).split("-")[0].equals(df.format(endDate.getTime()).split("-")[0])) {
            SimpleDateFormat df1 = new SimpleDateFormat("dd MMM", locale);
            SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yyyy", locale);
            String s1 = df1.format(startDate.getTime());
            String s2 = df2.format(endDate.getTime());

            return s1 + " - " + s2;

        }else {
            SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy", locale);
            String s1 = df1.format(startDate.getTime());
            String s2 = df1.format(endDate.getTime());

            return s1 + " - " + s2;

        }
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
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", locale);

        dateTitle.setText(df.format(startDate.getTime()));
        df = new SimpleDateFormat("MMM", locale);
        monthTitle.setText(df.format(showedDate.getTime()));
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

        if (secondClick) {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd", locale);

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", locale);
            try {

                if (new Integer(df.format(dfs.parse(date))) > new Integer(df.format(startDate.getTime()))) {
                    endDate.set(Calendar.DAY_OF_MONTH, new Integer(date.split("-")[2]));
                    endDate.set(Calendar.YEAR, new Integer(date.split("-")[0]));
                    endDate.set(Calendar.MONTH, new Integer(date.split("-")[1]) - 1);
                    SimpleDateFormat dfa = new SimpleDateFormat("dd MMM yyyy", locale);
                    dateTitle.setText(df.format(startDate.getTime()));

                }else{
                    startDate.set(Calendar.DAY_OF_MONTH, new Integer(date.split("-")[2]));
                    startDate.set(Calendar.YEAR, new Integer(date.split("-")[0]));
                    startDate.set(Calendar.MONTH, new Integer(date.split("-")[1]) - 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }else{
            startDate.set(Calendar.DAY_OF_MONTH, new Integer(date.split("-")[2]));
            startDate.set(Calendar.YEAR, new Integer(date.split("-")[0]));
            startDate.set(Calendar.MONTH, new Integer(date.split("-")[1]) - 1);

            endDate.set(Calendar.DAY_OF_MONTH, new Integer(date.split("-")[2]));
            endDate.set(Calendar.YEAR, new Integer(date.split("-")[0]));
            endDate.set(Calendar.MONTH, new Integer(date.split("-")[1]) - 1);

        }
        dateTitle.setText(getFullDate());

        secondClick = !secondClick;
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

            String cDate;
            Integer startD = new Integer(getStringStartDate().replace("-", ""));
            Integer endD = new Integer(getStringEndDate().replace("-", ""));

            if (i >= firstDay && i < firstDay + currentMonth.size()) {
                cDate = currentMonth.get(i - firstDay);
                Integer currentD = new Integer(cDate.replace("-", ""));

                    days.get(i).setTextColor(Color.BLACK);


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
                    cDate = preMonth.get(preMonth.size() - firstDay + i);

                }else{
                    days.get(i).setText(i - currentMonth.size() - firstDay + 1 + "");
                    cDate = nextMonth.get(i - currentMonth.size() - firstDay);
                }
                days.get(i).setTextColor(Color.GRAY);
                days.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }



            if (getStringEndDate().equals(cDate)) {
                days.get(i).setBackground(getContext().getResources().getDrawable(R.drawable.end_date_bg));
                days.get(i).setTextColor(Color.WHITE);

                if (getStringEndDate().equals(getStringStartDate())){
                    days.get(i).setBackground(getContext().getResources().getDrawable(R.drawable.current_date_bg));
                    days.get(i).setTextColor(Color.WHITE);

                }
            }
            if (getStringStartDate().equals(cDate)) {
                days.get(i).setBackground(getContext().getResources().getDrawable(R.drawable.start_date_bg));
                days.get(i).setTextColor(Color.WHITE);

                if (getStringEndDate().equals(getStringStartDate())){
                    days.get(i).setBackground(getContext().getResources().getDrawable(R.drawable.current_date_bg));
                    days.get(i).setTextColor(Color.WHITE);

                }
            }



            Integer currentD = new Integer(cDate.replace("-", ""));

            if (currentD > startD && currentD < endD) {
                days.get(i).setBackgroundColor(Color.parseColor("#B3fb8c00"));
                days.get(i).setTextColor(Color.WHITE);
            }

            }



    }
    public void setEndDate(Calendar date) {
        endDate.setTime(date.getTime());
        showedDate.setTime(date.getTime());
        secondClick = true;
        updateDates();

    }
    public void setEndDate(Integer day, Integer month, Integer year) {
        endDate.set(Calendar.DAY_OF_MONTH, day);
        endDate.set(Calendar.YEAR, year);
        endDate.set(Calendar.MONTH, month);
        showedDate.setTime(endDate.getTime());
        updateDates();

    }
    public void setEndDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            endDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
            secondClick = true;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("yyyy MM dd");
        try {
            endDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
            secondClick = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            endDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
            secondClick = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("dd MM yyyy");
        try {
            endDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
            secondClick = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void setStartDate(Calendar date) {
        startDate.setTime(date.getTime());
        showedDate.setTime(date.getTime());
        secondClick = true;
        updateDates();

    }
    public void setStartDate(Integer day, Integer month, Integer year) {
        startDate.set(Calendar.DAY_OF_MONTH, day);
        startDate.set(Calendar.YEAR, year);
        startDate.set(Calendar.MONTH, month);
        showedDate.setTime(startDate.getTime());
        updateDates();

    }
    public void setStartDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            startDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
            secondClick = true;
            return;


        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("yyyy MM dd");
        try {
            startDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
            secondClick = true;
            return;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            startDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
            secondClick = true;
            return;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("dd MM yyyy");
        try {
            startDate.setTime(dateFormat.parse(date));
            showedDate.setTime(dateFormat.parse(date));
            updateDates();
            secondClick = true;
            return;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void showCurrentMonth() {

        showedDate.setTime(startDate.getTime());

        updateDates();
    }
    private void Logs(String s) {
        Log.d("###", s);
    }

    int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public interface WpOnRangeChanged{
        void datesChanged(WPRangePicker rangePicker, String startDate, String endDate, String fullDate);
    }
}
