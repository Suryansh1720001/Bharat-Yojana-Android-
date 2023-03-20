package com.example.govscheme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class splash_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)
                }
                catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    val intent = Intent(this@splash_activity ,
                        MainActivity::class.java)
                    startActivity(intent)

                    finish()


                }
            }
        };thread.start()
    }
}