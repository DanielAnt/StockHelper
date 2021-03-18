package com.example.stockhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepeatedPassword;
    private ProgressBar registerProgressBar;
    private Button registryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextLogin = (EditText) findViewById(R.id.registerLogin);
        editTextEmail = findViewById(R.id.registerEmail);
        editTextPassword = findViewById(R.id.registerPassword);
        editTextRepeatedPassword = findViewById(R.id.registerRepeatedPassword);
        registerProgressBar = findViewById(R.id.registerProgressBar);

        registryButton = findViewById(R.id.registerButton);
        registryButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                RegisterUser();
                break;
        }
    }

    public void RegisterUser(){
        String login = editTextLogin.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatedPassword = editTextRepeatedPassword.getText().toString().trim();



        if(login.isEmpty()){
            editTextLogin.setError("This field is required!");
            editTextLogin.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("This field is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("This field is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(repeatedPassword.isEmpty()){
            editTextRepeatedPassword.setError("This field is required!");
            editTextRepeatedPassword.requestFocus();
            return;
        }



        if(!password.equals(repeatedPassword)){
            editTextRepeatedPassword.setError("Passwords don't match!");
            editTextRepeatedPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Password must be at least 6 characters!");
            editTextPassword.requestFocus();
            return;
        }






    }

}