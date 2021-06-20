package com.kirimchiqim;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class DatePicker {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    private String dateStamp = "";

    /*public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }
*/
    private String btnText;
    Context context;

    public DatePicker(Context myContext, Button dateBtn) {
        context = myContext;
        initDatePicker();
        dateButton = dateBtn; //  findViewById(R.id.datePickerButton);
        dateButton.setText(context.getResources().getString(R.string.enter_date_and_time));
        //setDateStamp("");

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                setDateStamp(year + "-" + String.format("%02d", month)  + "-" + String.format("%02d", day));
                System.out.println("\n\n\ninitDatePicker and setDateStamp: " + getDateStamp() + " " + year + "-" + month + "-" + day);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
//context
        datePickerDialog = new DatePickerDialog(context, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        //dateButton.setText(datePickerDialog.toString());
        //setBtnText(datePickerDialog.toString());


    }


    public String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return context.getResources().getString(R.string.JAN);
        if (month == 2)
            return context.getResources().getString(R.string.FEB);
        if (month == 3)
            return context.getResources().getString(R.string.MAR);
        if (month == 4)
            return context.getResources().getString(R.string.APR);
        if (month == 5)
            return context.getResources().getString(R.string.MAY);
        if (month == 6)
            return context.getResources().getString(R.string.JUN);
        if (month == 7)
            return context.getResources().getString(R.string.JUL);
        if (month == 8)
            return context.getResources().getString(R.string.AUG);
        if (month == 9)
            return context.getResources().getString(R.string.SEP);
        if (month == 10)
            return context.getResources().getString(R.string.OCT);
        if (month == 11)
            return context.getResources().getString(R.string.NOV);
        if (month == 12)
            return context.getResources().getString(R.string.DEC);

        //default should never happen
        return context.getResources().getString(R.string.JAN);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

}
