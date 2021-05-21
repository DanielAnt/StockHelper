package com.example.stockhelper;

import androidx.annotation.NonNull;
<<<<<<< HEAD
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> master
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
=======
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
>>>>>>> master
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
<<<<<<< HEAD

import java.util.ArrayList;
import java.util.HashMap;
=======
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
>>>>>>> master
import java.util.List;

public class StockPage extends AppCompatActivity implements View.OnClickListener {

    private TextView stockName;
    private TextView stockSymbol;
    private TextView stockPrice;
    private TextView stockChange;
    private TextView stockOpenValue;
    private TextView stockHighValue;
    private TextView stockLowValue;
    private TextView stockPrevCloseValue;
    private Button priceHistoryButton;
    private Button favButton;
<<<<<<< HEAD
    public static Button buyButton;


=======
>>>>>>> master
    private RequestQueue mQueue;
    private Stock chosenStock;
    private FirebaseAuth mAuth;
    private List<String> FavList;
<<<<<<< HEAD
    private HashMap buyList;
    EditText input;

    SharesBought shares = new SharesBought();
=======

>>>>>>> master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_page);

        mQueue = Volley.newRequestQueue(this);
        stockName = findViewById(R.id.stockName);
        stockSymbol = findViewById(R.id.stockSymbol);
        stockPrice = findViewById(R.id.stockPrice);
        stockChange = findViewById(R.id.stockChange);
        stockOpenValue = findViewById(R.id.stockOpenValue);
        stockLowValue = findViewById(R.id.stockLowValue);
        stockHighValue = findViewById(R.id.stockHighValue);
        stockPrevCloseValue = findViewById(R.id.stockPrevCloseValue);
        priceHistoryButton = findViewById(R.id.stockPriceHistoryButton);
        favButton = findViewById(R.id.addToFavButton);
<<<<<<< HEAD
        buyButton = findViewById(R.id.buyStocks);



        priceHistoryButton.setOnClickListener(this);
        favButton.setOnClickListener(this);
        buyButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();



=======
        priceHistoryButton.setOnClickListener(this);
        favButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();


>>>>>>> master
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);



<<<<<<< HEAD
=======

>>>>>>> master
        chosenStock = getIntent().getParcelableExtra("chosenStock");
        stockName.setText(chosenStock.name);
        stockSymbol.setText(chosenStock.symbol);
        stockPrice.setText(df.format(Float.parseFloat(chosenStock.price)) + " USD");
        stockChange.setText(df.format(Float.parseFloat(chosenStock.change)) +
                " (" + chosenStock.changePercentage + ")");
        Float changeValue = Float.parseFloat(chosenStock.change);
        if(changeValue < 0){
            stockChange.setTextColor(Color.rgb(200,0,0));
        } else if(changeValue == 0){
            stockChange.setTextColor(Color.rgb(204,204,204));
        }else{
            stockChange.setTextColor(Color.rgb(0,200, 0));
        }

        stockOpenValue.setText(df.format(Float.parseFloat(chosenStock.open)) + "$");
        stockLowValue.setText(df.format(Float.parseFloat(chosenStock.low)) + "$");
        stockHighValue.setText(df.format(Float.parseFloat(chosenStock.high)) + "$");
        stockPrevCloseValue.setText(df.format(Float.parseFloat(chosenStock.pervClose)) + "$");


<<<<<<< HEAD

