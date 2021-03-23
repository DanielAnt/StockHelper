package com.example.stockhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class StockPriceHistory extends AppCompatActivity {

    private LineGraphSeries<DataPoint> graphData;
    private RequestQueue mQueue;
    private GraphView graph;
    private String[]  dates;
    private Stock chosenStock;
    private ProgressBar graphProgressBar;
    private ProgressBar test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_price_history);

        graph = (GraphView) findViewById(R.id.graph);
        graphProgressBar = (ProgressBar) findViewById(R.id.graphProgressBar);

        graph.setVisibility(View.GONE);
        graphProgressBar.setVisibility(View.VISIBLE);

        mQueue = Volley.newRequestQueue(this);
        chosenStock = getIntent().getParcelableExtra("chosenStock");
        jsonParseDaily(chosenStock.symbol);





        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat("yy-MM-dd");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);

            }
        });








    }




    private void jsonParseDaily(String stockSymbol) {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="  +stockSymbol+  "&apikey=IBBOJPT8T6NZA44K";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject stock = response.getJSONObject("Time Series (Daily)");
                            Iterator<String> jsonKeys = stock.keys();
                            int datesLength = stock.length();
                            dates = new String[datesLength];
                            datesLength --;
                            while(jsonKeys.hasNext()){
                                dates[datesLength] = jsonKeys.next();
                                datesLength --;
                            }
                            graphData = new LineGraphSeries();
                            for(int i = 0; i < dates.length; i++){
                                JSONObject stockDay = stock.getJSONObject(dates[i]);
                                String price = stockDay.getString("4. close");
                                String[] date = dates[i].split("-");
                                Calendar cal = new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                                graphData.appendData(new DataPoint(cal.getTimeInMillis(), Double.parseDouble(price)), true, dates.length);
                            }

                            graph.getGridLabelRenderer().setTextSize(25);
                            graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                            graph.getViewport().setYAxisBoundsManual(true);
                            graph.getViewport().setMinX(graphData.getLowestValueX());
                            graph.getViewport().setMaxX(graphData.getHighestValueX());
                            graph.getViewport().setMinY(0);
                            graph.getViewport().setMaxY(graphData.getHighestValueY()*1.2);
                            graph.getViewport().setScalable(true);
                            graph.getViewport().setScalableY(true);
                            graph.addSeries(graphData);

                            graph.setVisibility(View.VISIBLE);
                            graphProgressBar.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(StockPriceHistory.this, "Something gone wrong! Try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

}