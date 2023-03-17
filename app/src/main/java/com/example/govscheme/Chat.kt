package com.example.govscheme

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class Chat: AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeTextView: TextView
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

        messageList = mutableListOf()

        recyclerView = findViewById(R.id.recycler_view)
        welcomeTextView = findViewById(R.id.welcome_text)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_btn)

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
            welcomeTextView.visibility = View.GONE
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
        messageList.removeAt(messageList.size - 1)
        addToChat(response, Message.SENT_BY_BOT)
    }

    private fun callAPI(question: String) {
        // okhttp
        messageList.add(Message("Typing... ", Message.SENT_BY_BOT))

        val jsonBody = JSONObject().apply {
            put("model", "text-davinci-003")
            put("prompt", question)
            put("max_tokens", 4000)
            put("temperature", 0)
        }
//        val body = RequestBody.create(jsonBody.toString(), JSON)
        val body = RequestBody.create(JSON, jsonBody.toString())

        val request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization", "Bearer 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")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//
//            }

//            override fun onResponse(call: Call, response: Response) {
//
//            }

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
}
