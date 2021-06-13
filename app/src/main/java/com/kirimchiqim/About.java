package com.kirimchiqim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class About extends AppCompatActivity {
    private TextView txtView_linkedin, txtView_gitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        txtView_linkedin = findViewById(R.id.textView8_linkedin2);
        txtView_gitHub = findViewById(R.id.textView_github2);

        txtView_linkedin.setMovementMethod(LinkMovementMethod.getInstance());
        txtView_gitHub.setMovementMethod(LinkMovementMethod.getInstance());

    }
}