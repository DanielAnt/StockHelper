package com.example.stockhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class StockPage extends AppCompatActivity {

    private TextView stockName;
    private TextView stockSymbol;
    private TextView stockPrice;
    private TextView stockChange;
    private TextView stockOpenValue;
    private TextView stockHighValue;
    private TextView stockLowValue;
    private TextView stockPrevCloseValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_page);

        stockName = findViewById(R.id.stockName);
        stockSymbol = findViewById(R.id.stockSymbol);
        stockPrice = findViewById(R.id.stockPrice);
        stockChange = findViewById(R.id.stockChange);
        stockOpenValue = findViewById(R.id.stockOpenValue);
        stockLowValue = findViewById(R.id.stockLowValue);
        stockHighValue = findViewById(R.id.stockHighValue);
        stockPrevCloseValue = findViewById(R.id.stockPrevCloseValue);

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
}