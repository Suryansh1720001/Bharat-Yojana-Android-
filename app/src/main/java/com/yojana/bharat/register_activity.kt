package com.yojana.bharat

import android.content.Intent

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yojana.bharat.models.User
import android.view.WindowManager
import com.yojana.bharat.Firestore.FirestoreClass
import com.yojana.bharat.databinding.ActivityRegisterBinding


class register_activity : BaseActivity() {
    private var binding: ActivityRegisterBinding?=null

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        binding =  ActivityRegisterBinding.inflate(layoutInflater)
        // This is used to align the xml view to this class
        setContentView(binding?.root)

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        // It is deprecated in the API level 30. I will update you with the alternate solution soon.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

       binding?.btnRegister?.setOnClickListener {

            registerUser()
        }

        // START
        binding?.tvLogin?.setOnClickListener{
            // Here when the user click on login text we can either call the login activity or call the onBackPressed function.
            // We will call the onBackPressed function.
           startActivity(Intent(this@register_activity,login_activity::class.java))
        }
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(binding?.toolbarRegisterActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        binding?.toolbarRegisterActivity?.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding?.etFirstName?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)

                false
            }

            TextUtils.isEmpty(binding?.etLastName?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(binding?.etEmail?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(binding?.etPassword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(binding?.etConfirmPassword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }
            binding?.etPassword?.text.toString().trim { it <= ' ' } != binding?.etConfirmPassword?.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
          (binding?.cbTermsAndCondition?.isChecked)!! -> {
              true
            }
            else -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_agree_terms_and_condition),
                    true
                )
                false
            }
        }
    }

    /**
     * A function to register the user with email and password using FirebaseAuth.
     */
    private fun registerUser() {

        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String =    binding?.etEmail?.text.toString().trim { it <= ' ' }
            val password: String = binding?.etPassword?.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // TODO Step 9: Remove the hide progress dialog.
                        // START
                        /*// Hide the progress dialog
                        hideProgressDialog()*/
                        // END

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            // TODO Step 2: Create an instance of the user data model class. And, pass the values in the constructor.
                            // Here we have passed only four values in the constructor as there are only four values at registration. So, instead of giving it blank or default.
                            // We have already added the default values in the data model class itself. Make sure the passing value order is correct.
                            // START
                            // Instance of User data model class.
                            val user = User(
                                firebaseUser.uid,
                               binding?.etFirstName?.text.toString().trim { it <= ' ' },
                               binding?.etLastName?.text.toString().trim { it <= ' ' },
                               binding?.etEmail?.text.toString().trim { it <= ' ' }
                            )
                            // END

                            // TODO Step 4: Move the success message and the Sign out piece of code to the success function.
                            // START
                            /*Toast.makeText(
                                this@RegisterActivity,
                                resources.getString(R.string.register_success),
                                Toast.LENGTH_SHORT
                            ).show()


                            *//**
                             * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
                             * and send him to Intro Screen for Sign-In
                             *//*
                            FirebaseAuth.getInstance().signOut()
                            // Finish the Register Screen
                            finish()*/
                            // END

                            // TODO Step 8: Call the function of FirestoreClass to make an entry in the Cloud Firestore of registered user.
                            // STAR
                            // Pass the required values in the constructor.
                            FirestoreClass().registerUser(this@register_activity, user)
                            // END

                        } else {

                            // TODO Step 10: Hide the progress dialog when the task is unsuccessful.
                            // START
                            // Hide the progress dialog
                            hideProgressDialog()
                            // END

                            // If the registering is not successful then show error message.
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }

    // TODO Step 3: Create a function to notify the success result of Firestore entry.
    // START
    /**
     * A function to notify the success result of Firestore entry when the user is registered successfully.
     */
    fun userRegistrationSuccess() {

        // Hide the progress dialog
        hideProgressDialog()

        // TODO Step 5: Replace the success message to the Toast instead of Snackbar.
        Toast.makeText(
            this@register_activity,
            resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()


        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        FirebaseAuth.getInstance().signOut()
        // Finish the Register Screen
        finish()
    }
    // END
}