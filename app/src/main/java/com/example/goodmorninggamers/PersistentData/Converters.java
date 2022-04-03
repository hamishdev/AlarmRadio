package com.example.goodmorninggamers.PersistentData;

import androidx.room.TypeConverter;

import com.example.goodmorninggamers.UI_Classes.RingtoneOption;

import java.util.Calendar;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return value == null ? null : calendar;
    }

    @TypeConverter
    public static Long calendarToTimestamp(Calendar calendar) {
        return calendar == null ? null : calendar.getTimeInMillis();
    }

    @TypeConverter
    public static RingtoneOption fromStrings(String[] strings){
        return new RingtoneOption(strings[0],strings[1],strings[2]);
    }

    @TypeConverter
    public static String[] RingtoneOptiontoStrings(RingtoneOption ringtoneOption) {
        return ringtoneOption == null ? null : new String[]{
                ringtoneOption.getLiveContentURL(),ringtoneOption.getRingtonePicture(),ringtoneOption.getName()
        };
    }

}
