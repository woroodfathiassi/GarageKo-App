package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ManageCarStatusActivity extends AppCompatActivity {

    private ImageView carImageView;
    private TextView ownerNameTextView, carModelTextView;
    private Spinner statusSpinner;
    private Button applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_car_request);

        // Initialize views
        carImageView = findViewById(R.id.carImageView);
        ownerNameTextView = findViewById(R.id.ownerNameTextView);
        carModelTextView = findViewById(R.id.carModelTextView);
        statusSpinner = findViewById(R.id.statusSpinner);
        applyButton = findViewById(R.id.applyButton);

        // Populate Spinner
        String[] statuses = {"Pending", "Employee Sent To The Customer", "Delivered"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        // Get data from Intent
        String ownerName = getIntent().getStringExtra("ownerName");
        String carModel = getIntent().getStringExtra("carModel");
        int carImage = getIntent().getIntExtra("carImage", R.drawable.hyundai_getz);

        // Populate data
        ownerNameTextView.setText(ownerName != null ? ownerName : "Amer");
        carModelTextView.setText(carModel != null ? carModel : "Unknown Model");
        carImageView.setImageResource(carImage);

        // Handle Apply button click
        applyButton.setOnClickListener(v -> {
            // Get selected status
            String selectedStatus = statusSpinner.getSelectedItem().toString();

            // Create an Intent and send the selected status
            Intent intent = new Intent();
            intent.putExtra("selectedStatus", selectedStatus);
            setResult(RESULT_OK, intent);
            finish(); // Close the current activity
        });
    }
}
