package com.example.goodmorninggamers.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.goodmorninggamers.Data.TwitchChannel;
import com.example.goodmorninggamers.Network.TwitchClient;
import com.example.goodmorninggamers.Network.VolleyListener;
import com.example.goodmorninggamers.R;

public interface CustomOnKeyListener extends View.OnKeyListener, VolleyListener {

}


