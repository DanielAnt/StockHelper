package com.example.stockhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Profile extends AppCompatActivity implements View.OnClickListener {


    private TextView accBText;
    private Button startButton;
    private ListView buyListView;
    boolean clicked=true;
    private List<SharesBought> buyList;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        accBText = findViewById(R.id.accBalanceText);
        startButton = findViewById(R.id.startBtn);
        buyListView = findViewById(R.id.purchaseList);
        startButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        gameStatus();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).hasChild("game")) {
                    databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("game").child("money").setValue(1000f);
                    startButton.setText("Reset");

                }else{
                    confirmDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
   }

    private void confirmDialog() {  // Confirm reset
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("You really want to reset the game?");
        alertDlg.setCancelable(false);
        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Profile.super.onBackPressed();
                databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("game").removeValue();
            }
        });
        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDlg.create().show();
    }

    public void gameStatus() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).hasChild("game")) {
                    String currentMoney = snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("game").child("money").getValue().toString();
                    startButton.setText("Reset");
                    accBText.setText(currentMoney);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }





}

