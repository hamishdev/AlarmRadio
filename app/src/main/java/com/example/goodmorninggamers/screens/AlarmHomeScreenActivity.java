package com.example.goodmorninggamers.screens;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goodmorninggamers.R;

public class AlarmHomeScreenActivity extends AppCompatActivity {



    @Override
    public void onCreate(Bundle SavedInstanceState) {

        String[] myAlarmArray= new String[]{
                "9:00Am Tuesday","10:00AM Wednesday"
        };
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.alarmhomescreen_activty);
        ArrayAdapter<String> alarmArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myAlarmArray);
        ListView alarmsListView = (ListView) findViewById(R.id.AlarmListView);
        alarmsListView.setAdapter(alarmArrayAdapter);
    }


}
