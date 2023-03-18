package com.example.govscheme

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.govscheme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var webView:WebView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        var binding: ActivityMainBinding? = null
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        webView = findViewById<WebView>(R.id.webView)
        webViewSetUp(webView)

        binding?.help?.setOnClickListener{
            startActivity(Intent(this@MainActivity,Chat::class.java))
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetUp(webView :WebView){
        webView.webViewClient = WebViewClient()
        webView.apply{
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            loadUrl("https://suryansh1720001.github.io/")



        }


    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

   
}