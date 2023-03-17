package com.example.govscheme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.govscheme.databinding.ActivityMainBinding
import com.example.govscheme.databinding.ActivityUserProfileBinding

class User_Profile : AppCompatActivity() {

    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var edtName : EditText

    private lateinit var btnSignUp : Button

//    private lateinit var mAuth: FirebaseAuth
//    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        var binding: ActivityUserProfileBinding? = null
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)





    }
}