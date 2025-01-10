package com.example.garageko;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CFeedbackActivity extends AppCompatActivity {

    private boolean IsRated = false;

    private RatingBar ratingBar;
    private EditText feedbackEditText;
    private Button submitButton;

    private DrawerLayout drawerLayout;
    private TextView menuItem1;
    private TextView menuItem2;
    private ImageView menuIcon;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cfeedback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int requestId = intent.getIntExtra("requestId", 0);
        Log.d("woroodassi123", requestId+"");
        initializeUIComponents();

        getFeedback(requestId);

        submitButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String feedback = feedbackEditText.getText().toString();

            if (feedback.isEmpty()) {
                Toast.makeText(CFeedbackActivity.this, "Please provide your feedback", Toast.LENGTH_SHORT).show();
            } else {
                if (IsRated) {
                    setFeedback(requestId, (int) rating, feedback);
                } else {
                    insertFeedback(requestId, (int) rating, feedback);
                }
            }
        });

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });

        menuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CFeedbackActivity.this, ProfileActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MenuClick", "Mcenu Item 2 licked");
                System.out.println("Logout!"); // Prints to logcat
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close this activity and return to the previous one
                finish();
            }
        });
    }

    private void initializeUIComponents() {
        ratingBar = findViewById(R.id.ratingBar);
        feedbackEditText = findViewById(R.id.et_feedback);
        submitButton = findViewById(R.id.btn_submit_feedback);

        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.imageView);

        backButton = findViewById(R.id.imageButtonBack);
    }

    private void getFeedback(int requestId){
        String url = getString(R.string.GET_CFEEDBACK);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject userJson = new JSONObject(response);
                        if (userJson != null) {
                            int rating = userJson.getInt("rating") | 0;
                            String notes = userJson.optString("notes");
                            ratingBar.setRating(rating);
                            feedbackEditText.setText(notes);
                            IsRated = true;
                        } else {
                            ratingBar.setRating(0);
                            feedbackEditText.setText("");
                            IsRated = false;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CFeedbackActivity.this, "Error parsing JSON", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(CFeedbackActivity.this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("request_id", String.valueOf(requestId));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void setFeedback(int requestId, int rating, String note) {
        String url = getString(R.string.SET_CFEEDBACK);

        // Initialize Volley Request Queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create StringRequest for POST request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Handle success response
                    Toast.makeText(CFeedbackActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle error response
                    String errorMsg = (error.networkResponse != null && error.networkResponse.data != null)
                            ? new String(error.networkResponse.data)
                            : (error.getMessage() != null ? error.getMessage() : "Unknown error occurred");
                    Toast.makeText(CFeedbackActivity.this, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent in the request body
                Map<String, String> params = new HashMap<>();
                Log.d("wwwww", requestId+" ");
                params.put("request_id", String.valueOf(requestId));
                params.put("rating", String.valueOf(rating));
                params.put("notes", note);
                return params;
            }

            @Override
            public String getBodyContentType() {
                // Specify content type
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        // Add the request to the queue
        queue.add(stringRequest);
    }

    private void insertFeedback(int requestId, int rating, String note) {
        String url = getString(R.string.INSERT_NEW_RATING);

        // Initialize Volley Request Queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create StringRequest for POST request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Log response to check the success message
                    Log.d("FeedbackResponse", "Response: " + response);

                    // Handle success response
                    Toast.makeText(CFeedbackActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Log the error message
                    String errorMsg = "Unknown error occurred";
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMsg = new String(error.networkResponse.data);
                    } else if (error.getMessage() != null) {
                        errorMsg = error.getMessage();
                    }

                    // Log the error response
                    Log.e("VolleyError", "Error: " + errorMsg);

                    // Handle error response
                    Toast.makeText(CFeedbackActivity.this, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to be sent in the request body
                Map<String, String> params = new HashMap<>();
                Log.d("wwwww", "requestId: " + requestId + ", rating: " + rating + ", note: " + note);

                params.put("request_id", String.valueOf(requestId));
                params.put("rating", String.valueOf(rating));
                params.put("notes", note);
                return params;
            }

            @Override
            public String getBodyContentType() {
                // Specify content type
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        // Add the request to the queue
        queue.add(stringRequest);
    }


}