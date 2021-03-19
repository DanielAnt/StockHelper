package com.example.stockhelper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Search extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ProgressBar searchProgressBar;
    Map stockSymbols = new HashMap();

    String[] nameList={};
    ArrayAdapter<String> arrayAdapter;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchProgressBar = findViewById(R.id.searchProgressBar);

        arrayToList();
        loadHashMapFromJson();
        searchingViewList();
        mQueue = Volley.newRequestQueue(this);

    }



    public void searchingViewList() {

        searchView =findViewById(R.id.searchBar);
        listView = findViewById(R.id.listItem);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, nameList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jsonParse((String) stockSymbols.get(adapterView.getItemAtPosition(i).toString()));


            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search.this.arrayAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Search.this.arrayAdapter.getFilter().filter((newText));
                return false;
            }
        });
    }


    private void arrayToList(){
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset("CompaniesArrayTrimmed.json"));

            String[] temporaryList=new String[jsonArray.length()];
            for(int i = 0; i <jsonArray.length(); i++){
                temporaryList[i]=jsonArray.optString(i);

            }
            nameList = temporaryList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void loadHashMapFromJson(){
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset("AllCompaniesWithSymbolsTrimmed.json"));

            for(int i = 0; i <jsonArray.length(); i++){
                JSONObject companyDetail = jsonArray.getJSONObject(i);
                stockSymbols.put(companyDetail.getString("Name"), companyDetail.getString("Symbol"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonParse(String stockSymbol) {
        System.out.println(stockSymbol);
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" +stockSymbol+ "&apikey=IBBOJPT8T6NZA44K";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject stock = response.getJSONObject("Global Quote");
                            String symbol = stock.getString("01. symbol");
                            String price = stock.getString("05. price");
                            String change = stock.getString("09. change");
                            String changeProcentage = stock.getString("10. change percent");
                            String test = (symbol + " " + price + " " + change + " " + changeProcentage);
                            Toast.makeText(Search.this, "You click -" + test, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(e);
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

