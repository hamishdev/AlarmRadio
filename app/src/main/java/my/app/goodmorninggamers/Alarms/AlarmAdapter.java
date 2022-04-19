package my.app.goodmorninggamers.Alarms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import my.app.goodmorninggamers.Helpers.GlideHelper;
import my.app.goodmorninggamers.R;

import java.util.ArrayList;

public class AlarmAdapter extends ArrayAdapter<Alarm> implements GlideHelper {

    private Context context;
    private ArrayList<Alarm> m_alarms;
    public String TAG ="Alarm Adapter";

    public AlarmAdapter(Context context, ArrayList<Alarm> alarms){
        super(context,0,alarms);
        this.context=context;
        this.m_alarms =alarms;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.listview_alarms,parent,false);

        Alarm currentAlarm = m_alarms.get(position);
        ToggleListener tl = (ToggleListener) context;

        listItem.setLongClickable(true);
        listItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //createDeleteScreen()
                tl.deleteAlarm(currentAlarm);
                m_alarms.remove(position);
                notifyDataSetChanged();
                return true;
            }
        });

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tl.editAlarm(currentAlarm);
            }
        });

        //Populating listview Data




        //Digitaltime
        TextView time = (TextView) listItem.findViewById(R.id.alarm12hourtime);
        time.setText(currentAlarm.getTwelvehourclock());

        //Day
        TextView day = (TextView) listItem.findViewById(R.id.listviewAlarms_day);
        day.setText(currentAlarm.getDayofAlarminRelationtoNow());


        Activity activity = (Activity) context;
        int youtubeRed = ContextCompat.getColor(context,R.color.youtubeRed);
        int twitchPurple = ContextCompat.getColor(context,R.color.twitchPurple);
        //Ring#1
        ImageView ringPic = (ImageView) listItem.findViewById(R.id.RingRingtonePic1);
        CardView cardview1 = listItem.findViewById(R.id.StreamerRing1);
        if(currentAlarm.getWakeupOptions().get(0).getLiveChecker()!=null){
            int platform = currentAlarm.getWakeupOptions().get(0).getLiveChecker().m_platform;
            cardview1.setCardBackgroundColor(platform==0?twitchPurple:youtubeRed);
        }

        glideResizeandLoadURL(activity, currentAlarm.getWakeupOptions().get(0).getRingtonePicture(),ringPic);


        //Ring#2
        ImageView ring2Pic = (ImageView) listItem.findViewById(R.id.RingRingtonePic2);
        CardView cardview2 = listItem.findViewById(R.id.StreamerRing2);
        if(currentAlarm.getWakeupOptions().get(1).getLiveChecker()!=null){
            int platform2 = currentAlarm.getWakeupOptions().get(1).getLiveChecker().m_platform;
            cardview2.setCardBackgroundColor(platform2==0?twitchPurple:youtubeRed);
        }
        glideResizeandLoadURL(activity, currentAlarm.getWakeupOptions().get(1).getRingtonePicture(),ring2Pic);


        //Ring#3

        CardView cardview3 = listItem.findViewById(R.id.StreamerRing3);

        if(currentAlarm.getWakeupOptions().get(2).getLiveChecker()!=null){
            int platform3 = currentAlarm.getWakeupOptions().get(2).getLiveChecker().m_platform;
            cardview3.setCardBackgroundColor(platform3==0?twitchPurple:youtubeRed);
        }
        ImageView ring3pic = (ImageView) listItem.findViewById(R.id.RingRingtonePic3);
        glideResizeandLoadURL(activity, currentAlarm.getWakeupOptions().get(2).getRingtonePicture(),ring3pic);

        //Toggle
        Switch toggleButton = (Switch) listItem.findViewById(R.id.AlarmToggle);
        toggleButton.setChecked(currentAlarm.on);


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    tl.turnOnAlarm(currentAlarm);
                    currentAlarm.switchOn();
                }
                else{
                    tl.turnOffAlarm(currentAlarm);
                    currentAlarm.switchOff();

                }
            }
        });


        return listItem;
    }
}
