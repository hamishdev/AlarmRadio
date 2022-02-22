package com.example.goodmorninggamers.alarms;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class chilledCowActivity extends AppCompatActivity {


    Uri webpage = Uri.parse("https://www.youtube.com/watch?v=xgirCNccI68");
    Intent webIntent = new Intent(Intent.ACTION_VIEW,webpage);
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

