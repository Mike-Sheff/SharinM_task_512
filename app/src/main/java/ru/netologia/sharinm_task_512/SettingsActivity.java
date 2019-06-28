package ru.netologia.sharinm_task_512;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                    
                } else {
                    Toast.makeText(SettingsActivity.this, getString(R.string.textMessangNotFile,  myFile), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
