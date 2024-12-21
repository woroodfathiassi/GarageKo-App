package com.example.garageko;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManageCarMaintainActivity extends AppCompatActivity {

    // Define Spinners
    private Spinner brakesSpinner, wheelsSpinner, bodySpinner, motorSpinner, oilSpinner;

    // Predefined options for spinners
    private final String[] statusOptions = {"Select a status", "ok", "under maintenance", "maintained"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_car_maintain);

        // Initialize Spinners
        brakesSpinner = findViewById(R.id.brakesSpinner);
        wheelsSpinner = findViewById(R.id.wheelsSpinner);
        bodySpinner = findViewById(R.id.bodySpinner);
        motorSpinner = findViewById(R.id.motorSpinner);
        oilSpinner = findViewById(R.id.oilSpinner);

        // Set up ArrayAdapter for all spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach adapter to each Spinner
        brakesSpinner.setAdapter(adapter);
        wheelsSpinner.setAdapter(adapter);
        bodySpinner.setAdapter(adapter);
        motorSpinner.setAdapter(adapter);
        oilSpinner.setAdapter(adapter);

        // Initialize Apply Button
        Button applyButton = findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate spinner selections
                if (validateSpinners()) {
                    // Get selected items from spinners
                    String brakesStatus = brakesSpinner.getSelectedItem().toString();
                    String wheelsStatus = wheelsSpinner.getSelectedItem().toString();
                    String bodyStatus = bodySpinner.getSelectedItem().toString();
                    String motorStatus = motorSpinner.getSelectedItem().toString();
                    String oilStatus = oilSpinner.getSelectedItem().toString();

                    // Display selected status in a Toast message
                    String message = "Selected Status:\n" +
                            "Brakes: " + brakesStatus + "\n" +
                            "Wheels: " + wheelsStatus + "\n" +
                            "Body: " + bodyStatus + "\n" +
                            "Motor: " + motorStatus + "\n" +
                            "Oil: " + oilStatus;

                    Toast.makeText(ManageCarMaintainActivity.this, message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ManageCarMaintainActivity.this, "Please select valid statuses for all parts.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Validate Spinner Selections
    private boolean validateSpinners() {
        return brakesSpinner.getSelectedItemPosition() != 0 &&
                wheelsSpinner.getSelectedItemPosition() != 0 &&
                bodySpinner.getSelectedItemPosition() != 0 &&
                motorSpinner.getSelectedItemPosition() != 0 &&
                oilSpinner.getSelectedItemPosition() != 0;
    }
}
