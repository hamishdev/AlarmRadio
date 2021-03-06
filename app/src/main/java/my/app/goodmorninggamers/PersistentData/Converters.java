package my.app.goodmorninggamers.PersistentData;

import androidx.room.TypeConverter;

import my.app.goodmorninggamers.Alarms.RingtoneOption;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

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
        public static ArrayList<RingtoneOption> arrayListfromString(String value) {
            Type listType = new TypeToken<ArrayList<RingtoneOption>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }

        @TypeConverter
        public static String fromArrayList(ArrayList<RingtoneOption> list) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson gson = builder.create();
        Type listType = new TypeToken<ArrayList<RingtoneOption>>() {}.getType();
            String json = gson.toJson(list,listType);
            return json;
        }
    }

