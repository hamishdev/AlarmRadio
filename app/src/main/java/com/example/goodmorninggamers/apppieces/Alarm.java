package com.example.goodmorninggamers.apppieces;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Alarm {
    private static final int TODAY = 0;
    private static final int TOMORROW = 1;
    private static final int ANYOTHER = 2;
    private Calendar timeOfAlarm;
    private int dayInRelationToToday;
    private Calendar today;
    private int amPM;
    private String murl;

    public Alarm(long timeInMillis,String url) {
        timeOfAlarm = Calendar.getInstance();
        timeOfAlarm.setTimeInMillis(timeInMillis);
        today = Calendar.getInstance();
        SetAMPM();
        SetDayInRelationToToday();
        murl = url;


    }

    public void SetAMPM() {
        amPM = timeOfAlarm.get(Calendar.AM_PM);
    }

    private void SetDayInRelationToToday() {
        //if date && year matches
        if (timeOfAlarm.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) && timeOfAlarm.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            dayInRelationToToday = TODAY;
        }
        //if year matches && day == next one
        else if (timeOfAlarm.get(Calendar.YEAR) == today.get(Calendar.YEAR) && timeOfAlarm.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 1) {
            dayInRelationToToday = TOMORROW;
        }
        //NOT TODAY OR TOMORROW
        else {
            dayInRelationToToday = ANYOTHER;
        }

    }


    public String getDay() {
        switch (dayInRelationToToday) {
            case 0:
                return "Today";
            case 1:
                return "Tomorrow";
            case 2:
                return timeOfAlarm.get(Calendar.DAY_OF_MONTH) + " " + timeOfAlarm.get(Calendar.MONTH);
        }
        return "ERORR";
    }

    public String getURL(){
        return murl;
    }

    public String getTime() {
        return getDisplayHour() + ":" + getDisplayMinute() + (amPM == Calendar.AM ? "am" : "pm");
    }

    public int getmImageDrawable(){
        return 0;
    }

    public String ToString() {
        String alarmTimeToString = new SimpleDateFormat("MM/dd/yyyy").format(timeOfAlarm.getTime());
        return alarmTimeToString;
    }

    private String getDisplayMinute(){
        int minute = timeOfAlarm.get(Calendar.MINUTE);
        if(String.valueOf(minute).length()==1){
            return "0"+String.valueOf(minute);
        }
        return String.valueOf(minute);
    }

    private String getDisplayHour(){
        int hour = timeOfAlarm.get(Calendar.HOUR);
        if(hour==0){
            return "12";
        }
        else return String.valueOf(hour);

    }

}
