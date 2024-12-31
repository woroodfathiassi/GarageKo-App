package com.example.garageko;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class AddCar extends AppCompatActivity {
    private EditText numberEdt, modelEdt, brandEdt;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_car);

        numberEdt = findViewById(R.id.numberEdt);
        brandEdt = findViewById(R.id.brandEdt);
        modelEdt = findViewById(R.id.modelEdt);
        addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(e->{
            addCar(brandEdt.getText().toString(), modelEdt.getText().toString(), numberEdt.getText().toString());
        });

    }

    private void addCar(String brand, String model, String number){
        if (brand.isEmpty()) {
            brandEdt.setError("Enter the brand");
            brandEdt.requestFocus();
            return;
        }
        if (model.isEmpty()) {
            modelEdt.setError("Enter the model");
            modelEdt.requestFocus();
            return;
        }
        if (number.isEmpty()) {
            numberEdt.setError("Enter the car number");
            numberEdt.requestFocus();
            return;
        }

        String url = "http://192.168.88.15/myPHP/AddCar";

        String str = url+"?brand="+brand+"&model="+model+"&number="+number+"&id="+AMainActivity.customerId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, str, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Toast.makeText(AddCar.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddCar.this, "The car number is added before", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AddCar.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddCar.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
