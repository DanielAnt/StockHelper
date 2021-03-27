package com.example.stockhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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
    private RequestQueue mQueue;
    private Stock chosenStock;



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
        priceHistoryButton.setOnClickListener(this);
        favButton.setOnClickListener(this);


        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);




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
                Log.d("Fav",String.valueOf(chosenStock));
                break;
        }
    }



}


