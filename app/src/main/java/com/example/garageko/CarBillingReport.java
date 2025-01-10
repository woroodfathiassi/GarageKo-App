package com.example.garageko;

import android.os.Bundle;
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

public class CarBillingReport extends AppCompatActivity {

    // Declare variables for all views
    private TextView ownerNameTextView, carModelTextView;
    private TextView dateOfMaintainTextView;
    private TextView brakesPriceTextView;
    private TextView wheelsPriceTextView;
    private TextView bodyPriceTextView;
    private TextView motorPriceTextView;
    private TextView oilPriceTextView;
    private TextView totalPriceTextView;
    private int request_id;

    private EditText moreInfoTextArea;
    private ImageView carImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_billing_report); // Replace with your XML file name

        // Initialize views using findViewById

        ownerNameTextView = findViewById(R.id.ownerNameTxt);
        carModelTextView = findViewById(R.id.carModelTxt);

        dateOfMaintainTextView = findViewById(R.id.dateOfMaintainPriceTxt);
        brakesPriceTextView = findViewById(R.id.brakesPriceTxt);
        wheelsPriceTextView = findViewById(R.id.wheelsPriceTxt);
        bodyPriceTextView = findViewById(R.id.bodyPriceTxt);
        motorPriceTextView = findViewById(R.id.motorPriceTxt);
        oilPriceTextView = findViewById(R.id.oilPriceTxt);
        totalPriceTextView = findViewById(R.id.totalPriceTxt);

        moreInfoTextArea = findViewById(R.id.moreInfoTextArea);
        carImageView = findViewById(R.id.carImageView);

        // Set or modify text dynamically
        String ownerName = getIntent().getStringExtra("ownerName");
        String carModel = getIntent().getStringExtra("carModel");
        int carImage = getIntent().getIntExtra("carImage", R.drawable.hyundai_getz);
        request_id = getIntent().getIntExtra("request_id", 0);

        ownerNameTextView.setText(ownerName);
        carModelTextView.setText(carModel);
        carImageView.setImageResource(carImage);

        fillBillingReport();
    }

    private void fillBillingReport(){
        String url = getString(R.string.abd_url)+"BillingReport.php";

        String str = url+"?id="+request_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, str, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dateOfMaintainTextView.setText(response.getString("date"));
                            brakesPriceTextView.setText(response.getString("brakes"));
                            wheelsPriceTextView.setText(response.getString("wheels"));
                            bodyPriceTextView.setText(response.getString("body"));
                            motorPriceTextView.setText(response.getString("motor"));
                            oilPriceTextView.setText(response.getString("oil"));
                            moreInfoTextArea.setText(response.getString("notes"));
                            totalPriceTextView.setText(response.getString("total"));
                        } catch (Exception e) {
                            Toast.makeText(CarBillingReport.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CarBillingReport.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
