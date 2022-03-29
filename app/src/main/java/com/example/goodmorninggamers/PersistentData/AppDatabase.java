package com.example.goodmorninggamers.PersistentData;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.goodmorninggamers.Alarms.Alarm;

//@Database(entities = {Alarm.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDao alarmDao();
}
