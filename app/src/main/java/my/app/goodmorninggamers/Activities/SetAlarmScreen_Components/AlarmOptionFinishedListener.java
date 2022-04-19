package my.app.goodmorninggamers.Activities.SetAlarmScreen_Components;

import android.app.Activity;

import my.app.goodmorninggamers.Alarms.RingtoneOption;

public interface AlarmOptionFinishedListener {

    public void saveOption(RingtoneOption option, Activity activity);
}
