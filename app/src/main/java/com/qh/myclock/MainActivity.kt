package com.qh.myclock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qh.myclock.controls.Clock

class MainActivity : AppCompatActivity() {

    lateinit var clock: Clock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clock = findViewById(R.id.clock)

        Thread(Runnable { updateClock() }).start()

        clock.invalidate()
    }

    private fun updateClock(){
        while (true){
            Thread.sleep(200)
            runOnUiThread {clock.postInvalidate() }
        }
    }
}
