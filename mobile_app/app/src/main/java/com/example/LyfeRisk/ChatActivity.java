package com.example.LyfeRisk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    List<String> messages;
    List<String> responses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Makes the app in portrait mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FloatingActionButton chatSend = findViewById(R.id.chatSendBtn);
        EditText inputMessage = findViewById(R.id.inputET);
        messages = new ArrayList<>();
        responses = new ArrayList<>();
        JSONArray messagesArray = new JSONArray();

        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Assuming userMessage is a MaterialTextView
                String userMessageContent = inputMessage.getText().toString();
                messages.add(userMessageContent);
                inputMessage.setText("");

                // Create a JSONObject for the user message
                JSONObject userMessageObject = new JSONObject();
                try {
                    userMessageObject.put("role", "user");
                    userMessageObject.put("content", userMessageContent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Add the user message object to the array
                messagesArray.put(userMessageObject);



                JSONObject chatMessage = new JSONObject();
                try {
                    chatMessage.put("message", messagesArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String docBotURL = "https://on-request-docbot-ef42g3nnla-uc.a.run.app";
                RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);

                StringRequest docBotRequest = new StringRequest(Request.Method.POST, docBotURL, response -> {
                    Log.d("CloudFunctionResponse", response);
                    JSONObject botResponseObject = new JSONObject();
                    try {
                        botResponseObject.put("role", "assistant");
                        botResponseObject.put("content", response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    messagesArray.put(botResponseObject);
                    responses.add(response);
                    createRecyclerView(messages, responses);

                }, error -> {
                    // Handle error
                    Log.e("CloudFunctionError", "Error sending data to Cloud Function: " + error.toString());
                }) {
                    @Override
                    public byte[] getBody() {
                        return chatMessage.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };
                queue.add(docBotRequest);

            }
        });
    }
    public void goToContact(View view) {
        Intent intent = new Intent(ChatActivity.this, ContactActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }
    private void createRecyclerView(List<String> prompts, List<String> responses){
        RecyclerView recyclerView = findViewById(R.id.RCchat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(prompts, responses,this);
        myRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}