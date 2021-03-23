package com.example.stockhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
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
    private  GraphView graph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_price_history);
        graph = (GraphView) findViewById(R.id.graph);
        mQueue = Volley.newRequestQueue(this);
        jsonParseDaily("IBM");
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                    return formatter.format(value);
                }
                return super.formatLabel(value, isValueX);

            }
        });
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
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
                            String[] dates = new String[datesLength];
                            datesLength --;
                            while(jsonKeys.hasNext()){
                                dates[datesLength] = jsonKeys.next();
                                datesLength --;
                            }
                            graphData = new LineGraphSeries();
                            for(int i = 0; i < dates.length; i++){
                                System.out.println(dates[i]);
                                JSONObject stockDay = stock.getJSONObject(dates[i]);
                                String price = stockDay.getString("4. close");
                                String[] date = dates[i].split("-");
                                Calendar cal = new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                                graphData.appendData(new DataPoint(cal.getTimeInMillis(), Double.parseDouble(price)), true, dates.length);
                            }
                            graph.addSeries(graphData);


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