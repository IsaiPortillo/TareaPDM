package com.example.hotelreservationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView regBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regBTN = (TextView)findViewById(R.id.btnGoToReg);
        regBTN.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        Intent logBTN = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(logBTN);
    }
}