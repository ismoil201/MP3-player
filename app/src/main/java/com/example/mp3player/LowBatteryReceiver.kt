package com.example.mp3player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class LowBatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {


        when(intent.action)
        {
            Intent.ACTION_BATTERY_LOW -> Log.d("Receiver", "Low Battery")

        }
    }
}