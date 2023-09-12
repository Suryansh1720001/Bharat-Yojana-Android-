package com.yojana.bharat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.yojana.bharat.databinding.ActivityForgetPasswordBinding


// TODO Step 1: Replace the AppCompactActivity with BaseActivity.
/**
 * Forgot Password Screen of the application.
 */
class forget_password  : AppCompatActivity() {
    private var binding: ActivityForgetPasswordBinding?=null


    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        // This is used to align the xml view to this class
        setContentView(binding?.root)


        //TODO Step 2: Assign the click even to the submit button.
        // In this screen there is only a one input field so we will not create the separate function what we have done in the Register and Login Screens.
        // I will show you how to perform all the operations in the on click function it self.

        // START
        binding?.btnSubmit?.setOnClickListener {

            // Get the email id from the input field.
            val email: String = binding?.etEmail?.text.toString().trim { it <= ' ' }

            // Now, If the email entered in blank then show the error message or else continue with the implemented feature.
            if (email.isEmpty()) {
                Toast.makeText(this@forget_password,"Enter the email id",Toast.LENGTH_LONG).show()
            } else {

                // Show the progress dialog.
//                showProgressDialog(resources.getString(R.string.please_wait))

                // This piece of code is used to send the reset password link to the user's email id if the user is registered.
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->

                        // Hide the progress dialog
//                        hideProgressDialog()

                        if (task.isSuccessful) {
                            // Show the toast message and finish the forgot password activity to go back to the login screen.
                            Toast.makeText(
                                this@forget_password,
                                resources.getString(R.string.email_sent_success),
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        } else {
                            Toast.makeText(this@forget_password,task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
//                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }
        // END
    }


    /**
     * A function for actionBar Setup.
     */
//    private fun setupActionBar() {
//
//        setSupportActionBar(binding?.toolbar_forgot_password_activity)
//
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
//        }
//
//        toolbar_forgot_password_activity.setNavigationOnClickListener { onBackPressed() }
//    }
}