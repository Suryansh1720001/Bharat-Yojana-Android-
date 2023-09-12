package com.yojana.bharat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yojana.bharat.Constants
import com.yojana.bharat.Detail_scheme
import com.yojana.bharat.Heading
import com.yojana.bharat.R
import com.yojana.bharat.chatbot.Chat
import com.yojana.bharat.databinding.ActivityGovSchemeBinding
import com.yojana.bharat.models.scheme_item_Adapter

class Gov_scheme : AppCompatActivity() {
    private var binding: ActivityGovSchemeBinding? = null

    private var mSchemeList: ArrayList<Heading>? =null
    private var mSelectedOptionPosition: Int =0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityGovSchemeBinding.inflate(layoutInflater)
        setContentView(binding?.root)


// Get the ActionBar object
        val actionBar = supportActionBar

// Set the app icon image in the ActionBar
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)

        // Retrieve the list of scheme titles from Constants object
        mSchemeList = Constants.getScheme()

        val adapter = scheme_item_Adapter(mSchemeList!!) { schemeId ->
            val intent = Intent(this, Detail_scheme::class.java)
            intent.putExtra("schemeId", schemeId)
            startActivity(intent)
        }


        binding?.rvItemsScheme?.layoutManager = LinearLayoutManager(this)
        binding?.rvItemsScheme?.adapter = adapter

        binding?.help?.setOnClickListener {
            startActivity(Intent(this, Chat::class.java))
        }

    }





}