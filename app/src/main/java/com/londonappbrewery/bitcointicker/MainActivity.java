package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s= (String) parent.getItemAtPosition(position);
                Log.d("Bitcoin","Selected: "+s);
                Log.d("Bitcoin",BASE_URL+s);
                letsDoSomeNetworking(BASE_URL+s);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin","Nothing is Selected !!");
            }
        });

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,new JsonHttpResponseHandler() {

//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                // called when response HTTP status is "200 OK"
//                Log.d("Bitcoin", "Success!! JSON: " + responseBody.toString());
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
//                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
//                Log.d("Bitcoin", "Fail response: " + responseBody.toString());
//                Log.e("ERROR", e.toString());
//                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
//            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("Bitcoin","Success !!"+ response.toString());
                try {
                    BitcoinDataModel bitcoinDataModel = BitcoinDataModel.fromJson(response);
                    updateUI(bitcoinDataModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,Throwable e, JSONObject response){
                Log.e("Bitcoin","Error Message+ "+e.toString());
                Log.d("Bitcoin","SatusCode "+statusCode);
                Toast.makeText(MainActivity.this,"Request Failed",Toast.LENGTH_SHORT).show();

            }



        });


    }

    private void updateUI(BitcoinDataModel bitc){
        mPriceTextView.setText(bitc.getPrice());
    }


}
