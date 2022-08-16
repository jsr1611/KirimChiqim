package com.kirimchiqim.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kirimchiqim.About;
import com.kirimchiqim.AddNew;
import com.kirimchiqim.R;
import com.kirimchiqim.ViewItems;
import com.kirimchiqim.backup.LocalBackup;
import com.kirimchiqim.backup.RemoteBackup;
import com.kirimchiqim.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Google Drive Activity";

    public static final int REQUEST_CODE_SIGN_IN = 0;
    public static final int REQUEST_CODE_OPENING = 1;
    public static final int REQUEST_CODE_CREATION = 2;
    public static final int REQUEST_CODE_PERMISSIONS = 2;

    private Button addNewBtn, readItemsBtn, aboutMe;

    //variable for decide if i need to do a backup or a restore.
    //True stands for backup, False for restore
    private boolean isBackup = true;

    private MainActivity activity;

    private RemoteBackup remoteBackup;
    private LocalBackup localBackup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewBtn = findViewById(R.id.btn_goto_add_new);
        readItemsBtn = findViewById(R.id.btn_goto_show_all);
        aboutMe = findViewById(R.id.btn_goto_aboutme);

        remoteBackup = new RemoteBackup(this);
        localBackup = new LocalBackup(this);

        // creating variables for our edittext, button and dbhandler
        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        setupUI();
        dbHelper.close();
    }

    private void setupUI(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showMultichoice());

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

    private void showMultichoice() {
        AlertDialog.Builder builderChoose = new AlertDialog.Builder(activity);
        final CharSequence[] items = {"Add student", "Add Exam"};


        builderChoose
                .setItems(items, (dialog, which) -> {

                    switch (which) {
                        case 0:
                            Intent addStud = new Intent(MainActivity.this, AddStudent.class);
                            startActivity(addStud);
                            break;

                        case 1:
                            Intent addExam = new Intent(MainActivity.this, AddExam.class);
                            startActivity(addExam);
                            break;

                        default:
                            break;
                    }


                });
        builderChoose.show();
    }
}