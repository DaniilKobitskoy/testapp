package com.app.testapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import com.app.testapp.databinding.ActivityMainBinding
import com.app.testapp.network.LocationCheckTask

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startTimer()
    }
    private fun startTimer() {
        val countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                LocationCheckTask(this@MainActivity).execute()
            }
        }

        countDownTimer.start()
    }
    fun openWebViewFragment(url: String) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, WebView.newInstance(url))
            .commit()
    }
    fun openGameViewFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, GameView())
            .commit()
    }
    fun cloceview(){
        binding.imageView.visibility = View.GONE
        binding.progressBar3.visibility = View.GONE
        binding.fragmentContainer.visibility = View.VISIBLE
    }

}