package com.example.goodmorninggamers.Alarms;

import androidx.room.Entity;

import com.example.goodmorninggamers.UI_Classes.RingtoneOption;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Entity
public class Alarm implements Serializable {

    private static final int TODAY = 0;
    private static final int TOMORROW = 1;
    private static final int ANYOTHER = 2;
    private int dayInRelationToToday;
    private Calendar today;
    private int amPM;

    private Calendar m_time;
    private ArrayList<RingtoneOption> m_Ringtone_Options;


    public Alarm(Calendar time, ArrayList<RingtoneOption> ringtoneOptions){
        m_time = time;
        m_Ringtone_Options = ringtoneOptions;
        today = Calendar.getInstance();
        SetAMPM();
        SetDayInRelationToToday();
    }



    public ArrayList<RingtoneOption> getWakeupOptions(){
        return m_Ringtone_Options;
    }
    public void SetAMPM() {
        amPM = m_time.get(Calendar.AM_PM);
    }

    private void SetDayInRelationToToday() {
        //if date && year matches
        if (m_time.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) && m_time.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            dayInRelationToToday = TODAY;
        }
        //if year matches && day == next one
        else if (m_time.get(Calendar.YEAR) == today.get(Calendar.YEAR) && m_time.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 1) {
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
                return m_time.get(Calendar.DAY_OF_MONTH) + " " + m_time.get(Calendar.MONTH);
        }
        return "ERORR";
    }

    public Calendar getCalendarTime(){
        return m_time;
    }

    public String getTwelvehourclock() {
        return getDisplayHour() + ":" + getDisplayMinute() + (amPM == Calendar.AM ? "am" : "pm");
    }

    public int getmImageDrawable(){
        return 0;
    }

    public String ToString() {
        String alarmTimeToString = new SimpleDateFormat("MM/dd/yyyy").format(m_time.getTime());
        return alarmTimeToString;
    }

    private String getDisplayMinute(){
        int minute = m_time.get(Calendar.MINUTE);
        if(String.valueOf(minute).length()==1){
            return "0"+String.valueOf(minute);
        }
        return String.valueOf(minute);
    }

    private String getDisplayHour(){
        int hour = m_time.get(Calendar.HOUR);
        if(hour==0){
            return "12";
        }
        else return String.valueOf(hour);

    }

}
