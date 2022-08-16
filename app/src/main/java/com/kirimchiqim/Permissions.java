package com.kirimchiqim;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
//import android.support.v4.app.ActivityCompat;

import static com.kirimchiqim.ui.MainActivity.REQUEST_CODE_PERMISSIONS;

import androidx.core.app.ActivityCompat;

public class Permissions {

        // Storage Permissions variables
        private static String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        //check permissions.
        public static void verifyStoragePermissions(Activity activity) {
            // Check if we have read or write permission
            int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

            if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_CODE_PERMISSIONS
                );
            }
        }


}
