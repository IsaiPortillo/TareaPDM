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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginEmail,etLoginPass;
    Button btnLogin;
    TextView gotoReg;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.buttonSendLogin);
        etLoginEmail = findViewById(R.id.etLogEmail);
        etLoginPass = findViewById(R.id.etLoginPass);
        gotoReg = findViewById(R.id.btnGoToReg);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view ->{
            loginUser();
        });

        gotoReg.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        });
    }

    private void loginUser(){
        String email = etLoginEmail.getText().toString();
        String pass = etLoginPass.getText().toString();


        if(TextUtils.isEmpty(email)){
            etLoginEmail.setError("Email cannot be empty");
            etLoginEmail.requestFocus();
        }else if(TextUtils.isEmpty(pass)){
            etLoginPass.setError("Password cannot be empty");
            etLoginPass.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this,"Log In Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}