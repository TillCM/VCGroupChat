package com.example.ctill.vcgroupchat;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ThrowOnExtraProperties;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
 int SIGN_IN_REQUEST_CODE =0;
 DatabaseReference db;
 Query query;
 FirebaseListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();

            // Load chat room contents
            displayChatMessages();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChatMessages();


            }
        });


    }

    public void addChatMessages()
    {
        EditText input = (EditText)findViewById(R.id.input);

        // Read the input field and push a new instance
        // of ChatMessage to the Firebase database

       long time = new Date().getTime();

        FirebaseDatabase.getInstance()
                .getReference("chatMessages")
                .push()
                .setValue(new Chat (input.getText().toString(),
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getDisplayName(),time)
                );


    }

    public void displayChatMessages()
    { query =FirebaseDatabase.getInstance().getReference("chatMessages");
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);



        FirebaseListOptions<Chat> options = new FirebaseListOptions.Builder<Chat>()
                .setLayout(R.layout.message)
                .setQuery(query, Chat.class)
                .build();
        FirebaseListAdapter<Chat> adapter = new FirebaseListAdapter<Chat>(options) {
            @Override
            protected void populateView(View v, Chat model, int position) {
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text

                messageText.setText(model.getmMessage());
                messageUser.setText(model.getmName());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy",
                        model.getmTime()));

            }
        };
        listOfMessages.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        }

    }





}










