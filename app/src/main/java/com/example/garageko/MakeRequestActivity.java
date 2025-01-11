package com.example.garageko;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MakeRequestActivity extends AppCompatActivity {
    private String model, brand, number;
    private TextView brandTxt, modelTxt, carNumberTxt, menuItem1, menuItem2;
    private Button requestBtn;
    private DrawerLayout drawerLayout;
    private ImageButton backBtn, menuBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_request);

        brandTxt = findViewById(R.id.brandTxt);
        modelTxt = findViewById(R.id.modelTxt);
        carNumberTxt = findViewById(R.id.carNumberTxt);
        requestBtn = findViewById(R.id.requestBtn);
        backBtn = findViewById(R.id.backBtn2);
        backBtn.setOnClickListener(e->finish());

        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawerLayout);
        menuBtn = findViewById(R.id.menuBtn);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });

        // Set click listeners
        menuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeRequestActivity.this, ProfileActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MenuClick", "Menu Item 2 licked");
                System.out.println("Logout!"); // Prints to logcat
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        int customerId = sharedPreferences.getInt("customerId", 0);

        getCarsInfo(customerId);

        requestBtn.setOnClickListener(e->{
            makeRequest();
        });
    }

    private void makeRequest(){
        String url = getString(R.string.abd_url)+"makeRequest.php";

        String str = url+"?number="+number;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, str, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Toast.makeText(MakeRequestActivity.this, "The request made successfully", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(MakeRequestActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MakeRequestActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MakeRequestActivity.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void getCarsInfo(int customerId){
        String url = getString(R.string.abd_url)+"carsInfo.php";

        String str = url+"?id="+customerId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, str, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                model = response.getString("name");
                                brand = response.getString("brand");
                                number = response.getString("number");
                                brandTxt.setText("Car's Brand: "+brand);
                                modelTxt.setText("Car's Model: "+model);
                                carNumberTxt.setText("Car's Number: "+number);
                            } else{
                                Toast.makeText(MakeRequestActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MakeRequestActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MakeRequestActivity.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}