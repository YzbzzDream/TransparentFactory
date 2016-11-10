package com.transparent.automationfactory.base.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.transparent.automationfactory.AutomationApplication;


public class PermisssionsManager {

    public static final int PERMISSION_REQUEST_CODE = 0;

    public static boolean deniedPermiss(String permission) {
        return ContextCompat.checkSelfPermission(AutomationApplication.sContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    public static boolean grantedPermiss(String permisssion) {
        return ContextCompat.checkSelfPermission(AutomationApplication.sContext, permisssion) ==
                PackageManager.PERMISSION_GRANTED;

    }

    public static boolean grantedPermiss(String... permissions) {
        for (String permission : permissions) {
            if (!grantedPermiss(permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean grantedLocation() {
        return grantedPermiss(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);


    }

    public static boolean grantedExternalStorage() {

        return grantedPermiss(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static void requestPermissions(Activity activity, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
    }

    public static boolean hasAllGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean grantedCamera() {
        return grantedPermiss(Manifest.permission.CAMERA);
    }

}
