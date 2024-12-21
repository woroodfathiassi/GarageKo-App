package com.example.garageko;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddCarBillingActivity extends AppCompatActivity {

    private ImageView carImageView;
    private TextView ownerNameTextView, carModelTextView;
    private EditText brakesCostEditText, wheelsCostEditText, bodyCostEditText, motorCostEditText, oilCostEditText, billingDateEditText, moreDetails;
    private Button applyButton;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_billing);

        // Initialize Views
        carImageView = findViewById(R.id.carImageView);
        ownerNameTextView = findViewById(R.id.ownerNameTextView);
        carModelTextView = findViewById(R.id.carModelTextView);
        brakesCostEditText = findViewById(R.id.brakesCostEditText);
        wheelsCostEditText = findViewById(R.id.wheelsCostEditText);
        bodyCostEditText = findViewById(R.id.bodyCostEditText);
        motorCostEditText = findViewById(R.id.motorCostEditText);
        oilCostEditText = findViewById(R.id.oilCostEditText);
        billingDateEditText = findViewById(R.id.billingDateEditText);
        moreDetails = findViewById(R.id.notesEditText);

        applyButton = findViewById(R.id.applyButton);

        // Calendar instance for date picker
        calendar = Calendar.getInstance();

        // DatePickerDialog for Billing Date
        billingDateEditText.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddCarBillingActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        billingDateEditText.setText(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // Get Data from Intent
        String ownerName = getIntent().getStringExtra("ownerName");
        String carModel = getIntent().getStringExtra("carModel");
        int carImage = getIntent().getIntExtra("carImage", R.drawable.hyundai_getz);

        // Set Data to Views
        ownerNameTextView.setText(ownerName);
        carModelTextView.setText(carModel);
        carImageView.setImageResource(carImage);

        // Apply Button Logic
        applyButton.setOnClickListener(v -> {
            String brakesCost = brakesCostEditText.getText().toString();
            String wheelsCost = wheelsCostEditText.getText().toString();
            String bodyCost = bodyCostEditText.getText().toString();
            String motorCost = motorCostEditText.getText().toString();
            String oilCost = oilCostEditText.getText().toString();
            String notes = moreDetails.getText().toString();

            String billingDate = billingDateEditText.getText().toString();

            // Simple validation
            if (brakesCost.isEmpty() || wheelsCost.isEmpty() || bodyCost.isEmpty() ||
                    motorCost.isEmpty() || oilCost.isEmpty() || billingDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            } else {
                // Handle saving data here (e.g., save to database, send to server, etc.)
                Toast.makeText(this, "Billing applied successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
