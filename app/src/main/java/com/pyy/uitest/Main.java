package com.pyy.uitest;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by pyy on 2017/8/2.
 */

public class Main extends Activity {
    private final String PATH =  Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + "/AutoSignIn/";
    private final String FILE  = PATH + "gesture.txt";
    int[] array = {803, 902, 554, 1167, 278, 1167, 278, 1432, 803, 1432};
    // TODO should write the user's lock point
    private final int REQUEST_WRITE_EXTERNAL_STORAGE = 727;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_EXTERNAL_STORAGE);
                    // REQUEST_WRITE_EXTERNAL_STORAGE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast toast = Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    showDialogTipUserRequestPermission();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
            .setTitle("存储权限不可用")
            .setMessage("需要获取存储空间，存储个人信息,\n否则，您将无法正常使用")
            .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            })
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setCancelable(false).show();
    }

    private void saveGesture() {
        File desDir = new File(PATH);
        try {
            if (!desDir.exists()) {
                boolean ret  = desDir.mkdir();
        }

            File file = new File(FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(array2string(array).getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String array2string(int[] array){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < array.length; i++){
            sb.append(array[i]).append(",");
        }
        sb.deleteCharAt(sb.length() -1);
        return sb.toString();
    }

    private void init() {
        Button gestureBtn = (Button)this.findViewById(R.id.button1);
        gestureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                saveGesture();
            }
        }
        );
    }

}
