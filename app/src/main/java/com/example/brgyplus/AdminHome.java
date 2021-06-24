package com.example.brgyplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class AdminHome extends AppCompatActivity {

    EditText title, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_BrgyPlus);
        setContentView(R.layout.activity_admin_home);

        title = findViewById(R.id.setTitle);
        message = findViewById(R.id.setMessage);
    }
}