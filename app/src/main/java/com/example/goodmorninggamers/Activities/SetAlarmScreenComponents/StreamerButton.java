package com.example.goodmorninggamers.Activities.SetAlarmScreenComponents;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.goodmorninggamers.Activities.GlideHelper;
import com.example.goodmorninggamers.Channels.StreamerChannel;

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
    public void saveOption(StreamerChannel option, Activity activity) {
        glideResizeandLoadURL(activity,option.getPicURL(),this);
    }
}