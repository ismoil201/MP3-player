package com.example.mp3player

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mp3player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initMusicList()// <- now not use this function

        binding.btnPlay.setOnClickListener {
            mediaPlayerPlay()
        }
        binding.btnPause.setOnClickListener {
            mediaPlayerPause()
        }

        binding.btnStop.setOnClickListener {
            mediaPlayerStop()
        }

    }

    private fun initMusicList() {

        var musicList = mutableListOf<Int>()

        musicList.add(R.raw.barbie_girl)
        musicList.add(R.raw.birinchi)
        musicList.add(R.raw.cukur)
        musicList.add(R.raw.fake_love)



    }


    private fun mediaPlayerStop() {
        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_STOP }
        startService(intent)


//        mediaPlayer?.stop()
//        mediaPlayer?.release()
//        mediaPlayer  = null
    }

    private fun mediaPlayerPause() {
        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_PAUSE }
        startService(intent)


//        mediaPlayer?.pause()
    }

    private fun mediaPlayerPlay() {
        val intent = Intent(this, MediaPlayerService::class.java)
            .apply { action = MEDIA_PLAYER_PLAY }
        startService(intent)

//        if(mediaPlayer==null){
//            mediaPlayer = MediaPlayer.create(this,R.raw.xcho)
//        }
//        mediaPlayer?.start()

    }

}