package com.yojana.bharat

import com.example.govscheme.databinding.ActivityRegisterBinding
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



// TODO Step 6: Create an empty activity as Register Activity.
/**
 * Register Screen of the application.
 */
class register_activity : AppCompatActivity() {
    private var binding: ActivityRegisterBinding?=null
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)
        binding =  ActivityRegisterBinding.inflate(layoutInflater)
        // This is used to align the xml view to this class
        setContentView(binding?.root)

        // TODO Step 9: Assign the click event to the register button and perform the functionality.
        // START
        binding?.btnRegister?.setOnClickListener {
            when {
                TextUtils.isEmpty(binding?.etRegisterEmail?.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@register_activity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding?.etRegisterPassword?.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@register_activity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = binding?.etRegisterEmail?.text.toString().trim { it <= ' ' }
                    val password: String = binding?.etRegisterPassword?.text.toString().trim { it <= ' ' }

                    // Create an instance and create a register a user with email and password.
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                // If the registration is successfully done
                                if (task.isSuccessful) {

                                    // Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@register_activity,
                                        "You are registered successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    /**
                                     * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
                                     * and send him to Main Screen with user id and email that user have used for registration.
                                     */

                                    val intent =
                                        Intent(this@register_activity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // If the registering is not successful then show error message.
                                    Toast.makeText(
                                        this@register_activity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                }
            }
        }
        // END

        // TODO Step 14: Assign the click event to the Login text and launch the login screen.
        // START
        binding?.tvLogin?.setOnClickListener {
            startActivity(Intent(this@register_activity,login_activity::class.java))
            finish()
        }
        // END
    }
}