=======
>>>>>>> master
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.stockPriceHistoryButton:
                chosenStock = getIntent().getParcelableExtra("chosenStock");
                Intent intent = new Intent(this, StockPriceHistory.class);
                intent.putExtra("chosenStock", chosenStock);
                startActivity(intent);
                break;
            case R.id.addToFavButton:
                chosenStock = getIntent().getParcelableExtra("chosenStock");

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).hasChild("fav")){
                            String currentFav = snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("fav").getValue().toString();
                            FavList = new ArrayList<>();
                            Boolean hasSymbol = false;
                            System.out.println(currentFav);
<<<<<<< HEAD
                            if (currentFav.contains("[")){
                                currentFav = currentFav.substring(1, currentFav.length() - 1);
                                String[] favArray = currentFav.split(",");
                                for(String item: favArray){
                                    FavList.add(item.trim());
                                }

                                for (String item: favArray){
                                    System.out.println(item.trim());
                                    System.out.println(item.trim().equals(chosenStock.symbol));
                                    if(item.trim().equals(chosenStock.symbol)){
                                        FavList.remove(item.trim());
                                        hasSymbol = true;
                                    }
                                }
                                if(!hasSymbol){
                                    FavList.add(chosenStock.symbol);
                                    Toast toast = Toast.makeText(getApplicationContext(), "Dodano do ulubionych", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                else{
                                    Toast toast = Toast.makeText(getApplicationContext(), "Usunieto z ulubionych", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            }
                            else {
                                if(!currentFav.equals(chosenStock.symbol)){
                                    FavList.add(currentFav);
                                    FavList.add(chosenStock.symbol);
                                }
                            }

                            databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("fav").removeValue();
                            if (FavList.size() > 0){
                                databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("fav").setValue(FavList);
                            }
=======
                             if (currentFav.contains("[")){
                                 currentFav = currentFav.substring(1, currentFav.length() - 1);
                                 String[] favArray = currentFav.split(",");
                                 for(String item: favArray){
                                     FavList.add(item.trim());
                                 }

                                 for (String item: favArray){
                                     System.out.println(item.trim());
                                     System.out.println(item.trim().equals(chosenStock.symbol));
                                     if(item.trim().equals(chosenStock.symbol)){
                                         FavList.remove(item.trim());
                                         hasSymbol = true;
                                     }
                                 }
                                 if(!hasSymbol){
                                     FavList.add(chosenStock.symbol);
                                     Toast toast = Toast.makeText(getApplicationContext(), "Dodano do ulubionych", Toast.LENGTH_SHORT);
                                     toast.show();
                                 }
                                 else{
                                     Toast toast = Toast.makeText(getApplicationContext(), "Usunieto z ulubionych", Toast.LENGTH_SHORT);
                                     toast.show();
                                 }

                             }
                             else {
                                 if(!currentFav.equals(chosenStock.symbol)){
                                     FavList.add(currentFav);
                                     FavList.add(chosenStock.symbol);
                                 }
                             }

                             databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("fav").removeValue();
                             if (FavList.size() > 0){
                                 databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("fav").setValue(FavList);
                             }
>>>>>>> master
                        }
                        else {
                            databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("fav").setValue(chosenStock.symbol);
                        }
<<<<<<< HEAD
=======

>>>>>>> master
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
<<<<<<< HEAD
            case R.id.buyStocks:

                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("The number of Stocks to be bought");
                builder.setMessage("How many stock you want to buy?");
                input = new EditText(this);
                builder.setView(input);
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int number = Integer.parseInt(input.getText().toString());

                        //System.out.println(number);


                        chosenStock = getIntent().getParcelableExtra("chosenStock");
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("game").hasChild("purchasedStocks")){
                                    buyList = (HashMap) dataSnapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("game").child("purchasedStocks").getValue();
                                    HashMap tempHash = new HashMap();
                                    tempHash.put("number", number);
                                    tempHash.put("price", chosenStock.price);
                                    buyList.put(chosenStock.symbol,tempHash);

                                    mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("game").child("purchasedStocks").setValue(buyList);
                                    System.out.println(" nie tutaj");
                                    System.out.println(buyList);

                                }
                                else{

                                    HashMap buyList = new HashMap();
                                    buyList.put("number", number);
                                    buyList.put("price", chosenStock.price);

                                    mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("game").child("purchasedStocks").child(chosenStock.symbol).setValue(buyList);
                                    System.out.println("tutaj");
                                    System.out.println(buyList);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                databaseError.toException();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();            }
                });
                builder.create().show();

        }
    }

=======
        }
    }



>>>>>>> master
}


