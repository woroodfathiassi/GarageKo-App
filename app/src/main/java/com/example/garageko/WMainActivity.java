package com.example.garageko;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//import android.widget.Toolbar;
//
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class WMainActivity extends AppCompatActivity {
//
//    private DrawerLayout drawerLayout;
//    private ActionBarDrawerToggle toggle;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wmain);
//
////        fetchData();
//
//
//        // Find the DrawerLayout and Toolbar
//        drawerLayout = findViewById(R.id.drawer_layout);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//
//        // Set up the Toolbar as the action bar
//        setSupportActionBar(toolbar);
//
//        // Set up the drawer toggle (hamburger icon)
//        toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        // Handle drawer item clicks
//        Button menuItem1 = findViewById(R.id.menu_item_1);
//        Button menuItem2 = findViewById(R.id.menu_item_2);
//        Button menuItem3 = findViewById(R.id.menu_item_3);
//
//        menuItem1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle Menu Item 1 click
//                Toast.makeText(WMainActivity.this, "Menu Item 1 clicked", Toast.LENGTH_SHORT).show();
//                drawerLayout.closeDrawer(GravityCompat.START); // Close the drawer
//            }
//        });
//
//        menuItem2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle Menu Item 2 click
//                Toast.makeText(WMainActivity.this, "Menu Item 2 clicked", Toast.LENGTH_SHORT).show();
//                drawerLayout.closeDrawer(GravityCompat.START);
//            }
//        });
//
//        menuItem3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle Menu Item 3 click
//                Toast.makeText(WMainActivity.this, "Menu Item 3 clicked", Toast.LENGTH_SHORT).show();
//                drawerLayout.closeDrawer(GravityCompat.START);
//            }
//        });
//    }
//    private void fetchData() {
//        String url = "http://10.0.2.2/myPHP";
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            if (response.getBoolean("success")) {
//                                JSONArray data = response.getJSONArray("data");
//                                for (int i = 0; i < data.length(); i++) {
//                                    JSONObject item = data.getJSONObject(i);
//                                    Log.d("worood", item.getString("name"));
//                                }
//                            } else {
//                                Toast.makeText(WMainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(WMainActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(WMainActivity.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(request);
//    }
//}

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

public class WMainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Button menuItem1, menuItem2, menuItem3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmain);

        // Initialize DrawerLayout and Toolbar
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ActionBarDrawerToggle to open/close the drawer when the hamburger icon is clicked
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        toggle.setDrawerIndicatorEnabled(false); // Disable the default hamburger icon
        toolbar.setNavigationIcon(R.drawable.custom_icon_with_size);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize the menu items inside the drawer
        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        menuItem3 = findViewById(R.id.menu_item_3);

        // Set click listeners to close the drawer on item click
        menuItem1.setOnClickListener(v -> {
            Toast.makeText(WMainActivity.this, "Menu Item 1 clicked", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);  // Close the drawer
        });

        menuItem2.setOnClickListener(v -> {
            Toast.makeText(WMainActivity.this, "Menu Item 2 clicked", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);  // Close the drawer
        });

        menuItem3.setOnClickListener(v -> {
            Toast.makeText(WMainActivity.this, "Menu Item 3 clicked", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);  // Close the drawer
        });
    }
}
