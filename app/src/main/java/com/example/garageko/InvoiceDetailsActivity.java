package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InvoiceDetailsActivity extends AppCompatActivity {

    int carReq =0;

    private RecyclerView recycler;
    private Button btnLeaveFeedback;
    private TextView Idate;
    private TextView Iprice;

    private DrawerLayout drawerLayout;
    private TextView menuItem1;
    private TextView menuItem2;
    private ImageView menuIcon;
    private ImageButton backButton;

    int[] imageIds;
    String[] services;
    String[] servicesPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice_details);

        initializeUIComponents();

        Intent intent = getIntent();
        int requestId = intent.getIntExtra("requestId", 0);
        String dates = intent.getStringExtra("dates");
        String prices = intent.getStringExtra("prices");
        Log.d("PpPpPpPpPpPp", prices+" "+dates);
        if (dates != null) {
            Idate.setText(dates);
        }
        Iprice.setText(prices);

        getPaymentDetails(1, requestId);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d("woroodassi123", requestId+"");

        btnLeaveFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvoiceDetailsActivity.this, CFeedbackActivity.class);
                intent.putExtra("requestId", carReq);
                startActivity(intent);
            }
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
                Intent intent = new Intent(InvoiceDetailsActivity.this, ProfileActivity.class);
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close this activity and return to the previous one
                finish();
            }
        });
    }

    private void initializeUIComponents() {
        recycler = findViewById(R.id.service_recycler);
        btnLeaveFeedback = findViewById(R.id.leaveFeedback);

        Idate = findViewById(R.id.Idate);
        Iprice = findViewById(R.id.Iprice);

        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.imageView);

        backButton = findViewById(R.id.imageButtonBack);
    }

    private void getPaymentDetails(int carId, int paymentId) {
        String url = getString(R.string.GET_PAYMENT_DETAILS);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        // Wrap the response into a JSON Array
                        JSONArray jsonArray = new JSONArray(response);

                        // Check if the response is empty
                        if (jsonArray.length() == 0) {
                            Toast.makeText(InvoiceDetailsActivity.this, "No payment details found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int size = jsonArray.length();
                        imageIds = new int[size];
                        services = new String[size];
                        servicesPrice = new String[size];

                        // Loop through the JSON array
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject paymentDetail = jsonArray.getJSONObject(i);
                            String requestId = paymentDetail.getString("request_id");
                            Log.d("asasasasasasasas", "Request ID: " + requestId);
                            carReq = Integer.parseInt(requestId);
                            imageIds[i] = R.drawable.tools;
                            services[i] = paymentDetail.getString("part_name");
                            servicesPrice[i] = paymentDetail.getString("price");
                        }
                        ServiceAdapter adapter = new ServiceAdapter(imageIds, services, servicesPrice);
                        recycler.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(InvoiceDetailsActivity.this, "Error parsing JSON", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    // Handle network or Volley error
                    Toast.makeText(InvoiceDetailsActivity.this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("car_id", String.valueOf(carId));
                params.put("payment_id", String.valueOf(paymentId));
                return params;
            }
        };
        queue.add(stringRequest);
    }
}