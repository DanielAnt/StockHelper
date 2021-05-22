package com.example.stockhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepeatedPassword;
    private ProgressBar registerProgressBar;
    private Button registryButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextUsername = findViewById(R.id.registerUsername);
        editTextEmail = findViewById(R.id.registerEmail);
        editTextPassword = findViewById(R.id.registerPassword);
        editTextRepeatedPassword = findViewById(R.id.registerRepeatedPassword);
        registerProgressBar = findViewById(R.id.registerProgressBar);

        registryButton = findViewById(R.id.registerButton);
        registryButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                RegisterUser();
                break;
        }
    }

    public void RegisterUser() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatedPassword = editTextRepeatedPassword.getText().toString().trim();


        if (ValidateUsername(username) && ValidateEmail(email) && ValidatePassword(password, repeatedPassword)) {
            registerProgressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(username, email);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this, "You have registered successfully!", Toast.LENGTH_LONG).show();
                                            registerProgressBar.setVisibility(View.GONE);
                                            ReturnToLoginPage();
                                        } else {
                                            Toast.makeText(Register.this, "Something gone wrong! Try again!", Toast.LENGTH_LONG).show();
                                            registerProgressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(Register.this, "Something gone wrong! Try again!", Toast.LENGTH_LONG).show();
                                registerProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });

        }
    }

    private void ReturnToLoginPage(){
        startActivity(new Intent(this, Login.class));
    }

    public boolean ValidateUsername(String username) {
        if (username.isEmpty()) {
            editTextUsername.setError("This field is required!");
            editTextUsername.requestFocus();
            return false;
        }
        return true;
    }

    public boolean ValidateEmail(String email) {
        if (email.isEmpty()) {
            editTextEmail.setError("This field is required!");
            editTextEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return false;
        }
        return true;
    }

    public boolean ValidatePassword(String password, String repeatedPassword) {
        if (password.isEmpty()) {
            editTextPassword.setError("This field is required!");
            editTextPassword.requestFocus();
            return false;
        }

        if (repeatedPassword.isEmpty()) {
            editTextRepeatedPassword.setError("This field is required!");
            editTextRepeatedPassword.requestFocus();
            return false;
        }


        if (!password.equals(repeatedPassword)) {
            editTextRepeatedPassword.setError("Passwords don't match!");
            editTextRepeatedPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters!");
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }

}