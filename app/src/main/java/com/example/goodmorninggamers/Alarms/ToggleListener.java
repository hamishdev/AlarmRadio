package com.example.goodmorninggamers.Alarms;

public interface ToggleListener {

    public void turnOffAlarm(Alarm alarm);

    void turnOnAlarm(Alarm alarm);

    void deleteAlarm(Alarm currentAlarm);

    void editAlarm(Alarm currentAlarm);
}
