package com.example.goodmorninggamers.PersistentData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.goodmorninggamers.Alarms.Alarm;

import java.util.List;

//@Dao
public interface AlarmDao {
    //@Query("SELECT * FROM alarm")
    List<Alarm> getAll();

    //@Query("SELECT * FROM alarm WHERE id IN (:alarmIDs)")
    //List<Alarm> loadAllByIds(int[] alarmids);



    //@Insert
    void insertAll(Alarm... alarms);

    //@Delete
    void delete(Alarm alarm);
}
