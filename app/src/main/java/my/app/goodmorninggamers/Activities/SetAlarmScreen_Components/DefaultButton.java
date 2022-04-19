package my.app.goodmorninggamers.Activities.SetAlarmScreen_Components;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import my.app.goodmorninggamers.Helpers.GlideHelper;
import my.app.goodmorninggamers.Alarms.RingtoneOption;

public class DefaultButton extends androidx.appcompat.widget.AppCompatImageButton implements GlideHelper, DefaultOptionFinishedListener {
    public DefaultButton(@NonNull Context context) {
        super(context);
    }

    public DefaultButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void saveDefaultOption(Activity activity, RingtoneOption defaultOption) {
        glideResizeandLoadURL(activity, defaultOption.getRingtonePicture(), this);
    }

}
