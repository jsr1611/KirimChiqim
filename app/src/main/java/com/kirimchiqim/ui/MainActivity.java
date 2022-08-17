package com.kirimchiqim.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.kirimchiqim.About;
import com.kirimchiqim.AddNew;
import com.kirimchiqim.R;
import com.kirimchiqim.ViewItems;
import com.kirimchiqim.backup.LocalBackup;
import com.kirimchiqim.backup.RemoteBackup;
import com.kirimchiqim.db.DBHelper;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Google Drive Activity";

    public static final int REQUEST_CODE_SIGN_IN = 0;
    public static final int REQUEST_CODE_OPENING = 1;
    public static final int REQUEST_CODE_CREATION = 2;
    public static final int REQUEST_CODE_PERMISSIONS = 2;

    private Button addNewBtn, showItemsBtn, aboutMe;

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
        Toolbar toolbar = findViewById(R.id.stoolbar);
        setSupportActionBar(toolbar);

        addNewBtn = findViewById(R.id.btn_goto_add_new);
        showItemsBtn = findViewById(R.id.btn_goto_show_all);
        aboutMe = findViewById(R.id.btn_goto_aboutme);

        remoteBackup = new RemoteBackup(this);
        localBackup = new LocalBackup(this);

        // creating variables for our edittext, button and dbhandler
        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        setupUI();
        dbHelper.close();
    }

    private void setupUI(){

        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddNew.class);
                startActivity(intent);
            }
        });
        showItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity via a intent.
                Intent i = new Intent(activity, ViewItems.class);
                startActivity(i);
            }
        });

        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, About.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        final DBHelper db = new DBHelper(getApplicationContext());

        switch (id) {
            case R.id.action_backup:
                String outFileName = Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_title) + File.separator;
                localBackup.performBackup(db, outFileName);
                break;
            case R.id.action_import:
                localBackup.performRestore(db);
                break;
            case R.id.action_backup_Drive:
                isBackup = true;
                remoteBackup.connectToDrive(isBackup);
                break;
            case R.id.action_import_Drive:
                isBackup = false;
                remoteBackup.connectToDrive(isBackup);
                break;
            case R.id.action_delete_all:
                //reinitialize the backup
                SQLiteDatabase database = db.getWritableDatabase();
                db.clearAllData(database);
                Log.i("Clearing All Data", "All data was deleted.");
                Toast.makeText(this, "All data was deleted.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_recalculate_Balance:
                SQLiteDatabase database1 = db.getWritableDatabase();
                double newBalance = db.dbUpdateBalance(database1);
                Toast.makeText(this, "Balance corrected: " + newBalance, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_CODE_SIGN_IN:
                Log.i(TAG, "Sign in request code");
                // Called after user is signed in.
                if (resultCode == RESULT_OK) {
                    remoteBackup.connectToDrive(isBackup);
                }
                break;

            case REQUEST_CODE_CREATION:
                // Called after a file is saved to Drive.
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "Backup successfully saved.");
                    Toast.makeText(this, "Backup successufly loaded!", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_CODE_OPENING:
                if (resultCode == RESULT_OK) {
                    DriveId driveId = data.getParcelableExtra(
                            OpenFileActivityOptions.EXTRA_RESPONSE_DRIVE_ID);
                    remoteBackup.mOpenItemTaskSource.setResult(driveId);
                } else {
                    remoteBackup.mOpenItemTaskSource.setException(new RuntimeException("Unable to open file"));
                }

        }
    }

}