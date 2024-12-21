package com.example.garageko;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CarBillingReport extends AppCompatActivity {

    // Declare variables for all views
    private TextView headerTextView, ownerNameTextView, carModelTextView, maintenanceHeaderTextView;
    private TextView dateOfMaintainTextView, dateOfMaintainPriceTextView;
    private TextView handWorkingTextView, handWorkingPriceTextView;
    private TextView brakesTextView, brakesPriceTextView;
    private TextView wheelsTextView, wheelsPriceTextView;
    private TextView bodyTextView, bodyPriceTextView;
    private TextView motorTextView, motorPriceTextView;
    private TextView oilTextView, oilPriceTextView;
    private TextView otherTextView, otherPriceTextView;
    private TextView totalPriceLabel, totalPriceTextView;

    private EditText moreInfoTextArea;
    private ImageView carImageView;
    private Button addBillingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_billing_report); // Replace with your XML file name

        // Initialize views using findViewById

        ownerNameTextView = findViewById(R.id.ownerNameTextView);
        carModelTextView = findViewById(R.id.carModelTextView);
        maintenanceHeaderTextView = findViewById(R.id.maintenanceHeaderTextView);

        dateOfMaintainTextView = findViewById(R.id.dateOfMaintainTextView);
        dateOfMaintainPriceTextView = findViewById(R.id.dateOfMaintainPriceTextView);

        handWorkingTextView = findViewById(R.id.handWorkingTextView);
        handWorkingPriceTextView = findViewById(R.id.handWorkingPriceTextView);

        brakesTextView = findViewById(R.id.brakesTextView);
        brakesPriceTextView = findViewById(R.id.brakesPriceTextView);

        wheelsTextView = findViewById(R.id.wheelsTextView);
        wheelsPriceTextView = findViewById(R.id.wheelsPriceTextView);

        bodyTextView = findViewById(R.id.bodyTextView);
        bodyPriceTextView = findViewById(R.id.bodyPriceTextView);

        motorTextView = findViewById(R.id.motorTextView);
        motorPriceTextView = findViewById(R.id.motorPriceTextView);

        oilTextView = findViewById(R.id.oilTextView);
        oilPriceTextView = findViewById(R.id.oilPriceTextView);

        otherTextView = findViewById(R.id.otherTextView);
        otherPriceTextView = findViewById(R.id.otherPriceTextView);

        totalPriceLabel = findViewById(R.id.totalPriceLabel);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);

        moreInfoTextArea = findViewById(R.id.moreInfoTextArea);
        carImageView = findViewById(R.id.carImageView);
        addBillingButton = findViewById(R.id.addBillingButton);

        // Set or modify text dynamically
        ownerNameTextView.setText("John Doe");
        carModelTextView.setText("Toyota Corolla 2022");
        dateOfMaintainPriceTextView.setText("$150");
        brakesPriceTextView.setText("$120");

        // Update total price dynamically
        String totalPrice = "$270";
        totalPriceTextView.setText(totalPrice);

        // Add more information to EditText
        moreInfoTextArea.setText("Car was serviced on 12/06/2024.");

        // Change header text color programmatically
//        headerTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

        // Set image dynamically (replace 'new_image' with your drawable)
        carImageView.setImageResource(R.drawable.hyundai_getz);

        // Button Click Listener Example
        addBillingButton.setOnClickListener(view -> {
            // Example: Update total price dynamically when the button is clicked
            totalPriceTextView.setText("$300");
            moreInfoTextArea.setText("Billing updated successfully!");
        });
    }
}
