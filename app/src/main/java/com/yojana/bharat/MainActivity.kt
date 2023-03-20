package com.yojana.bharat

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.webkit.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.govscheme.R
import com.example.govscheme.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var binding: ActivityMainBinding? = null

    private var isFabOpen = false
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        webView = findViewById(R.id.webView)


        webViewSetUp(webView)
        webView.apply{
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            loadUrl("https://hackathon-chi-five.vercel.app/")
        }



        binding?.help?.setOnClickListener {
            if(isConnectedToInternet()) {
                startActivity(Intent(this@MainActivity, Chat::class.java))
            }else{
                Toast.makeText(this@MainActivity,"Kindly Check your network connection",Toast.LENGTH_SHORT).show()
            }
        }




    }

    private fun webViewSetUp(webView: WebView) {
        webView.webViewClient = object : WebViewClient() {



            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding?.progressBar?.visibility = View.VISIBLE

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding?.progressBar?.visibility = View.GONE

            }


            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?,
            ) {
                super.onReceivedError(view, request, error)

                showErrorView(webView)
            }


            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?,
            ): Boolean {
                val url = request?.url?.toString()

                if (url != null && isWebUrl(url)) {
                    if (!isConnectedToInternet()) {
                        showErrorView(webView)
                        Toast.makeText(
                            applicationContext,
                            "No internet connection",
                            Toast.LENGTH_SHORT
                        ).show()
                        return true
                    }

                }



                return super.shouldOverrideUrlLoading(view, request)


            }
        }


    }



    private fun showErrorView(webView: WebView) {
        val imageError = findViewById<ImageView>(R.id.error_image)
        val retryButton = findViewById<Button>(R.id.retry_button)
        val errorMessage = findViewById<TextView>(R.id.text_error_message)

//        errorMessage.text = "Error loading webpage"
        errorMessage.text = "Please Check your Internet Connection"
        imageError.setImageResource(R.drawable.no_connection)
        webView.visibility = View.GONE
        imageError.visibility = View.VISIBLE
        retryButton.visibility = View.VISIBLE
        errorMessage.visibility = View.VISIBLE

        retryButton.setOnClickListener {
            webView.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.VISIBLE
            imageError.visibility = View.GONE
            retryButton.visibility = View.GONE
            errorMessage.visibility = View.GONE

            val url = webView.url
            if (isWebUrl(url!!)) {
                webView.loadUrl(url)
            }


        }
    }

    private fun isWebUrl(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}



