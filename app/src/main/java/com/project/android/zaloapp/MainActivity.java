package com.project.android.zaloapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.android.zaloapp.Fragments.LocationMap;
import com.project.android.zaloapp.Fragments.WeatherCalenderFrag;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends Activity {

    private String NETWORK = "android.permission.INTERNET";
    private String LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    private String[] req_permission = {
            "android.permission.INTERNET",
            "android.permission.ACCESS_FINE_LOCATION"
    };
    private static final int  PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!checkPermission());
            requestPermission();
        if(savedInstanceState != null){
            getFragmentManager().executePendingTransactions();
            Fragment fragmentId = getFragmentManager().findFragmentById(R.id.fragment_container);
            if(fragmentId != null){
                getFragmentManager().beginTransaction().remove(fragmentId).commit();
            }


        }
        WeatherCalenderFrag wFragment = new WeatherCalenderFrag();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, wFragment).commit();
    }

    private boolean checkPermission() {
        ///int result = ContextCompat.checkSelfPermission(getApplicationContext(), NETWORK);
        //int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), LOCATION);

        //return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        boolean is_Ok = false;
        for (int i =0; i < req_permission.length ;i++) {
            int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), req_permission[i]);
            if(result1 == PackageManager.PERMISSION_GRANTED){
                is_Ok = true;
            }else {

                return false;
            }

        }
        return is_Ok;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,req_permission , PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted)
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access location data and NETWORK.", Toast.LENGTH_LONG).show();
                    else {

                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data and NETWORK.", Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{LOCATION, NETWORK},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public void cal_Weather(View view){
        WeatherCalenderFrag wFragment = new WeatherCalenderFrag();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, wFragment).commit();

    }
    public void location_map(View view){
        LocationMap mFragment = new LocationMap();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
    }
}
