package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GarageOwnerBillingManageScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList;
    private ImageButton backBtn, menuBtn;
    private TextView menuItem1;
    private TextView menuItem2;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_owner_billing_manage_screen);
        try {
            // Initialize RecyclerView
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            backBtn = findViewById(R.id.backBtn2);
            menuBtn = findViewById(R.id.menuBtn);

            backBtn.setOnClickListener(e->finish());

            menuItem1 = findViewById(R.id.menu_item_1);
            menuItem2 = findViewById(R.id.menu_item_2);
            drawerLayout = findViewById(R.id.drawerLayout);

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
                    Intent intent = new Intent(GarageOwnerBillingManageScreen.this, ProfileActivity.class);
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
            // Populate data
            carList = new ArrayList<>();
            fillRequests();

            // Setup bottom bar
            setupBottomBar();
        } catch (Exception e) {
            Log.e("GarageOwnerHomeScreen", "Error initializing RecyclerView", e);
        }
        ImageButton menuButton = findViewById(R.id.menuButton);
//        menuButton.setOnClickListener(v -> {
//            PopupMenu popupMenu = new PopupMenu(GarageOwnerBillingManageScreen.this, v);
//            popupMenu.getMenuInflater().inflate(R.menu.ic_menu, popupMenu.getMenu());
//            popupMenu.show();
//        });

    }

    private void fillRequests(){
        String url = "http://192.168.1.251/myPHP/CarsRequests";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray data = response.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject car = data.getJSONObject(i);
                                    String name = car.getString("name");
                                    String brand = car.getString("brand");
                                    int request_id = car.getInt("request_id");
                                    carList.add(new Car(name, brand, R.drawable.bmw_x5, request_id));
                                }
                                carAdapter = new CarAdapter(carList,"Manage");
                                recyclerView.setAdapter(carAdapter);
                            } else {
                                String message = response.getString("message");
                                Toast.makeText(GarageOwnerBillingManageScreen.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(GarageOwnerBillingManageScreen.this, "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GarageOwnerBillingManageScreen.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void setupBottomBar() {
        try {
            findViewById(R.id.homeIcon).setOnClickListener(v -> {
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, GarageOwnerHomeScreen.class);
                startActivity(intent);
            });

            findViewById(R.id.manageCarsIcon).setOnClickListener(v -> {
                        Intent intent = new Intent(this, GarageOwnerCarsManageScreen.class);
                        startActivity(intent);
                    }
            );

            findViewById(R.id.manageRequestsIcon).setOnClickListener(v -> {
                Intent intent = new Intent(this, GarageOwnerRequestsManageScreen.class);
                startActivity(intent);
            });

            findViewById(R.id.billingIcon).setOnClickListener(v -> {
                Toast.makeText(this, "You are already on Billings screen", Toast.LENGTH_SHORT).show();

            });

            findViewById(R.id.ratesIcon).setOnClickListener(v -> {
                Intent intent = new Intent(this, CustomerRateActivity.class);
                startActivity(intent);
            });
        } catch (Exception e) {
            Log.e("GarageOwnerCarsManageScreen", "Error setting up bottom bar", e);
        }
    }
}
