package com.studio.sameer.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FeedBack extends AppCompatActivity {
    private EditText userName,messageBody;
    private Button sendMyFeedback;
    private TextView messageSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        userName = (EditText)findViewById(R.id.userName);
        messageBody = (EditText)findViewById(R.id.messageBody);
        sendMyFeedback = (Button)findViewById(R.id.sendMyFeedbackButton);
        messageSent = (TextView)findViewById(R.id.messageSent);

        sendMyFeedback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                messageSent.setText("");
                if (userName.getText().toString().equals("")){
                    messageSent.setText("Enter your name");
                }
                else if(messageBody.getText().toString().equals("")){
                    messageSent.setText("Please enter a message");
                }
                else {
                    messageSent.setText("Thank you "+userName.getText().toString()+" for your feedback");
                    Intent Email = new Intent(Intent.ACTION_SEND);
                    Email.setType("text/email");
                    Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "sameerul97@gmail.com" });
                    Email.putExtra(Intent.EXTRA_SUBJECT, userName.getText().toString());
                    Email.putExtra(Intent.EXTRA_TEXT, messageBody.getText().toString());
                    startActivity(Intent.createChooser(Email, "Send Message Via"));
                }
            }
        });

    }
}
