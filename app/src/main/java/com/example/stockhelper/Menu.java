package com.example.stockhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    private TextView welcomeText;
    private Button searchButton;
    private Button favoriteButton;
    private Button profileButton;
    private Button settingsButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        welcomeText = findViewById(R.id.menuWelcomeText);
        searchButton = findViewById(R.id.menuSearchButton);
        favoriteButton = findViewById(R.id.menuFavoriteButton);
        profileButton = findViewById(R.id.menuProfileButton);
        settingsButton = findViewById(R.id.menuSettingsButton);
        logoutButton = findViewById(R.id.menuLogoutButton)


        ;


    }
}