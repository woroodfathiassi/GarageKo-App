//package com.example.garageko;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class InvoiceActivity extends AppCompatActivity {
//
//    private RecyclerView recycler;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_invoice);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//
//        recycler = findViewById(R.id.invoice_recycler);
//
//        int[] imageIds = new int[Invoice.invoices.length];
//        String[] dates = new String[Invoice.invoices.length];
//        double[] prices = new double[Invoice.invoices.length];
//        String[] details = new String[Invoice.invoices.length];
//
//        for(int i = 0; i<dates.length;i++){
//            imageIds[i] = Invoice.invoices[i].getImageId();
//            dates[i] = Invoice.invoices[i].getDate();
//            prices[i] = Invoice.invoices[i].getPrice();
//            details[i] = Invoice.invoices[i].getDetail();
//        }
//
//        recycler.setLayoutManager(new LinearLayoutManager(this));
//        InvoiceAdapter adapter = new InvoiceAdapter(imageIds, dates, prices, details);
//        recycler.setAdapter(adapter);
//    }
//}


package com.example.garageko;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.garageko.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InvoiceActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private DrawerLayout drawerLayout;
    private TextView menuItem1;
    private TextView menuItem2;
    private ImageView menuIcon;

    private int[] imageIds;
    private int[] requestIds;
    private String[] dates;
    private double[] prices;
    private String[] details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice);

        initializeUIComponents();

        getPaymentDetails(1);

        // Set padding for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recycler.setLayoutManager(new LinearLayoutManager(this));


        // Menu icon click listener to open/close the drawer
        menuIcon.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(InvoiceActivity.this, ProfileActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MenuClick", "Mcenu Item 2 licked");
                System.out.println("Logout!"); // Prints to logcat
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set up navigation using the utility
        BottomNavigationHandler.setupBottomNavigation(this, bottomNavigationView);

    }

    private void initializeUIComponents() {
        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.imageView);

        recycler = findViewById(R.id.invoice_recycler);
    }

    private void getPaymentDetails(int carId) {
        String url = getString(R.string.GET_INVOICES_LIST);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        // Wrap the response into a JSON Array
                        JSONArray jsonArray = new JSONArray(response);

                        // Check if the response is empty
                        if (jsonArray.length() == 0) {
                            Toast.makeText(InvoiceActivity.this, "No payment details found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        imageIds = new int[jsonArray.length()];
                        dates = new String[jsonArray.length()];
                        prices = new double[jsonArray.length()];
                        details = new String[jsonArray.length()];
                        requestIds = new int[jsonArray.length()];
Log.d("woroodassi123", Arrays.toString(requestIds));

                        // Loop through the JSON array
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject paymentDetail = jsonArray.getJSONObject(i);

                            imageIds[i] = R.drawable.tools;
                            dates[i] = paymentDetail.getString("payment_date");
                            prices[i] = paymentDetail.getDouble("total_price");
                            details[i] = paymentDetail.getString("part_names");
                            requestIds[i] = paymentDetail.getInt("payment_id");

                        }
                        Log.d("woroodassi123", Arrays.toString(requestIds));
                        InvoiceAdapter adapter = new InvoiceAdapter(this, imageIds, dates, prices, details, requestIds);
                        adapter.setOnItemClickListener(position ->
                                Toast.makeText(getApplicationContext(), "Item " + position + " clicked!", Toast.LENGTH_SHORT).show()
                        );
                        recycler.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(InvoiceActivity.this, "Error parsing JSON", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    // Handle network or Volley error
                    Toast.makeText(InvoiceActivity.this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("car_id", String.valueOf(carId));
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
