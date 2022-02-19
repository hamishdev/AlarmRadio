package com.example.goodmorninggamers;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

public class twitchActivity extends AppCompatActivity {

    Uri webpage = Uri.parse("https://www.twitch.tv/xqcow");
    Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            startActivity(webIntent);
        } catch (
                ActivityNotFoundException e) {
            // cry.
        }
    }
}
