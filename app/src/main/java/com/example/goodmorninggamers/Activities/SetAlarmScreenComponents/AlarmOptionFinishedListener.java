package com.example.goodmorninggamers.Activities.SetAlarmScreenComponents;

import android.app.Activity;

import com.example.goodmorninggamers.Channels.StreamerChannel;
import com.example.goodmorninggamers.UI_Classes.RingtoneOption;

public interface AlarmOptionFinishedListener {

    public void saveOption(RingtoneOption option, Activity activity);
}
