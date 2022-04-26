package my.app.goodmorninggamers.Activities

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import my.app.goodmorninggamers.Activities.SetAlarmScreen_Components.turnScreenOffAndKeyguardOn
import my.app.goodmorninggamers.Activities.SetAlarmScreen_Components.turnScreenOnAndKeyguardOff
import my.app.goodmorninggamers.R

class FullScreenAlarm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_alarm)
        turnScreenOnAndKeyguardOff()
        var mWebView = findViewById<View>(R.id.webview) as WebView
        mWebView.loadUrl("https://main--coruscating-heliotrope-63f939.netlify.app")


    }

    override fun onDestroy() {
        super.onDestroy()
        turnScreenOffAndKeyguardOn()
    }


}