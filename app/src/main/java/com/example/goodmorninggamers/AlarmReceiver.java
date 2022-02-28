package com.example.goodmorninggamers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "Received alarm");

        String videoID = "0mgu0n81nYE";
        Intent H3H3intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoID));
        H3H3intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);



        //Need to start a fullscreenIntent
        //https://medium.com/android-news/full-screen-intent-notifications-android-85ea2f5b5dc1
        //Can't just open apps that aren't in the foreground on a trigger without user input, need to show a notification and get user to click that or break user immersion and do an exception
        context.startActivity(H3H3intent);
    }
}

