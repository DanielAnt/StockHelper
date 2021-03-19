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
import android.widget.Toast;

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
    Map stockSymbols = new HashMap();
    String[] nameList={};

    ArrayAdapter<String> arrayAdapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        arrayToList();
        loadHashMapFromJson();
        searchingViewList();
    }



    public void searchingViewList() {

        searchView =findViewById(R.id.searchBar);
        listView = findViewById(R.id.listItem);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, nameList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=IBBOJPT8T6NZA44K
                Toast.makeText(Search.this, "You click -" + stockSymbols.get(adapterView.getItemAtPosition(i).toString()), Toast.LENGTH_SHORT).show();
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


    public void arrayToList(){
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset("CompaniesArray.json"));

            String[] temporaryList=new String[jsonArray.length()];
            for(int i = 0; i <jsonArray.length(); i++){
                temporaryList[i]=jsonArray.optString(i);

            }
            nameList = temporaryList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String loadJSONFromAsset(String fileName) {
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

    public void loadHashMapFromJson(){
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset("AllCompaniesWithSymbols.json"));

            for(int i = 0; i <jsonArray.length(); i++){
                JSONObject companyDetail = jsonArray.getJSONObject(i);
                stockSymbols.put(companyDetail.getString("Name"), companyDetail.getString("Symbol"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}