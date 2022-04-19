package my.app.goodmorninggamers.Activities.SetAlarmScreen_Components;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import my.app.goodmorninggamers.Helpers.GlideHelper;
import my.app.goodmorninggamers.Alarms.RingtoneOption;

public class StreamerButton extends androidx.appcompat.widget.AppCompatImageButton implements GlideHelper,AlarmOptionFinishedListener {
    public StreamerButton(@NonNull Context context) {
        super(context);
    }

    public StreamerButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StreamerButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void saveOption(RingtoneOption option, Activity activity) {
        glideResizeandLoadURL(activity,option.getRingtonePicture(),this);
    }
}