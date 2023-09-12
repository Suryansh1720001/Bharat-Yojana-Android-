package com.yojana.bharat.chatbot

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yojana.bharat.R
import com.yojana.bharat.databinding.DialogBackHelpBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class Chat: AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeImageView: ImageView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var messageList: MutableList<Message>
    private lateinit var messageAdapter: MessageAdapter

    //    private val JSON = MediaType.get("application/json; charset=utf-8")
    private val JSON = "application/json; charset=utf-8".toMediaType()
    private val client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        Handler().postDelayed({
            messageList.add(
                Message("Hey! How may I assist you with Indian Government schemes today? ",
                    Message.SENT_BY_BOT
                )
            )
            // Your code to display the first message of your chatbot here
        }, 500) // 2000 milliseconds = 2 seconds delay


        messageList = mutableListOf()

        recyclerView = findViewById(R.id.recycler_view)
        welcomeImageView = findViewById(R.id.welcome_text)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_btn)


        messageEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                // Your code to perform when the EditText gains focus
                welcomeImageView.visibility = View.GONE
//                welcomeImageView.alpha = 0.8f
            }
        }
        // setup recycler view
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }




        sendButton.setOnClickListener {
            val question = messageEditText.text.toString().trim()
            addToChat(question, Message.SENT_BY_ME)
            messageEditText.text.clear()
            callAPI(question)
            welcomeImageView.visibility = View.GONE
//            welcomeImageView.alpha = 0.5f

        }
    }



    private fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            messageList.add(Message(message, sentBy))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }

    private fun addResponse(response: String) {
        messageList.removeAt(messageList.size -1)
        addToChat(response, Message.SENT_BY_BOT)
    }

    private fun callAPI(question: String) {

        // okhttp
        messageList.add(Message("Typing... ", Message.SENT_BY_BOT))

        val jsonBody = JSONObject().apply {
            put("model", "babbage:ft-personal-2023-04-05-19-43-58")
            put("prompt", question)
            put("max_tokens", 15)
            put("temperature", 0)
        }
//        val body = RequestBody.create(jsonBody.toString(), JSON)
        val body = RequestBody.create(JSON, jsonBody.toString())

        val request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization", "API KEY")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                addResponse("Failed to load response due to ${e.message}")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    try {
                        val jsonObject = JSONObject(response.body?.string())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getString("text")
                        addResponse(result.trim())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    addResponse("Failed to load response due to ${response.body?.toString()}")
                }
            }
        })
    }


    override fun onBackPressed() {
        Log.d("MyActivity", "Back button pressed")
        chatBack()

    }

    private fun chatBack() {
        val chatBack = Dialog(this)
        chatBack.setCancelable(false)
        val binding = DialogBackHelpBinding.inflate(layoutInflater)
        chatBack.setContentView(binding.root)

        binding?.btnDeleteNo?.setOnClickListener {
            chatBack.dismiss()
        }
        binding?.btnDeleteYes?.setOnClickListener {
            super.onBackPressed()
            }



    chatBack.show()

    }


}