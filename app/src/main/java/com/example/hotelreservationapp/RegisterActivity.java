package com.example.hotelreservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegEmail,etRegPass,etRegName;
    Button btnRegister;
    TextView logBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.regEmail);
        etRegPass = findViewById(R.id.regPass);
        etRegName = findViewById(R.id.regName);
        logBtn = findViewById(R.id.btnLog);
        btnRegister = findViewById(R.id.buttonReg);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{
            createUser();
        });

        logBtn.setOnClickListener(view ->{
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        });


    }

    private void createUser(){
        String email = etRegEmail.getText().toString();
        String pass = etRegPass.getText().toString();
        String name = etRegName.getText().toString();

        if(TextUtils.isEmpty(email)){
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        }else if(TextUtils.isEmpty(name)){
            etRegName.setError("Name cannot be empty");
            etRegName.requestFocus();
        }else if(TextUtils.isEmpty(pass)){
            etRegPass.setError("Password cannot be empty");
            etRegPass.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this,"Registration Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}