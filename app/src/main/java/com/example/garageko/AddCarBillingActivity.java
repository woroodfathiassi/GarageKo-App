package com.example.garageko;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCarBillingActivity extends AppCompatActivity {

    private ImageView carImageView;
    private TextView ownerNameTextView, carModelTextView;
    private EditText brakesCostEditText, wheelsCostEditText, bodyCostEditText, motorCostEditText, oilCostEditText, billingDateEditText, moreDetails;
    private Button applyButton;
    private Calendar calendar;
    private int request_id;

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
        moreDetails = findViewById(R.id.notesEdt);
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
        request_id = getIntent().getIntExtra("request_id", 0);

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

            SimpleDateFormat inputFormat = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            try {
                Date date = inputFormat.parse(billingDate);
                billingDate = outputFormat.format(date);
            } catch (ParseException e) {
            }

            // Simple validation
            if (brakesCost.isEmpty() || wheelsCost.isEmpty() || bodyCost.isEmpty() ||
                    motorCost.isEmpty() || oilCost.isEmpty() || billingDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            } else {
                addBilling(brakesCost, wheelsCost, bodyCost, motorCost, oilCost, notes, billingDate);
            }
        });
    }

    private void addBilling(String brakes, String wheels, String body, String motor, String oil, String notes, String billingDate){
        String url = "http://192.168.1.251/myPHP/AddBilling";

        String str = url+"?brakes="+brakes+"&wheels="+wheels+"&body="+body+"&motor="+motor+"&oil="+oil
                +"&notes="+notes+"&date="+billingDate+"&id="+request_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, str, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Toast.makeText(AddCarBillingActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                applyButton.setActivated(false);
                                finish();
                            } else {
                                Toast.makeText(AddCarBillingActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AddCarBillingActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddCarBillingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
