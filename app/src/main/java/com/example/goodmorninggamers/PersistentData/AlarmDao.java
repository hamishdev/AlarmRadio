package com.example.goodmorninggamers.PersistentData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.goodmorninggamers.Alarms.Alarm;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AlarmDao {
    @Query("SELECT * FROM alarm")
    List<Alarm> getAll();


    @Insert
    void insertAll(Alarm... alarms);

    @Delete
    void delete(Alarm alarm);

    @Update
    void updateAlarms(ArrayList<Alarm> alarms);
}
