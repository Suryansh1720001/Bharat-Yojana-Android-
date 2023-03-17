package com.example.govscheme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.govscheme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var binding: ActivityMainBinding? = null
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.help?.setOnClickListener{
            startActivity(Intent(this@MainActivity,Chat::class.java))
        }




    }
}