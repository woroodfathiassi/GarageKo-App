package com.example.garageko;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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


public class SignUp extends AppCompatActivity {
    private EditText nameEdt, passwordEdt, addressEdt, emailEdt, phoneEdt;
    private Button signUpButton;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        signUpButton = findViewById(R.id.signUpButton);
        nameEdt = findViewById(R.id.nameEdt);
        passwordEdt = findViewById(R.id.passwordEdt);
        addressEdt = findViewById(R.id.addressEdt);
        emailEdt = findViewById(R.id.emailEdt);
        phoneEdt = findViewById(R.id.phoneEdt);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(e->{
            finish();
        });

        signUpButton.setOnClickListener(e->{
            signUp(nameEdt.getText().toString(), emailEdt.getText().toString(), addressEdt.getText().toString(),
                    phoneEdt.getText().toString(), passwordEdt.getText().toString());
        });
    }

    private void signUp(String name, String email, String address, String phone, String password) {
        if (name.isEmpty()) {
            nameEdt.setError("Name is required");
            nameEdt.requestFocus();
            return;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdt.setError("Enter a valid email address");
            emailEdt.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordEdt.setError("Password must be at least 6 characters");
            passwordEdt.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            addressEdt.setError("Address is required");
            addressEdt.requestFocus();
            return;
        }

        if (phone.isEmpty() || phone.length() < 10) {
            phoneEdt.setError("Phone number must be at least 10 digits");
            phoneEdt.requestFocus();
            return;
        }

        String url = "http://192.168.1.251/myPHP/SignUp";

        String str = url+"?name="+name+"&email="+email+"&password="+password+"&phone="+phone+"&address="+address;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, str, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUp.this, "Email is registered before", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SignUp.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
