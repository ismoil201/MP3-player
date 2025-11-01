package com.example.mp3player

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ServiceInfo
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi

class MediaPlayerService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    private val receiver  = LowBatteryReceiver()



    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            MEDIA_PLAYER_PLAY -> {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(baseContext, R.raw.xcho)
                }
                mediaPlayer?.start()
            }

            MEDIA_PLAYER_PAUSE -> mediaPlayer?.pause()

            MEDIA_PLAYER_STOP -> {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                stopSelf()
            }
        }
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()

        initReceiver()  // receiver set qilish




        createNotificationChannel()

        val playIcon = Icon.createWithResource(this, R.drawable.baseline_play_arrow_24)
        val pauseIcon = Icon.createWithResource(this, R.drawable.pause_svgrepo_com)
        val stopIcon = Icon.createWithResource(this, R.drawable.stop_svgrepo_com)

        // ❗ Har bir PendingIntent uchun unique requestCode ber
        val playPendingIntent = PendingIntent.getService(
            this,
            1,
            Intent(this, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PLAY },
            PendingIntent.FLAG_IMMUTABLE
        )

        val pausePendingIntent = PendingIntent.getService(
            this,
            2,
            Intent(this, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_PAUSE },
            PendingIntent.FLAG_IMMUTABLE
        )

        val stopPendingIntent = PendingIntent.getService(
            this,
            3,
            Intent(this, MediaPlayerService::class.java).apply { action = MEDIA_PLAYER_STOP },
            PendingIntent.FLAG_IMMUTABLE
        )

        val mainPendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = Notification.Builder(this, CHANNEL_ID)
            .setStyle(
                Notification.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.img)
            .addAction(Notification.Action.Builder(pauseIcon, "Pause", pausePendingIntent).build())
            .addAction(Notification.Action.Builder(playIcon, "Play", playPendingIntent).build())
            .addAction(Notification.Action.Builder(stopIcon, "Stop", stopPendingIntent).build())
            .setContentIntent(mainPendingIntent)
            .setContentTitle("Media Player")
            .setContentText("Simple Media Player")
            .build()

        // ✅ Android 14 (SDK 34+) uchun foreground type ko‘rsatish majburiy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(100, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        } else {
            startForeground(100, notification)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Media Player Controls",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows playback controls while playing music"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

    }


    private fun initReceiver() {

        val filter  = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
        }


        registerReceiver(receiver, filter)
    }




    override fun onDestroy() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null


        unregisterReceiver(receiver)

        super.onDestroy()

    }
}
