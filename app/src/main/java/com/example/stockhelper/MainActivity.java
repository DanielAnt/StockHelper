package com.example.stockhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String login, password;
    EditText loginInput;
    EditText passwordInput;
    TextView registerText;

    Button submitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginInput = (EditText) findViewById(R.id.loginInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        registerText = (TextView) findViewById(R.id.registerText);
        submitButton = (Button) findViewById((R.id.login_button));
        registerText.setOnClickListener(this);
        submitButton.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.registerText:
                break;
            case R.id.login_button:
                login = loginInput.getText().toString();
                password = loginInput.getText().toString();
        }
    }
}