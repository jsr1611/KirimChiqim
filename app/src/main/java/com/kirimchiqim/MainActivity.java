package com.kirimchiqim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_PERMISSIONS = 2;

    private Button addNewBtn, readItemsBtn, aboutMe;
    // creating variables for our edittext, button and dbhandler
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewBtn = findViewById(R.id.btn_goto_add_new);
        readItemsBtn = findViewById(R.id.btn_goto_show_all);
        aboutMe = findViewById(R.id.btn_goto_aboutme);

        dbHelper = new DBHelper(MainActivity.this);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNew.class);
                startActivity(intent);
            }
        });
        readItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity via a intent.
                Intent i = new Intent(MainActivity.this, ViewItems.class);
                startActivity(i);
            }
        });

        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
            }
        });

    }
}