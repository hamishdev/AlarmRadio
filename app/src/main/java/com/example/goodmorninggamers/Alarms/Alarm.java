package com.example.goodmorninggamers.Alarms;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Entity
public class Alarm implements Serializable {

    public static final int TODAY = 0;
    public static final int TOMORROW = 1;
    public static final int ANYOTHER = 2;


    public int getId() {
        return id;
    }

    public int dayInRelationToToday;
    public Calendar today;

    public Calendar time;
    public ArrayList<RingtoneOption> ringtoneOptions;
    public boolean on;

    @PrimaryKey
    public int id;


    public Alarm(Calendar time, ArrayList<RingtoneOption> ringtoneOptions){
        on = true;
        this.time = time;
        this.ringtoneOptions = ringtoneOptions;
        today = Calendar.getInstance();
        SetDayInRelationToToday();
        id = (int) System.currentTimeMillis();
    }



    public ArrayList<RingtoneOption> getWakeupOptions(){
        return ringtoneOptions;
    }

    private void SetDayInRelationToToday() {
        //if date && year matches
        if (time.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) && time.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            dayInRelationToToday = TODAY;
        }
        //if year matches && day == next one
        else if (time.get(Calendar.YEAR) == today.get(Calendar.YEAR) && time.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 1) {
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
                return time.get(Calendar.DAY_OF_MONTH) + " " + time.get(Calendar.MONTH);
        }
        return "ERORR";
    }

    public Calendar getCalendarTime(){
        return time;
    }

    public String getTwelvehourclock() {
        return getDisplayHour() + ":" + getDisplayMinute() + (time.get(Calendar.AM_PM) == Calendar.AM ? "am" : "pm");
    }


    public String ToString() {
        String alarmTimeToString = new SimpleDateFormat("MM/dd/yyyy").format(time.getTime());
        return alarmTimeToString;
    }

    private String getDisplayMinute(){
        int minute = time.get(Calendar.MINUTE);
        if(String.valueOf(minute).length()==1){
            return "0"+String.valueOf(minute);
        }
        return String.valueOf(minute);
    }

    private String getDisplayHour(){
        int hour = time.get(Calendar.HOUR);
        if(hour==0){
            return "12";
        }
        else return String.valueOf(hour);

    }

    public void switchOff() {
        this.on = false;
    }
    public void switchOn(){
        this.on = true;
    }

    public void updateRingtoneOptions(ArrayList<RingtoneOption> m_wakeupRingtoneOptions) {
        this.ringtoneOptions=m_wakeupRingtoneOptions;
    }

    public void updateTime(Calendar m_clockTime) {
        this.time = m_clockTime;
    }
}
