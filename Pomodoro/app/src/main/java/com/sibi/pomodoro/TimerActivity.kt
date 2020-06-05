package com.sibi.pomodoro

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.icu.util.TimeUnit
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_timer.*
import java.lang.StringBuilder
import java.time.Duration

class TimerActivity : AppCompatActivity() {

    lateinit var notifiManager: NotificationManager
    lateinit var notifiChannel: NotificationChannel
    lateinit var notifiBuilder: Notification.Builder
    val CHANNEL_ID = "TIMER_APP_NOTIFY"
    val DESC = "TIMER_FINISH"
    val TAG = "TIMER_NOTIFY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        notifiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val timer = object : CountDownTimer(25 * 60 * 1000, 1000) {
            override fun onFinish() {
                showNotification()
            }

            override fun onTick(millisUntilFinished: Long) {
                val mins = millisUntilFinished / 1000 / 60
                val secs = millisUntilFinished / 1000 % 60
                val time_text = "$mins:$secs"
                timer_view.text = time_text
            }

        }

        var isTimerRunning = false
        timer_start.setOnClickListener {
            if (isTimerRunning == false){
                isTimerRunning = true
                timer.start()
                timer_start.text = getString(R.string.reset)
            }else{
             timer.cancel()
                isTimerRunning = false
                timer_view.text = "25:00"
                timer_start.text = getString(R.string.start)
            }
        }


    }

    private fun showNotification(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //notification channel
            notifiChannel = NotificationChannel(CHANNEL_ID, DESC, NotificationManager.IMPORTANCE_HIGH)
            notifiChannel.enableLights(true)
            notifiChannel.lightColor = Color.BLUE
            notifiChannel.enableVibration(true)

            notifiManager.createNotificationChannel(notifiChannel)

            notifiBuilder = Notification.Builder(applicationContext, CHANNEL_ID)
        }else{
            notifiBuilder = Notification.Builder(applicationContext)
        }
        notifiBuilder.setContentTitle(getString(R.string.app_name))
        notifiBuilder.setContentText(getString(R.string.time_up))
        notifiBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
        notifiManager.notify(200, notifiBuilder.build())
    }

}
