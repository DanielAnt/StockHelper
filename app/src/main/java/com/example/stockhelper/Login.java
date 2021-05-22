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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private String email, password;
    private EditText emailInput;
    private EditText passwordInput;
    private TextView registerText;
    private ProgressBar loginProgressBar;
    private Button submitButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        registerText = (TextView) findViewById(R.id.registerText);
        submitButton = (Button) findViewById((R.id.login_button));
        loginProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);

        registerText.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            startActivity(new Intent(Login.this, Menu.class));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerText:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.login_button:
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                if (email.isEmpty()) {
                    emailInput.setError("This field is required!");
                    emailInput.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInput.setError("Please provide valid email");
                    emailInput.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordInput.setError("This field is required!");
                    passwordInput.requestFocus();
                    return;
                }

                loginProgressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    loginProgressBar.setVisibility(View.GONE);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(Login.this, Menu.class));
                                } else {
                                    loginProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Wrong email or password!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
        }
    }
}