package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class AMainActivity extends AppCompatActivity {
    private Button loginBtn, signupBtn;
    private EditText emailLoginEdt, passwordLoginEdt;
    public static int customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_activity_main);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        emailLoginEdt = findViewById(R.id.emailLoginEdt);
        passwordLoginEdt = findViewById(R.id.passwordLoginEdt);
        signupBtn.setOnClickListener(e->{
            Intent intent = new Intent(AMainActivity.this, SignUp.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(e->{
            login(emailLoginEdt.getText().toString(), passwordLoginEdt.getText().toString());
        });
    }
    private void login(String email, String password){
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLoginEdt.setError("Enter a valid email address");
            emailLoginEdt.requestFocus();
            return;
        }
        if (password.isEmpty() || password.length() < 6) {
            passwordLoginEdt.setError("Password must be at least 6 characters");
            passwordLoginEdt.requestFocus();
            return;
        }

        String url = "http://192.168.88.15/myPHP/Login";

        String str = url+"?email="+email+"&password="+password;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, str, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                if(response.getString("message").equals("customer")){
                                    // open customers activity
                                    customerId = response.getInt("id");
                                    if(response.getInt("cars")==0) {
                                        Intent intent = new Intent(AMainActivity.this, AddCar.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(AMainActivity.this, "Added before", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    // open owners activity
                                    Toast.makeText(AMainActivity.this, "Owner", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AMainActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AMainActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AMainActivity.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
