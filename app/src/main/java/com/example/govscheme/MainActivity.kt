//package com.example.govscheme
//
//import android.animation.ObjectAnimator
//import android.content.Intent
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.animation.OvershootInterpolator
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import androidx.annotation.RequiresApi
//import com.example.govscheme.databinding.ActivityMainBinding
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var webView:WebView
//
//    private var isFabOpen = false
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        var binding: ActivityMainBinding? = null
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding?.root)
//        webView = findViewById<WebView>(R.id.webView)
//        webViewSetUp(webView)
//
//        binding?.help?.setOnClickListener{
//            startActivity(Intent(this@MainActivity,Chat::class.java))
//        }
//
//    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun webViewSetUp(webView :WebView){
//        webView.webViewClient = WebViewClient()
//        webView.apply{
//            settings.javaScriptEnabled = true
//            settings.safeBrowsingEnabled = true
//            loadUrl("https://hackathon-kxd3fntti-kinsteve.vercel.app/")
//
//        }
//
//
//    }
//
//    override fun onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack()
//        } else {
//            super.onBackPressed()
//        }
//    }
//
//    }
//

package com.example.govscheme

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.govscheme.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    private var isFabOpen = false
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        var binding: ActivityMainBinding? = null
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        webView = findViewById<WebView>(R.id.webView)
        webViewSetUp(webView)

        binding?.help?.setOnClickListener {
            startActivity(Intent(this@MainActivity, Chat::class.java))
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetUp(webView: WebView) {
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)

                val imageError = findViewById<ImageView>(R.id.error_image)
//                val errorMessage = findViewById<TextView>(R.id.text_error_message)
                imageError.setImageResource(R.drawable.two)
//                errorMessage.text = "Error loading webpage"
                webView.visibility = View.GONE
                imageError.visibility = View.VISIBLE
                Toast.makeText(applicationContext, "Error loading webpage", Toast.LENGTH_SHORT)
                    .show()
                view?.loadData("", "text/html", null)
            }
        }

        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (!isConnected) {
            val imageError = findViewById<ImageView>(R.id.error_image)
//            val errorMessage = findViewById<TextView>(R.id.text_error_message)
            imageError.setImageResource(R.drawable.two)
            webView.visibility = View.GONE
            imageError.visibility = View.VISIBLE
//            errorMessage.text = "No internet connection"
            Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_SHORT)
                .show()
        } else {
            webView.apply {
                settings.javaScriptEnabled = true
                settings.safeBrowsingEnabled = true
                loadUrl("https://hackathon-kxd3fntti-kinsteve.vercel.app/")
            }
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

