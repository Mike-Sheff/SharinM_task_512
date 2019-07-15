package ru.netologia.sharinm_task_512;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final EditText imageName = findViewById(R.id.namePicture);

        Button buttonOK = findViewById(R.id.buttonPicture);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), imageName.getText().toString());

                if (myFile.exists()) {

                    int writeExternalStoragePermission = ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    }
                    else {
                        SharedPreferences.Editor myEditor = MainActivity.sharedPreferences.edit();
                        myEditor.putString(MainActivity.PATH_FILE_IMAGE, myFile.getAbsolutePath());
                        myEditor.apply();

                        finish();
                    }
                }
                else {
                    Toast.makeText(SettingsActivity.this, getString(R.string.textMessangNotFile,  myFile), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
