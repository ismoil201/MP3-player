package com.example.mp3player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mp3player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



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


    private fun mediaPlayerStop() {
        TODO("Not yet implemented")
    }

    private fun mediaPlayerPause() {
        TODO("Not yet implemented")
    }

    private fun mediaPlayerPlay() {
        TODO("Not yet implemented")
    }

}