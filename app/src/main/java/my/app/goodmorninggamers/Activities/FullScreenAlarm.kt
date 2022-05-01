package my.app.goodmorninggamers.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import my.app.goodmorninggamers.Activities.SetAlarmScreen_Components.turnScreenOffAndKeyguardOn
import my.app.goodmorninggamers.Activities.SetAlarmScreen_Components.turnScreenOnAndKeyguardOff
import my.app.goodmorninggamers.Alarms.RingtoneOption
import my.app.goodmorninggamers.R

private const val TAG = "fullscreenalarm activity"
class FullScreenAlarm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rO  =  intent.getSerializableExtra("RingtoneOption") as RingtoneOption
        val url = rO.liveContentURL;
        setContentView(R.layout.activity_full_screen_alarm)
        turnScreenOnAndKeyguardOff()
        var myWebView = findViewById<View>(R.id.webview) as WebView
        val webSettings = myWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.mediaPlaybackRequiresUserGesture = false;
        myWebView.webViewClient = WebViewClient()
        myWebView.webChromeClient = WebChromeClient()
        myWebView.loadUrl(url)


    }

    override fun onDestroy() {
        super.onDestroy()
        turnScreenOffAndKeyguardOn()
    }


}