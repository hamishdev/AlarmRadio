package com.example.goodmorninggamers.Alarms;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Entity
public class Alarm implements Serializable {

    
    private int hour;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    private  int minute;
    public  int totalMinutes;


    public int getId() {
        return id;
    }

    public int dayInRelationToToday;
    public Calendar today;

    public ArrayList<RingtoneOption> ringtoneOptions;
    public boolean on;
    public boolean calendarAlarm=false;
    @PrimaryKey
    public int id;


    //by default repeats every day
    public Alarm(int hour24, int minute,ArrayList<Calendar> repeating , ArrayList<RingtoneOption> ringtoneOptions){
        on = true;
        this.hour = hour24;
        this.minute = minute;
        if(repeating==null){
            calendarAlarm=false;
        }
        this.totalMinutes = hour*60+ minute;
        this.ringtoneOptions = ringtoneOptions;
        today = Calendar.getInstance();
        id = (int) System.currentTimeMillis();
    }



    public ArrayList<RingtoneOption> getWakeupOptions(){
        return ringtoneOptions;
    }

    private String getDayofAlarminRelationtoNow() {
        if(!calendarAlarm){
            int currentTimeTotalMinutes = today.get(Calendar.HOUR_OF_DAY)*60+today.get(Calendar.MINUTE);
            if(currentTimeTotalMinutes<=totalMinutes){
                return "Today";
            }
            else{
                return "Tomorrow";
            }
        }
        return"Calendar date";


    }
    


    public String getTwelvehourclock() {
        return getDisplayHour() + ":" + getDisplayMinute() + (hour<12 ? "am" : "pm");
    }


    private String getDisplayMinute(){
        int displayMInute = minute;
        if(String.valueOf(displayMInute).length()==1){
            return "0"+String.valueOf(displayMInute);
        }
        return String.valueOf(displayMInute);
    }

    private String getDisplayHour(){
        int hour = this.hour;
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

    public void updateTime(int hour, int minute) {
        this.hour = hour;
        this.minute= minute;
    }
}
