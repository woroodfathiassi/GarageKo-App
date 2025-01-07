package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class ManageCarBillingActivity extends AppCompatActivity {

    private ImageView carImageView;
    private TextView ownerNameTextView, carModelTextView, menuItem1, menuItem2;
    private Button addBillingButton, billingReportButton;
    private ImageButton backBtn, menuBtn;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_car_billing);

        // Initialize views
        carImageView = findViewById(R.id.carImageView);
        ownerNameTextView = findViewById(R.id.ownerNameTextView);
        carModelTextView = findViewById(R.id.carModelTextView);
        addBillingButton = findViewById(R.id.addBillingButton);
        billingReportButton = findViewById(R.id.billingReportButton);

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
                Intent intent = new Intent(ManageCarBillingActivity.this, ProfileActivity.class);
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

        // Get data from Intent
        String ownerName = getIntent().getStringExtra("ownerName");
        String carModel = getIntent().getStringExtra("carModel");
        int request_id = getIntent().getIntExtra("request_id", 0);
        int carImage = getIntent().getIntExtra("carImage", R.drawable.hyundai_getz);

        ownerNameTextView.setText(ownerName);
        carModelTextView.setText(carModel);
        carImageView.setImageResource(carImage);
        backBtn = findViewById(R.id.backBtn2);
        backBtn.setOnClickListener(e->finish());

        addBillingButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManageCarBillingActivity.this, AddCarBillingActivity.class);
            intent.putExtra("ownerName", ownerName);
            intent.putExtra("carModel", carModel);
            intent.putExtra("carImage", carImage);
            intent.putExtra("request_id", request_id);
            startActivity(intent);
        });

        billingReportButton.setOnClickListener(v -> {
            // Billing Report logic here
        });
    }
}
