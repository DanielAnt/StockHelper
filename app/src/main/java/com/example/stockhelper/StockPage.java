package com.example.stockhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
    private RequestQueue mQueue;



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
        priceHistoryButton.setOnClickListener(this);

        Stock chosenStock = getIntent().getParcelableExtra("chosenStock");

        stockName.setText(chosenStock.name);
        stockSymbol.setText(chosenStock.symbol);
        stockPrice.setText(chosenStock.price + " USD");
        stockChange.setText(chosenStock.change +
                " (" +chosenStock.changePercentage+ ")");
        Float changeValue = Float.parseFloat(chosenStock.change);
        if(changeValue < 0){
            stockChange.setTextColor(Color.rgb(200,0,0));
        } else if(changeValue == 0){
            stockChange.setTextColor(Color.rgb(204,204,204));
        }else{
            stockChange.setTextColor(Color.rgb(0,200, 0));
        }

        stockOpenValue.setText(chosenStock.open);
        stockLowValue.setText(chosenStock.low);
        stockHighValue.setText(chosenStock.high);
        stockPrevCloseValue.setText(chosenStock.pervClose);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.stockPriceHistoryButton:
                startActivity(new Intent(this, StockPriceHistory.class));
                break;
        }
    }


}


