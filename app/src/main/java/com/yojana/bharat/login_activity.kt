package com.yojana.bharat

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import com.yojana.bharat.activity.Gov_scheme
import com.yojana.bharat.databinding.ActivityLoginBinding


// TODO Step 1 : Create an empty activity as Login Activity.
/**
 * Login Screen of the application.
 */
class login_activity : AppCompatActivity() {
    private var binding: ActivityLoginBinding?=null

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        // This is used to align the xml view to this class
        setContentView(binding?.root)

        // TODO Step 13: Assign the click event to the Register text and launch the register screen.
        // START
        binding?.tvRegister?.setOnClickListener {

            startActivity(Intent(this@login_activity, register_activity::class.java))
        }

        binding?.tvForgotPassword?.setOnClickListener{
            startActivity(Intent(this@login_activity,forget_password::class.java))
        }
//        // END

        // TODO Step 15: Assign the click event to the login button and add the feature to login.
        // START
        binding?.btnLogin?.setOnClickListener {

            when {
                TextUtils.isEmpty(binding?.etLoginEmail?.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@login_activity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding?.etLoginPassword?.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@login_activity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = binding?.etLoginEmail?.text.toString().trim { it <= ' ' }
                    val password: String = binding?.etLoginPassword?.text.toString().trim { it <= ' ' }

                    // Log-In using FirebaseAuth
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this@login_activity,
                                    "You are logged in successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                /**
                                 * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
                                 * and send him to Main Screen with user id and email that user have used for registration.
                                 */

                                val intent =
                                    Intent(this@login_activity, Gov_scheme::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {

                                // If the login is not successful then show error message.
                                Toast.makeText(
                                    this@login_activity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
        // END
    }
}