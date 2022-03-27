package com.example.goodmorninggamers.Activities.SetAlarmScreenComponents;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.goodmorninggamers.Activities.GlideHelper;
import com.example.goodmorninggamers.UI_Classes.RingtoneOption;

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
