package com.yojana.bharat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.util.*

import android.widget.Toast

import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class Help : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var emailContentEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        // Initialize the EditText views
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        emailContentEditText = findViewById(R.id.emailContentEditText)

        // Set up the submit button click listener
        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            // Get the text from the EditText views
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val emailContent = emailContentEditText.text.toString()

            // Create a Properties object to hold the SMTP server settings
            val props = Properties()
            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.port"] = "587"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.starttls.enable"] = "true"

            // Create a Session object with the SMTP server settings and user credentials
            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication("your-gmail-address", "your-gmail-password")
                }
            })

            try {
                // Create a MimeMessage object
                val message = MimeMessage(session)

                // Set the sender, recipient, subject, and message text
                message.setFrom(InternetAddress("suryansh7202@gmail.com"))

//                message.addRecipient(Message.RecipientType.TO, InternetAddress("recipient@example.com"))

                message.subject = "Help Desk Request"
                message.setText(emailContent)

                // Send the message
                Transport.send(message)

                // Display a success message
                Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show()

                // Clear the EditText views
                nameEditText.text.clear()
                emailEditText.text.clear()
                emailContentEditText.text.clear()

            } catch (e: Exception) {
                e.printStackTrace()
                // Display an error message
                Toast.makeText(this, "Error sending email", Toast.LENGTH_SHORT).show()
            }
        }
    }}