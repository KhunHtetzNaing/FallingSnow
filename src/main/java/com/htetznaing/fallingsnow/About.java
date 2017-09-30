package com.htetznaing.fallingsnow;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void sendEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","khunhtetznaing@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Android Background Changer");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter your feedback here!.");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
