package my.app.goodmorninggamers.PersistentData;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import my.app.goodmorninggamers.Alarms.Alarm;

@Database(entities = {Alarm.class},version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDao alarmDao();
}
