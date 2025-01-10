package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ManageCarMaintainActivity extends AppCompatActivity {

    private Spinner brakeSpinner, wheelSpinner, bodySpinner, motorSpinner, oilSpinner;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_car_maintain);

        // Bind UI elements
        ImageView carImageView = findViewById(R.id.carImageView);
        TextView ownerNameTextView = findViewById(R.id.ownerNameTextView);
        TextView carModelTextView = findViewById(R.id.carModelTextView);
        ImageView backIcon = findViewById(R.id.backIcon);
        brakeSpinner = findViewById(R.id.brakesSpinner);
        wheelSpinner = findViewById(R.id.wheelsSpinner);
        bodySpinner = findViewById(R.id.bodySpinner);
        motorSpinner = findViewById(R.id.motorSpinner);
        oilSpinner = findViewById(R.id.oilSpinner);
        Button applyButton = findViewById(R.id.applyButton);
        Button addToBillingButton = findViewById(R.id.addToBilling);

        // Receive car data from the intent
        Car car = (Car) getIntent().getSerializableExtra("carData");
        if (car != null) {
            ownerNameTextView.setText(car.getOwnerName());
            carModelTextView.setText(car.getCarModel());
            carImageView.setImageResource(car.getCarImage());

            // Fetch existing statuses for the car
            fetchStatusesFromServer(car.getCarId());
        }

        // Spinner setup
        String[] maintenanceStatuses = {"Select a status", "ok", "under maintenance", "maintained"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maintenanceStatuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brakeSpinner.setAdapter(adapter);
        wheelSpinner.setAdapter(adapter);
        bodySpinner.setAdapter(adapter);
        motorSpinner.setAdapter(adapter);
        oilSpinner.setAdapter(adapter);

        // Back button functionality
        backIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ManageCarMaintainActivity.this, GarageOwnerCarsManageScreen.class);
            startActivity(intent);
            finish();
        });

        // Apply button functionality
        applyButton.setOnClickListener(v -> {
            HashMap<String, String> partStatuses = new HashMap<>();
            partStatuses.put("Brakes", brakeSpinner.getSelectedItem().toString());
            partStatuses.put("Wheels", wheelSpinner.getSelectedItem().toString());
            partStatuses.put("Body", bodySpinner.getSelectedItem().toString());
            partStatuses.put("Motor", motorSpinner.getSelectedItem().toString());
            partStatuses.put("Oil", oilSpinner.getSelectedItem().toString());

            JSONObject requestData = new JSONObject();
            try {
                requestData.put("car_id", car.getCarId());
                requestData.put("part_statuses", new JSONObject(partStatuses));
            } catch (JSONException e) {
                Log.e("ManageCarMaintain", "JSON Error: " + e.getMessage());
                return;
            }

            sendStatusUpdateToServer(requestData);
        });

        // Add to billing functionality
        addToBillingButton.setOnClickListener(v -> {
            JSONObject requestData = new JSONObject();
            try {
                requestData.put("car_id", car.getCarId());
            } catch (JSONException e) {
                Log.e("ManageCarMaintain", "JSON Error: " + e.getMessage());
                return;
            }

            addToBilling(requestData);
        });
    }

    private void fetchStatusesFromServer(int carId) {
        String url = "http://10.0.2.2/api/retriveparts.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("car_id", carId);
        } catch (JSONException e) {
            Log.e("ManageCarMaintain", "JSON Error: " + e.getMessage());
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestData,
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            JSONObject partStatuses = response.getJSONObject("part_statuses");
                            updateSpinnersWithStatuses(partStatuses);
                        } else {
                            Toast.makeText(this, "Error: " + response.optString("error"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "Response Parsing Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Server Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        queue.add(jsonObjectRequest);
    }

    private void updateSpinnersWithStatuses(JSONObject partStatuses) {
        try {
            if (partStatuses.has("Brakes")) {
                brakeSpinner.setSelection(adapter.getPosition(partStatuses.getString("Brakes")));
            }
            if (partStatuses.has("Wheels")) {
                wheelSpinner.setSelection(adapter.getPosition(partStatuses.getString("Wheels")));
            }
            if (partStatuses.has("Body")) {
                bodySpinner.setSelection(adapter.getPosition(partStatuses.getString("Body")));
            }
            if (partStatuses.has("Motor")) {
                motorSpinner.setSelection(adapter.getPosition(partStatuses.getString("Motor")));
            }
            if (partStatuses.has("Oil")) {
                oilSpinner.setSelection(adapter.getPosition(partStatuses.getString("Oil")));
            }
        } catch (JSONException e) {
            Log.e("ManageCarMaintain", "JSON Parsing Error: " + e.getMessage());
        }
    }

    private void sendStatusUpdateToServer(JSONObject requestData) {
        String url = "http://10.0.2.2/api/modifypartstatus.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestData,
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            Toast.makeText(this, "Statuses updated successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error: " + response.optString("error"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "Response Parsing Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Server Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        queue.add(jsonObjectRequest);
    }

    private void addToBilling(JSONObject requestData) {
        String url = "http://10.0.2.2/api/addtobilling.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestData,
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            Toast.makeText(this, "Car added to billing successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ManageCarMaintainActivity.this, GarageOwnerCarsManageScreen.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Error: " + response.optString("error"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "Response Parsing Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Server Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        queue.add(jsonObjectRequest);
    }
}
