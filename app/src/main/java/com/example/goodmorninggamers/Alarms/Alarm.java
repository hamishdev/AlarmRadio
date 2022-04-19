package com.example.goodmorninggamers.Alarms;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

@Entity
public class Alarm implements Serializable, Comparable {

    
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
    private  int totalMinutes;
    public int TODAY=0;
    public int TOMORROW=1;

    public int getId() {
        return id;
    }


    public ArrayList<RingtoneOption> ringtoneOptions;
    public boolean on;
    public boolean repeating=false;
    @PrimaryKey
    public int id;


    //by default repeats every day
    public Alarm(int hour, int minute, ArrayList<RingtoneOption> ringtoneOptions){
        on = true;
        this.hour = hour;
        this.minute = minute;
        this.totalMinutes = hour*60+ minute;
        this.ringtoneOptions = ringtoneOptions;
        id = (int) System.currentTimeMillis();
    }



    public ArrayList<RingtoneOption> getWakeupOptions(){
        return ringtoneOptions;
    }


    public int getDayOfAlarminRelationtoNow(){
        Calendar rightNowCalendar = Calendar.getInstance();

        int currentTimeTotalMinutes = rightNowCalendar.get(Calendar.HOUR_OF_DAY)*60+rightNowCalendar.get(Calendar.MINUTE);
            if(currentTimeTotalMinutes>=totalMinutes){
                return TODAY;
            }
            else{
                return TOMORROW;
            }
    }
    public String getDayofAlarminRelationtoNow() {
            Calendar rightNowCalendar = Calendar.getInstance();
            int currentTimeTotalMinutes = rightNowCalendar.get(Calendar.HOUR_OF_DAY)*60+rightNowCalendar.get(Calendar.MINUTE);
            if(currentTimeTotalMinutes>=totalMinutes){
                return "Tomorrow";
            }
            else{
                return "Today";
            }


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


    @Override
    public int compareTo(Object o) {
        Alarm toCompare = (Alarm) o;
        if(this.totalMinutes<toCompare.totalMinutes){
            return -1;
        }
        else{
            return 1;
        }
    }
}

