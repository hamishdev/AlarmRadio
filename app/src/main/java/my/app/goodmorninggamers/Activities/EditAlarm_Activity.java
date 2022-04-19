package my.app.goodmorninggamers.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import my.app.goodmorninggamers.Activities.SetAlarmScreen_Components.AlarmOptionClickedListener;
import my.app.goodmorninggamers.Activities.SetAlarmScreen_Components.DefaultButton;
import my.app.goodmorninggamers.Activities.SetAlarmScreen_Components.DefaultOptionClickedListener;
import my.app.goodmorninggamers.Activities.SetAlarmScreen_Components.RingtoneOptionFinishedListener;
import my.app.goodmorninggamers.Activities.SetAlarmScreen_Components.StreamerButton;
import my.app.goodmorninggamers.Alarms.Alarm;
import my.app.goodmorninggamers.Alarms.RingtoneOption;
import my.app.goodmorninggamers.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class EditAlarm_Activity extends AppCompatActivity implements RingtoneOptionFinishedListener, Serializable {


    private static final String TAG = "EditAlarm_Screen";
    private int hour;
    private int minute;

    private ArrayList<RingtoneOption> m_wakeupRingtoneOptions;
    private Boolean defaultIsSet = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);

        Intent intent = getIntent();
        Alarm toEditAlarm = (Alarm)intent.getSerializableExtra("Alarm");
        hour = toEditAlarm.getHour();
        minute = toEditAlarm.getMinute();
        m_wakeupRingtoneOptions = toEditAlarm.getWakeupOptions();
        Calendar now = Calendar.getInstance();

        setContentView(R.layout.setalarm_screen);

        TimePicker clock = (TimePicker) findViewById(R.id.timePicker1);
        clock.setHour(hour);
        clock.setMinute(minute);
        ImageButton backButton = (ImageButton) findViewById(R.id.SetAlarmBackBUtton);
        StreamerButton setFirstStreamerButton =  (StreamerButton) findViewById(R.id.firstStreamer);
        setFirstStreamerButton.saveOption(m_wakeupRingtoneOptions.get(0),this);
        StreamerButton setSecondStreamerButton = (StreamerButton) findViewById(R.id.secondsStreamer);
        setSecondStreamerButton.saveOption(m_wakeupRingtoneOptions.get(1),this);
        DefaultButton setDefaultStreamerButton = (DefaultButton) findViewById(R.id.defaultStreamer);
        setDefaultStreamerButton.saveDefaultOption(this,m_wakeupRingtoneOptions.get(2));

        ImageButton setAlarmButton = (ImageButton) findViewById(R.id.setAlarmButton);
        TextView alarmTimeText = (TextView) findViewById(R.id.alarmTimeText);
        updateNextAlarmTextString(alarmTimeText);
        DatePicker datepicker = (DatePicker) findViewById(R.id.datePicker1);
        datepicker.setMinDate(System.currentTimeMillis() - 1000);

        //Update calendar to reflect tomorrow's alarms
        if(toEditAlarm.getDayOfAlarminRelationtoNow()== toEditAlarm.TOMORROW){
            now.add(Calendar.DAY_OF_YEAR,1);
            datepicker.updateDate(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
        }


        datepicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                //Does nothing
                updateNextAlarmTextString(alarmTimeText);
            }
        });

        clock.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                updateAlarmTime(i,i1);
                updateNextAlarmTextString(alarmTimeText);
            }
        });


        //FirstStreamer
        setFirstStreamerButton.setOnClickListener(new AlarmOptionClickedListener(EditAlarm_Activity.this,setFirstStreamerButton,0));


        //SecondStreamer
        setSecondStreamerButton.setOnClickListener(new AlarmOptionClickedListener(EditAlarm_Activity.this,setSecondStreamerButton,1));


        //DefaultStreamer
        setDefaultStreamerButton.setOnClickListener(new DefaultOptionClickedListener(EditAlarm_Activity.this,setDefaultStreamerButton,2));

        //SetAlarm
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(defaultIsSet) {
                    Intent intent = new Intent();
                    //edit alarm
                    Alarm toReturn = toEditAlarm;
                    toReturn.updateRingtoneOptions(m_wakeupRingtoneOptions);
                    toReturn.updateTime(hour,minute);
                    intent.putExtra("alarm", toReturn);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditAlarm_Activity.this);
                    builder.setMessage("Cannot create an alarm without a backup alarm (in case your streamers aren't live)");
                    builder.create().show();

                }
            }
        });

        //backButton
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAlarm_Activity.this);
                SpannableString title = new SpannableString("Discard modifications?");

                // alert dialog title align center
                title.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        title.length(),
                        0
                );

                builder.setTitle(title);

                SpannableString[] items = new SpannableString[]{
                        new SpannableString("Discard"),new SpannableString("Cancel")
                };
                items[0].setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        items[0].length(),
                        0
                );
                items[1].setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        items[1].length(),
                        0
                );
                builder.setItems(items,new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                        else{

                        }
                    }
                });
                AlertDialog alert =builder.create();
                alert.show();
            }
        });



    }



    public void updateDefault(Boolean defaultstatus){
        defaultIsSet=defaultstatus;
    }


    public void updateAlarmTime(int hourOfDay,int minute){

        this.minute=minute;
        hour = hourOfDay;


    }

    public void updateNextAlarmTextString(TextView alarmTimeText){
        Calendar currentClockTime = Calendar.getInstance();
        currentClockTime.set(Calendar.HOUR_OF_DAY,hour);
        currentClockTime.set(Calendar.MINUTE,minute);
        long difference = currentClockTime.getTimeInMillis()-Calendar.getInstance().getTimeInMillis();
        int days = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        String clockAlarmString = "Next alarm will ring in "+(days==0?"":days+"days")+(hours==0?"":hours+"h")+min+"m";
        alarmTimeText.setText(clockAlarmString);
    }






    @Override
    public void RingtoneOptionFinished(int option, RingtoneOption ringtone) {
        m_wakeupRingtoneOptions.set(option,ringtone);
        if(option==2){
            updateDefault(true);
        }
    }
}
