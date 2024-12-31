package com.example.garageko;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class WMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmain);

        // Create a request queue using Volley
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        // Define the URL for your PHP script (use 10.0.2.2 for the Android Emulator)
//        String url = "http://10.0.2.2:3000/mobile.php";  // For Emulator
//        // If using a real device, replace the IP address with your local machine IP
//        // String url = "http://192.168.x.x/yourproject/fetch_data.php"; // For Real Device
//
//        // Create the GET request
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Handle the successful response
//                        try {
//                            // Parse the JSON response
//                            JSONArray jsonArray = new JSONArray(response);
//
//                            // Log or use the data (Here, just logging for demonstration)
//                            Log.d("Response", jsonArray.toString());
//
//                            // Show a toast message or update the UI
//                            Toast.makeText(WMainActivity.this, "Data received!", Toast.LENGTH_SHORT).show();
//
//                        } catch (JSONException e) {
//                            // Handle JSON parsing error
//                            e.printStackTrace();
//                            Toast.makeText(WMainActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // Handle error response
//                Log.e("Error", error.toString());
//                Toast.makeText(WMainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Add the request to the request queue
//        queue.add(stringRequest);
//    }
    }
}
