package com.example.garageko;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ManageCarBillingActivity extends AppCompatActivity {

    private ImageView carImageView;
    private TextView ownerNameTextView, carModelTextView;
    private Button addBillingButton, billingReportButton;

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

        // Get data from Intent
        String ownerName = getIntent().getStringExtra("ownerName");
        String carModel = getIntent().getStringExtra("carModel");
        int carImage = getIntent().getIntExtra("carImage", R.drawable.hyundai_getz);

        // Populate data
//        ownerNameTextView.setText(ownerName);
        ownerNameTextView.setText("Amer");
        carModelTextView.setText(carModel);
        carImageView.setImageResource(carImage);

        // Handle button clicks
        addBillingButton.setOnClickListener(v -> {
            // Add Billing logic here
        });

        billingReportButton.setOnClickListener(v -> {
            // Billing Report logic here
        });
    }
}
