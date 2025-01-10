//package com.example.garageko;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.GridView;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.GravityCompat;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//public class TrackMyCarActivity extends AppCompatActivity {
//
//    RecyclerView repairStatusRV;
//    ArrayList<RepairStatusModel> repairList;
//    RepairStatusAdapter adapter;
//
//    private DrawerLayout drawerLayout;
//    private TextView menuItem1;
//    private TextView menuItem2;
//    private ImageView menuIcon;
//
//    StaggeredGridLayoutManager layoutManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_track_my_car);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//
//        initializeUIComponents();
//        getData(3);
//
//        // Sample data
//
////        repairList.add(new RepairStatusModel("Worood Fathi Assi and my name is worood assis Car Received", true, false, false));
////        repairList.add(new RepairStatusModel("Brake System Maintenance", false, true, false));
////        repairList.add(new RepairStatusModel("Cooling System Maintenance", false, true, true));
////        repairList.add(new RepairStatusModel("Suspension Repairs", true, false, false));
////        repairList.add(new RepairStatusModel("Air Filter Replacement", false, false, true));
////        repairList.add(new RepairStatusModel("Transmission Repair", false, true, false));
////        repairList.add(new RepairStatusModel("Oil Change", true, false, false));
//
//        // Set up Adapter
//
//        repairStatusRV.setLayoutManager(layoutManager);
//
//
//        // Menu icon click listener to open/close the drawer
//        menuIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
//                    drawerLayout.closeDrawer(GravityCompat.END);
//                } else {
//                    drawerLayout.openDrawer(GravityCompat.END);
//                }
//            }
//        });
//
//        // Set click listeners
//        menuItem1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TrackMyCarActivity.this, ProfileActivity.class);
//                startActivity(intent);
//                drawerLayout.closeDrawer(GravityCompat.END);
//            }
//        });
//
//        menuItem2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("MenuClick", "Mcenu Item 2 licked");
//                System.out.println("Logout!"); // Prints to logcat
//                drawerLayout.closeDrawer(GravityCompat.END);
//            }
//        });
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//
//        // Set up navigation using the utility
//        BottomNavigationHandler.setupBottomNavigation(this, bottomNavigationView);
//
//    }
//
//    private void initializeUIComponents() {
//        repairStatusRV = findViewById(R.id.idRVRepairStatus);
//
//        menuItem1 = findViewById(R.id.menu_item_1);
//        menuItem2 = findViewById(R.id.menu_item_2);
//        drawerLayout = findViewById(R.id.drawer_layout);
//        menuIcon = findViewById(R.id.imageView);
//
//    }
//
//    private void getData(int requestId) {
//        String url = getString(R.string.GET_MAINTENANCE_DETAILS);
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                response -> {
//                    try {
//                        // Parse the response into a JSON array
//                        JSONArray jsonArray = new JSONArray(response);
//
//                        if (jsonArray.length() == 0) {
//                            Toast.makeText(this, "No maintenance details found", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        repairList = new ArrayList<>();
//                        String[] partNames = new String[jsonArray.length()];
//                        String[] statuses = new String[jsonArray.length()];
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject maintenanceDetail = jsonArray.getJSONObject(i);
//
//                            String str = maintenanceDetail.getString("status");
//                            if(str.equals("ok")){
//                                repairList.add(new RepairStatusModel(maintenanceDetail.getString("part_name"), false, true, false));
//                            }else if(str.equals("maintained")){
//                                repairList.add(new RepairStatusModel(maintenanceDetail.getString("part_name"), true, false, false));
//                            }else{
//                                repairList.add(new RepairStatusModel(maintenanceDetail.getString("part_name"), false, false, true));
//                            }
//
//
//
//                        }
//                        adapter = new RepairStatusAdapter(this, repairList);
//
//                        // Set up StaggeredGridLayoutManager
//                        layoutManager =
//                                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                        repairStatusRV.setAdapter(adapter);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_LONG).show();
//                    }
//                },
//                error -> {
//                    // Handle network or Volley error
//                    Toast.makeText(this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("request_id", String.valueOf(requestId));
//                return params;
//            }
//        };
//
//        queue.add(stringRequest);
//    }
//
//}


package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackMyCarActivity extends AppCompatActivity {

    // UI Components
    private RecyclerView repairStatusRV;
    private DrawerLayout drawerLayout;
    private TextView menuItem1, menuItem2;
    private ImageView menuIcon;
    private BottomNavigationView bottomNavigationView;

    // Data
    private ArrayList<RepairStatusModel> repairList;
    private RepairStatusAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_my_car);

        // Set up edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        initializeUIComponents();

        // Fetch repair data
        repairList = new ArrayList<>();
        adapter = new RepairStatusAdapter(this, repairList);
        repairStatusRV.setAdapter(adapter);
        repairStatusRV.setLayoutManager(layoutManager);

        getData(3);

        // Set up menu actions
        setupMenu();

        // Bottom navigation
        BottomNavigationHandler.setupBottomNavigation(this, bottomNavigationView);
    }

    private void initializeUIComponents() {
        repairStatusRV = findViewById(R.id.idRVRepairStatus);
        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.imageView);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    private void setupMenu() {
        // Open/Close drawer
        menuIcon.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        // Menu item actions
        menuItem1.setOnClickListener(v -> {
            Intent intent = new Intent(TrackMyCarActivity.this, ProfileActivity.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.END);
        });

        menuItem2.setOnClickListener(v -> {
            Log.d("MenuClick", "Menu Item 2 clicked");
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.END);
        });
    }

    private void getData(int requestId) {
        String url = getString(R.string.GET_MAINTENANCE_DETAILS);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() == 0) {
                            Toast.makeText(this, "No maintenance details found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        repairList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject maintenanceDetail = jsonArray.getJSONObject(i);

                            String partName = maintenanceDetail.getString("part_name");
                            String status = maintenanceDetail.getString("status");

                            repairList.add(new RepairStatusModel(
                                    partName,
                                    "maintained".equals(status),
                                    "ok".equals(status),
                                    !("ok".equals(status) || "maintained".equals(status))
                            ));
                        }

                        adapter.notifyDataSetChanged(); // Update UI
                    } catch (JSONException e) {
                        Log.e("JSONError", "Error parsing JSON", e);
                        Toast.makeText(this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("VolleyError", "Network error", error);
                    Toast.makeText(this, "Failed to fetch data. Check your connection.", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("request_id", String.valueOf(requestId));
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
