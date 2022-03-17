package com.example.goodmorninggamers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.goodmorninggamers.apppieces.Alarm;

import java.util.ArrayList;

public class AlarmAdapter extends ArrayAdapter<Alarm> {

    private Context context;
    private ArrayList<Alarm> alarms;

    public AlarmAdapter(Context context, ArrayList<Alarm> alarms){
        super(context,0,alarms);
        this.context=context;
        this.alarms=alarms;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.alarmlistlayout,parent,false);

        Alarm currentAlarm = alarms.get(position);



        TextView time = (TextView) listItem.findViewById(R.id.textView_name);
        time.setText(currentAlarm.getTime());

        TextView day = (TextView) listItem.findViewById(R.id.textView_release);
        day.setText(currentAlarm.getDay());

        TextView URL = (TextView) listItem.findViewById(R.id.textView_URL);
        URL.setText(currentAlarm.getURL());



        return listItem;
    }
}
