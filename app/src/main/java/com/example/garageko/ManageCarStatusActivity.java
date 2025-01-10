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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ManageCarStatusActivity extends AppCompatActivity {
    private ImageView backIcon, carImageView;
    private TextView ownerNameTextView, carModelTextView;
    private Spinner statusSpinner;
    private Button applyButton;
    private Car car;
GarageOwnerRequestsManageScreen Gow = new GarageOwnerRequestsManageScreen();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_car_request);

        // Initialize views
        backIcon = findViewById(R.id.backIcon);
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

        // Restore state if available
        if (savedInstanceState != null) {
            car = (Car) savedInstanceState.getSerializable("carData");
            int selectedPosition = savedInstanceState.getInt("selectedStatus", 0);
            statusSpinner.setSelection(selectedPosition);
        } else {
            // Get data from Intent
            car = (Car) getIntent().getSerializableExtra("carData");
        }

        if (car != null) {
            ownerNameTextView.setText(car.getOwnerName());
            carModelTextView.setText(car.getCarModel());
            carImageView.setImageResource(car.getCarImage());
            Log.d("ManageCarStatus", "Owner: " + car.getOwnerName());
            Log.d("ManageCarStatus", "Model: " + car.getCarModel());
        } else {
            Log.e("ManageCarStatus", "Car object is null!");
        }

        backIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ManageCarStatusActivity.this, GarageOwnerRequestsManageScreen.class);
            startActivity(intent);
            finish();
        });

        // Handle Apply button click
        applyButton.setOnClickListener(v -> {
            String selectedStatus = statusSpinner.getSelectedItem().toString();
            int carId = car.getCarId(); // Assuming Car object has an ID

            String statusForDatabase = "";
            switch (selectedStatus) {
                case "Employee Sent To The Customer":
                    statusForDatabase = "employee_sent_to_customer";
                    break;
                case "Delivered":
                    statusForDatabase = "approved";
                    break;
                default:
                    statusForDatabase = "pending";
                    break;
            }

            sendStatusUpdateToServer(carId, statusForDatabase);
        });
    }

    // Save state during configuration changes or activity destruction
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save car object and selected spinner position
        outState.putSerializable("carData", car);
        outState.putInt("selectedStatus", statusSpinner.getSelectedItemPosition());
    }

    // Restore state after activity recreation
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        car = (Car) savedInstanceState.getSerializable("carData");
        int selectedPosition = savedInstanceState.getInt("selectedStatus", 0);

        if (car != null) {
            ownerNameTextView.setText(car.getOwnerName());
            carModelTextView.setText(car.getCarModel());
            carImageView.setImageResource(car.getCarImage());
        }

        statusSpinner.setSelection(selectedPosition);
    }

    private void sendStatusUpdateToServer(int carId, String status) {
        String url = "http://10.0.2.2/api/changestatusofrequest.php"; // Replace with your server URL

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("car_id", carId);
            requestBody.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            Toast.makeText(this, "Status updated successfully!", Toast.LENGTH_SHORT).show();
                                Gow.fetchDataFromServer();
                        } else {
                            String error = response.getString("error");
                            Toast.makeText(this, "Failed to update status: " + error, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
